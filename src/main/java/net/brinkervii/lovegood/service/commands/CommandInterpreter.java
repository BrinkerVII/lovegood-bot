package net.brinkervii.lovegood.service.commands;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.annotation.LovegoodServiceParams;
import net.brinkervii.lovegood.core.InjectionProfile;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.brinkervii.lovegood.util.StacktraceUtil;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@LovegoodServiceParams
@Slf4j
public class CommandInterpreter implements LovegoodService {
	HashMap<String, Class<RunnableCommand>> commands = new HashMap<>();
	LovegoodContext context;
	private InjectionProfile injectionProfile = new InjectionProfile();

	public CommandInterpreter() {

	}

	public void run() {
		CommandCollection commandCollection = new CommandCollection();
		commandCollection.getCommandClasses(context.debug()).forEach(commandClass -> {
			LovegoodCommand annotation = commandClass.getAnnotation(LovegoodCommand.class);
			commands.put(annotation.name().toLowerCase(), (Class<RunnableCommand>) commandClass);
		});

		injectionProfile.provide(context, context.getJDA()).lock();
		CommandInterpreter interpreter = this;

		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				String content = event.getMessage().getContentRaw();
				if (content.startsWith(context.getCommandPrefix())) {
					InjectionProfile cmdInjectionProfile = new InjectionProfile()
						.provide(context)
						.provide(event)
						.provide(context.getJDA())
						.provide(interpreter);

					ParsedCommandInput input = new ParsedCommandInput();
					cmdInjectionProfile.apply(input);

					if (commands.containsKey(input.command())) {
						try {
							cmdInjectionProfile.provide(input).lock();

							Class<RunnableCommand> runnableCommandClass = commands.get(input.command());

							RunnableCommand commandInstance = runnableCommandClass.newInstance();
							cmdInjectionProfile.apply(commandInstance);
							runCommand(commandInstance);
						} catch (InstantiationException | IllegalAccessException e) {
							e.printStackTrace();
						}
					}
				}
			}
		});
	}

	private void runCommand(final RunnableCommand runnable) {
		Thread t = new Thread(() -> {
			try {
				runnable.run();
			} catch (Exception e) {
				log.error("Failed to execute a command\n\n" + StacktraceUtil.concat(e));
			}
		});
		t.start();
	}

	public List<String> getCommandList() {
		List<String> commandsList = new ArrayList<>();

		commands.forEach((name, clazz) -> {
			LovegoodCommand annotation = clazz.getAnnotation(LovegoodCommand.class);
			if (annotation != null) {
				if (annotation.debug() && !context.debug()) {

				} else {
					commandsList.add(name);
				}
			}
		});

		return commandsList;
	}
}
