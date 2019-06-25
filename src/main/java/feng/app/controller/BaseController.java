package feng.app.controller;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;

public abstract class BaseController {
	protected static final String MEDIA_TYPE = MediaType.APPLICATION_JSON_UTF8_VALUE;
	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	protected static String ACTION_LOGIN = "LOGIN";
	protected static String ACTION_LOGOUT = "LOGOUT";
	protected static String ACTION_ACCESS = "ACCESS";
	
	protected static String ACTION_QUERY_TRIGGER ="QUERY_TRIGGER";
	protected static String ACTION_ADD_JOB="ACTION_ADD_JOB";
	protected static String ACTION_PAUSE_JOB ="ACTION_PAUSE_JOB";
	protected static String ACTION_DELETE_JOB ="ACTION_DELETE_JOB";
	protected static String ACTION_RESUME_JOB ="ACTION_RESUME_JOB"; 

	public BaseController() {
	}

	protected String objectResult(String action,int code,Object object) {
		JSONObject root = new JSONObject();

		try {
			root.put("action", action);
			root.put("code", code);
			root.put("success", true);
			root.put("data", object);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return root.toString();
	}

	protected String errorMessage(String action,int code, String message) {
		JSONObject root = new JSONObject();
		try {
			root.put("action", action);
			root.put("code", code);
			root.put("success", false);
			root.put("message", message);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return root.toString();
	}
}
