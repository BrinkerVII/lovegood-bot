package net.brinkervii.lovegood.service;

import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodService(debug = true)
public class MessageReader {
	private LovegoodContext context;

	public void init() {
		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				String join = "MESSAGE:: " + event.getMessage();
				System.out.println(join);
			}
		});
	}
}
