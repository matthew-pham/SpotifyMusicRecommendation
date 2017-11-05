import java.security.NoSuchAlgorithmException;

public class IoTSmartHouse {

	public static void main(String[] args)throws NoSuchAlgorithmException{
		Mongo connect = new Mongo();
		connect.init(); 
		AccountManager create = new AccountManager();
		//create.createAccount("matt", "matt");
		//create.changePassword("matt","matthew","bob");
		
		Authentication authenticate = new Authentication();
		authenticate.authenticate("matt", "bob");
	}
}
