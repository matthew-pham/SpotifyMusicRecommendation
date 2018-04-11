import java.util.ArrayList;
import java.util.Scanner;

public class MusicMain {
	
	static ArrayList<String> chosenArtists = new ArrayList<String>();
	static ArrayList<String> chosenSongs = new ArrayList<String>();
	static ArrayList<String> playlist;
	
	public static void main(String[] args) {
		Data data = new Data();
		data.manageData();
		if(data.getCreated()){
		System.out.println("Welcome to Music Recommender");
		System.out.println();
		System.out.println("You will add your top 5 favorite songs");
		System.out.print("Enter the name of your first favorite song: ");
		Scanner sc = new Scanner(System.in);
		String song1 = sc.nextLine();
		chosenSongs.add(song1);
		MusicRecommend m = new MusicRecommend(song1);
		m.queryArtist();
		chosenArtists.add(m.getArtist());
		System.out.print("Enter the name of your second favorite song: ");
		String song2 = sc.nextLine();
		chosenSongs.add(song2);
		MusicRecommend m1 = new MusicRecommend(song2);
		m1.queryArtist();
		chosenArtists.add(m1.getArtist());
		System.out.print("Enter the name of your third favorite song: ");
		String song3 = sc.nextLine();
		chosenSongs.add(song3);
		MusicRecommend m2 = new MusicRecommend(song3);
		m2.queryArtist();
		chosenArtists.add(m2.getArtist());
		System.out.print("Enter the name of your fourth favorite song: ");
		String song4 = sc.nextLine();
		chosenSongs.add(song4);
		MusicRecommend m3 = new MusicRecommend(song4);
		m3.queryArtist();
		chosenArtists.add(m3.getArtist());
		System.out.print("Enter the name of your fifth favorite song: ");
		String song5 = sc.nextLine();
		chosenSongs.add(song5);
		MusicRecommend m4 = new MusicRecommend(song5);
		m4.queryArtist();
		chosenArtists.add(m4.getArtist());
		
		System.out.println();
		MusicRecommend recommend = new MusicRecommend();
		for(int i = 0; i < 5; i++) {
			if(recommend.getPlayList().size() < (i * 10)){
				recommend.modifiedRecommend(chosenArtists.get(i), chosenSongs.get(i));
			}else {
			recommend.recommend(chosenArtists.get(i), chosenSongs.get(i));
			}
			
		}
		playlist = recommend.getPlayList();
		System.out.println("Based on your 5 favorite songs, here is a playlist: ");
		for(int i = 0; i < playlist.size(); i++) {
			System.out.println(i + 1 + " " + playlist.get(i));
		}
		
		WebBrowser wb = new WebBrowser();
		wb.launchBrowser();
		
		try {
			Thread.sleep(30000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		SpotifyAuth spotifyAuth = new SpotifyAuth(wb.getCode());
		spotifyAuth.authorizationCode();
		String accessToken = spotifyAuth.getAccessToken();
		SpotifyGetProfile user = new SpotifyGetProfile(accessToken);
		user.getCurrentUsersProfile();
		Spotify spotify = new Spotify(accessToken, user.getUserID(), playlist);
				spotify.createPlaylist();
				System.out.println();
				System.out.println("created playlist");
				System.out.println();
				spotify.addTracks();
				System.out.println("added tracks");
				data.writeToFile(spotify.getURIS());							
	}
	}
}
