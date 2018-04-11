package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.Bean;
import net.brinkervii.lovegood.annotation.Configuration;
import net.brinkervii.lovegood.exception.InitializationException;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;

import java.util.Arrays;

public class LovegoodBoot {
	public static void main(String[] arguments) {
		try {

			for (Class<?> clazz : Arrays.asList(Configuration.class, Bean.class)) {
				AnnotationScanner scanner = new AnnotationScanner(clazz);
				scanner.scan("net.brinkervii.lovegood");
				LovegoodRunner runner = new LovegoodRunner(scanner);
				runner.run();
			}
		} catch (NotAnAnnotationException e) {
			e.printStackTrace();
		} catch (InitializationException e) {
			e.printStackTrace();
		}
	}
}
