package net.brinkervii.lovegood.service;

import net.brinkervii.lovegood.annotation.LovegoodService;
import net.brinkervii.lovegood.annotation.LovegoodServiceParams;
import net.brinkervii.lovegood.core.singletons.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.client.entities.IncomingFriendRequest;
import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;

@LovegoodServiceParams
public class FriendRequestResponder implements LovegoodService {
	LovegoodContext context;

	public void run() {
		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
				IncomingFriendRequest friendRequest = event.getFriendRequest();
				System.out.println(friendRequest.getUser().getName());
			}
		});
	}
}
