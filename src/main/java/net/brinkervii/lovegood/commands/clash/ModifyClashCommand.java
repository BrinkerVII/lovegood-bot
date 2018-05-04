package net.brinkervii.lovegood.commands.clash;

import net.brinkervii.lovegood.annotation.LovegoodCommand;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.service.commands.ParsedCommandInput;
import net.brinkervii.lovegood.service.commands.RunnableCommand;

import java.util.ArrayList;

@LovegoodCommand(name = "modclash", debug = true)
public class ModifyClashCommand implements RunnableCommand {
	LovegoodContext context;
	ParsedCommandInput input;

	@Override
	public void run() {
		int amount = input.getInt(0);
		ArrayList<ActiveClash> clashes = context.getClashUpdater().getClashes();
		for (ActiveClash clash : clashes) {
			if (amount < 0) {
				clash.changeLeftVotes(Math.abs(amount));
			} else if (amount > 0) {
				clash.changeRightVotes(amount);
			}
		}

		context.getClashUpdater().removeConcludedClashes();
	}
}
