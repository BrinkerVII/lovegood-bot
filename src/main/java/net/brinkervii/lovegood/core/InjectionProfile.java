package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class InjectionProfile {
	private static InjectionProfile globalProfile = null;

	private HashMap<Class<?>, Object> profile = new HashMap<>();
	private boolean locked = false;

	public InjectionProfile() {
	}

	public static InjectionProfile getGlobalProfile() {
		if (globalProfile != null) return globalProfile;

		InjectionProfile profile = new InjectionProfile();
		LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
		profile.provide(context);
		profile.provide(context.getJDA());
		profile.lock();

		globalProfile = profile;
		return globalProfile;
	}

	public InjectionProfile provide(Class<?> clazz, Object o) {
		if (locked) return this;

		if (profile.containsKey(clazz)) {
			profile.remove(clazz);
		}
		profile.put(clazz, o);

		return this;
	}

	public void apply(Object target) {
		Class<?> targetClass = target.getClass();
		for (Field field : targetClass.getDeclaredFields()) {
			boolean accesible = field.isAccessible();
			field.setAccessible(true);
			for (Map.Entry<Class<?>, Object> entry : profile.entrySet()) {
				if (entry.getKey().equals(field.getType())) {
					try {
						field.set(target, entry.getValue());
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
			field.setAccessible(accesible);
		}
	}

	public InjectionProfile lock() {
		locked = true;
		return this;
	}

	public InjectionProfile provide(Object target) {
		return provide(target.getClass(), target);
	}

	public InjectionProfile provide(Object... targets) {
		for (Object t : targets) {
			provide(t);
		}
		return this;
	}
}
