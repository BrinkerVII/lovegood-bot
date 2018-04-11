package net.brinkervii.lovegood.commands.clash;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodCommand(name = "clash")
public class ClashCommand implements RunnableCommand {
	MessageReceivedEvent event;
	LovegoodContext context;

	@Override
	public void run() {
		ActiveClash clash = new ActiveClash(event.getChannel());
		ClashUpdater updater = context.getClashUpdater();
		if (updater != null) {
			updater.add(clash);
		}

		clash.step();
	}
}
