package net.brinkervii.lovegood.service;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.commands.clash.ActiveClash;
import net.brinkervii.lovegood.commands.util.StacktraceUtil;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.brinkervii.lovegood.util.ArrayListCleaner;
import net.dv8tion.jda.core.entities.MessageReaction;
import net.dv8tion.jda.core.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.core.events.message.react.MessageReactionRemoveEvent;

import java.util.ArrayList;

import static net.brinkervii.lovegood.commands.clash.ClashConstants.BLUE_CIRCLE;
import static net.brinkervii.lovegood.commands.clash.ClashConstants.RED_CIRCLE;
import static net.brinkervii.lovegood.core.LovegoodConstants.CLASH_UPDATE_INTERVAL;
import static net.brinkervii.lovegood.core.LovegoodConstants.CLASH_WAITBEFORE_UPDATE;

@LovegoodService
@Slf4j
public class ClashUpdater {
	LovegoodContext context;
	private ArrayList<ActiveClash> clashes = new ArrayList<>();
	private Thread ticker = null;

	public void init() {
		context.setClashUpdater(this);

		context.getJdaManager().addListener(new LovegoodListener() {
			void onReaction(ActiveClash clash, MessageReaction reaction, int direction) {
				try {
					MessageReaction.ReactionEmote reactionEmote = reaction.getReactionEmote();
					if (reactionEmote.getName().equals(RED_CIRCLE)) {
						clash.changeLeftVotes(direction);
					} else if (reactionEmote.getName().equals(BLUE_CIRCLE)) {
						clash.changeRightVotes(direction);
					}
					clash.send();

					removeConcludedClashes();
				} catch (Exception e) {
					log.error("Failed to update a clash:\n\n" + StacktraceUtil.concat(e));
				}
			}

			@Override
			public void onMessageReactionAdd(MessageReactionAddEvent event) {
				ActiveClash clash = getClashByMessage(event.getMessageIdLong());
				if (clash == null) return;
				onReaction(clash, event.getReaction(), 1);
			}

			@Override
			public void onMessageReactionRemove(MessageReactionRemoveEvent event) {
				ActiveClash clash = getClashByMessage(event.getMessageIdLong());
				if (clash == null) return;
				onReaction(clash, event.getReaction(), -1);
			}
		});
	}

	private ActiveClash getClashByMessage(long messageIdLong) {
		for (ActiveClash clash : clashes) {
			if (clash.getMessageIdLong() == messageIdLong) {
				return clash;
			}
		}

		return null;
	}

	public void add(ActiveClash clash) {
		clashes.add(clash);

		if (this.ticker != null) return;
		this.ticker = new Thread(() -> {
			try {
				Thread.sleep(CLASH_WAITBEFORE_UPDATE);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			while (clashes.size() > 0) {
				for (ActiveClash activeClash : clashes) {
					if (activeClash.isChanged()) {
						activeClash.updateMessageString();
						activeClash.send();
					}
				}

				try {
					Thread.sleep(CLASH_UPDATE_INTERVAL);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

			ticker = null;
		});

		this.ticker.start();
	}

	public boolean removeConcludedClashes() {
		return new ArrayListCleaner<>(clashes, ActiveClash::concluded).clean();
	}

	public ArrayList<ActiveClash> getClashes() {
		return clashes;
	}

	public int numberOfClashes() {
		return clashes.size();
	}
}
