import org.bson.Document;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

public class Mongo {

	static MongoDatabase database;
	static MongoCollection<Document> collection;
	
	
	public static void init() throws IOException {
		String clientURI = readFile("src/config.txt", StandardCharsets.UTF_8);
		MongoClientURI connectionString = new MongoClientURI(clientURI);
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
	static String readFile(String path, Charset encoding) 
			  throws IOException 
			{
			  byte[] encoded = Files.readAllBytes(Paths.get(path));
			  return new String(encoded, encoding);
			}
}
