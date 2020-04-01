package io.domisum.lib.youtubeapilib.action.impl;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential.Builder;
import com.google.api.client.http.HttpRequestInitializer;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtubeAnalytics.v2.YouTubeAnalytics;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Duration;

@RequiredArgsConstructor
public class AuthorizedYouTubeApiClient
{
	
	// CONSTANTS
	private static final Duration TIMEOUT = Duration.ofMinutes(5);
	
	// REFERENCES
	@Getter
	private final YouTubeApiCredentialProvider youTubeApiCredentialProvider;
	
	
	// INIT
	public AuthorizedYouTubeApiClient(YouTubeApiCredential youTubeApiCredential)
	{
		youTubeApiCredentialProvider = ()->youTubeApiCredential;
	}
	
	
	// GETTERS
	public YouTube getYouTubeApiClient()
	{
		var requestInitializer = getInitializerWithTimeout(createCredential());
		return new YouTube.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer)
				.setApplicationName("YouTubeApiLib")
				.build();
	}
	
	public YouTubeAnalytics getYouTubeAnalyticsApiClient()
	{
		var requestInitializer = getInitializerWithTimeout(createCredential());
		return new YouTubeAnalytics.Builder(new NetHttpTransport(), new JacksonFactory(), requestInitializer)
				.setApplicationName("YouTubeApiLib")
				.build();
	}
	
	
	private HttpRequestInitializer getInitializerWithTimeout(HttpRequestInitializer requestInitializer)
	{
		return httpRequest->
		{
			requestInitializer.initialize(httpRequest);
			httpRequest.setConnectTimeout((int) TIMEOUT.toMillis());
			httpRequest.setReadTimeout((int) TIMEOUT.toMillis());
		};
	}
	
	private Credential createCredential()
	{
		var youTubeApiCredential = youTubeApiCredentialProvider.get();
		
		var credential = new Builder()
				.setJsonFactory(new JacksonFactory())
				.setTransport(new NetHttpTransport())
				.setClientSecrets(youTubeApiCredential.getClientId(), youTubeApiCredential.getClientSecret())
				.build()
				.setRefreshToken(youTubeApiCredential.getRefreshToken());
		return credential;
	}
	
}
