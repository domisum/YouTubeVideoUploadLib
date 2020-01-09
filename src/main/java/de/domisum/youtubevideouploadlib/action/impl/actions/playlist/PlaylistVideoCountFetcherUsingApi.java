package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Playlists.List;
import com.google.api.services.youtube.model.PlaylistContentDetails;
import com.google.api.services.youtube.model.PlaylistListResponse;
import de.domisum.lib.auxilium.util.java.annotations.API;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistVideoCountFetcher;
import de.domisum.youtubevideouploadlib.exceptions.PlaylistDoesNotExistException;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@API
@RequiredArgsConstructor
public class PlaylistVideoCountFetcherUsingApi implements PlaylistVideoCountFetcher
{

	// REFERENCES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// FETCH
	@Override
	public int fetch(String playlistId) throws IOException
	{
		YouTube youTube = authorizedYouTubeApiClient.getYouTubeApiClient();
		List request = youTube.playlists().list("contentDetails");
		request.setId(playlistId);

		PlaylistListResponse response = request.execute();
		if(response.getItems().isEmpty())
			throw new PlaylistDoesNotExistException();

		PlaylistContentDetails contentDetails = response.getItems().get(0).getContentDetails();
		return contentDetails.getItemCount().intValue();
	}

}