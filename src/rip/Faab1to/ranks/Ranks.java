package rip.Faab1to.ranks;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import lombok.Getter;
import rip.Faab1to.ranks.commands.RankBaseCommand;
import rip.Faab1to.ranks.database.DatabaseDAO;
import rip.Faab1to.ranks.database.Mongo;
import rip.Faab1to.ranks.listeners.PlayerJoinListener;
import rip.Faab1to.ranks.listeners.PlayerQuitListener;
import rip.Faab1to.ranks.manager.PlayerManager;

@Getter
public class Ranks extends JavaPlugin {

	private DatabaseDAO databaseDAO;
	private PlayerManager playerManager;

	@Getter
	public static Ranks instance;

	@SuppressWarnings("deprecation")
	public void onEnable() {
		instance = this;
		this.databaseDAO = new Mongo();
		this.playerManager = new PlayerManager();
		for (Player player : getServer().getOnlinePlayers()) {
			this.databaseDAO.getUser(player)
					.whenComplete((user, throwable) -> playerManager.getUserMap().put(player, user));
		}
		new RankBaseCommand(this);
		new PlayerJoinListener(this);
		new PlayerQuitListener(this);
	}

	public void onDisable() {
		this.playerManager.getUserMap().values().forEach(this.databaseDAO::saveUser);
	}
}