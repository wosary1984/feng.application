package feng.app.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTest {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TestRestTemplate restTemplate;

	
	@Test
	public void exampleTest() {
		logger.info("info");
		//String body = this.restTemplate.getForObject("/hello", String.class);
		//assertThat(body).isEqualTo("Hello World");
	}

}
