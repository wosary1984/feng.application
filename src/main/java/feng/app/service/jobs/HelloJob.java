package feng.app.service.jobs;

import java.util.Date;

import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HelloJob implements BaseJob {  

    private  Logger logger = LoggerFactory.getLogger(HelloJob.class);  

    public HelloJob() {  

	}
	@Override
	public void execute(JobExecutionContext context) throws JobExecutionException {
		// TODO Auto-generated method stub
		logger.error("Hello Job执行时间: " + new Date());  
	}  
}  