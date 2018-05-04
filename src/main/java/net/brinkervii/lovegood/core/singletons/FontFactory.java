package net.brinkervii.lovegood.core.singletons;

import java.awt.*;
import java.io.*;
import java.util.HashMap;

public class FontFactory {
	private static final FontFactory instance = new FontFactory();

	public static FontFactory getInstance() {
		return instance;
	}

	private HashMap<String, Font> cache = new HashMap<>();

	private FontFactory() {

	}

	public Font load(int fontType, String path) throws IOException, FontFormatException {
		if (cache.containsKey(path)) return cache.get(path);

		File file = new File(path);
		Font font = null;
		if (file.exists()) {
			InputStream inputStream = new FileInputStream(file);
			font = Font.createFont(fontType, inputStream);
		} else {
			Font.createFont(fontType, System.class.getResourceAsStream(path));
		}

		if (font == null) {
			throw new FileNotFoundException();
		}

		cache.put(path, font);
		return font;
	}
}
