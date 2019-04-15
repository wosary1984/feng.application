package feng.app.config.security;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.stereotype.Service;

import feng.app.repository.privilege.entity.ApplicationPrivilegeAction;

@Service
public class MyAccessDecisionManager implements AccessDecisionManager {
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		HttpServletRequest request = ((FilterInvocation) object).getHttpRequest();
		String path, method;
		AntPathRequestMatcher matcher;
		
		if (new AntPathRequestMatcher("/my/session").matches(request) 
				|| new AntPathRequestMatcher("/").matches(request)
				|| new AntPathRequestMatcher("/**/swagger-ui.html").matches(request)
				|| new AntPathRequestMatcher("/webjars/**").matches(request)
				|| new AntPathRequestMatcher("/swagger-resources/**").matches(request)
				|| new AntPathRequestMatcher("/configuration/**").matches(request)
				|| new AntPathRequestMatcher("/v2/api-docs/**").matches(request)
				
				|| new AntPathRequestMatcher("/index.html").matches(request)
				|| new AntPathRequestMatcher("/treant/**").matches(request)
				|| new AntPathRequestMatcher("/img/**").matches(request)
				|| new AntPathRequestMatcher("/headshots/**").matches(request)
				|| new AntPathRequestMatcher("/Bootstrap-video-player-jQuery-plugin-master/**").matches(request)
				
				|| new AntPathRequestMatcher("/socket/**").matches(request)
				) {
			    
			return;
		}
		for (GrantedAuthority ga : authentication.getAuthorities()) {
			if (ga instanceof MyGrantedAuthority) {
				MyGrantedAuthority myga = (MyGrantedAuthority) ga;
				for (ApplicationPrivilegeAction a : myga.getActions()) {
					path = a.getActionpath();
					logger.debug("action path: "+path);
					method = a.getActionmethod();
					matcher = new AntPathRequestMatcher(path);
					if (matcher.matches(request)) {
						// 当权限表权限的method为ALL时表示拥有此路径的所有请求方式权利。
						if (method.equals(request.getMethod()) || "ALL".equals(method)) {
							return;
						}
					}
				}
			} else if (ga.getAuthority().equals("ROLE_ANONYMOUS")) {
				
			}
		}
		throw new AccessDeniedException("No Permission");
	}

	@Override
	public boolean supports(ConfigAttribute attribute) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return true;
	}

}
