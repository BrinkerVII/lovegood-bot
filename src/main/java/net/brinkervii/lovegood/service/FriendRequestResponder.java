package net.brinkervii.lovegood.service;

import net.brinkervii.lovegood.annotation.Service;
import net.brinkervii.lovegood.core.LovegoodContext;
import net.brinkervii.lovegood.jda.LovegoodListener;
import net.dv8tion.jda.client.entities.IncomingFriendRequest;
import net.dv8tion.jda.client.events.relationship.FriendRequestReceivedEvent;

@Service
public class FriendRequestResponder {
	LovegoodContext context;

	public void	init() {
		context.getJdaManager().addListener(new LovegoodListener() {
			@Override
			public void onFriendRequestReceived(FriendRequestReceivedEvent event) {
				IncomingFriendRequest friendRequest = event.getFriendRequest();
				System.out.println(friendRequest.getUser().getName());
			}
		});
	}
}
