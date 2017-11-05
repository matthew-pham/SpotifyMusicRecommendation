import java.security.NoSuchAlgorithmException;

public class IoTSmartHouse {

	public static void main(String[] args)throws NoSuchAlgorithmException{
		
		//AccountManager create = new AccountManager();
		//create.createAccount("Matthew","matthew");
		
		Authentication authenticate = new Authentication();
		authenticate.authenticate("bob", "bob");
	}
}
