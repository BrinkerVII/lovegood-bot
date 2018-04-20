package net.brinkervii.lovegood.commands.clash;

public enum ClashFace {
	GRINNING(":grinning:"),
	GRIN(":grin:"),
	SMILEY(":smiley:"),
	SMILE(":smile:"),
	SCREAM(":scream:"),
	OPEN_MOUTH(":open_mouth:"),
	SKULL(":skull:"),
	NEUTRAL_FACE(":neutral_face:");

	private final String shortcode;

	ClashFace(String shortcode) {
		this.shortcode = shortcode;
	}

	public static ClashFace DEFAULT() {
		return SMILE;
	}

	public String getShortcode() {
		return shortcode;
	}

	public boolean equals(ClashFace other) {
		return shortcode.equals(other.shortcode);
	}
}
