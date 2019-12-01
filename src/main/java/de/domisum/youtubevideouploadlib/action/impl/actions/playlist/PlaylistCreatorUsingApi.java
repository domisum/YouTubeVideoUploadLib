package de.domisum.youtubevideouploadlib.action.impl.actions.playlist;

import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.YouTube.Playlists.Insert;
import com.google.api.services.youtube.model.Playlist;
import com.google.api.services.youtube.model.PlaylistSnippet;
import com.google.api.services.youtube.model.PlaylistStatus;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.action.playlist.PlaylistCreator;
import de.domisum.youtubevideouploadlib.model.playlist.YouTubePlaylistSpec;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class PlaylistCreatorUsingApi implements PlaylistCreator
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// UPLOAD
	@Override
	public String create(YouTubePlaylistSpec youTubePlaylistSpec) throws IOException
	{
		Playlist playlist = createRequestPlaylist(youTubePlaylistSpec);

		Insert playlistsInsertRequest = createInsertRequest(playlist);
		Playlist response = playlistsInsertRequest.execute();

		return response.getId();
	}

	private Playlist createRequestPlaylist(YouTubePlaylistSpec youTubePlaylistSpec)
	{
		Playlist playlist = new Playlist();

		PlaylistSnippet snippet = new PlaylistSnippet();
		snippet.setTitle(youTubePlaylistSpec.getTitle());
		snippet.setDescription(youTubePlaylistSpec.getDescription());
		playlist.setSnippet(snippet);

		PlaylistStatus status = new PlaylistStatus();
		status.setPrivacyStatus(youTubePlaylistSpec.getPrivacyStatus().name());
		playlist.setStatus(status);
		return playlist;
	}

	private Insert createInsertRequest(Playlist playlist) throws IOException
	{
		YouTube youtube = authorizedYouTubeApiClient.getYouTubeApiClient();
		return youtube.playlists().insert("snippet,status", playlist);
	}

}
