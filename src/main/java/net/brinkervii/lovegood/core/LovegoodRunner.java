package net.brinkervii.lovegood.core;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.ConfigurationClass;
import net.brinkervii.lovegood.exception.InitializationException;

import java.util.ArrayList;

@Slf4j
public class LovegoodRunner {
	private final AnnotationScanner scanner;
	private ArrayList<Object> pool;
	private ArrayList<Thread> threads = new ArrayList<>();

	public LovegoodRunner(AnnotationScanner scanner) {
		this.scanner = scanner;
	}

	public void run() throws InitializationException {
		pool = new ArrayList<>();
		ArrayList<ConfigurationClass> configurables = new ArrayList<>();
		ArrayList<Runnable> runnables = new ArrayList<>();

		log.info(String.format("Running classes of type %s", scanner.getClazz().getName()));
		for (Class<?> clazz : scanner.getClasses()) {
			if (!clazz.isInterface()) {
				try {
					Object o;

					if (InstanceCache.getInstance().contains(clazz)) {
						o = InstanceCache.getInstance().get(clazz);
					} else {
						o = clazz.newInstance();
					}

					assert o != null;
					pool.add(o);

					if (o instanceof Runnable) {
						runnables.add((Runnable) o);
					}
					if (o instanceof ConfigurationClass) {
						configurables.add((ConfigurationClass) o);
					}
				} catch (InstantiationException | IllegalAccessException | NullPointerException e) {
					throw new InitializationException(e);
				}
			}
		}

		log.info(String.format("Got %d configurables", configurables.size()));
		for (ConfigurationClass cfg : configurables) {
			log.info(String.format("Configuring %s", cfg.getClass().getName()));
			cfg.configure();
		}

		log.info(String.format("Got %d runnables", runnables.size()));
		for (Runnable runnable : runnables) {
			Thread thread = new Thread(runnable);
			log.info(String.format("Starting thread for %s", runnable.getClass().getName()));
			thread.start();

			threads.add(thread);
		}

		configurables.clear();
		runnables.clear();
	}

	public void stop() {
		for (Thread thread : threads) {
			thread.interrupt();
		}
	}

	public int running() {
		int counter = 0;
		for (Thread t : threads) {
			if (t.isAlive()) {
				counter++;
			}
		}
		return counter;
	}
}
