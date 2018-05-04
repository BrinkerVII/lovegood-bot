package net.brinkervii.lovegood.util;

import net.brinkervii.lovegood.core.ApplicationProperties;
import net.brinkervii.lovegood.core.collections.EntityCollection;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Environment;

import java.util.HashMap;
import java.util.Map;

import static net.brinkervii.lovegood.core.LovegoodConstants.*;

public class HibernateUtil {
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
			settings.put(Environment.DRIVER, properties.get(PROP_DB_DRIVER_CLASS));
			settings.put(Environment.URL, properties.get(PROP_DB_CONNECTION_URL));
			settings.put(Environment.DIALECT, properties.get(PROP_DB_DIALECT));
			settings.put(Environment.HBM2DDL_AUTO, properties.get(PROP_DB_HBM2DDL_AUTO));
			settings.put(Environment.SHOW_SQL, properties.get(PROP_DB_SHOW_SQL));

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
			new EntityCollection().getEntities().forEach(sources::addAnnotatedClass);

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
