import java.net.URI;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import com.wrapper.spotify.SpotifyApi;
import com.wrapper.spotify.SpotifyHttpManager;
import com.wrapper.spotify.model_objects.credentials.AuthorizationCodeCredentials;
import com.wrapper.spotify.requests.authorization.authorization_code.AuthorizationCodeRequest;

public class SpotifyAuth {
	 private final String clientID = "clientID";
	 private final String clientSecret = "clientSecret";
	 private final URI redirectUri = SpotifyHttpManager.makeUri("redirectUri");
	 private String accessToken;
	 private String refreshToken;
	  
	  private final SpotifyApi spotifyApi = new SpotifyApi.Builder()
	          .setClientId(clientID)
	          .setClientSecret(clientSecret)
	          .setRedirectUri(redirectUri)
	          .build();
	  
	  
	  
	  private AuthorizationCodeRequest authorizationCodeRequest;
	  
	  public SpotifyAuth(String code) {
		  authorizationCodeRequest = spotifyApi.authorizationCode(code).build();

	  }
	  
	  
	  public void authorizationCode() {
		    try {
		      final Future<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = authorizationCodeRequest.executeAsync();

		      // ...

		      final AuthorizationCodeCredentials authorizationCodeCredentials = authorizationCodeCredentialsFuture.get();

	
		      spotifyApi.setAccessToken(authorizationCodeCredentials.getAccessToken());
		      spotifyApi.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		      accessToken = authorizationCodeCredentials.getAccessToken();
		      refreshToken = authorizationCodeCredentials.getRefreshToken();	      
		    } catch (InterruptedException | ExecutionException e) {
		      System.out.println("Error: " + e.getCause().getMessage());
		    }
		  }
	  
	  public String getAccessToken() {
		  return accessToken;
	  }
	  
	  public String getRefreshToken() {
		  return refreshToken;
	  }
}
