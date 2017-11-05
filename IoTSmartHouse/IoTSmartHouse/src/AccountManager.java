import static com.mongodb.client.model.Filters.eq;

import java.security.NoSuchAlgorithmException;
import java.util.UUID;

import org.bson.Document;



public class AccountManager {
	
	public void createAccount(String username, String password) throws NoSuchAlgorithmException {
		Mongo connect = new Mongo();
		connect.init(); 
		Encryption salt = new Encryption();
		 
		 String saltGen = salt.getSaltString();
		String passwordAndSalt = password + saltGen;
	 
		Encryption hash = new Encryption();
		String hashedPass = hash.hash256(passwordAndSalt);
		
		UUID uuidGen = UUID.randomUUID();
		String uuid = String.valueOf(uuidGen);
		
		 Document doc = new Document("User", username)
	                .append("Salt", saltGen)
	                .append("Password", hashedPass)
	                .append("UUID",uuid)
	                .append("Comments", "");
		 connect.getMongoCollection().insertOne(doc);
		 
		 System.out.println("Account Successfully Created");
		
	}
	
	public void deleteAccount(String username, String password) throws NoSuchAlgorithmException {
		
		Mongo connect = new Mongo();
		connect.init(); 
		Document myDoc = connect.getMongoCollection().find(eq("User", username)).first();
			
			String query = myDoc.toJson();
			String dbSalt = query.substring(query.indexOf("Salt")+ 9, query.indexOf("Salt")+ 27);
		
		 Encryption hash = new Encryption();
		 String hashedPass = hash.hash256(password + dbSalt);

		
		 Document doc = new Document("User", username)
				 .append("Password", hashedPass);
		 
		 connect.getMongoCollection().deleteOne(doc);
		
		 System.out.println("Account Successfully Deleted");
	}

}
