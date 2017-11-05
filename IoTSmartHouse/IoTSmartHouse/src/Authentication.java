
import org.bson.Document;

import static com.mongodb.client.model.Filters.*;



import java.security.NoSuchAlgorithmException;


public class Authentication {
	public static boolean authenticate(String username, String inputPassword) throws NoSuchAlgorithmException {

		Mongo connect = new Mongo();
		connect.init(); 
		Document myDoc = connect.getMongoCollection().find(eq("User", username)).first();
		
		if(myDoc != null) {
		String query = myDoc.toJson();
		
		
		String dbSalt = query.substring(query.indexOf("Salt")+9, query.indexOf("Salt")+27);
		String dbPass = query.substring((query.indexOf("Password")+13), (query.indexOf("Password")+77));
		
		
		 
		Encryption hash = new Encryption();
		
		String hashedInputedPass = hash.hash256(inputPassword + dbSalt);
		
		
		System.out.println("Username: " + username);
		System.out.println(hashedInputedPass);
		System.out.println(dbPass);
		
		
		
		if(hashedInputedPass.equals(dbPass)) {
			System.out.println("Authentication Success");
			return true;
			
		}else {
			System.out.println("Authentication Fail");
			return false;
		}
		}
		else {
			System.out.println("Invalid User");
			return false;
		}
		
	}

}
