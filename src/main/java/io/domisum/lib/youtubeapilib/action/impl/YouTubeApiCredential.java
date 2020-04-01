package io.domisum.lib.youtubeapilib.action.impl;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class YouTubeApiCredential
{
	
	@Getter
	private final String clientId;
	@Getter
	private final String clientSecret;
	@Getter
	private final String refreshToken;
	
}