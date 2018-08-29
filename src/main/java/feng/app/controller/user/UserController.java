package feng.app.controller.user;

import org.json.JSONException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import feng.app.controller.BaseController;
import feng.app.controller.RequestPath;
import feng.app.controller.exception.def.NotFoundException;
import feng.app.repository.privilege.entity.ApplicationPrivilege;
import feng.app.repository.user.entity.ApplicationUser;
import feng.app.service.user.CustomUserService;

@RestController
public class UserController extends BaseController implements RequestPath{

	@Autowired
	CustomUserService userService;

	/***
	 * Get all users
	 * 
	 * @return
	 * @throws Exception 
	 */
	@RequestMapping(path = PATH_USERS, method = RequestMethod.GET)
	public Iterable<ApplicationUser> getUsers() throws Exception {
		//throw new Exception("test");
		return userService.getAllUsers();
	}

	/****
	 * Get system user  by userid
	 * 
	 * @param userid
	 * @return
	 */
	@RequestMapping(path = PATH_USER + "/{userid}", method = RequestMethod.GET)
	ApplicationUser getUserByUserId(@PathVariable Long userid) {
		
		ApplicationUser user =userService.getUserByUserId(userid);
		
		if(user == null)
			throw new NotFoundException(String.format("User with userid: %s, doesn't exist!", userid));
		
		return user;
	}

	/*****
	 * Update system user by userid
	 * 
	 * @param userid
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(path = PATH_USER + "/{userid}", method = RequestMethod.POST)
	public ApplicationUser updateUser(@PathVariable Long userid, @RequestBody String data) throws JSONException {
		userService.updateUser(userid, data);
		return userService.getUserByUserId(userid);
	}
	
	/*****
	 * Create user
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(path = PATH_USER , method = RequestMethod.POST)
	public ApplicationUser createUser( @RequestBody String data) throws JSONException {
		return userService.createUser(data);
	}

	/***
	 * Get all privileges
	 * 
	 * @return
	 */
	@RequestMapping(path = PATH_PRIVILEGES, method = RequestMethod.GET)
	public Iterable<ApplicationPrivilege> view() {
		return userService.getAllPrivileges();
	}

}
