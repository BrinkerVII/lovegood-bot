package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.service.commands.CommandInterpreter;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "commands")
public class CommandsCommand implements RunnableCommand {
	CommandInterpreter interpreter;
	MessageReceivedEvent event;

	@Override
	public void run() {
		StringBuilder stringBuilder = new StringBuilder("Available commands:\n```\n");
		for (String cmdString : interpreter.getCommandList()) {
			stringBuilder.append(cmdString + "\n");
		}
		stringBuilder.append("```");

		event.getChannel().sendMessage(stringBuilder.toString()).complete();
	}
}
