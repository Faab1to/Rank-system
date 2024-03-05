package rip.Faab1to.ranks.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import rip.Faab1to.ranks.Ranks;
import rip.Faab1to.ranks.user.RankUser;

public class PlayerQuitListener implements Listener {

	private final Ranks plugin;

	public PlayerQuitListener(Ranks plugin) {
		this.plugin = plugin;
		this.plugin.getServer().getPluginManager().registerEvents(this, plugin);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent event) {
		event.setQuitMessage(null);
		RankUser user = this.plugin.getPlayerManager().getUserMap().remove(event.getPlayer());
		this.plugin.getDatabaseDAO().saveUser(user);
	}
}