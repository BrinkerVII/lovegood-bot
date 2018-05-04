package net.brinkervii.lovegood.core.collections;

import net.brinkervii.lovegood.annotation.LovegoodConfiguration;
import net.brinkervii.lovegood.config.DBConfiguration;

import java.util.ArrayList;
import java.util.List;

public class ConfigurationCollection {
	private List<LovegoodConfiguration> configurations = new ArrayList<>();

	public ConfigurationCollection() {
		this
			.add(new DBConfiguration());
	}

	private ConfigurationCollection add(LovegoodConfiguration configuration) {
		this.configurations.add(configuration);

		return this;
	}

	public void configure() {
		configurations.forEach(LovegoodConfiguration::configure);
	}
}
