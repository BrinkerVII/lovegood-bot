package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.Bean;
import net.brinkervii.lovegood.annotation.Configuration;
import net.brinkervii.lovegood.exception.InitializationException;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;

import java.util.ArrayList;
import java.util.Arrays;

public class LovegoodBoot {
	public static void main(String[] arguments) {
		ArrayList<LovegoodRunner> runners = new ArrayList<>();
		try {
			for (Class<?> clazz : Arrays.asList(Configuration.class, Bean.class)) {
				AnnotationScanner scanner = new AnnotationScanner(clazz);
				scanner.scan(LovegoodConstants.PACKAGE);
				LovegoodRunner runner = new LovegoodRunner(scanner);
				runners.add(runner);
				runner.run();
			}
		} catch (NotAnAnnotationException e) {
			e.printStackTrace();
		} catch (InitializationException e) {
			e.printStackTrace();
		}

		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				for (LovegoodRunner runner : runners) {
					runner.stop();
				}
			}
		});

		boolean running = true;
		while (running) {
			int counter = 0;
			for (LovegoodRunner runner : runners) {
				if (runner.running() > 0) {
					counter++;
				}
			}

			if (counter <= 0) {
				running = false;
			}
		}
	}
}
