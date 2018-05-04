package net.brinkervii.lovegood.service.commands;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.commands.*;
import net.brinkervii.lovegood.commands.clash.ClashCommand;
import net.brinkervii.lovegood.commands.clash.ModifyClashCommand;

import java.util.ArrayList;
import java.util.List;

public class CommandCollection {
	private List<Class<?>> commands = new ArrayList<>();

	public CommandCollection() {
		this
			.add(ClashCommand.class)
			.add(CommandsCommand.class)
			.add(EchoCommand.class)
			.add(PrefixCommand.class)
			.add(ProfileCommand.class)
			.add(SystemInformationCommand.class)
			.add(ModifyClashCommand.class);
	}

	private CommandCollection add(Class<?> commandClass) {
		commands.add(commandClass);
		return this;
	}

	public List<Class<?>> getCommandClasses(boolean debug) {
		if (debug) return commands;

		ArrayList<Class<?>> filteredList = new ArrayList<>();
		commands.forEach(commandClass -> {
			LovegoodCommand annotation = commandClass.getAnnotation(LovegoodCommand.class);
			if (annotation != null) {
				if (!annotation.debug()) {
					filteredList.add(commandClass);
				}
			}
		});

		return filteredList;
	}
}
