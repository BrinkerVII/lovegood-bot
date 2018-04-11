package net.brinkervii.lovegood.jda;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import javax.security.auth.login.LoginException;

public class JDAManager {
	private JDA jda;

	public JDAManager() {}

	public void build(String token) throws LoginException, InterruptedException {
		JDA jda = new JDABuilder(AccountType.BOT)
				.setToken(token)
				.buildBlocking();

		this.jda = jda;
	}

	public boolean good() {
		return jda != null;
	}

	public void addListener(ListenerAdapter listenerAdapter) {
		jda.addEventListener(listenerAdapter);
	}

	public JDA jda() {
		assert jda != null;
		return jda;
	}
}
