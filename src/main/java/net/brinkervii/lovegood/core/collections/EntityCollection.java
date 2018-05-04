package net.brinkervii.lovegood.core.collections;

import net.brinkervii.lovegood.model.entity.DiscordUser;

import java.util.ArrayList;
import java.util.List;

public class EntityCollection {
	private final List<Class<?>> entities = new ArrayList<>();

	public EntityCollection() {
		entities.add(DiscordUser.class);
	}

	public List<Class<?>> getEntities() {
		return entities;
	}
}
