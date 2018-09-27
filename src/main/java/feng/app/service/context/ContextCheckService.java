package feng.app.service.context;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import feng.app.repository.user.UserRepository;
import feng.app.repository.user.entity.ApplicationUser;
import feng.app.repository.util.EntityFieldRegistration;
import feng.app.repository.util.repository.EntityFieldRegistrationRepository;
import feng.app.service.Restriction;
import feng.app.service.annotation.EntityFieldRestriction;

@Service
public class ContextCheckService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private EntityFieldRegistrationRepository entityFieldRepository;

	@Autowired
	UserRepository userRepository;

	/***
	 * get current login user
	 * 
	 * @param userRepository
	 * @return
	 */
	public ApplicationUser getLoginUser() {
		ApplicationUser loginUser = null;
		SecurityContext context = SecurityContextHolder.getContext();
		Authentication authentication = context.getAuthentication();
		if (authentication.getName() != null) {
			loginUser = userRepository.findByUsername(authentication.getName());
		}
		return loginUser;
	}

	public HashSet<String> getReadExclusionFields(String entity) {
		HashSet<String> exclusion = new HashSet<String>();
		if (this.getLoginUser() != null) {
			List<EntityFieldRegistration> fields = entityFieldRepository
					.findEntityFieldsByRead(this.getLoginUser().getUserid(), entity, false);

			for (EntityFieldRegistration field : fields) {
				exclusion.add(field.getFieldname());
			}
		}

		return exclusion;
	}

	public List<EntityFieldRegistration> getObjectAccessRestriction(String entity) {
		List<EntityFieldRegistration> result = new ArrayList<EntityFieldRegistration>();
		if (this.getLoginUser() != null) {
			result = entityFieldRepository.findEntityFieldsByRead(this.getLoginUser().getUserid(), entity, true);
		}
		return result;
	}

	public List<EntityFieldRegistration> getObjectAccessRestriction(Class<?> clazz) {
		List<EntityFieldRegistration> result = new ArrayList<EntityFieldRegistration>();
		List<EntityFieldRegistration> dbs = null;

		if (this.getLoginUser() != null) {
			dbs = entityFieldRepository.findEntityFields(this.getLoginUser().getUserid(), clazz.getName());
		}

		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(clazz);
		for (PropertyDescriptor pd : pds) {

			if (pd.getWriteMethod() == null) {
				continue;
			}

			logger.debug("{} fields name:{}", clazz.getName(), pd.getName());
			EntityFieldRegistration registration = new EntityFieldRegistration();
			registration.setEntityname(clazz.getName());
			registration.setFieldname(pd.getName());
			registration.setFieldtype(pd.getPropertyType().getName());
			registration.setReadable(true);

			if (pd.getReadMethod().isAnnotationPresent(EntityFieldRestriction.class)) {
				registration.setFieldrestriction(pd.getReadMethod().getAnnotation(EntityFieldRestriction.class).value());
			} else {
				registration.setFieldrestriction(Restriction.NONE.getName());
			}

			if (dbs != null) {
				for (EntityFieldRegistration db : dbs) {
					if (registration.getFieldname().equals(db.getFieldname())) {
						registration.setReadable(db.isReadable());
						registration.setFieldrestriction(db.getFieldrestriction());
						continue;
					}
				}
			}
			result.add(registration);
		}
		return result;
	}

	public boolean restrictionCheck(Object value, String restriction) {
		return false;
	}

}
