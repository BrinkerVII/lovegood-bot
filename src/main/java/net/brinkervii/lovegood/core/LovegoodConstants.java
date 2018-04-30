package net.brinkervii.lovegood.core;

public class LovegoodConstants {
	public static final String PACKAGE = "net.brinkervii.lovegood";
	public static final long CLASH_UPDATE_INTERVAL = 1000L;
	public static final long CLASH_WAITBEFORE_UPDATE = 500L;

	// Property keys (General)
	public static final String PROP_APPLICATION_PROFILE = "lovegood.profile";
	public static final String PROP_APPLICATION_TOKEN = "lovegood.token";
	public static final String PROP_APPLICATION_DEBUG = "lovegood.debug";

	// Property keys (Clash)
	public static final String PROP_CLASH_LIFETIME = "lovegood.clash.lifetime";

	// Property keys (Database)
	public static final String PROP_DB_DRIVER_CLASS = "lovegood.db.connection.driver_class";
	public static final String PROP_DB_CONNECTION_URL = "lovegood.db.connection.url";
	public static final String PROP_DB_DIALECT = "lovegood.db.dialect";
	public static final String PROP_DB_HBM2DDL_AUTO = "lovegood.db.hbm2ddl_auto";
	public static final String PROP_DB_SHOW_SQL = "lovegood.db.show_sql";
}
