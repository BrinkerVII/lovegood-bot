package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.awt.*;

@LovegoodCommand(name = "sysinfo")
public class SystemInformationCommand implements RunnableCommand {
	MessageReceivedEvent event;
	LovegoodContext context;

	@Override
	public void run() {
		String debugString = context.debug() ?
				"Debugging commands and services are enabled"
				:
				"Debugging commands and services are NOT enabled";

		MessageEmbed embed = new EmbedBuilder()
				.setTitle("Lovegood bot system information")
				.setDescription("This is the system information of the Lovegood bot instance that is online in this server")
				.setColor(Color.red)
				.addField("Debug mode", debugString, false)
				.addField("Bot start date and time", context.startDateAsString(), false)
				.build();

		event.getChannel().sendMessage(embed).complete();
	}
}
