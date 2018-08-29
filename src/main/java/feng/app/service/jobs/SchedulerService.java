package feng.app.service.jobs;

import java.util.Set;

import org.quartz.CronScheduleBuilder;
import org.quartz.CronTrigger;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.quartz.JobKey;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.Trigger;
import org.quartz.Trigger.TriggerState;
import org.quartz.TriggerBuilder;
import org.quartz.TriggerKey;
import org.quartz.impl.matchers.GroupMatcher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
/***
 * 
 * @author i068981
 * 
 */
@Service
public class SchedulerService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	// 加入Qulifier注解，通过名称注入bean
	@Autowired
	@Qualifier("Scheduler")
	private Scheduler scheduler;

	public boolean addJob(String jobClassName, String cronExpression, String goupName, boolean active) throws Exception {

		boolean success = false;
		// 启动调度器
		if (!scheduler.isStarted())
			scheduler.start();
		

		// 构建job信息
		JobDetail jobDetail = JobBuilder.newJob(getClass(jobClassName).getClass()).withIdentity(jobClassName, goupName)
				.build();

		// 表达式调度构建器(即任务执行的时间)
		CronScheduleBuilder scheduleBuilder = CronScheduleBuilder.cronSchedule(cronExpression);

		// 按新的cronExpression表达式构建一个新的trigger
		CronTrigger trigger = TriggerBuilder.newTrigger().withIdentity(jobClassName, goupName)
				.withSchedule(scheduleBuilder).build();
		

		try {
			if(active) {
				scheduler.scheduleJob(jobDetail, trigger);
			}else {
//				scheduler.addJob(jobDetail, true);
//				scheduler.sc
			}
			success = true;

		} catch (SchedulerException e) {
			logger.error("创建定时任务失败" + e);
			throw new Exception("创建定时任务失败");
		}

		return success;
	}
	
	/***
	 * if job with key already existed, return true
	 * @param key
	 * @return
	 * @throws SchedulerException
	 */
	public boolean checkJobIdentity(String key,String group) throws SchedulerException {
		
		return scheduler.checkExists(JobKey.jobKey(key,group));
		
//		if(scheduler.getJobDetail(JobKey.jobKey(key,group)) != null) {
//			return true;
//		}
//		else return false;
		
	}

	public void pauseTrigger(String triggerKey, String groupName) throws SchedulerException {

		scheduler.pauseTrigger(TriggerKey.triggerKey(triggerKey, groupName));
	}

	public Set<TriggerKey> queryTriggers(String groupName) throws SchedulerException {
		return scheduler.getTriggerKeys(GroupMatcher.groupEquals(groupName));
	}

	public TriggerState getTriggerState(TriggerKey key) throws SchedulerException {
		return scheduler.getTriggerState(key);
	}

	public void deleteJob(String jobClassName, String jobGroupName) throws Exception {
		scheduler.pauseTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.unscheduleJob(TriggerKey.triggerKey(jobClassName, jobGroupName));
		scheduler.deleteJob(JobKey.jobKey(jobClassName, jobGroupName));
	}
	
	public void resumeTrigger(String jobClassName, String jobGroupName) throws Exception
    {
		scheduler.resumeTrigger(TriggerKey.triggerKey(jobClassName, jobGroupName));
        //scheduler.resumeJob(JobKey.jobKey(jobClassName, jobGroupName));
    }
	
	public String getCronTriggerExpression(String jobClassName, String jobGroupName) throws SchedulerException {
		String cronExpr =null;
		Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(jobClassName,jobGroupName));
		if(trigger instanceof CronTrigger) {
			 CronTrigger cronTrigger = (CronTrigger) trigger;
		     cronExpr = cronTrigger.getCronExpression();
		}
		return cronExpr;
	}

	private BaseJob getClass(String classname) throws Exception {
		Class<?> class1 = Class.forName(classname);
		return (BaseJob) class1.newInstance();
	}

}
