package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.service.commands.ParsedCommandInput;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "echo", debug = true)
public class EchoCommand implements RunnableCommand {
	MessageReceivedEvent event;
	ParsedCommandInput input;

	@Override
	public void run() {
		String s = String.format("Got %d arguments!", input.arguments().length);
		event.getMessage().getChannel().sendMessage(s).complete();
	}
}
