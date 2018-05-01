package net.brinkervii.lovegood.commands.clash;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.util.MoreMath;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import static net.brinkervii.lovegood.commands.clash.ClashConstants.*;
import static net.brinkervii.lovegood.core.LovegoodConstants.PROP_CLASH_LIFETIME;

@Slf4j
public class ActiveClash {
	final static int MAX_LENGTH = 5;

	private final MessageChannel channel;
	private final Member sourceMember;
	private final Member targetMember;
	private final ClashFaceScale faceScale;
	private final long start = System.currentTimeMillis();
	private long lifetime;

	private Message message = null;
	private int leftVotes = 0;
	private int rightVotes = 0;
	private ClashFace leftFace = ClashFace.SMILE;
	private ClashFace rightFace = ClashFace.SMILE;
	private Member winner;
	private boolean changed = false;

	private String currentMessagestring = null;

	public ActiveClash(LovegoodContext context, MessageChannel channel, Member sourceMember, Member targetMember) {
		this.channel = channel;
		this.sourceMember = sourceMember;
		this.targetMember = targetMember;

		this.lifetime = LIFETIME;
		if (context.getProperties().containsKey(PROP_CLASH_LIFETIME)) {
			try {
				this.lifetime = Long.parseLong(context.getProperties().get(PROP_CLASH_LIFETIME));
			} catch (Exception e) {
				this.lifetime = -1L;
			}

			if (this.lifetime <= 0) {
				this.lifetime = LIFETIME;
				log.warn("Lifetime variable from application settings could not be parsed properly");
			}
		}

		this.faceScale = new ClashFaceScale()
				.set(0f, ClashFace.SKULL)
				.set(.2f, ClashFace.SCREAM)
				.set(.4f, ClashFace.SCREAM)
				.set(1f, ClashFace.SMILE)
				.set(1.2f, ClashFace.OPEN_MOUTH)
				.set(1.4f, ClashFace.SMILEY)
				.set(2f, ClashFace.GRINNING);

		changed = true;
	}

	private String makeClashString(int left, int right) {
		StringBuilder lstring = new StringBuilder();
		for (int i = 0; i < Math.abs(left); i++) {
			lstring.append("=");
		}

		StringBuilder rstring = new StringBuilder();
		for (int i = 0; i < Math.abs(right); i++) {
			rstring.append("=");
		}

		String clashString = CLASH_TEMPLATE
				.replace("$0", lstring.toString())
				.replace("$1", rstring.toString())
				.replace("#0", leftFace.getShortcode())
				.replace("#1", rightFace.getShortcode());

		if (expired()) {
			clashString = clashString
					.replace("=", " ")
					.replace(">", " ")
					.replace("<", " ")
					.replace(":atom:", ":dash:")
					.replace(":sparkles:", " ");
		}

		return clashString;
	}

	public synchronized void updateMessageString() {
		final int DLEN = MAX_LENGTH * 2;

		int balance = leftVotes - rightVotes;
		int leftlen = MoreMath.clamp(MAX_LENGTH + balance, 0, DLEN);
		int rightlen = MoreMath.clamp(MAX_LENGTH - balance, 0, DLEN);

		if (leftlen <= 0) {
			winner = targetMember;
			this.changed = true;
		} else if (rightlen <= 0) {
			winner = sourceMember;
			this.changed = true;
		}

		if (expired()) {
			leftFace = ClashFace.NEUTRAL_FACE;
			rightFace = ClashFace.NEUTRAL_FACE;
		} else {
			double leftProgress = (double) leftlen / (double) MAX_LENGTH;
			leftFace = faceScale.get(leftProgress);
			double rightProgress = (double) rightlen / (double) MAX_LENGTH;
			rightFace = faceScale.get(rightProgress);
		}

		String descriptorString = String.format("CLASH! :red_circle:%s vs %s:large_blue_circle:", sourceMember.getAsMention(), targetMember.getAsMention());

		if (expired()) {
			descriptorString += " (Ran out of magic)\n";
		} else {
			if (winner == null) {
				descriptorString += " (No winner)\n";
			} else {
				descriptorString += " (The winner is " + winner.getEffectiveName() + ")\n";
				log.info("Added winner to descriptor string (%s)", winner.getAsMention());
			}
		}

		String clashLine = makeClashString(leftlen, rightlen);
		String messageString = descriptorString + clashLine;

		if (!concluded()) {
			messageString += "\n\nVote on who should win by clicking the reaction with their coloured circle";
		}

		this.currentMessagestring = messageString;
	}

	public synchronized void send() {
		send(false);
	}

	public synchronized void send(boolean forceSend) {
		if (!forceSend && !changed && message != null) return;

		if (message == null) {
			message = channel.sendMessage(currentMessagestring).complete();
			message.addReaction(RED_CIRCLE).complete();
			message.addReaction(BLUE_CIRCLE).complete();
			log.info("Sent clash message with response, clash members are %s and %s", sourceMember.getAsMention(), targetMember.getAsMention());
		} else {
			message.editMessage(currentMessagestring).complete();
			log.info("Edited clash message with response, clash members are %s and %s", sourceMember.getAsMention(), targetMember.getAsMention());
		}

		this.changed = false;
	}

	public long getMessageIdLong() {
		return message.getIdLong();
	}

	public synchronized void changeLeftVotes(int count) {
		leftVotes += count;
		changed = true;
		updateMessageString();
		log.info("Changed left votes to %d", leftVotes);
	}

	public synchronized void changeRightVotes(int count) {
		rightVotes += count;
		changed = true;
		updateMessageString();
		log.info("Changed right votes to %d", rightVotes);
	}

	public boolean concluded() {
		return winner != null || expired();
	}

	public boolean expired() {
		return System.currentTimeMillis() - start >= this.lifetime;
	}

	public boolean isChanged() {
		return changed;
	}
}
