package adaptors.youtube.ytObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;

public class ytChannelV2
{
	public String channelId;
	public String name;
	public String thumbnail;
	public String title;
	public String username;

	public ytChannelV2()
	{
	}

	public ytChannelV2(JSONObject json)
	{
		String title = ParseUtilities.doubleJsParse(json, "title", "$t");
		this.title = title;
		String channelId = ParseUtilities.doubleJsParse(json, "yt$channelId", "$t");
		this.channelId = channelId;
		String thumbnail = ParseUtilities.doubleJsParse(json, "media$thumbnail", "url");
		this.thumbnail = thumbnail;
		String username = ParseUtilities.doubleJsParse(json, "yt$username", "$t");
		this.username = username;
		String name = ParseUtilities.doubleJsParse(json, "yt$username", "display");
		this.name = name;
	}
}
