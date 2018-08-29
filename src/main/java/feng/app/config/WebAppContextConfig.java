package feng.app.config;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.beanvalidation.MethodValidationPostProcessor;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


/**
 * Activates Web MVC and its @Controller classes via
 * RequestMappingHandlerMapping. Defines Spring beans for the application
 * context and triggers via @ComponentScan the search and the registration of
 * Beans. Beans are detected within @Configuration, @Component and @Controller
 * annotated classes.
 */
@Configuration
@EnableAutoConfiguration
public class WebAppContextConfig implements WebMvcConfigurer {
	
	//private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/").setViewName("forward:/index.html");

	}

	/***
	 * static resource handler
	 */
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/myres/**").addResourceLocations("classpath:/myres/");
		registry.addResourceHandler("/icons/**").addResourceLocations("classpath:/icons/");
		registry.addResourceHandler("/treant/**").addResourceLocations("classpath:/treant/");
		registry.addResourceHandler("/img/**").addResourceLocations("classpath:/img/");
		registry.addResourceHandler("/index.html").addResourceLocations("classpath:/");
		registry.addResourceHandler("/headshots/**").addResourceLocations("classpath:/treant/treant-js-master/examples/headshots/");

	}
	
//	@Override
//	public void addCorsMappings(CorsRegistry registry) {
//		logger.info("register cors");
//		registry.addMapping("/**")
//		.allowedOrigins("http://localhost:4200")
//		.allowedHeaders("*")
//		.allowedMethods("OPTIONS")
//	    .allowedMethods("HEAD")
//	    .allowedMethods("GET")
//	    .allowedMethods("PUT")
//	    .allowedMethods("POST")
//	    .allowedMethods("DELETE")
//		.allowCredentials(false).maxAge(3600);
//	}
	

//	@Bean
//	public FilterRegistrationBean<CorsFilter> corsFilter() {
//		
//		CorsConfiguration config = new CorsConfiguration();
//		config.setAllowCredentials(true);
//		// 设置你要允许的网站域名，如果全允许则设为 *
//		config.addAllowedOrigin("http://localhost:4200");
//		// 如果要限制 HEADER 或 METHOD 请自行更改
//		config.addAllowedHeader("*");
//		config.addAllowedMethod("*");
//		
//		logger.info("注册CORS过滤器");
//		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//		source.registerCorsConfiguration("/**", config);
//		FilterRegistrationBean<CorsFilter> bean = new FilterRegistrationBean<CorsFilter>(new CorsFilter(source));
//		// 这个顺序很重要哦，为避免麻烦请设置在最前
//		bean.setOrder(0);
//		return bean;
//	}

	@Bean
	public MethodValidationPostProcessor methodValidationPostProcessor() {
		return new MethodValidationPostProcessor();
	}
}
