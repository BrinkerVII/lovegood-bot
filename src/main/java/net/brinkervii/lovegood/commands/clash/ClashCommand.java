package net.brinkervii.lovegood.commands.clash;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.commands.util.StacktraceUtil;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.brinkervii.lovegood.service.commands.RunnableCommand;
import net.brinkervii.lovegood.util.MessageUtil;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.List;

import static net.brinkervii.lovegood.commands.clash.ClashConstants.CLASH_LIMIT;

@LovegoodCommand(name = "clash")
@Slf4j
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

		ActiveClash clash = new ActiveClash(context, event.getChannel(), sourceMember, targetMember);
		ClashUpdater updater = context.getClashUpdater();
		if (updater != null) {
			if (updater.numberOfClashes() >= CLASH_LIMIT) {
				try {
					event.getMessage().delete().complete();
				} catch (Exception e) {
					log.warn("Could not delete clash message that went over CLASH_LIMIT\n\n" + StacktraceUtil.concat(e));
				}

				return;
			}

			updater.add(clash);
		}

		clash.updateMessageString();
		clash.send();
	}
}
