package feng.app.service.user;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import feng.app.annotation.MethodContextCheck;
import feng.app.config.security.MyGrantedAuthority;
import feng.app.controller.exception.def.NotFoundException;
import feng.app.controller.exception.def.NullException;
import feng.app.repository.privilege.PrivilegeRepository;
import feng.app.repository.privilege.entity.ApplicationPrivilege;
import feng.app.repository.user.ApplicationRoleRepository;
import feng.app.repository.user.RefUserPrivilegeRepository;
import feng.app.repository.user.UserRepository;
import feng.app.repository.user.entity.ApplicationRefRoleUser;
import feng.app.repository.user.entity.ApplicationRefUserPrivilege;
import feng.app.repository.user.entity.ApplicationRole;
import feng.app.repository.user.entity.ApplicationUser;

@Service
public class CustomUserService implements UserDetailsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	private ApplicationRoleRepository applicationRoleRepository;

	@Autowired
	private RefUserPrivilegeRepository refUserPrivilegeRepository;

	@Autowired
	PrivilegeRepository privilegeRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		ApplicationUser loginUser = userRepository.findByUsername(username);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();

		if (loginUser != null) {
			for (ApplicationRefUserPrivilege ref : loginUser.getPrivileges()) {
				logger.debug("Authtication user:{} has privilege:{},{},{}", username, ref.getPrivilege().getLink(),
						ref.getPrivilege().getName(), ref.getPrivilege().getLink());
				MyGrantedAuthority grantedAuthority = new MyGrantedAuthority(ref.getPrivilege().getFid(),
						ref.getPrivilege().getName(), ref.getPrivilege().getLink());
				grantedAuthority.setActions(ref.getPrivilege().getActions());
				grantedAuthorities.add(grantedAuthority);
			}
			return new User(loginUser.getUsername(), loginUser.getPassword(), grantedAuthorities);
		} else {
			logger.error("user:{} is not existed", username);
			throw new UsernameNotFoundException("user: " + username + " is not exised!");

		}

	}

	public List<GrantedAuthority> getAuthority(String username) {
		ApplicationUser loginUser = userRepository.findByUsername(username);
		List<GrantedAuthority> grantedAuthorities = new ArrayList<>();
		if (loginUser != null) {
			for (ApplicationRefUserPrivilege ref : loginUser.getPrivileges()) {
				logger.debug("Authtication user:{} has privilege:{},{},{}", username, ref.getPrivilege().getLink(),
						ref.getPrivilege().getName(), ref.getPrivilege().getLink());
				MyGrantedAuthority grantedAuthority = new MyGrantedAuthority(ref.getPrivilege().getFid(),
						ref.getPrivilege().getName(), ref.getPrivilege().getLink());
				grantedAuthority.setActions(ref.getPrivilege().getActions());
				grantedAuthorities.add(grantedAuthority);
			}
			return grantedAuthorities;
		} else {
			logger.error("user:{} is not existed", username);
			throw new UsernameNotFoundException("user: " + username + " is not exised!");

		}
	}

	public Iterable<ApplicationRefRoleUser> getLoginUserRoles(ApplicationUser loginUser) {
		return loginUser.getRoles();
	}

	public ApplicationUser getLoginUser() {
		ApplicationUser loginUser = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication.getName() != null) {
			loginUser = userRepository.findByUsername(authentication.getName());
		}
		return loginUser;
	}

	public Iterable<ApplicationRefUserPrivilege> getLoginUserPrivileges() {

		// return refUserPrivilegeRepository.findByUser(this.getLoginUser());

		if (this.getLoginUser() != null) {
			return refUserPrivilegeRepository.findUserPrivileges(this.getLoginUser().getUserid());
		} else
			throw new NotFoundException(String.format("login user not found"));

	}

	@MethodContextCheck
	public Iterable<ApplicationUser> getAllUsers() {
		return userRepository.findAll();
	}

	public ApplicationUser getUserByUserId(Long id) {
		return userRepository.findByUserid(id);
	}

	public Iterable<ApplicationRole> getAllRoles() {
		return applicationRoleRepository.findAll();
	}

	public Iterable<ApplicationPrivilege> getAllPrivileges() {
		return privilegeRepository.getTopPrivileges();
	}

	/***
	 * 
	 * @param userid
	 * @param data
	 * @throws JSONException
	 */
	public void updateUser(Long userid, String data) throws JSONException {

		JSONObject object = new JSONObject(data);
		boolean initializeRole = object.isNull("initializeRole") ? false : object.getBoolean("initializeRole");

		JSONObject user = object.getJSONObject("user");

		ApplicationUser dbuser = userRepository.findByUserid(userid);

		dbuser.setPhoneNumber(user.isNull("phoneNumber") ? "" : user.getString("phoneNumber"));
		dbuser.setEmail(user.isNull("email") ? "" : user.getString("email"));
		dbuser.setGranttype(user.isNull("granttype") ? "" : user.getString("granttype"));
		dbuser.setFirstname(user.isNull("firstname") ? "" : user.getString("firstname"));
		dbuser.setLastname(user.isNull("lastname") ? "" : user.getString("lastname"));
		dbuser.setLocked(user.isNull("locked") ? false : user.getBoolean("locked"));

		if (initializeRole) {

			JSONArray arr = object.getJSONArray("privileges");
			List<ApplicationRefUserPrivilege> list = dbuser.getPrivileges();
			dbuser.getPrivileges().clear();

			for (int i = 0; i < arr.length(); i++) {
				JSONObject tempJson = arr.getJSONObject(i);
				ApplicationRefUserPrivilege ref = new ApplicationRefUserPrivilege();

				ref.setPrivilege(privilegeRepository.getByFid(tempJson.getLong("fid")));
				ref.setUser(dbuser);
				list.add(ref);
			}

			if (dbuser.getGranttype().equalsIgnoreCase("ROLE")) {
				JSONArray rolesArr = object.getJSONArray("roles");
				Set<ApplicationRefRoleUser> set = dbuser.getRoles();
				dbuser.getRoles().clear();
				for (int i = 0; i < rolesArr.length(); i++) {
					JSONObject tempJson = rolesArr.getJSONObject(i);

					ApplicationRefRoleUser ref = new ApplicationRefRoleUser();
					ref.setRole(applicationRoleRepository.findById(tempJson.getLong("id")).get());
					logger.info("roleid is {}", ref.getRole().getId());
					ref.setUser(dbuser);
					set.add(ref);
				}
			}
		}
		userRepository.save(dbuser);

	}

	public ApplicationUser createUser(String data) throws JSONException {

		JSONObject object = new JSONObject(data);

		JSONObject user = object.getJSONObject("user");

		ApplicationUser dbuser = new ApplicationUser();

		dbuser.setUsername(user.isNull("username") ? "" : user.getString("username"));

		if (dbuser.getUsername() == "") {
			throw new NullException("username is null");
		}
		dbuser.setPhoneNumber(user.isNull("phoneNumber") ? "" : user.getString("phoneNumber"));
		dbuser.setEmail(user.isNull("email") ? "" : user.getString("email"));
		dbuser.setGranttype(user.isNull("granttype") ? "" : user.getString("granttype"));
		dbuser.setFirstname(user.isNull("firstname") ? "" : user.getString("firstname"));
		dbuser.setLastname(user.isNull("lastname") ? "" : user.getString("lastname"));
		dbuser.setLocked(user.isNull("locked") ? false : user.getBoolean("locked"));

		JSONArray arr = object.getJSONArray("privileges");
		List<ApplicationRefUserPrivilege> list = dbuser.getPrivileges();
		for (int i = 0; i < arr.length(); i++) {
			JSONObject tempJson = arr.getJSONObject(i);
			ApplicationRefUserPrivilege ref = new ApplicationRefUserPrivilege();

			ref.setPrivilege(privilegeRepository.getByFid(tempJson.getLong("fid")));
			ref.setUser(dbuser);
			list.add(ref);
		}

		if (dbuser.getGranttype().equalsIgnoreCase("ROLE")) {
			JSONArray rolesArr = object.getJSONArray("roles");
			Set<ApplicationRefRoleUser> set = dbuser.getRoles();
			for (int i = 0; i < rolesArr.length(); i++) {
				JSONObject tempJson = rolesArr.getJSONObject(i);

				ApplicationRefRoleUser ref = new ApplicationRefRoleUser();
				ref.setRole(applicationRoleRepository.findById(tempJson.getLong("id")).get());
				logger.info("roleid is {}", ref.getRole().getId());
				ref.setUser(dbuser);
				set.add(ref);
			}
		}

		userRepository.save(dbuser);
		return dbuser;

	}
}
