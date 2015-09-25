package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class ytChannelV2 {
	private String channelId;
	private String name;
	private String thumbnail;
	private String title;
	private String username;

	public ytChannelV2() {
	}

	public ytChannelV2(JSONObject json) {
		this.setTitle(ParseUtilities.doubleJsParse(json, "title", "$t"));
		this.setChannelId(ParseUtilities.doubleJsParse(json, "yt$channelId", "$t"));
		this.setThumbnail(ParseUtilities.doubleJsParse(json, "media$thumbnail", "url"));
		this.setUsername(ParseUtilities.doubleJsParse(json, "yt$username", "$t"));
		this.setName(ParseUtilities.doubleJsParse(json, "yt$username", "display"));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
