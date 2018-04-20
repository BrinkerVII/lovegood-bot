package net.brinkervii.lovegood.util;

public class MessageUtil {

	private static final String HERE = "@here";
	private static final String EVERYONE = "@everyone";

	public static boolean containsAllMention(String message) {
		String lower = message.toLowerCase();
		return lower.contains(EVERYONE) || lower.contains(HERE);
	}
}
