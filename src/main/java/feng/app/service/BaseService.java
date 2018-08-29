package feng.app.service;

import java.util.ArrayList;
import java.util.List;

import feng.app.service.annotation.EntityFiledCheck;
import feng.app.service.context.ContextCheckService;
import feng.app.service.context.TransformerAdapter;

public abstract class BaseService {

	protected Object ContextCheckObject(Class<? extends Object> clazz, Object object, ContextCheckService service) {
		if (clazz.isAnnotationPresent(EntityFiledCheck.class)) {
			Object dto = new TransformerAdapter(clazz, service).transform2(clazz, object,
					service.getObjectAccessRestriction(clazz));
			return dto;
		} else {
			return object;
		}
	}

	protected Iterable<? extends Object> ContextCheckObjectList(Class<? extends Object> clazz, List<? extends Object> objects,
			ContextCheckService service) {

		if (clazz.isAnnotationPresent(EntityFiledCheck.class)) {
			List<Object> result = new ArrayList<Object>();
			for (Object object : objects) {
				Object dto = new TransformerAdapter(clazz, service).transform2(clazz, object,
						service.getObjectAccessRestriction(clazz));
				result.add(dto);
			}
			return result;
		} else {
			return objects;
		}
	}

}
