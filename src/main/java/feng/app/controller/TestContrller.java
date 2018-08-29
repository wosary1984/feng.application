package feng.app.controller;


import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import feng.app.repository.util.Utility;
import feng.app.repository.util.repository.UtilityRepository;

@RestController
@RequestMapping("/api")
public class TestContrller {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	
	@Autowired
	private UtilityRepository utilityRepository;
	
	@Autowired
	private HttpServletRequest request;

	@RequestMapping("/hello")
	public String hello() {
		
		if(request.getSession() != null) {
			logger.info("session id:{}",request.getSession().getId());
		}
		return "Hello World from contrller";
	}
	
	@RequestMapping("/utility")
	public Iterable<Utility> utility() {

		return utilityRepository.findByCategoryKey("jobClass");

	}

}
