package feng.app.service.context;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;


public class TransformerJsonAdapter<T> {

	@SuppressWarnings("unused")
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Class<T> targetClass;

	private Map<String, PropertyDescriptor> targetFields;
	private Set<String> targetProperties;

	public TransformerJsonAdapter() {

	}

	public TransformerJsonAdapter(Class<T> targetClass) {
		initialize(targetClass);
	}

	protected void initialize(Class<T> targetClass) {
		this.targetClass = targetClass;
		this.targetFields = new HashMap<String, PropertyDescriptor>();
		this.targetProperties = new HashSet<String>();

		PropertyDescriptor[] pds = BeanUtils.getPropertyDescriptors(targetClass);
		for (PropertyDescriptor pd : pds) {
			if (pd.getWriteMethod() != null) {
				this.targetFields.put(pd.getName(), pd);
				this.targetProperties.add(pd.getName());
			}
		}
	}

	public final Class<T> getTargetClass() {
		return this.targetClass;
	}

	@SuppressWarnings("unused")
	public JSONObject transform(Class<T> sourceClass, T sourceObject, HashSet<String> exclusion) {

		Object targetObject = BeanUtils.instantiateClass(this.targetClass);

		JSONObject object = new JSONObject();

		BeanWrapper sourceBW = PropertyAccessorFactory.forBeanPropertyAccess(sourceObject);

		PropertyDescriptor[] spds = BeanUtils.getPropertyDescriptors(sourceClass);
		for (PropertyDescriptor spd : spds) {

			if (spd.getWriteMethod() == null) {
				continue;
			}
		}
		return object;
	}
}
