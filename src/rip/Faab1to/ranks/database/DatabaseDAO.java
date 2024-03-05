package rip.Faab1to.ranks.database;

import java.util.concurrent.CompletableFuture;

import org.bukkit.entity.Player;

import rip.Faab1to.ranks.user.RankUser;

public interface DatabaseDAO {

    CompletableFuture<RankUser> getUser(Player player);
    void saveUser(RankUser user);

}