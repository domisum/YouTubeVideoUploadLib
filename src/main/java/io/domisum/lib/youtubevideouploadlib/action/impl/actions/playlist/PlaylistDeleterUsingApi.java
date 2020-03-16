package io.domisum.lib.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import io.domisum.lib.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import io.domisum.lib.youtubevideouploadlib.action.playlist.PlaylistDeleter;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistDeleterUsingApi implements PlaylistDeleter
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override
	public void delete(String playlistId) throws IOException
	{
		YouTube youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		youtube.playlists().delete(playlistId).execute();
	}

}