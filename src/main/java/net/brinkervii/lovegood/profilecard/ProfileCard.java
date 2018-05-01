package net.brinkervii.lovegood.profilecard;

import com.sun.xml.internal.messaging.saaj.util.ByteOutputStream;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

import static net.brinkervii.lovegood.core.LovegoodConstants.RESOURCE_PROFILE_CARD;

public class ProfileCard {
	private static BufferedImage background;

	static {
		try {
			final InputStream resourceAsStream = System.class.getResourceAsStream(RESOURCE_PROFILE_CARD);
			background = ImageIO.read(resourceAsStream);
		} catch (IOException e) {
			background = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		}
	}

	private final MessageReceivedEvent event;
	private BufferedImage image;
	private BufferedImage avatar;

	public ProfileCard(MessageReceivedEvent event) {
		this.event = event;
		this.image = new BufferedImage(background.getWidth(), background.getHeight(), BufferedImage.TYPE_INT_ARGB);
		drawBackground();

		// getAvatar();
		// drawAvatar(); // This no workey yet
	}

	private void drawAvatar() {
		Graphics2D graphics = image.createGraphics();
		drawAvatar(graphics);
		graphics.dispose();
	}

	private void drawAvatar(Graphics2D graphics) {
		graphics.drawImage(avatar, 0, 0, null);
	}

	private void getAvatar() {
		try {
			URL avatarURL = new URL(event.getAuthor().getAvatarUrl());
			final BufferedImage avatar = ImageIO.read(avatarURL);
			this.avatar = avatar;
		} catch (IOException e) {
			e.printStackTrace();
			this.avatar = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
		}
	}

	private void drawBackground() {
		Graphics2D graphics = image.createGraphics();
		drawBackground(graphics);
		graphics.dispose();
	}

	private void drawBackground(Graphics2D graphics) {
		graphics.drawImage(background, 0, 0, null);
	}

	public byte[] toBytes() throws IOException {
		ByteOutputStream byteOutputStream = new ByteOutputStream();
		ImageIO.write(image, "png", byteOutputStream);
		return byteOutputStream.getBytes();
	}
}
