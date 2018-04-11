
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class MusicRecommend {
	private String song;
	private ArrayList<String> queryResultsArtist = new ArrayList<String>();
	private ArrayList<String> similarTracks = new ArrayList<String>();
	private ArrayList<String> similarArtists = new ArrayList<String>();
	private ArrayList<String> playList = new ArrayList<String>();
	private String search;
	private String artist;
	

	public MusicRecommend(String songName) {
		if(songName.contains(" ")) {
		search = songName;
		song = songName.replace(" ", "%20");
		}else {
		search = songName;
		song = songName;
		}
	}
	
	
	public MusicRecommend() {
		// TODO Auto-generated constructor stub
	}


	public void queryArtist() {

		System.out.println("Artists with a song matching '" + search + "' :" );
		String uri = "http://ws.audioscrobbler.com/2.0/?method=track.search&track="+song+"API_KEY";
		JSONParser parser = new JSONParser();	
		try {
			URL url = new URL(uri);
			URLConnection uc = url.openConnection();
			String jsonString = "";
			Scanner sc = new Scanner(uc.getInputStream());
			while(sc.hasNextLine()) {
				jsonString += sc.nextLine();
			}
			
			JSONObject j = (JSONObject) parser.parse(jsonString);
			JSONObject j1 = (JSONObject) j.get("results");
			JSONObject j2 = (JSONObject) j1.get("trackmatches");
			JSONArray jArr = (JSONArray)j2.get("track");
			for(int i = 0; i < jArr.size(); i++) {
				JSONObject var = (JSONObject) jArr.get(i);
				String artist = var.get("artist").toString();
				if(!queryResultsArtist.contains(artist) && !artist.contains("www.") && !artist.contains("&") &&!artist.contains("Feat") && !artist.contains("feat") && !artist.contains("and") && !artist.contains("AND") && !artist.contains(",") && !artist.contains("(") && !artist.contains("/") && !artist.contains("\\") && !artist.contains("'") && !artist.contains(".") && !artist.contains(" ft ")) {
					queryResultsArtist.add(artist);
				}			
			}
			
			for(int i = 0; i < queryResultsArtist.size(); i++) {
				System.out.println(i + 1 + " " + queryResultsArtist.get(i));
			}
			System.out.println();
			
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			System.out.println("Enter the number of the artist of your specified song: ");
			Scanner sc = new Scanner(System.in);
			int chosen = sc.nextInt();
			System.out.println( "You chose: " + chosen + ", which is " + queryResultsArtist.get(chosen - 1));
			System.out.println();
			artist = queryResultsArtist.get(chosen - 1);
	}
		public String getArtist() {
			return artist;
		}
		
		
		
	public void recommend(String input, String s) {
		String artistToSearch = "";
		if(input.contains(" ")) {
			artistToSearch = input.toLowerCase().replace(" ", "+");
		}else {
			artistToSearch = input.toLowerCase();
		}
		
		if(s.contains(" ")) {
			s = s.replace(" ", "%20");
			}
		
		String uri = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=" + artistToSearch + "&track="+ s +"API_KEY";
		JSONParser parser = new JSONParser();
		try {
			URL url = new URL(uri);
			URLConnection uc = url.openConnection();
			String jsonString = "";
			Scanner sc = new Scanner(uc.getInputStream());
			while(sc.hasNextLine()) {
				jsonString += sc.nextLine();
			}
			
			JSONObject j = (JSONObject) parser.parse(jsonString);
			JSONObject j1 = (JSONObject) j.get("similartracks");

			try {
			JSONArray jArr = (JSONArray) j1.get("track");
			for(int i = 0; i < jArr.size(); i++) {
				JSONObject var = (JSONObject) jArr.get(i);
				String title = var.get("name").toString();
				similarTracks.add(title);
				JSONObject j3 = (JSONObject) var.get("artist");
				similarArtists.add(j3.get("name").toString());
			}
			}catch(NullPointerException e) {
				//System.err.println("Sorry, no similar tracks found for that song");
	
			}
			
			
			if(similarTracks.size() == 0) {
		
			}
			
			Random rand = new Random();
				
			
			for(int i = 0; i < 10; i++) {
				int index = rand.nextInt(similarTracks.size());
				while(similarTracks.get(index).equals("matthewpham")) {
					index = rand.nextInt(similarTracks.size());
				}
				playList.add(similarArtists.get(index) + " - " + similarTracks.get(index));
				similarTracks.set(index, "matthewpham");
			}
			
			
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void modifiedRecommend(String input, String s) {
		String artistToSearch = "";
		if(input.contains(" ")) {
			artistToSearch = input.toLowerCase().replace(" ", "+");
		}else {
			artistToSearch = input.toLowerCase();
		}
		
		if(s.contains(" ")) {
			s = s.replace(" ", "%20");
			}
		
		String uri = "http://ws.audioscrobbler.com/2.0/?method=track.getsimilar&artist=" + artistToSearch + "&track="+ s +"&api_key=c0f8c2f5cca206cffe9ac17c00438e27&format=json";
		JSONParser parser = new JSONParser();
		try {
			URL url = new URL(uri);
			URLConnection uc = url.openConnection();
			String jsonString = "";
			Scanner sc = new Scanner(uc.getInputStream());
			while(sc.hasNextLine()) {
				jsonString += sc.nextLine();
			}
			
			JSONObject j = (JSONObject) parser.parse(jsonString);
			JSONObject j1 = (JSONObject) j.get("similartracks");

			try {
			JSONArray jArr = (JSONArray) j1.get("track");
			for(int i = 0; i < jArr.size(); i++) {
				JSONObject var = (JSONObject) jArr.get(i);
				String title = var.get("name").toString();
				similarTracks.add(title);
				JSONObject j3 = (JSONObject) var.get("artist");
				similarArtists.add(j3.get("name").toString());
			}
			}catch(NullPointerException e) {
				//System.err.println("Sorry, no similar tracks found for that song");
	
			}
			
			
			if(similarTracks.size() == 0) {
		
			}
			
			Random rand = new Random();
				
			
			for(int i = 0; i < 20; i++) {
				int index = rand.nextInt(similarTracks.size());
				while(similarTracks.get(index).equals("matthewpham")) {
					index = rand.nextInt(similarTracks.size());
				}
				playList.add(similarArtists.get(index) + " - " + similarTracks.get(index));
				similarTracks.set(index, "matthewpham");
			}
			
			
		
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}


	public ArrayList<String> getPlayList() {
		// TODO Auto-generated method stub
		return playList;
	}


	
}
