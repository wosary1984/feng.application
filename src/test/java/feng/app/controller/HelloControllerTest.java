package feng.app.controller;


import java.util.Base64;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	//@Autowired
	//private TestRestTemplate restTemplate;

	
	@Test
	public void seedTest() {

		String encryptData = "370882198401011295";
		
		String result = Base64.getEncoder().encodeToString(encryptData.getBytes());
		
		logger.info(result);

	}
 
	
	

}
