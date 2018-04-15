package net.brinkervii.lovegood.commands.clash;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static net.brinkervii.lovegood.commands.clash.ClashFace.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ClashFaceScaleTest {

	private ClashFaceScale scale;

	@Test
	void scaleFunctionality() {
		scale
				.set(.1, GRINNING)
				.set(.2, GRIN)
				.set(.3, SMILEY)
				.set(.4, SMILE)
				.set(.5, SCREAM)
				.set(.6, OPEN_MOUTH)
				.set(.7, SKULL);

		assertTrue(scale.get(.1).equals(GRINNING));
		assertTrue(scale.get(.2).equals(GRIN));
		assertTrue(scale.get(.3).equals(SMILEY));
		assertTrue(scale.get(.4).equals(SMILE));
		assertTrue(scale.get(.5).equals(SCREAM));
		assertTrue(scale.get(.6).equals(OPEN_MOUTH));
		assertTrue(scale.get(.7).equals(SKULL));
	}

	@Test
	void irregularScaleFunction() {
		scale
				.set(.1, GRINNING)
				.set(.2, GRIN)
				.set(.3, SMILEY)
				.set(.4, SMILE)
				.set(.5, SCREAM)
				.set(.6, OPEN_MOUTH)
				.set(.7, SKULL);

		assertTrue(scale.get(.05).equals(GRINNING));
		assertTrue(scale.get(.15).equals(GRIN));
		assertTrue(scale.get(.25).equals(SMILEY));
		assertTrue(scale.get(.35).equals(SMILE));
		assertTrue(scale.get(.45).equals(SCREAM));
		assertTrue(scale.get(.55).equals(OPEN_MOUTH));
		assertTrue(scale.get(.65).equals(SKULL));
	}

	@Test
	void skullyZero() {
		scale.set(.4, GRINNING).set(0, SKULL);
		assertTrue(scale.get(0).equals(SKULL));
	}

	@BeforeEach
	void setUp() {
		this.scale = new ClashFaceScale();
	}

	@AfterEach
	void tearDown() {
		this.scale = null;
	}
}