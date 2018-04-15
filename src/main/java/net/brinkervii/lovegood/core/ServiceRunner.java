package net.brinkervii.lovegood.core;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.Bean;
import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Bean
@Slf4j
public class ServiceRunner implements Runnable {
	private ArrayList<Object> services;
	private InjectionProfile injectionProfile = new InjectionProfile();

	@Override
	public void run() {
		services = new ArrayList<>();
		LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
		injectionProfile.provide(context, context.getJDA());

		try {
			AnnotationScanner scanner = new AnnotationScanner(LovegoodService.class);
			scanner.scan(LovegoodConstants.PACKAGE);

			for (Class<?> clazz : scanner.getClasses()) {
				log.info("Found service " + clazz.getName());
				LovegoodService annotation = clazz.getAnnotation(LovegoodService.class);
				if (annotation.debug() && !context.debug()) {
					log.info(String.format("Ignoring service %s, because debugging is disabled", clazz.getName()));
					continue;
				}

				Object o = clazz.newInstance();
				services.add(o);
				injectionProfile.apply(o);

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
