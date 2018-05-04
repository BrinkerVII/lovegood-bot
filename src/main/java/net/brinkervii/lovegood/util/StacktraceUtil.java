package net.brinkervii.lovegood.util;

public class StacktraceUtil {
	public static String concat(Exception e) {
		StringBuilder stringBuilder = new StringBuilder();
		for (StackTraceElement element : e.getStackTrace()) {
			stringBuilder.append(element.toString()).append('\n');
		}

		return stringBuilder.toString();
	}
}
