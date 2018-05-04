package net.brinkervii.lovegood.service.debugging;

import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.annotation.LovegoodServiceParams;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

@LovegoodServiceParams(debug = true)
public class MessageReader implements LovegoodService {
	private LovegoodContext context;

	public void run() {
		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {
				String join = "MESSAGE:: " + event.getMessage();
				System.out.println(join);
			}
		});
	}
}
