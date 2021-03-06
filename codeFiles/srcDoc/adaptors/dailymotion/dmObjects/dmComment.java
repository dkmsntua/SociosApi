package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmComment {
	private long createdTime;
	private String id;
	private String language;
	private String message;
	private String screenname;
	private String userId;
	private String username;
	private String videoId;
	private String videoTitle;

	public dmComment() {
	}

	public dmComment(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setCreatedTime(json.optLong("created_time", -1));
		this.setMessage(json.optString("message", null));
		this.setVideoId(json.optString("video.id", null));
		this.setVideoTitle(json.optString("video.title", null));
		this.setUserId(json.optString("owner.id", null));
		this.setUsername(json.optString("owner.username", null));
		this.setScreenname(json.optString("owner.screenname", null));
		this.setLanguage(json.optString("language", null));
	}

	public String getVideoTitle() {
		return videoTitle;
	}

	public void setVideoTitle(String videoTitle) {
		this.videoTitle = videoTitle;
	}

	public String getVideoId() {
		return videoId;
	}

	public void setVideoId(String videoId) {
		this.videoId = videoId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getScreenname() {
		return screenname;
	}

	public void setScreenname(String screenname) {
		this.screenname = screenname;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(long createdTime) {
		this.createdTime = createdTime;
	}
}
