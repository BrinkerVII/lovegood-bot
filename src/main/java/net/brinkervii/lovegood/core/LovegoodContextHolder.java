package net.brinkervii.lovegood.core;

import net.dv8tion.jda.core.JDA;

import java.lang.reflect.Field;

public class LovegoodContextHolder {
	private final static LovegoodContextHolder instance = new LovegoodContextHolder();
	private LovegoodContext context;

	public static LovegoodContextHolder getInstance() {
		return instance;
	}

	private LovegoodContextHolder() {
		this.context = new LovegoodContext();
	}

	public LovegoodContext getContext() {
		return context;
	}

	public static void putObjectFields(Class<?> clazz, Object o) {
		LovegoodContext context = getInstance().getContext();
		for (Field field : clazz.getDeclaredFields()) {
			if (field.getType().equals(LovegoodContext.class)) {
				boolean a = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(o, context);
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(a);
			}

			if (field.getType().equals(JDA.class)) {
				boolean a = field.isAccessible();
				field.setAccessible(true);
				try {
					field.set(o, context.getJDA());
				} catch (IllegalAccessException e) {
					e.printStackTrace();
				}
				field.setAccessible(a);
			}
		}
	}
}
