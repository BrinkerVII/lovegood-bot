package net.brinkervii.lovegood.core.beans;

import net.brinkervii.lovegood.annotation.Bean;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.brinkervii.lovegood.job.ClashInvalidatorJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

@Bean
public class QuartzBoot implements Runnable {

	@Override
	public void run() {
		final LovegoodContext context = LovegoodContextHolder.getInstance().getContext();

		try {
			final Scheduler scheduler = new StdSchedulerFactory().getScheduler();
			context.setScheduler(scheduler);

			addJobs(scheduler);
			scheduler.start();
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}

	private void addJobs(Scheduler scheduler) {
		try {
			ClashInvalidatorJob.schedule(scheduler);
		} catch (SchedulerException e) {
			e.printStackTrace();
		}
	}
}
