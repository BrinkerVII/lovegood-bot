package net.brinkervii.lovegood.util;

import static java.lang.Math.*;

public class MoreMath {
	/**
	 * Clamp a number to a range of numbers
	 * @param value The value to clamp
	 * @param limlow The lower target limit
	 * @param limhigh The upper target limit
	 * @return The clamped value
	 */
	public static int clamp(int value, int limlow, int limhigh) {
		return min(limhigh, max(limlow, value));
	}
}
