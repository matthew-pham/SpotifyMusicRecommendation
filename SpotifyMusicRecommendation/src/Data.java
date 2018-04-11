import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class Data {
	private File file = new File("data.txt");
	private boolean created = false;
	
	public void manageData() {
		
		if(!file.exists()) {
			created = true;
			String path = System.getProperty("user.dir")+ File.separator + "data.txt";
			File f = new File(path);

			f.getParentFile().mkdirs(); 
			try {
				f.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public boolean getCreated() {
		return created;
	}
	public void writeToFile(String[] tracks) {
		try
		{
		    PrintWriter pr = new PrintWriter(file);    

		    for (int i = 0; i < tracks.length; i++)
		    {
		        pr.println(tracks[i]);
		    }
		    pr.close();
		}
		catch (Exception e)
		{
		    e.printStackTrace();
		    System.out.println("No such file exists.");
		}
	}
}
