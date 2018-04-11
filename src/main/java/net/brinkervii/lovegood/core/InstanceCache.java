package net.brinkervii.lovegood.core;

import java.util.HashMap;

public class InstanceCache {
	private final static InstanceCache instance = new InstanceCache();
	private HashMap<Class<?>, Object> cache = new HashMap<>();

	public static InstanceCache getInstance() {
		return instance;
	}

	private InstanceCache() {
	}

	public boolean contains(Class<?> clazz) {
		return cache.containsKey(clazz);
	}

	public Object get(Class<?> clazz) {
		return cache.get(clazz);
	}
}
