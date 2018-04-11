package net.brinkervii.lovegood.core;

import net.brinkervii.lovegood.annotation.Bean;

@Bean
public class TesterClass implements Runnable {
	@Override
	public void run() {
		System.out.println("I am a thread :D");
	}
}
