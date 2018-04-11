package net.brinkervii.lovegood.service;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.core.InjectionProfile;
import net.brinkervii.lovegood.core.LovegoodConstants;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Set;

@LovegoodService
@Slf4j
public class CommandInterpreter {
	HashMap<String, Class<RunnableCommand>> commands = new HashMap<>();
	LovegoodContext context;
	private InjectionProfile injectionProfile = new InjectionProfile();

	public CommandInterpreter() {
		try {
			AnnotationScanner scanner = new AnnotationScanner(LovegoodCommand.class);
			scanner.scan(LovegoodConstants.PACKAGE);
			for (Class<?> clazz : scanner.getClasses()) {
				Annotation annotation = clazz.getAnnotation(LovegoodCommand.class);
				String commandString = ((LovegoodCommand) annotation).name();
				commands.put(commandString, (Class<RunnableCommand>) clazz);
			}
		} catch (NotAnAnnotationException e) {
			e.printStackTrace();
		}

	}

	public void init() {
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
							.provide(interpreter)
							.lock();

					String command = content.substring(context.getCommandPrefix().length());
					if (commands.containsKey(command)) {
						ParsedCommandInput input = new ParsedCommandInput(command);
						cmdInjectionProfile.apply(input);

						try {
							RunnableCommand commandInstance = commands.get(command).newInstance();
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
		Thread t = new Thread(runnable::run);
		t.start();
	}

	public Set<String> getCommandList() {
		return commands.keySet();
	}
}
