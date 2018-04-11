package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.service.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(commandString = "clash")
public class ClashCommand implements RunnableCommand {
	@Override
	public void run(MessageReceivedEvent event) {
		event
				.getChannel()
				.sendMessage("o -----> * <----- o")
				.complete()
				.addReaction("\uD83D\uDC7A")
				.complete();
	}
}
