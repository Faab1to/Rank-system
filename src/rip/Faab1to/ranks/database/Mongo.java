package rip.Faab1to.ranks.database;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ForkJoinPool;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.bukkit.entity.Player;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;

import rip.Faab1to.ranks.rank.Rank;
import rip.Faab1to.ranks.user.RankUser;

public class Mongo implements DatabaseDAO {

	private MongoClient mongoClient;
	private MongoCollection<Document> collection;

	public Mongo() {
		this.mongoClient = new MongoClient(
				new MongoClientURI("mongodb+srv://nermin:12345@database-5pjh0.mongodb.net/admin")); // edit this to your mongodb cluster / atlas.
		this.collection = this.mongoClient.getDatabase("database").getCollection("data");
	}

	@Override
	public CompletableFuture<RankUser> getUser(Player player) {
		return CompletableFuture.supplyAsync(() -> {
			UUID uuid = player.getUniqueId();
			RankUser user = new RankUser(uuid, player.getName());
			Document found = this.collection.find(new Document("uuid", uuid.toString())).first();
			if (found == null) {
				found = new Document("uuid", uuid.toString());
				found.append("rank", Rank.DEFAULT.toString());
				found.append("name", user.getName());
				this.collection.insertOne(found);
			} else {
				user.setRank(Rank.valueOf(found.getString("rank")));
			}
			return user;
		});
	}

	@Override
	public void saveUser(RankUser user) {
		ForkJoinPool.commonPool().execute(() -> {
			Document found = this.collection.find(new Document("uuid", user.getUuid().toString())).first();
			Bson newValue = new Document("rank", user.getRank().toString());
			Bson updateOperation = new Document("$set", newValue);
			this.collection.updateOne(found, updateOperation);
		});
	}
}