package net.brinkervii.lovegood.core;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.jda.JDAManager;
import net.brinkervii.lovegood.service.ClashUpdater;
import net.dv8tion.jda.core.JDA;
import org.hibernate.SessionFactory;
import org.quartz.Scheduler;

import javax.security.auth.login.LoginException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static net.brinkervii.lovegood.core.LovegoodConstants.PROP_APPLICATION_DEBUG;
import static net.brinkervii.lovegood.core.LovegoodConstants.PROP_APPLICATION_TOKEN;

@Slf4j
public class LovegoodContext {
	private final JDAManager jdaManager;
	private ApplicationProperties properties = new ApplicationProperties();
	private String commandPrefix = ">";
	private ClashUpdater clashUpdater;
	private Date startDate = new Date();
	private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	private SessionFactory sessionFactory;
	private Scheduler scheduler;

	LovegoodContext() {
		log.info(String.format("Debug mode: %s", String.valueOf(debug())));

		this.jdaManager = new JDAManager();
		try {
			log.info("Logging in....");
			jdaManager.build(properties.get(PROP_APPLICATION_TOKEN));
		} catch (LoginException | InterruptedException e) {
			e.printStackTrace();
		}
	}

	public JDA getJDA() {
		return jdaManager.jda();
	}

	public JDAManager getJdaManager() {
		return jdaManager;
	}

	public String getCommandPrefix() {
		return commandPrefix;
	}

	public void setClashUpdater(ClashUpdater clashUpdater) {
		this.clashUpdater = clashUpdater;
	}

	public ClashUpdater getClashUpdater() {
		return clashUpdater;
	}

	public boolean debug() {
		if (properties.containsKey(PROP_APPLICATION_DEBUG)) {
			return Boolean.parseBoolean(properties.get(PROP_APPLICATION_DEBUG));
		}

		return false;
	}

	public SimpleDateFormat dateFormat() {
		return dateFormat;
	}

	public String startDateAsString() {
		return dateFormat.format(startDate);
	}

	public ApplicationProperties getProperties() {
		return properties;
	}

	public void setHibernateSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public void setScheduler(Scheduler scheduler) {
		this.scheduler = scheduler;
	}

	public Scheduler getScheduler() {
		return scheduler;
	}
}
