package net.brinkervii.lovegood.core;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.BiConsumer;

@Slf4j
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

		if (properties.containsKey("default")) {
			log.warn("A set of properties did not load correctly, check if all properties files are in place and correctly configured");
		}
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

	public Properties extract(ExtractionProfile profile) {
		Properties target = new Properties();
		profile.keyMap().forEach((withoutPrefix, withPrefix) -> {
			if (properties.containsKey(withPrefix)) {
				target.put(withoutPrefix, properties.get(withPrefix));
			}
		});

		return target;
	}

	public void forEach(BiConsumer<? super String, ? super String> consumer) {
		properties.forEach(consumer);
	}

	@Data
	public static class ExtractionProfile {
		private final String[] keys;
		private String prefix = "";

		public ExtractionProfile(String... keys) {
			this.keys = keys;
		}

		public Map<String, String> keyMap() {
			HashMap<String, String> map = new HashMap<>();
			for (String key : keys) {
				map.put(key, prefix + key);
			}

			return map;
		}
	}
}
