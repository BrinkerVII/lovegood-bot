package net.brinkervii.lovegood.annotation;

import net.brinkervii.lovegood.exception.NotAnAnnotationException;
import org.reflections.Reflections;

import java.lang.annotation.Annotation;
import java.util.ArrayList;

public class AnnotationScanner {
	private final Class<? extends Annotation> clazz;
	private ArrayList<Class<?>> classes = new ArrayList<Class<?>>();

	public AnnotationScanner(Class<?> annotationType) throws NotAnAnnotationException {
		try {
			clazz = (Class<? extends Annotation>) annotationType;
		} catch (Exception e) {
			throw new NotAnAnnotationException(e);
		}
	}

	public void scan(String path) {
		Reflections ref = new Reflections(path);
		for (Class<?> cl : ref.getTypesAnnotatedWith(clazz)) {
			classes.add(cl);
		}
	}

	public ArrayList<Class<?>> getClasses() {
		return classes;
	}

	public Class<?> getClazz() {
		return clazz;
	}
}
