package feng.app.controller.scheduler;

import java.io.IOException;
import java.io.InputStream;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.quartz.SchedulerException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.PathNotFoundException;

import feng.app.controller.BaseController;
import feng.app.service.jobs.SchedulerService;

@RestController
public class SchedulerController extends BaseController {
	
	final String PATH_JOBS = "/api/cookie/jobs";
	final String PATH_ADD_JOB = "/api/cookie/addjob";
	final String PATH_RESUME_JOB = "/api/cookie/resumejob";
	final String PATH_PAUSE_JOB = "/api/cookie/pausejob";
	final String PATH_DELETE_JOB = "/api/cookie/deletejob";

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	private static String GROUP_NAME = "feng.sports";

	// 加入Qulifier注解，通过名称注入bean
	@Autowired
	private SchedulerService schedulerService;

	@Autowired
	HttpServletRequest request;

	@RequestMapping(path = PATH_JOBS, method = RequestMethod.GET)
	public String queryJobs() throws Exception {
		logger.info("requested session id:{}, session id:{}", request.getRequestedSessionId(),
				request.getSession().getId());
		return objectResult(BaseController.ACTION_QUERY_TRIGGER, 200, getTriggerKeys());
	}

	private JSONArray getTriggerKeys() throws SchedulerException {
		JSONArray tiggerKeys = new JSONArray();
		schedulerService.queryTriggers(GROUP_NAME).stream().map(key -> {
			JSONObject object = new JSONObject();
			try {
				object.put("groupName", GROUP_NAME);
				object.put("name", key.getName());
				object.put("state", schedulerService.getTriggerState(key));
				object.put("expression", schedulerService.getCronTriggerExpression(key.getName(), GROUP_NAME));
			} catch (JSONException | SchedulerException e) {
				e.printStackTrace();
			}
			return object;
		}).forEach(tiggerKeys::put);
		return tiggerKeys;
	}

	@RequestMapping(path = PATH_ADD_JOB, method = RequestMethod.POST)
	public String addJob() throws Exception {
		try (InputStream is = request.getInputStream()) {
			DocumentContext context = JsonPath.parse(is);
			String jobClassName = context.read("$.jobClassName", String.class);
			String cronExpression = context.read("$.cronExpression", String.class);
			Boolean active = context.read("$.active", Boolean.class);

			if (!schedulerService.checkJobIdentity(jobClassName, GROUP_NAME)) {
				schedulerService.addJob(jobClassName, cronExpression, GROUP_NAME, active);
			}
			return objectResult(BaseController.ACTION_ADD_JOB, 200, getTriggerKeys());

		} catch (IOException | PathNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return errorMessage(BaseController.ACTION_ADD_JOB, 200, e.getMessage());
		}
	}

	@RequestMapping(path = PATH_RESUME_JOB, method = RequestMethod.POST)
	public String resumeJob() throws Exception {
		try (InputStream is = request.getInputStream()) {
			DocumentContext context = JsonPath.parse(is);
			String jobClassName = context.read("$.jobClassName", String.class);

			schedulerService.resumeTrigger(jobClassName, GROUP_NAME);
			return objectResult(BaseController.ACTION_RESUME_JOB, 200, getTriggerKeys());

		} catch (IOException | PathNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return objectResult(BaseController.ACTION_RESUME_JOB, 200, e.getMessage());
		}
	}

	@RequestMapping(path = PATH_PAUSE_JOB, method = RequestMethod.POST)
	public String pauseJob() throws Exception {
		try (InputStream is = request.getInputStream()) {
			DocumentContext context = JsonPath.parse(is);
			String jobClassName = context.read("$.jobClassName", String.class);

			schedulerService.pauseTrigger(jobClassName, GROUP_NAME);

			return objectResult(BaseController.ACTION_PAUSE_JOB, 200, getTriggerKeys());
		} catch (IOException | PathNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return objectResult(BaseController.ACTION_PAUSE_JOB, 200, e.getMessage());
		}
	}

	@RequestMapping(path = PATH_DELETE_JOB, method = RequestMethod.POST)
	public String deleteJob() throws Exception {
		try (InputStream is = request.getInputStream()) {
			DocumentContext context = JsonPath.parse(is);
			String jobClassName = context.read("$.jobClassName", String.class);

			schedulerService.deleteJob(jobClassName, GROUP_NAME);

			return objectResult(BaseController.ACTION_DELETE_JOB, 200, getTriggerKeys());
		} catch (IOException | PathNotFoundException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
			return objectResult(BaseController.ACTION_DELETE_JOB, 200, e.getMessage());
		}
	}
}
