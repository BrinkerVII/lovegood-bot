package net.brinkervii.lovegood.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;


public class ApplicationProperties {
	private HashMap<String, String> properties = new HashMap<>();

	public ApplicationProperties() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load() throws IOException {
		Properties privateProperties = new Properties();
		privateProperties.load(System.class.getResourceAsStream("/application.private.properties"));
		for (Map.Entry<Object, Object> entry : privateProperties.entrySet()) {
			properties.put(String.valueOf(entry.getKey()), String.valueOf(entry.getValue()));
		}
	}

	public String get(String key) {
		return properties.get(key);
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}
}
