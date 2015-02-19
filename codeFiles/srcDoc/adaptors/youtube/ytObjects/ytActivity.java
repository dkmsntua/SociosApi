package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class ytActivity {
	private String id;
	private String itemChannelId;
	private String itemVideoId;
	private YtaSnippet snippet;

	public ytActivity() {
	}

	public ytActivity(JSONObject json) {
		this.setId(json.optString("id", null));
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null) {
			this.setSnippet(new YtaSnippet(jssnippet));
		}
		if (Utilities.isValid(this.getSnippet().getType())) {
			JSONObject jscontentDetails = json.optJSONObject("contentDetails");
			if (jscontentDetails != null) {
				JSONObject jsholder = jscontentDetails.optJSONObject(this.getSnippet().getType());
				if (jsholder != null) {
					JSONObject jsresource = jsholder.optJSONObject("resourceId");
					if (jsresource != null) {
						this.setItemVideoId(jsresource.optString("videoId", null));
						this.setItemChannelId(jsresource.optString("channelId", null));
					}
					else {
						this.setItemVideoId(jsholder.optString("videoId", null));
					}
				}
			}
		}
	}

	public YtaSnippet getSnippet() {
		return snippet;
	}

	public void setSnippet(YtaSnippet snippet) {
		this.snippet = snippet;
	}

	public String getItemVideoId() {
		return itemVideoId;
	}

	public void setItemVideoId(String itemVideoId) {
		this.itemVideoId = itemVideoId;
	}

	public String getItemChannelId() {
		return itemChannelId;
	}

	public void setItemChannelId(String itemChannelId) {
		this.itemChannelId = itemChannelId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
	public class YtaSnippet {
		private String channelId;
		private String channelTitle;
		private String description;
		private String groupId;
		private String publishedAt;
		private List<String> thumbnails;
		private String title;
		private String type;

		public YtaSnippet() {
		}

		public YtaSnippet(JSONObject json) {
			this.setPublishedAt(json.optString("publishedAt", null));
			this.setChannelId(json.optString("channelId", null));
			this.setTitle(json.optString("title", null));
			this.setDescription(json.optString("description", null));
			JSONObject jsthumbnails = json.optJSONObject("thumbnails");
			if (jsthumbnails != null) {
				this.setThumbnails(new ArrayList<String>());
				String defaultThumb = ParseUtilities.doubleJsParse(jsthumbnails, "default", "url");
				if (Utilities.isValid(defaultThumb)) {
					this.getThumbnails().add(defaultThumb);
				}
				String highThumb = ParseUtilities.doubleJsParse(jsthumbnails, "high", "url");
				if (Utilities.isValid(highThumb)) {
					this.getThumbnails().add(highThumb);
				}
				String mediumThumb = ParseUtilities.doubleJsParse(jsthumbnails, "medium", "url");
				if (Utilities.isValid(mediumThumb)) {
					this.getThumbnails().add(mediumThumb);
				}
			}
			this.setChannelTitle(json.optString("channelTitle", null));
			this.setType(json.optString("type", null));
			this.setGroupId(json.optString("groupId", null));
		}

		public String getTitle() {
			return title;
		}

		public void setTitle(String title) {
			this.title = title;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public List<String> getThumbnails() {
			return thumbnails;
		}

		public void setThumbnails(List<String> thumbnails) {
			this.thumbnails = thumbnails;
		}

		public String getPublishedAt() {
			return publishedAt;
		}

		public void setPublishedAt(String publishedAt) {
			this.publishedAt = publishedAt;
		}

		public String getGroupId() {
			return groupId;
		}

		public void setGroupId(String groupId) {
			this.groupId = groupId;
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
	}
}
