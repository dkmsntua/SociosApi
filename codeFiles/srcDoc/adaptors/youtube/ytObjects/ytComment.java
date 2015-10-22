package adaptors.youtube.ytObjects;

import org.json.JSONObject;

public class ytComment {
	private String id;
	private String channelId;
	private String videoId;
	private String description;
	private String authorDisplayName;
	private String authorProfileImageUrl;
	private String authorChannelUrl;
	private String authorChannelId;
	private Integer likeCount;
	private String publishedAt;

	public ytComment() {
	}

	public ytComment(JSONObject json) {
		this.setId(json.optString("id", null));
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null) {
			JSONObject jsTopLevelComment = jssnippet.optJSONObject("topLevelComment");
			if (jsTopLevelComment != null) {
				JSONObject jsInnerSnippetComment = jsTopLevelComment.optJSONObject("snippet");
				if (jsInnerSnippetComment != null) {
					jssnippet = jsInnerSnippetComment;
				}
			}
			this.channelId = jssnippet.optString("channelId", null);
			this.videoId = jssnippet.optString("videoId", null);
			this.setDescription(jssnippet.optString("textDisplay", null));
			this.setAuthorDisplayName(jssnippet.optString("authorDisplayName", null));
			this.setAuthorProfileImageUrl(jssnippet.optString("authorProfileImageUrl", null));
			this.setAuthorChannelUrl(jssnippet.optString("authorChannelUrl", null));
			this.setPublishedAt(jssnippet.optString("publishedAt", null));
			this.setLikeCount(jssnippet.optInt("likeCount"));
			JSONObject jsAuthorChannelId = jssnippet.optJSONObject("authorChannelId");
			if (jsAuthorChannelId != null) {
				this.setAuthorChannelId(jsAuthorChannelId.optString("value", null));
			}
		}
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getAuthorDisplayName() {
		return authorDisplayName;
	}

	public void setAuthorDisplayName(String authorDisplayName) {
		this.authorDisplayName = authorDisplayName;
	}

	public String getAuthorProfileImageUrl() {
		return authorProfileImageUrl;
	}

	public void setAuthorProfileImageUrl(String authorProfileImageUrl) {
		this.authorProfileImageUrl = authorProfileImageUrl;
	}

	public String getAuthorChannelUrl() {
		return authorChannelUrl;
	}

	public void setAuthorChannelUrl(String authorChannelUrl) {
		this.authorChannelUrl = authorChannelUrl;
	}

	public String getAuthorChannelId() {
		return authorChannelId;
	}

	public void setAuthorChannelId(String authorChannelId) {
		this.authorChannelId = authorChannelId;
	}

	public Integer getLikeCount() {
		return likeCount;
	}

	public void setLikeCount(int i) {
		this.likeCount = i;
	}

	public String getPublishedAt() {
		return publishedAt;
	}

	public void setPublishedAt(String publishedAt) {
		this.publishedAt = publishedAt;
	}
}
