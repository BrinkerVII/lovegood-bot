package net.brinkervii.lovegood.util;

import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.core.ApplicationProperties;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import javax.persistence.Entity;
import java.util.HashMap;
import java.util.Map;

import static net.brinkervii.lovegood.core.LovegoodConstants.PACKAGE;

public class HibernateUtil {
	private static final String DRIVER_CLASS = "lovegood.db.connection.driver_class";
	private static final String CONNECTION_URL = "lovegood.db.connection.url";
	private static final String DIALECT = "lovegood.db.dialect";
	private static final String HBM2DDL_AUTO = "lovegood.db.hbm2ddl_auto";
	private static final String SHOW_SQL = "lovegood.db.show_sql";

	private static SessionFactory sessionFactory;
	private static StandardServiceRegistry registry;

	public static SessionFactory getSessionFactory() {
		if (sessionFactory != null) {
			return sessionFactory;
		}

		try {
			StandardServiceRegistryBuilder registryBuilder = new StandardServiceRegistryBuilder();
			final ApplicationProperties properties = LovegoodContextHolder.getInstance().getContext().getProperties();

			Map<String, Object> settings = new HashMap<>();
			settings.put(Environment.DRIVER, properties.get(DRIVER_CLASS));
			settings.put(Environment.URL, properties.get(CONNECTION_URL));
			settings.put(Environment.DIALECT, properties.get(DIALECT));
			settings.put(Environment.HBM2DDL_AUTO, properties.get(HBM2DDL_AUTO));
			settings.put(Environment.SHOW_SQL, properties.get(SHOW_SQL));

			// Maximum waiting time for a connection from the pool
			settings.put("hibernate.hikari.connectionTimeout", "20000");
			// Minimum number of ideal connections in the pool
			settings.put("hibernate.hikari.minimumIdle", "10");
			// Maximum number of actual connection in the pool
			settings.put("hibernate.hikari.maximumPoolSize", "20");
			// Maximum time that a connection is allowed to sit ideal in the pool
			settings.put("hibernate.hikari.idleTimeout", "300000");

			registryBuilder.applySettings(settings);

			registry = registryBuilder.build();
			MetadataSources sources = new MetadataSources(registry);
			AnnotationScanner scanner = null;
			try {
				scanner = new AnnotationScanner(Entity.class);
				scanner.scan(PACKAGE);
				scanner.getClasses().forEach(sources::addAnnotatedClass);
			} catch (NotAnAnnotationException e) {
				e.printStackTrace();
			}

			Metadata metadata = sources.getMetadataBuilder().build();
			sessionFactory = metadata.getSessionFactoryBuilder().build();

		} catch (Exception e) {
			if (registry != null) {
				StandardServiceRegistryBuilder.destroy(registry);
			}
			e.printStackTrace();
		}

		return sessionFactory;
	}

	public static void shutdown() {
		if (registry != null) {
			StandardServiceRegistryBuilder.destroy(registry);
		}
	}
}
