package net.brinkervii.lovegood.service;

import lombok.extern.slf4j.Slf4j;
import net.brinkervii.lovegood.annotation.AnnotationScanner;
import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.core.LovegoodConstants;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.brinkervii.lovegood.exception.NotAnAnnotationException;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.lang.annotation.Annotation;
import java.util.HashMap;

@LovegoodService
@Slf4j
public class CommandInterpreter {
	HashMap<String, RunnableCommand> commands = new HashMap<>();
	LovegoodContext context;

	public CommandInterpreter() throws IllegalAccessException, InstantiationException {
		try {
			AnnotationScanner scanner = new AnnotationScanner(LovegoodCommand.class);
			scanner.scan(LovegoodConstants.PACKAGE);
			for (Class<?> clazz : scanner.getClasses()) {
				Annotation annotation = clazz.getAnnotation(LovegoodCommand.class);
				String commandString = ((LovegoodCommand) annotation).commandString();

				Object o = clazz.newInstance();
				LovegoodContextHolder.putObjectFields(clazz, o);
				if (o instanceof RunnableCommand) {
					commands.put(commandString, (RunnableCommand) o);
				}
			}
		} catch (NotAnAnnotationException e) {
			e.printStackTrace();
		}

	}

	public void init() {
		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				String content = event.getMessage().getContentRaw();
				if (content.startsWith(context.getCommandPrefix())) {
					String command = content.substring(context.getCommandPrefix().length());
					if (commands.containsKey(command)) {
						log.info("Running command " + command);
						runCommand(commands.get(command), event);
					}
				}
			}
		});
	}

	private void runCommand(final RunnableCommand runnable, final MessageReceivedEvent src) {
		Thread t = new Thread(new Runnable() {
			@Override
			public void run() {
				runnable.run(src);
			}
		});

		t.start();
	}
}
