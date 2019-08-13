package de.domisum.youtubevideouploadlib.action.impl.actions;

import com.google.api.services.youtube.YouTube.Videos.Update;
import com.google.api.services.youtube.model.Video;
import com.google.api.services.youtube.model.VideoSnippet;
import de.domisum.youtubevideouploadlib.action.videometadata.VideoMetadataSetter;
import de.domisum.youtubevideouploadlib.action.impl.AuthorizedYouTubeApiClient;
import de.domisum.youtubevideouploadlib.model.video.YouTubeVideoMetadata;
import lombok.RequiredArgsConstructor;

import java.io.IOException;

@RequiredArgsConstructor
public class VideoMetadataSetterUsingApi implements VideoMetadataSetter
{

	// DEPENDENCIES
	private final AuthorizedYouTubeApiClient authorizedYouTubeApiClient;


	// SET
	@Override public void setMetadata(String videoId, YouTubeVideoMetadata metadata) throws IOException
	{
		Video video = new Video();
		video.setId(videoId);
		video.setSnippet(createVideoSnippet(metadata));

		Update videosUpdateRequest = authorizedYouTubeApiClient.getYouTubeApiClient().videos().update("snippet", video);
		videosUpdateRequest.execute();
	}

	private VideoSnippet createVideoSnippet(YouTubeVideoMetadata metadata)
	{
		VideoSnippet snippet = new VideoSnippet();
		snippet.setTitle(metadata.getTitle());
		snippet.setDescription(metadata.getDescription());
		snippet.setTags(metadata.getTags());
		snippet.setCategoryId(metadata.getCategory().categoryId+"");
		return snippet;
	}

}
