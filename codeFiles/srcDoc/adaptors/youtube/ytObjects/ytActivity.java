package adaptors.youtube.ytObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class ytActivity
{
	public class YtaSnippet
	{
		public String channelId;
		public String channelTitle;
		public String description;
		public String groupId;
		public String publishedAt;
		public List<String> thumbnails;
		public String title;
		public String type;

		public YtaSnippet()
		{
		}

		public YtaSnippet(JSONObject json)
		{
			String publishedAt = json.optString("publishedAt", null);
			this.publishedAt = publishedAt;
			String channelId = json.optString("channelId", null);
			this.channelId = channelId;
			String title = json.optString("title", null);
			this.title = title;
			String description = json.optString("description", null);
			this.description = description;
			JSONObject jsthumbnails = json.optJSONObject("thumbnails");
			if (jsthumbnails != null)
			{
				this.thumbnails = new ArrayList<String>();
				String defaultThumb = ParseUtilities.doubleJsParse(jsthumbnails, "default", "url");
				if (Utilities.isValid(defaultThumb))
				{
					this.thumbnails.add(defaultThumb);
				}
				String highThumb = ParseUtilities.doubleJsParse(jsthumbnails, "high", "url");
				if (Utilities.isValid(highThumb))
				{
					this.thumbnails.add(highThumb);
				}
				String mediumThumb = ParseUtilities.doubleJsParse(jsthumbnails, "medium", "url");
				if (Utilities.isValid(mediumThumb))
				{
					this.thumbnails.add(mediumThumb);
				}
			}
			String channelTitle = json.optString("channelTitle", null);
			this.channelTitle = channelTitle;
			String type = json.optString("type", null);
			this.type = type;
			String groupId = json.optString("groupId", null);
			this.groupId = groupId;
		}
	}
	public String id;
	public String itemChannelId;
	public String itemVideoId;
	public YtaSnippet snippet;

	public ytActivity()
	{
	}

	public ytActivity(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null)
		{
			YtaSnippet snippet = new YtaSnippet(jssnippet);
			this.snippet = snippet;
		}
		if (Utilities.isValid(this.snippet.type))
		{
			JSONObject jscontentDetails = json.optJSONObject("contentDetails");
			if (jscontentDetails != null)
			{
				JSONObject jsholder = jscontentDetails.optJSONObject(this.snippet.type);
				if (jsholder != null)
				{
					JSONObject jsresource = jsholder.optJSONObject("resourceId");
					if (jsresource != null)
					{
						String videoId = jsresource.optString("videoId", null);
						this.itemVideoId = videoId;
						String channelId = jsresource.optString("channelId", null);
						this.itemChannelId = channelId;
					}
					else
					{
						String videoId = jsholder.optString("videoId", null);
						this.itemVideoId = videoId;
					}
				}
			}
		}
	}
}
