package net.brinkervii.lovegood.job;

import net.brinkervii.lovegood.commands.clash.ActiveClash;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.core.singletons.LovegoodContextHolder;
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

		// Clash updated may be null during initialization
		if (clashUpdater == null) return;

		for (ActiveClash clash : clashUpdater.getClashes()) {
			clash.updateMessageString();
			if(clash.concluded()) {
				clash.send();
			}
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
								.withIntervalInSeconds(10)
								.repeatForever()
				)
				.build();

		scheduler.scheduleJob(jobDetail, trigger);
	}
}
