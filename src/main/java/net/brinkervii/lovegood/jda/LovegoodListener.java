package net.brinkervii.lovegood.jda;

import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.core.LovegoodContextHolder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class LovegoodListener extends ListenerAdapter {
	protected LovegoodContext context = LovegoodContextHolder.getInstance().getContext();
	protected JDA jda = LovegoodContextHolder.getInstance().getContext().getJDA();
}
