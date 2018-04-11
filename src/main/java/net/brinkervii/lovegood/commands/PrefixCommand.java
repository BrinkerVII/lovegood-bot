package net.brinkervii.lovegood.commands;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.RunnableCommand;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(commandString = "prefix")
@Slf4j
public class PrefixCommand implements RunnableCommand {
	LovegoodContext context;

	@Override
	public void run(MessageReceivedEvent event) {
		String reply = "The current prefix is " + context.getCommandPrefix();
		MessageChannel channel = event.getChannel();
		log.info("Sending message to channel " + channel.getName());

		event.getChannel().sendMessage(reply).complete();
	}
}
