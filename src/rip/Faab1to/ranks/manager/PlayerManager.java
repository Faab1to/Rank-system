package rip.Faab1to.ranks.manager;

import java.util.IdentityHashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import lombok.Getter;
import rip.Faab1to.ranks.user.RankUser;

@Getter
public class PlayerManager {

	private Map<Player, RankUser> userMap;

	public PlayerManager() {
		this.userMap = new IdentityHashMap<>();
	}
}