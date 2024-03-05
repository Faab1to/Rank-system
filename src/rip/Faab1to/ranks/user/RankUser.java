package rip.Faab1to.ranks.user;

import java.util.UUID;

import lombok.Getter;
import lombok.Setter;
import rip.Faab1to.ranks.rank.Rank;

@Getter
@Setter
public class RankUser {

	private final UUID uuid;
	private final String name;
	private Rank rank = Rank.DEFAULT; // default rank.

	public RankUser(UUID uuid, String name) {
		this.uuid = uuid;
		this.name = name;
	}
}