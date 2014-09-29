package adaptors.youtube.ytObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class ytVideo
{
	public class YtvSnippet
	{
		public String categoryId;
		public String channelId;
		public String channelTitle;
		public String description;
		public String publishedAt;
		public List<String> tags;
		public YtvThumbnails thumbnails;
		public String title;

		public YtvSnippet()
		{
		}

		public YtvSnippet(JSONObject json)
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
				YtvThumbnails thumbnails = new YtvThumbnails(jsthumbnails);
				this.thumbnails = thumbnails;
			}
			String channelTitle = json.optString("channelTitle", null);
			this.channelTitle = channelTitle;
			JSONArray tagsArray = json.optJSONArray("tags");
			if (Utilities.isValid(tagsArray))
			{
				this.tags = new ArrayList<String>();
				for (int index = 0; index < tagsArray.length(); index++)
				{
					String tag = tagsArray.optString(index, null);
					this.tags.add(tag);
				}
			}
			String categoryId = json.optString("categoryId", null);
			this.categoryId = categoryId;
		}
	}
	public class YtvStatistics
	{
		public int commentCount;
		public int dislikeCount;
		public int favoriteCount;
		public int likeCount;
		public int viewCount;

		public YtvStatistics()
		{
		}

		public YtvStatistics(JSONObject json)
		{
			int viewCount = json.optInt("viewCount", -1);
			this.viewCount = viewCount;
			int likeCount = json.optInt("likeCount", -1);
			this.likeCount = likeCount;
			int dislikeCount = json.optInt("dislikeCount", -1);
			this.dislikeCount = dislikeCount;
			int favoriteCount = json.optInt("favoriteCount", -1);
			this.favoriteCount = favoriteCount;
			int commentCount = json.optInt("commentCount", -1);
			this.commentCount = commentCount;
		}
	}
	public class YtvThumbnails
	{
		public String defaultThumb;
		public String highThumb;
		public String mediumThumb;

		public YtvThumbnails()
		{
		}

		public YtvThumbnails(JSONObject json)
		{
			String defaultThumb = ParseUtilities.doubleJsParse(json, "default", "url");
			this.defaultThumb = defaultThumb;
			String mediumThumb = ParseUtilities.doubleJsParse(json, "medium", "url");
			this.mediumThumb = mediumThumb;
			String highThumb = ParseUtilities.doubleJsParse(json, "high", "url");
			this.highThumb = highThumb;
		}
	}
	public String duration;
	public String embedHtml;
	public long filesize;
	public String id;
	public String license;
	public YtvSnippet snippet;
	public YtvStatistics statistics;

	public ytVideo()
	{
	}

	public ytVideo(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null)
		{
			YtvSnippet snippet = new YtvSnippet(jssnippet);
			this.snippet = snippet;
		}
		String duration = ParseUtilities.doubleJsParse(json, "contentDetails", "duration");
		this.duration = duration;
		String embedHtml = ParseUtilities.doubleJsParse(json, "player", "embedHtml");
		this.embedHtml = embedHtml;
		JSONObject jsstatistics = json.optJSONObject("statistics");
		if (jsstatistics != null)
		{
			YtvStatistics statistics = new YtvStatistics(jsstatistics);
			this.statistics = statistics;
		}
		String license = ParseUtilities.doubleJsParse(json, "status", "license");
		this.license = license;
		JSONObject jsfileDetails = json.optJSONObject("fileDetails");
		if (jsfileDetails != null)
		{
			long filesize = jsfileDetails.optLong("fileSize");
			this.filesize = filesize;
		}
	}
}
