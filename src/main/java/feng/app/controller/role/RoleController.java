package feng.app.controller.role;

import java.util.Optional;

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
import feng.app.repository.user.entity.ApplicationRole;
import feng.app.service.user.RoleService;

@RestController
public class RoleController extends BaseController implements RequestPath{


	@Autowired
	RoleService roleService;


	/***
	 * Get  Al Roles
	 * 
	 * @return
	 */
	@RequestMapping(path = PATH_ROLES, method = RequestMethod.GET)
	public Iterable<ApplicationRole> role() {
		return roleService.getAllRoles();
	}
	
	/****
	 * Get Role by Role Id
	 * 
	 * @param roleid
	 * @return
	 */
	@RequestMapping(path = PATH_ROLE + "/{roleid}", method = RequestMethod.GET)
	ApplicationRole getRolebyRoleid(@PathVariable Long roleid) {
		
		ApplicationRole role =roleService.getRoleById(roleid).get();
		
		if(role == null)
			throw new NotFoundException(String.format("Role with role id: %s, doesn't exist!", roleid));
		
		return role;
	}
	
	/***
	 * update role
	 * @param roleid
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	
	@RequestMapping(path = PATH_ROLE + "/{roleid}", method = RequestMethod.POST)
	public Optional<ApplicationRole> updateRole(@PathVariable Long roleid, @RequestBody String data) throws JSONException {
		roleService.updateRole(roleid, data);
		return roleService.getRoleById(roleid);
	}
	
	/*****
	 * Create role
	 * 
	 * @param data
	 * @return
	 * @throws JSONException
	 */
	@RequestMapping(path = PATH_ROLE , method = RequestMethod.POST)
	public ApplicationRole createRole( @RequestBody String data) throws JSONException {
		return roleService.createRole(data);
	}

}
