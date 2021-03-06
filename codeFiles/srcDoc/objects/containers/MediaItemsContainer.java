package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.MediaItem;
import objects.main.SociosException;

public class MediaItemsContainer {
	private List<MediaItem> mediaItems;
	private List<SociosException> exceptions;
	private String elapsedTime;

	public MediaItemsContainer() {
	}

	public List<MediaItem> getMediaItems() {
		if (mediaItems == null) {
			mediaItems = new ArrayList<MediaItem>();
		}
		return this.mediaItems;
	}

	public void setMediaItems(List<MediaItem> value) {
		this.mediaItems = value;
	}

	public List<SociosException> getExceptions() {
		if (exceptions == null) {
			exceptions = new ArrayList<SociosException>();
		}
		return this.exceptions;
	}

	public void setExceptions(List<SociosException> value) {
		this.exceptions = value;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String value) {
		this.elapsedTime = value;
	}
}
