package org.gz.sms.supports;

/**
 * 
 * @author yangdx
 *
 */
public abstract class AbstractSelfRegistration {

	public AbstractSelfRegistration() {
		tidyClazz(this.getClass());
	}

	public void tidyClazz(Class<?> clazz) {

		if (clazz == null || clazz.equals(AbstractSelfRegistration.class)
				|| clazz.equals(Object.class)) {
			return;
		}

		Class<?>[] interfaceList = clazz.getInterfaces();
		if (interfaceList != null) {
			for (Class<?> interfaceClazz : interfaceList) {
				RegistrationClassHolder.putClassInstance(interfaceClazz, this);
			}
		}

		tidyClazz(clazz.getSuperclass());
	}
}
