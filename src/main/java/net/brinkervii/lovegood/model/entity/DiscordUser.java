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

	@Column(name = "knuts") // Bronze knuts
	private int knuts = 0;
	@Column(name = "sickles") // Silver sickles
	private int sickles = 0; // 29 knuts = 1 sickle
	@Column(name = "galleons") // Golden galleons
	private int galleons = 0; // 17 sickles = 1 galleon
}
