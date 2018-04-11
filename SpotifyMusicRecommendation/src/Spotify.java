import java.io.File;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.neovisionaries.i18n.CountryCode;
import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.model_objects.special.SnapshotResult;
import com.wrapper.spotify.model_objects.specification.ArtistSimplified;
import com.wrapper.spotify.model_objects.specification.Paging;
import com.wrapper.spotify.model_objects.specification.Playlist;
import com.wrapper.spotify.model_objects.specification.Track;
import com.wrapper.spotify.requests.data.playlists.AddTracksToPlaylistRequest;
import com.wrapper.spotify.requests.data.playlists.CreatePlaylistRequest;
import com.wrapper.spotify.requests.data.search.simplified.SearchTracksRequest;

public class Spotify {
	private String accessToken;
	private String userID;
	private String playlistID;
	private ArrayList <String> recommendPlaylist;
	  private String[] uris;
	  private static final String name = "Training Playlisttest";
	
	public Spotify(String accessToken, String userID, ArrayList recommendPlaylist) {
		this.accessToken = accessToken;
		this.userID = userID;
		this.recommendPlaylist = recommendPlaylist;
	}
	
	public void createPlaylist() {
		
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(accessToken)
		          .build();
		
		CreatePlaylistRequest createPlaylistRequest = spotifyApi.createPlaylist(userID, name)
		          .collaborative(false)
		          .public_(false)
		          .description("Playlist to train neural network.")
		          .build();
		
	    try {
	      final Future<Playlist> playlistFuture = createPlaylistRequest.executeAsync();

	      // ...

	      final Playlist playlist = playlistFuture.get();
	      playlistID = playlist.getId();
	    } catch (InterruptedException | ExecutionException e) {
	      System.out.println("Error: " + e.getCause().getMessage());
	    }
	  }
	
	public void addTracks() {
		SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(accessToken)
		          .build();
		 AddTracksToPlaylistRequest addTracksToPlaylistRequest = spotifyApi
		          .addTracksToPlaylist(userID, playlistID, getTracks())
		          .position(0)
		          .build();
		 try {
		      final Future<SnapshotResult> snapshotResultFuture = addTracksToPlaylistRequest.executeAsync();

		      // ...

		      final SnapshotResult snapshotResult = snapshotResultFuture.get();

		    } catch (InterruptedException | ExecutionException e) {
		      System.out.println("Error: " + e.getCause().getMessage());
		    }
	}
	
	
	public String[] getTracks() {
		Random rand = new Random();
		ArrayList<String> trackURIs = new ArrayList<String>();
		ArrayList<String> added = new ArrayList<String>();
		ArrayList<Integer> random = new ArrayList<Integer>();
	     int numAdded = 0;
	     int counter = 0;
		while(numAdded < 25) {
			int index = rand.nextInt(recommendPlaylist.size());
			while(random.contains(index)) {
				index = rand.nextInt(recommendPlaylist.size());
			}
			random.add(index);
			String artist = recommendPlaylist.get(index).substring(0, recommendPlaylist.get(index).indexOf("-") - 1);
			String query = recommendPlaylist.get(index).substring(recommendPlaylist.get(index).indexOf("-") + 2);
			
			if(artist.equals("Ke$ha")) {
				artist = "Kesha";
			}
			
			SpotifyApi spotifyApi = new SpotifyApi.Builder()
			          .setAccessToken(accessToken)
			          .build();
			SearchTracksRequest searchTracksRequest = spotifyApi.searchTracks(query)
			          .market(CountryCode.US)
			          .limit(10)
			          .offset(0)
			          .build();
			try {
			      final Future<Paging<Track>> pagingFuture = searchTracksRequest.executeAsync();

			      // ...

			      final Paging<Track> trackPaging = pagingFuture.get();
			      
			     Track[] track = trackPaging.getItems();
		
			    
			    	 for(int j = 0; j < track.length; j++) {
			    	ArtistSimplified[] artistsArr = track[j].getArtists();
			    	
			    	String compareSpotify = track[j].getName().replaceAll("\\s+","").toLowerCase();
			    	String compareQuery = query.replaceAll("\\s+","").toLowerCase();
			    	String artistSpotify = artistsArr[0].getName().replaceAll("\\s+","").toLowerCase();
			    	String artistQuery = artist.replaceAll("\\s+","").toLowerCase();
			    	
			    	if(artistSpotify.contains(artistQuery) && compareSpotify.contains(compareQuery))
			    	{
			    		
			    		trackURIs.add(track[j].getUri());
			    		added.add(query); 
			    		System.out.println(numAdded + 1 + " Added " + query + " - " + artist);
			    		numAdded++;
			    		break;
			    	}
			    	 }
			     
			    
			   
			}catch (InterruptedException | ExecutionException e) {
		
			    }
			
			counter++;
		}
		
		uris = new String[trackURIs.size()];
		for(int i = 0; i < uris.length; i++) {
			uris[i] = trackURIs.get(i);
		}
		
		
		return uris;
	}
	
	public void train(File f) {
		boolean isFirstRun = true;
		try {
		Scanner sc = new Scanner(f);
		while(sc.hasNextLine()) {
			if(sc.nextLine().contains("matthewpham")) {
				isFirstRun = false;
			}
		}
		}catch(Exception e) {
			
		}
		
		if(isFirstRun) {
			System.out.println();
			System.out.println("Listen to the playlist of 25 songs and say whether or not you like each song!");
			for(int i = 0; i < uris.length; i++) {
				
			}
		}
		
	}
	
	public String[] getURIS() {
		return uris;
	}
	public String getPlaylistID() {
		return playlistID;
	}
}
