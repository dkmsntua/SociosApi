package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmComment
{
	public long createdTime;
	public String id;
	public String language;
	public String message;
	public String screenname;
	public String userId;
	public String username;
	public String videoId;
	public String videoTitle;

	public dmComment()
	{
	}

	public dmComment(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		long createdTime = json.optLong("created_time", -1);
		this.createdTime = createdTime;
		String message = json.optString("message", null);
		this.message = message;
		String videoId = json.optString("video.id", null);
		this.videoId = videoId;
		String videoTitle = json.optString("video.title", null);
		this.videoTitle = videoTitle;
		String userId = json.optString("owner.id", null);
		this.userId = userId;
		String username = json.optString("owner.username", null);
		this.username = username;
		String screenname = json.optString("owner.screenname", null);
		this.screenname = screenname;
		String language = json.optString("language", null);
		this.language = language;
	}
}
