package feng.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import feng.app.repository.base.BaseRepositoryFactoryBean;

/***
 * 
 * @author i068981
 *@EnableJpaAuditing 用来开启@CreatedDate、@CreatedBy、@LastModifiedDate、@LastModifiedBy 注解 
 */
@SpringBootApplication
@EnableJpaRepositories(repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)//指定自己的工厂类
@EnableJpaAuditing
public class MainApplication extends SpringBootServletInitializer{

	public static void main(String[] args) {
		SpringApplication.run(MainApplication.class, args);
	}
	
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(MainApplication.class);
	}

}
