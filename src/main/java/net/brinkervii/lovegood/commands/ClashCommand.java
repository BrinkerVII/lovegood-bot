package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "clash")
public class ClashCommand implements RunnableCommand {
	final static String RIGHT_ARROW = "️\uD83D\uDC49";
	final static String LEFT_ARROW = "\uD83D\uDC48";

	MessageReceivedEvent event;
	LovegoodContext context;

	@Override
	public void run() {
		Message message = event
				.getChannel()
				.sendMessage(":japanese_goblin: =====>:sparkles: :atom: :sparkles:<===== :japanese_goblin:")
				.complete();

		message.addReaction(LEFT_ARROW).complete();
		message.addReaction(RIGHT_ARROW).complete();
	}
}
