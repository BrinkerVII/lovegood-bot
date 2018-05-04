package net.brinkervii.lovegood.service;

import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;
import net.brinkervii.lovegood.job.ClashInvalidatorJob;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.quartz.impl.StdSchedulerFactory;

public class QuartzBoot implements LovegoodService {
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
