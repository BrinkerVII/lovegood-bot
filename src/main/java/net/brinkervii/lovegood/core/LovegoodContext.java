package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.jda.JDAManager;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.dv8tion.jda.core.JDA;

import javax.security.auth.login.LoginException;

public class LovegoodContext {
	private final JDAManager jdaManager;
	private ApplicationProperties properties = new ApplicationProperties();
	private String commandPrefix = ">";
	private ClashUpdater clashUpdater;

	LovegoodContext() {
		this.jdaManager = new JDAManager();
		try {
			jdaManager.build(properties.get("lovegood.token"));
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public JDA getJDA() {
		return jdaManager.jda();
	}

	public JDAManager getJdaManager() {
		return jdaManager;
	}

	public String getCommandPrefix() {
		return commandPrefix;
	}

	public void setClashUpdater(ClashUpdater clashUpdater) {
		this.clashUpdater = clashUpdater;
	}

	public ClashUpdater getClashUpdater() {
		return clashUpdater;
	}
}
