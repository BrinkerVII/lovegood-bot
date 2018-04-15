package net.brinkervii.lovegood.commands.clash;

import java.util.HashMap;
import java.util.Map;

public class ClashFaceScale {
	private final static double VERY_SMALL = .00001f;
	private HashMap<Double, ClashFace> innerScale = new HashMap<>();

	public ClashFaceScale set(double d, ClashFace face) {
		innerScale.put(d, face);
		return this;
	}

	public ClashFace get(double d) {
		Map.Entry<Double, ClashFace> current = null;
		for (Map.Entry<Double, ClashFace> entry : innerScale.entrySet()) {
			if (d <= entry.getKey()) {
				if (current == null) {
					current = entry;
					continue;
				}

				if (entry.getKey() < current.getKey()) {
					current = entry;
				}
			}
		}

		if (current == null) {
			return ClashFace.SKULL;
		}

		return current.getValue();
	}
}
