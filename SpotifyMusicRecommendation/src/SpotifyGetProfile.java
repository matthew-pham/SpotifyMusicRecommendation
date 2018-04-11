import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.exceptions.SpotifyWebApiException;
import com.wrapper.spotify.model_objects.specification.User;
import com.wrapper.spotify.requests.data.users_profile.GetCurrentUsersProfileRequest;

import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

public class SpotifyGetProfile {
	private String accessToken;
	private String userID;
	
	public SpotifyGetProfile(String accessToken) {
		this.accessToken = accessToken;
		
	}

	  
	  public void getCurrentUsersProfile() {
		  SpotifyApi spotifyApi = new SpotifyApi.Builder()
		          .setAccessToken(accessToken)
		          .build();
		  
		  GetCurrentUsersProfileRequest getCurrentUsersProfileRequest = spotifyApi.getCurrentUsersProfile()
		          .build();
		    try {
		      final Future<User> userFuture = getCurrentUsersProfileRequest.executeAsync();
		      final User user = userFuture.get();
		      userID = user.getId();

		    } catch (InterruptedException | ExecutionException e) {
		      System.out.println("Error: " + e.getCause().getMessage());
		    }
		  }
	  
	  public String getUserID() {
		  return userID;
	  }
	  
	
}
