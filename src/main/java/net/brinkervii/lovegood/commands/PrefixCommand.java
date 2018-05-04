package net.brinkervii.lovegood.commands;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "prefix")
@Slf4j
public class PrefixCommand implements RunnableCommand {
	LovegoodContext context;
	MessageReceivedEvent event;

	@Override
	public void run() {
		String reply = "The current prefix is " + context.getCommandPrefix();
		MessageChannel channel = event.getChannel();
		log.info("Sending message to channel " + channel.getName());

		channel.sendMessage(reply).complete();
	}
}
