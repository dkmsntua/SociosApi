package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class ytChannel {
	private String channelId;
	private String description;
	private String picture;
	private String thumbnail;
	private String title;
	private Integer subscriberCount;

	public ytChannel() {
	}

	public ytChannel(JSONObject json) {
		this.setChannelId(json.optString("id"));
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null) {
			this.setTitle(jssnippet.optString("title", null));
			this.setDescription(jssnippet.optString("description", null));
			JSONObject jsthumbnails = jssnippet.optJSONObject("thumbnails");
			if (jsthumbnails != null) {
				this.setThumbnail(ParseUtilities.doubleJsParse(jsthumbnails, "default", "url"));
				this.setPicture(ParseUtilities.doubleJsParse(jsthumbnails, "high", "url"));
			}
		}
		JSONObject jsStatistics = json.optJSONObject("statistics");
		if (jsStatistics != null) {
			if (!jsStatistics.isNull("subscriberCount")) {
				this.setSubscriberCount(jsStatistics.optInt("subscriberCount"));
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public Integer getSubscriberCount() {
		return subscriberCount;
	}

	public void setSubscriberCount(Integer subscriberCount) {
		this.subscriberCount = subscriberCount;
	}
}
