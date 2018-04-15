package net.brinkervii.lovegood.config;

import net.brinkervii.lovegood.annotation.ConfigurationClass;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.brinkervii.lovegood.util.HibernateUtil;

public class DBConfiguration implements ConfigurationClass {

	@Override
	public void configure() {
		LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
		context.setHibernateSessionFactory(HibernateUtil.getSessionFactory());

		int bp = 0;
	}
}
