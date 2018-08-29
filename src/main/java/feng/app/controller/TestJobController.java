package feng.app.controller;

import java.util.List;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feng.app.service.jobs.BaseJob;

import org.quartz.impl.matchers.GroupMatcher;

@RestController
@RequestMapping(value = "/job")
public class TestJobController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 加入Qulifier注解，通过名称注入bean
	@Autowired
	@Qualifier("Scheduler")
	private Scheduler scheduler;

	@RequestMapping(path = "/queryJobs", method = RequestMethod.GET)
	public String queryJobs() throws Exception {
		// enumerate each job group
		for (String group : scheduler.getJobGroupNames()) {
			// enumerate each job in group
			for (JobKey jobKey : scheduler.getJobKeys(GroupMatcher.groupEquals(group))) {
				logger.info("Found job identified by: " + jobKey);
				
				List<? extends Trigger> triggers = scheduler.getTriggersOfJob(jobKey);
				
				for(Trigger tr: triggers) {
					logger.info("trigger key:{}, state:{}",tr.getKey(),scheduler.getTriggerState(tr.getKey()));
				}				
			}
		}
		
		// enumerate each trigger group
		for(String group: scheduler.getTriggerGroupNames()) {
		    // enumerate each trigger in group
		    for(TriggerKey triggerKey : scheduler.getTriggerKeys(GroupMatcher.groupEquals(group))) {
		    	logger.info("Found trigger identified by: " + triggerKey);
		    }
		}
		return "ok";
	}

	@RequestMapping(path = "/addJob", method = RequestMethod.GET)
	public String addJob() throws Exception {
		// 启动调度器
		scheduler.start();

		String jobClassName = "feng.sport.service.jobs.HelloJob";
		String jobGroupName = "feng.sports";
		String cronExpression = "0/3 * * * * ?";

		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass())
				.withIdentity(jobClassName, jobGroupName).build();

		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, jobGroupName)
				.withSchedule(scheduleBuilder).build();

		try {
			scheduler.scheduleJob(jobDetail, trigger);

		} catch (SchedulerException e) {
			logger.error("创建定时任务失败" + e);
			throw new Exception("创建定时任务失败");
		}
		return cronExpression;
	}

	public static BaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (BaseJob) class1.newInstance();
	}
}
