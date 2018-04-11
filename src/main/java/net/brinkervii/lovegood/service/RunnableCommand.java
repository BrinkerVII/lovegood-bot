package net.brinkervii.lovegood.service;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface RunnableCommand {
	void run(MessageReceivedEvent event);
}
