package net.brinkervii.lovegood.core;

import java.io.*;
import java.util.HashMap;
import java.util.Properties;


public class ApplicationProperties {
	private final static String PROFILE_KEY = "lovegood.profile";
	private HashMap<String, String> properties = new HashMap<>();
	private String profile = "prod";

	public ApplicationProperties() {
		try {
			load();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void load() throws IOException {
		// Load application private properties
		Properties privateProperties = new Properties();
		privateProperties.load(getResourceStream("application.private.properties"));
		privateProperties.forEach((key, value) -> properties.put(String.valueOf(key), String.valueOf(value)));

		// Load application properties
		Properties applicationProperties = new Properties();
		applicationProperties.load(getResourceStream("application.properties"));
		applicationProperties.forEach((key, value) -> properties.put(String.valueOf(key), String.valueOf(value)));

		// Load system properties
		Properties systemProperties = System.getProperties();
		systemProperties.forEach((key, value) -> properties.put(String.valueOf(key), String.valueOf(value)));

		if (properties.containsKey(PROFILE_KEY)) {
			profile = properties.get(PROFILE_KEY);
		}

		// Load profile specific properties
		Properties profileProperties = new Properties();
		profileProperties.load(getResourceStream(String.format("application-%s.properties", profile)));
		profileProperties.forEach((key, value) -> properties.put(String.valueOf(key), String.valueOf(value)));

		// Re-apply system properties, because these always have priority
		systemProperties.forEach((key, value) -> properties.put(String.valueOf(key), String.valueOf(value)));
	}

	public String get(String key) {
		return properties.get(key);
	}

	private InputStream getResourceStream(String resourceName) throws FileNotFoundException {
		File file = new File(resourceName);
		if (file.exists()) {
			return new FileInputStream(file);
		}

		InputStream resourceAsStream = System.class.getResourceAsStream("/" + resourceName);
		if (resourceAsStream == null) {
			return getResourceStream("default.properties");
		}

		return resourceAsStream;
	}

	public boolean containsKey(String key) {
		return properties.containsKey(key);
	}
}
