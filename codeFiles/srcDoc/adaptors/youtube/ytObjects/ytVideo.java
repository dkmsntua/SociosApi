package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ytVideo {
	private String duration;
	private String embedHtml;
	private long filesize;
	private String id;
	private String license;
	private YtvSnippet snippet;
	private YtvStatistics statistics;

	public ytVideo() {
	}

	public ytVideo(JSONObject json) {
		JSONObject jsId = json.optJSONObject("id");
		if (jsId == null) {
			this.setId(json.optString("id", null));
		}
		else {
			this.setId(jsId.optString("videoId", null));
		}
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null) {
			this.setSnippet(new YtvSnippet(jssnippet));
		}
		this.setDuration(ParseUtilities.doubleJsParse(json, "contentDetails", "duration"));
		this.setEmbedHtml(ParseUtilities.doubleJsParse(json, "player", "embedHtml"));
		JSONObject jsstatistics = json.optJSONObject("statistics");
		if (jsstatistics != null) {
			this.setStatistics(new YtvStatistics(jsstatistics));
		}
		this.setLicense(ParseUtilities.doubleJsParse(json, "status", "license"));
		JSONObject jsfileDetails = json.optJSONObject("fileDetails");
		if (jsfileDetails != null) {
			this.setFilesize(jsfileDetails.optLong("fileSize"));
		}
	}

	public YtvStatistics getStatistics() {
		return statistics;
	}

	public void setStatistics(YtvStatistics statistics) {
		this.statistics = statistics;
	}

	public YtvSnippet getSnippet() {
		return snippet;
	}

	public void setSnippet(YtvSnippet snippet) {
		this.snippet = snippet;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getFilesize() {
		return filesize;
	}

	public void setFilesize(long filesize) {
		this.filesize = filesize;
	}

	public String getEmbedHtml() {
		return embedHtml;
	}

	public void setEmbedHtml(String embedHtml) {
		this.embedHtml = embedHtml;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}
	public class YtvSnippet {
		private String categoryId;
		private String channelId;
		private String channelTitle;
		private String description;
		private String publishedAt;
		private List<String> tags;
		private YtvThumbnails thumbnails;
		private String title;

		public YtvSnippet() {
		}

		public YtvSnippet(JSONObject json) {
			this.setPublishedAt(json.optString("publishedAt", null));
			this.setChannelId(json.optString("channelId", null));
			this.setTitle(json.optString("title", null));
			this.setDescription(json.optString("description", null));
			JSONObject jsthumbnails = json.optJSONObject("thumbnails");
			if (jsthumbnails != null) {
				this.setThumbnails(new YtvThumbnails(jsthumbnails));
			}
			this.setChannelTitle(json.optString("channelTitle", null));
			JSONArray tagsArray = json.optJSONArray("tags");
			if (Utilities.isValid(tagsArray)) {
				this.tags = new ArrayList<String>();
				for (int index = 0; index < tagsArray.length(); index++) {
					this.tags.add(tagsArray.optString(index, null));
				}
			}
			this.setCategoryId(json.optString("categoryId", null));
		}

		public List<String> getTags() {
			return tags;
		}

		public void setTags(List<String> tags) {
			this.tags = tags;
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public YtvThumbnails getThumbnails() {
			return thumbnails;
		}

		public void setThumbnails(YtvThumbnails thumbnails) {
			this.thumbnails = thumbnails;
		}

		public String getPublishedAt() {
			return publishedAt;
		}

		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}

		public String getChannelTitle() {
			return channelTitle;
		}

		public void setChannelTitle(String channelTitle) {
			this.channelTitle = channelTitle;
		}

		public String getChannelId() {
			return channelId;
		}

		public void setChannelId(String channelId) {
			this.channelId = channelId;
		}

		public String getCategoryId() {
			return categoryId;
		}

		public void setCategoryId(String categoryId) {
			this.categoryId = categoryId;
		}
	}
	public class YtvStatistics {
		private int commentCount;
		private int dislikeCount;
		private int favoriteCount;
		private int likeCount;
		private int viewCount;

		public YtvStatistics() {
		}

		public YtvStatistics(JSONObject json) {
			this.setViewCount(json.optInt("viewCount", -1));
			this.setLikeCount(json.optInt("likeCount", -1));
			this.setDislikeCount(json.optInt("dislikeCount", -1));
			this.setFavoriteCount(json.optInt("favoriteCount", -1));
			this.setCommentCount(json.optInt("commentCount", -1));
		}

		public int getViewCount() {
			return viewCount;
		}

		public void setViewCount(int viewCount) {
			this.viewCount = viewCount;
		}

		public int getLikeCount() {
			return likeCount;
		}

		public void setLikeCount(int likeCount) {
			this.likeCount = likeCount;
		}

		public int getFavoriteCount() {
			return favoriteCount;
		}

		public void setFavoriteCount(int favoriteCount) {
			this.favoriteCount = favoriteCount;
		}

		public int getDislikeCount() {
			return dislikeCount;
		}

		public void setDislikeCount(int dislikeCount) {
			this.dislikeCount = dislikeCount;
		}

		public int getCommentCount() {
			return commentCount;
		}

		public void setCommentCount(int commentCount) {
			this.commentCount = commentCount;
		}
	}
	public class YtvThumbnails {
		private String defaultThumb;
		private String highThumb;
		private String mediumThumb;

		public YtvThumbnails() {
		}

		public YtvThumbnails(JSONObject json) {
			this.setDefaultThumb(ParseUtilities.doubleJsParse(json, "default", "url"));
			this.setMediumThumb(ParseUtilities.doubleJsParse(json, "medium", "url"));
			this.setHighThumb(ParseUtilities.doubleJsParse(json, "high", "url"));
		}

		public String getMediumThumb() {
			return mediumThumb;
		}

		public void setMediumThumb(String mediumThumb) {
			this.mediumThumb = mediumThumb;
		}

		public String getHighThumb() {
			return highThumb;
		}

		public void setHighThumb(String highThumb) {
			this.highThumb = highThumb;
		}

		public String getDefaultThumb() {
			return defaultThumb;
		}

		public void setDefaultThumb(String defaultThumb) {
			this.defaultThumb = defaultThumb;
		}
	}
}
