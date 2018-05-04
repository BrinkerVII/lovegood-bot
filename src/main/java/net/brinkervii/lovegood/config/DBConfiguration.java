package net.brinkervii.lovegood.config;

import net.brinkervii.lovegood.annotation.LovegoodConfiguration;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;
import net.brinkervii.lovegood.util.HibernateUtil;

public class DBConfiguration implements LovegoodConfiguration {

	@Override
	public void configure() {
		LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
		context.setHibernateSessionFactory(HibernateUtil.getSessionFactory());
	}
}
