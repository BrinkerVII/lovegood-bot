package net.brinkervii.lovegood.commands.clash;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.brinkervii.lovegood.util.MessageUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

@LovegoodCommand(name = "clash")
public class ClashCommand implements RunnableCommand {
	MessageReceivedEvent event;
	LovegoodContext context;

	@Override
	public void run() {
		List<Member> mentionedMembers = event.getMessage().getMentionedMembers();
		Member sourceMember = event.getMember();
		Member targetMember = null;
		for (Member member : mentionedMembers) {
			targetMember = member;
			break;
		}

		if (targetMember == null) {
			if (MessageUtil.containsAllMention(event.getMessage().getContentRaw())) {
				String failMsg = String.format("%s, even you do not have that kind of power.", sourceMember.getAsMention());
				event.getChannel().sendMessage(failMsg).complete();
				return;
			}

			String failMsg = String.format("%s You didn't tell me who to clash with :frowning:", sourceMember.getAsMention());
			event.getChannel().sendMessage(failMsg).complete();
			return;
		}

		if (sourceMember.getUser().getIdLong() == targetMember.getUser().getIdLong()) {
			String failMsg = String.format("%s, that's not how it works, that's not how any of this works. You can't clash with yourself :neutral_face:", sourceMember.getAsMention());
			event.getChannel().sendMessage(failMsg).complete();
			return;
		}

		ActiveClash clash = new ActiveClash(event.getChannel(), sourceMember, targetMember);
		ClashUpdater updater = context.getClashUpdater();
		if (updater != null) {
			updater.add(clash);
		}

		clash.step();
	}
}
