package net.brinkervii.lovegood.job;

import net.brinkervii.lovegood.commands.clash.ActiveClash;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.brinkervii.lovegood.service.ClashUpdater;
import org.quartz.*;

import static org.quartz.JobBuilder.newJob;
import static org.quartz.SimpleScheduleBuilder.simpleSchedule;
import static org.quartz.TriggerBuilder.newTrigger;

public class ClashInvalidatorJob implements Job {
	@Override
	public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
		final LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
		final ClashUpdater clashUpdater = context.getClashUpdater();

		for (ActiveClash clash : clashUpdater.getClashes()) {
			clash.step();
		}
		clashUpdater.removeConcludedClashes();
	}

	public static void schedule(Scheduler scheduler) throws SchedulerException {
		JobDataMap dataMap = new JobDataMap();

		JobDetail jobDetail = newJob(ClashInvalidatorJob.class)
				.withIdentity("invalidateClashes", "gc")
				.usingJobData(dataMap)
				.build();

		Trigger trigger = newTrigger()
				.withIdentity("invalidateClashes", "gc")
				.startNow()
				.withSchedule(
						simpleSchedule()
								.withIntervalInMinutes(1)
								.repeatForever()
				)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
	}
}
