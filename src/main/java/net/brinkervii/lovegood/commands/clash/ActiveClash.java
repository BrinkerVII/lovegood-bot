package net.brinkervii.lovegood.commands.clash;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;

import static net.brinkervii.lovegood.commands.clash.ClashConstants.BLUE_CIRCLE;
import static net.brinkervii.lovegood.commands.clash.ClashConstants.RED_CIRCLE;

public class ActiveClash {
	final static String CLASH_TEMPLATE = ":red_circle::japanese_goblin: $0>:sparkles: :atom: :sparkles:<$1 :japanese_goblin::large_blue_circle:";
	final static int MAX_LENGTH = 5;
	private final MessageChannel channel;
	private Message message = null;
	private int leftVotes = 0;
	private int rightVotes = 0;

	public ActiveClash(MessageChannel channel) {
		this.channel = channel;
	}

	private String makeString(int left, int right) {
		StringBuilder lstring = new StringBuilder();
		for (int i = 0; i < Math.abs(left); i++) {
			lstring.append("=");
		}

		StringBuilder rstring = new StringBuilder();
		for (int i = 0; i < Math.abs(right); i++) {
			rstring.append("=");
		}

		return CLASH_TEMPLATE.replace("$0", lstring.toString()).replace("$1", rstring.toString());
	}


	public void step() {
		int balance = leftVotes - rightVotes;
//		System.out.println("BALANCE = " + balance);

		String s = makeString(MAX_LENGTH + balance, MAX_LENGTH - balance);
		if (message == null) {
			message = channel.sendMessage(s).complete();
			message.addReaction(RED_CIRCLE).complete();
			message.addReaction(BLUE_CIRCLE).complete();
		} else {
			message.editMessage(s).complete();
		}
	}

	public long getMessageIdLong() {
		return message.getIdLong();
	}

	public void changeLeftVotes(int count) {
		leftVotes += count;
		step();
	}

	public void changeRightVotes(int count) {
		rightVotes += count;
		step();
	}
}
