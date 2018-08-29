package feng.app.config.security;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Locale;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.access.intercept.FilterSecurityInterceptor;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.security.web.csrf.CsrfFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.CorsUtils;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import feng.app.controller.BaseController;
import feng.app.controller.SessionController;
import feng.app.service.user.CustomUserService;

@EnableWebSecurity
public class ApiWebSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private CustomUserService loginUserDetailsService;

	@Autowired
	private CustomLoginHandler customLoginHandler;

	@Autowired
	private CustomLogoutHandler customLogoutHandler;

	@Autowired
	private CustomAccessDeniedHandler customAccessDeniedHandler;
	
	@Autowired
    private MyFilterSecurityInterceptor myFilterSecurityInterceptor;

	@Override
	protected void configure(final AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(loginUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
		// auth.inMemoryAuthentication().withUser("temporary").password("temporary").roles("ADMIN").and().withUser("user").password("userPass").roles("USER");
	}

	protected void configure(HttpSecurity http) throws Exception {
		http.cors();
				
		http.authorizeRequests()
		
			//below code doesn't work, because registered  FilterSecurityInterceptor will intercept all request URL
			//in MyAccessDecisionManager will released permitted URL
		
			// allow anonymous access access to Swagger docs
		    .antMatchers("/v2/api-docs", "/**/swagger-ui.html", "/webjars/**", "/swagger-resources/**", "/configuration/**").permitAll()
		    // allow anonymous access treant resource, icon resource
		    .antMatchers("/","/index.html","/resources/**","/treant/**","/icons/**").permitAll()
		    //allow anonymous check his session
        		.antMatchers("/my/session").permitAll()
			.antMatchers("/api/**").authenticated()
			.requestMatchers(CorsUtils::isPreFlightRequest).permitAll();

		http.formLogin();

		http.logout()
			.logoutRequestMatcher(new AntPathRequestMatcher("/api/session/logout"))
			// 登出前调用，可用于日志
			.addLogoutHandler(customLogoutHandler)
			// 登出后调用，用户信息已不存在	
			.logoutSuccessHandler(customLogoutHandler);

		http.exceptionHandling()
			// 已登入用户的权限错误
			.accessDeniedHandler(customAccessDeniedHandler)
			// 未登入用户的权限错误		
			.authenticationEntryPoint(customAccessDeniedHandler);

		http.csrf()
			// 登入API不启用CSFR检查
			.ignoringAntMatchers("/api/session/**");

		http.addFilterBefore(new AcceptHeaderLocaleFilter(), UsernamePasswordAuthenticationFilter.class);
		 
		// 替换原先的表单登入 Filter
		http.addFilterAt(customAuthenticationFilter(), UsernamePasswordAuthenticationFilter.class);
		
		// 绑定 CSRF TOKEN 到响应的 HEADER 上
		http.addFilterAfter(new CsrfTokenResponseHeaderBindingFilter(), CsrfFilter.class);
		
		//拦截所有的请求进行定制的授权验证
		http.addFilterBefore(myFilterSecurityInterceptor, FilterSecurityInterceptor.class);
	}
	
//	@Override
//    public void configure(WebSecurity webSecurity) throws Exception
//    {
//        webSecurity
//        .ignoring()
//        .antMatchers("/resources/**").anyRequest();
//    }
	

	@Bean
    public CorsConfigurationSource corsConfigurationSource() {
		logger.debug("register cors filter");
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "PATCH", "DELETE", "OPTIONS"));
        //configuration.setAllowedHeaders(Arrays.asList("authorization", "content-type", "x-auth-token"));
        configuration.setAllowCredentials(true);
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setExposedHeaders(Arrays.asList("X-CSRF-TOKEN"));
        
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }
	
	private CustomAuthenticationFilter customAuthenticationFilter() throws Exception {
		CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
		filter.setAuthenticationSuccessHandler(customLoginHandler);
		filter.setAuthenticationFailureHandler(customLoginHandler);
		filter.setAuthenticationManager(authenticationManager());
		filter.setFilterProcessesUrl("/api/session/login");
		return filter;
	}

	private static void responseText(HttpServletResponse response, String content) throws IOException {
		response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
		byte[] bytes = content.getBytes(StandardCharsets.UTF_8);
		response.setContentLength(bytes.length);
		response.getOutputStream().write(bytes);
		response.flushBuffer();
	}

	@Component
	public static class CustomAccessDeniedHandler extends BaseController
			implements AuthenticationEntryPoint, AccessDeniedHandler {
		// NoLogged Access Denied
		@Override
		public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException {
			logger.debug("anonymousUser access:{}, method:{},requested session id:{}",request.getRequestURI(),request.getMethod(),request.getRequestedSessionId());
			response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			responseText(response, errorMessage(BaseController.ACTION_ACCESS, HttpServletResponse.SC_UNAUTHORIZED,authException.getMessage()));
		}

		// Logged Access Denied
		@Override
		public void handle(HttpServletRequest request, HttpServletResponse response,
			AccessDeniedException accessDeniedException) throws IOException {
			logger.debug("{} access:{}, method:{}, requested session id:{}",request.getRemoteUser(),request.getRequestURI(),request.getMethod(), request.getRequestedSessionId());
			response.setStatus(HttpServletResponse.SC_FORBIDDEN);
			responseText(response, errorMessage(BaseController.ACTION_ACCESS, HttpServletResponse.SC_FORBIDDEN,accessDeniedException.getMessage()));
		}
	}

	@Component
	public static class CustomLoginHandler extends BaseController
			implements AuthenticationSuccessHandler, AuthenticationFailureHandler {
		// Login Success
		@Override
		public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
			logger.info("User login successfully, name={}, requested seesionid={}, current seesionid={}", authentication.getName(),request.getRequestedSessionId(),request.getSession(false).getId());
			responseText(response, objectResult(BaseController.ACTION_LOGIN, HttpServletResponse.SC_OK, SessionController.getJSON(authentication)));
		}

		// Login Failure
		@Override
		public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
			logger.info("Login failed: {}, requested seesionid={}, current seesionid={}", exception.getMessage(),request.getRequestedSessionId(),request.getSession(false).getId());
			responseText(response, errorMessage(BaseController.ACTION_LOGIN,HttpServletResponse.SC_OK,exception.getMessage()));
		}
	}

	@Component
	public static class CustomLogoutHandler extends BaseController implements LogoutHandler, LogoutSuccessHandler {
		
		// Before Logout
		@Override
		public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
			if(authentication != null) {
				logger.info("Before==>user={} logout, requested sessionid={}", authentication.getName(),request.getRequestedSessionId());
			}
			else {
				logger.info("requested sessionid={}, authentication is null",request.getRequestedSessionId());
			}
				
		}

		// After Logout
		@Override
		public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException {
			if(authentication !=null) {
				logger.info("After==>user={} logout,requested session id={}", authentication.getName(),request.getRequestedSessionId());
			}else {
				logger.info("requested sessionid={}, authentication is null",request.getRequestedSessionId());
			}
			responseText(response, objectResult(BaseController.ACTION_LOGOUT,HttpServletResponse.SC_OK,SessionController.getJSON(null)));
		}
	}
	
	private static class AcceptHeaderLocaleFilter implements Filter {
        private AcceptHeaderLocaleResolver localeResolver;

        private AcceptHeaderLocaleFilter() {
            localeResolver = new AcceptHeaderLocaleResolver();
            localeResolver.setDefaultLocale(Locale.US);
        }

        @Override
        public void init(FilterConfig filterConfig) {
        }

        @Override
        public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
            Locale locale = localeResolver.resolveLocale((HttpServletRequest) request);
            LocaleContextHolder.setLocale(locale);

            chain.doFilter(request, response);
        }

        @Override
        public void destroy() {
        }
    }

}
