package net.brinkervii.lovegood.util;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MoreMathTest {
	@Test
	void clampWithPositiveRange() {
		int low = 0;
		int high = 10;
		int value = 5;

		int result = MoreMath.clamp(value, low, high);

		assertEquals(value, result);
	}

	@Test
	void clampWithPositiveRangeExpectingToClamp() {
		int low = 0;
		int high = 10;
		int vlow = -10;
		int vhigh = 20;

		int resultLow = MoreMath.clamp(vlow, low, high);
		int resultHigh = MoreMath.clamp(vhigh, low, high);

		assertEquals(low, resultLow);
		assertEquals(high, resultHigh);
	}


}