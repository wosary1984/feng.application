package feng.app.service.context;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;
import org.springframework.beans.TypeMismatchException;

import feng.app.repository.util.EntityFieldRegistration;
import feng.app.service.annotation.EntityFiledCheck;
import feng.app.service.annotation.TransformRecursionIgnore;

public class TransformerAdapter {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	private Class<? extends Object> targetClass;

	private Map<String, PropertyDescriptor> targetFields;
	private Set<String> targetProperties;

	private ContextCheckService service;

	public TransformerAdapter() {

	}

	public TransformerAdapter(Class<? extends Object> clazz, ContextCheckService service) {
		initialize(clazz);
		this.service = service;
	}

	protected void initialize(Class<? extends Object> targetClass) {
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

	public final Class<? extends Object> getTargetClass() {
		return this.targetClass;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object transform(Class<? extends Object> sourceClass, Object sourceObject, HashSet<String> exclusion) {
		Object targetObject = BeanUtils.instantiateClass(this.targetClass);

		BeanWrapper targetBW = PropertyAccessorFactory.forBeanPropertyAccess(targetObject);
		BeanWrapper sourceBW = PropertyAccessorFactory.forBeanPropertyAccess(sourceObject);

		PropertyDescriptor[] spds = BeanUtils.getPropertyDescriptors(sourceClass);
		for (PropertyDescriptor spd : spds) {
			String name = spd.getName();
			PropertyDescriptor tpd = this.targetFields.get(name);
			if (tpd == null) {
				continue;
			}
			logger.info("source name: {} of type {} and target name:{} of type {}", spd.getName(),
					spd.getPropertyType(), tpd.getName(), tpd.getPropertyType());
			try {
				if (exclusion != null && exclusion.contains(name)) {
					continue;
				}
				if (spd.getWriteMethod() != null) {
					Object value = sourceBW.getPropertyValue(name);

					if (value == null) {
						continue;
					}
					logger.debug("value type:{}", value.getClass());

					boolean ignore = spd.getReadMethod().isAnnotationPresent(TransformRecursionIgnore.class);

					if (ignore) {
						// targetBW.setPropertyValue(name, value);
					} else {
						if (value instanceof java.util.List) {
							List<Object> result = new ArrayList<>();
							for (Object o : (List<?>) value) {
								logger.debug("object class:{}", o.getClass());
								boolean check = o.getClass().isAnnotationPresent(EntityFiledCheck.class);
								if (check) {
									HashSet<String> ex = service.getReadExclusionFields(o.getClass().getName());
									Object dto = new TransformerAdapter(o.getClass(), this.service)
											.transform(o.getClass(), o, ex);
									result.add(dto);
								} else {
									result.add(o);
								}

							}
							targetBW.setPropertyValue(name, (Object) result);
						} else {
							boolean check = value.getClass().isAnnotationPresent(EntityFiledCheck.class);
							if (check) {
								HashSet<String> ex = service.getReadExclusionFields(value.getClass().getName());
								Object dto = new TransformerAdapter(value.getClass(), this.service)
										.transform(value.getClass(), value, ex);
								targetBW.setPropertyValue(name, dto);
							} else {
								targetBW.setPropertyValue(name, value);
							}
						}
					}
				}

			} catch (TypeMismatchException e) {
				this.logger.info(e.getMessage());
				this.logger.info("Intercepted TypeMismatchException when setting property {} of type {} on object",
						tpd.getName(), tpd.getPropertyType());
			}

		}

		return targetObject;
	}

	// @SuppressWarnings({ "rawtypes", "unchecked" })
	public Object transform2(Class<? extends Object> sourceClass, Object sourceObject,
			List<EntityFieldRegistration> fields) {

		if (sourceObject == null)
			return null;

		Object targetObject = BeanUtils.instantiateClass(this.targetClass);

		BeanWrapper targetBW = PropertyAccessorFactory.forBeanPropertyAccess(targetObject);
		BeanWrapper sourceBW = PropertyAccessorFactory.forBeanPropertyAccess(sourceObject);

		PropertyDescriptor[] spds = BeanUtils.getPropertyDescriptors(sourceClass);
		for (PropertyDescriptor spd : spds) {
			String name = spd.getName();
			PropertyDescriptor tpd = this.targetFields.get(name);
			if (tpd == null) {
				continue;
			}
			logger.debug("source name: {} of type {} and target name:{} of type {}", spd.getName(),
					spd.getPropertyType(), tpd.getName(), tpd.getPropertyType());
			try {

				EntityFieldRegistration registration = this.getFieldRegistration(name, fields);
				if (registration == null || !registration.isReadable()) {
					continue;
				}
				if (spd.getWriteMethod() != null) {
					Object value = sourceBW.getPropertyValue(name);
					if (value == null) {
						continue;
					}
					logger.debug("value type:{}", value.getClass());

					boolean ignore = spd.getReadMethod().isAnnotationPresent(TransformRecursionIgnore.class);
					if (ignore) {
						// targetBW.setPropertyValue(name, value);
					} else {
						if (value instanceof java.util.List) {
							List<Object> result = new ArrayList<>();
							for (Object o : (List<?>) value) {
								if (service.restrictionCheck(o, registration.getFieldrestriction())) {

								} else {
									boolean check = o.getClass().isAnnotationPresent(EntityFiledCheck.class);
									if (check) {
										Object dto = new TransformerAdapter(o.getClass(), this.service).transform2(
												o.getClass(), o, service.getObjectAccessRestriction(o.getClass()));
										result.add(dto);
									} else {
										result.add(o);
									}
								}

							}
							targetBW.setPropertyValue(name, (Object) result);
						} else if (value instanceof java.util.Set) {
							Set<Object> result = new HashSet<>();
							for (Object o : (Set<?>) value) {
								if (service.restrictionCheck(o, registration.getFieldrestriction())) {

								} else {
									boolean check = o.getClass().isAnnotationPresent(EntityFiledCheck.class);
									if (check) {
										Object dto = new TransformerAdapter(o.getClass(), this.service).transform2(
												o.getClass(), o, service.getObjectAccessRestriction(o.getClass()));
										result.add(dto);
									} else {
										result.add(o);
									}
								}
							}
							targetBW.setPropertyValue(name, (Object) result);

						} else {
							if (service.restrictionCheck(value, registration.getFieldrestriction())) {

							} else {
								boolean check = value.getClass().isAnnotationPresent(EntityFiledCheck.class);
								if (check) {
									Object dto = new TransformerAdapter(value.getClass(), this.service).transform2(
											value.getClass(), value,
											service.getObjectAccessRestriction(value.getClass()));
									targetBW.setPropertyValue(name, dto);
								} else {
									targetBW.setPropertyValue(name, value);
								}
							}

						}
					}
				}

			} catch (TypeMismatchException e) {
				this.logger.info(e.getMessage());
				this.logger.info("Intercepted TypeMismatchException when setting property {} of type {} on object",
						tpd.getName(), tpd.getPropertyType());
			}

		}

		return targetObject;
	}

	private EntityFieldRegistration getFieldRegistration(String name, List<EntityFieldRegistration> fields) {

		EntityFieldRegistration registration = null;
		for (EntityFieldRegistration field : fields) {
			if (field.getFieldname().equals(name)) {
				registration = field;
			}
		}
		return registration;

	}
}
