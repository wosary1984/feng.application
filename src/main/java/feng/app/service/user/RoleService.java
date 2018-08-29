package feng.app.service.user;

import java.util.Optional;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import feng.app.controller.exception.def.NullException;
import feng.app.repository.privilege.PrivilegeRepository;
import feng.app.repository.user.ApplicationRoleRepository;
import feng.app.repository.user.entity.ApplicationRefRolePrivilege;
import feng.app.repository.user.entity.ApplicationRole;

@Service
public class RoleService {
	
	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

	@Autowired
	private PrivilegeRepository privilegeRepository;

	public Iterable<ApplicationRole> getAllRoles() {
		return applicationRoleRepository.findAll();
	}

	public Optional<ApplicationRole> getRoleById(long id) {
		return applicationRoleRepository.findById(id);
	}

	public void updateRole(Long roleid, String data) throws JSONException {

		JSONObject object = new JSONObject(data);

		JSONObject role = object.getJSONObject("role");
		ApplicationRole dbrole = applicationRoleRepository.findById(roleid).get();

		dbrole.setName(role.isNull("name") ? "" : role.getString("name"));
		dbrole.setDescription(role.isNull("description") ? "" : role.getString("description"));

		dbrole.getPrivileges().clear();
		Set<ApplicationRefRolePrivilege> set = dbrole.getPrivileges();

		JSONArray arr = object.getJSONArray("privileges");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject tempJson = arr.getJSONObject(i);

			ApplicationRefRolePrivilege ref = new ApplicationRefRolePrivilege();
			ref.setRole(dbrole);
			ref.setPrivilege(privilegeRepository.getByFid(tempJson.getLong("fid")));
			set.add(ref);
		}

		applicationRoleRepository.save(dbrole);

	}

	public ApplicationRole createRole(String data) throws JSONException {

		JSONObject object = new JSONObject(data);

		JSONObject role = object.getJSONObject("role");

		ApplicationRole dbrole = new ApplicationRole();

		dbrole.setName(role.isNull("name") ? "" : role.getString("name"));

		if (dbrole.getName() == "") {
			throw new NullException("name is null");
		}
		dbrole.setDescription(role.isNull("description") ? "" : role.getString("description"));

		Set<ApplicationRefRolePrivilege> set = dbrole.getPrivileges();
		JSONArray arr = object.getJSONArray("privileges");
		for (int i = 0; i < arr.length(); i++) {
			JSONObject tempJson = arr.getJSONObject(i);

			ApplicationRefRolePrivilege ref = new ApplicationRefRolePrivilege();
			ref.setRole(dbrole);
			ref.setPrivilege(privilegeRepository.getByFid(tempJson.getLong("fid")));
			set.add(ref);
		}

		applicationRoleRepository.save(dbrole);
		return dbrole;

	}

}
