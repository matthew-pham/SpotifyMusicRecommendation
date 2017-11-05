import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;


public class Mongo {

	static MongoDatabase database;
	static MongoCollection<Document> collection;
	
	
	public static void init() {
		MongoClientURI connectionString = new MongoClientURI("mongodb://iotmatthew:3012OliverDR@iotsmarthouse-shard-00-00-bcd3h.mongodb.net:27017,iotsmarthouse-shard-00-01-bcd3h.mongodb.net:27017,iotsmarthouse-shard-00-02-bcd3h.mongodb.net:27017/test?ssl=true&replicaSet=IoTSmartHouse-shard-0&authSource=admin");
		 MongoClient mongoClient = new MongoClient(connectionString);
		 
		 database = mongoClient.getDatabase("login");
		 collection = database.getCollection("login");
		
	}
	
	public static MongoDatabase getMongo() {
		return database;
	}
	
	public static MongoCollection<Document> getMongoCollection(){
		return collection;
		
	}
}
