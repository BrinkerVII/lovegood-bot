package net.brinkervii.lovegood.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.profilecard.ProfileCard;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.io.IOException;

@LovegoodCommand(name = "profile")
public class ProfileCommand implements RunnableCommand {
	MessageReceivedEvent event;

	@Override
	public void run() {
		ProfileCard card = new ProfileCard(event);

		try {
			event.getChannel().sendFile(card.toBytes(), "profile-for-" + event.getAuthor().getIdLong() + ".png").complete();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
