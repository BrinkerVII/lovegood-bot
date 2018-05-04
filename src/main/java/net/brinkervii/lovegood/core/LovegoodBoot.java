package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.core.collections.ConfigurationCollection;
import net.brinkervii.lovegood.core.collections.ServiceCollection;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;

public class LovegoodBoot {
	public static void main(String[] arguments) {
		final LovegoodContext context = LovegoodContextHolder.getInstance().getContext();

		ConfigurationCollection configuration = new ConfigurationCollection();
		ServiceCollection services = new ServiceCollection();

		configuration.configure();
		services.run();
	}
}
