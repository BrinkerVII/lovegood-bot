package net.brinkervii.lovegood.util;

import java.util.Random;

public class StringUtil {
	private static final String RANDOM_CHARSET = "abcdefghijklmnopqrstuvwxyz01234567899";

	public static String random(int length) {
		Random random = new Random(System.currentTimeMillis());
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; i < length; i++) {
			stringBuilder.append(RANDOM_CHARSET.substring(random.nextInt(RANDOM_CHARSET.length() - 1), 1));
		}

		return stringBuilder.toString();
	}
}
