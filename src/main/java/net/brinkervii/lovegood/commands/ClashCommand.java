package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.service.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "clash")
public class ClashCommand implements RunnableCommand {
	MessageReceivedEvent event;

	@Override
	public void run() {
		event
				.getChannel()
				.sendMessage("o -----> * <----- o")
				.complete()
				.addReaction("\uD83D\uDC7A")
				.complete();
	}
}
