package adaptors.youtube.ytObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;

public class ytChannel
{
	public String channelId;
	public String description;
	public String picture;
	public String thumbnail;
	public String title;

	public ytChannel()
	{
	}

	public ytChannel(JSONObject json)
	{
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null)
		{
			String title = jssnippet.optString("title", null);
			this.title = title;
			String description = jssnippet.optString("description", null);
			this.description = description;
			String channelId = ParseUtilities.doubleJsParse(jssnippet, "resourceId", "channelId");
			this.channelId = channelId;
			JSONObject jsthumbnails = jssnippet.optJSONObject("thumbnails");
			if (jsthumbnails != null)
			{
				String thumbnail = ParseUtilities.doubleJsParse(jsthumbnails, "default", "url");
				this.thumbnail = thumbnail;
				String picture = ParseUtilities.doubleJsParse(jsthumbnails, "high", "url");
				this.picture = picture;
			}
		}
	}
}
