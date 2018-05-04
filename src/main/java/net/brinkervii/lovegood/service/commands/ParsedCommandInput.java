package net.brinkervii.lovegood.service.commands;

import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import java.util.ArrayList;

public class ParsedCommandInput {
	private final static char ESCAPE_CHARACTER = '\\';
	private final static char DELIMETER = ' ';
	private MessageReceivedEvent event;
	private LovegoodContext context;
	private ArrayList<String> components = new ArrayList<>();
	private boolean parsed = false;

	public ParsedCommandInput() {

	}

	public void parse() {
		if (parsed) return;
		components = new ArrayList<>();
		StringBuilder stringBuilder = new StringBuilder();

		boolean escaping = false;
		String content = event.getMessage().getContentRaw().substring(context.getCommandPrefix().length());
		for (char c : content.toCharArray()) {
			if (c == ESCAPE_CHARACTER) {
				escaping = true;
				continue;
			}

			if (escaping) {
				stringBuilder.append(c);
				continue;
			}

			if (c == DELIMETER) {
				String s = stringBuilder.toString().trim();
				if (!s.isEmpty()) {
					components.add(s);
				}
				stringBuilder = new StringBuilder();
			} else {
				stringBuilder.append(c);
			}
		}
		String s = stringBuilder.toString().trim();
		if (!s.isEmpty()) {
			components.add(s);
		}

		parsed = true;
	}

	public String command() {
		parse();
		return components.get(0).toLowerCase();
	}

	public String[] arguments() {
		parse();
		if (components.size() == 1) {
			return new String[]{};
		}

		String[] args = new String[components.size() - 1];
		for (int i = 1; i < components.size(); i++) {
			args[i - 1] = components.get(i);
		}

		return args;
	}

	public int getInt(int i) {
		if (arguments()[i] == null) return 0;
		return Integer.parseInt(arguments()[i]);
	}
}
