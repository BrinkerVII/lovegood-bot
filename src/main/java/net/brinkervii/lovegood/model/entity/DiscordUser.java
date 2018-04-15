package net.brinkervii.lovegood.model.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Entity
public class DiscordUser {
	@Id
	@Column(name = "id")
	private Long id;
}
