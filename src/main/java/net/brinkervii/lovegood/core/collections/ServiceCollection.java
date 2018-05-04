package net.brinkervii.lovegood.core.collections;

import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.core.InjectionProfile;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.brinkervii.lovegood.service.FriendRequestResponder;
import net.brinkervii.lovegood.service.QuartzBoot;
import net.brinkervii.lovegood.service.commands.CommandInterpreter;
import net.brinkervii.lovegood.service.debugging.MessageReader;

import java.util.ArrayList;
import java.util.List;

public class ServiceCollection {
	private List<LovegoodService> services = new ArrayList<>();

	public ServiceCollection() {
		this
			.add(new CommandInterpreter())
			.add(new MessageReader())
			.add(new ClashUpdater())
			.add(new FriendRequestResponder())
			.add(new QuartzBoot());
	}

	private ServiceCollection add(LovegoodService service) {
		InjectionProfile.getGlobalProfile().apply(service);
		this.services.add(service);

		return this;
	}

	public void run() {
		services.forEach(LovegoodService::run);
	}
}
