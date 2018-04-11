package net.brinkervii.lovegood.core;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.Bean;
import net.brinkervii.lovegood.annotation.Service;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;
import net.dv8tion.jda.core.JDA;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Bean
@Slf4j
public class ServiceRunner implements Runnable {
	private ArrayList<Object> services;

	@Override
	public void run() {
		services = new ArrayList<>();
		LovegoodContext context = LovegoodContextHolder.getInstance().getContext();

		try {
			AnnotationScanner scanner = new AnnotationScanner(Service.class);
			scanner.scan(LovegoodConstants.PACKAGE);

			for (Class<?> clazz : scanner.getClasses()) {
				log.info("Found service " + clazz.getName());
				Object o = clazz.newInstance();
				services.add(o);

				LovegoodContextHolder.putObjectFields(clazz, o);

				for (Method method : clazz.getDeclaredMethods()) {
					if (method.getName().equals("init")) {
						method.setAccessible(true);
						method.invoke(o);
					}
				}
			}
		} catch (NotAnAnnotationException | IllegalAccessException | InstantiationException | InvocationTargetException e) {
			e.printStackTrace();
		}
	}
}
