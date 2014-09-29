package adaptors.youtube.ytObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import adaptors.youtube.ytObjects.ytPerson.ytAuthor;

public class ytVideoV2
{
	public ytAuthor author;
	public double averageRating;
	public int comments;
	public String description;
	public int duration;
	public int favoriteCount;
	public String id;
	public String license;
	public int maxRating;
	public int minRating;
	public int numDislikes;
	public int numLikes;
	public int numRaters;
	public String playerUrl;
	public String published;
	public String thumbnail;
	public String title;
	public int viewCount;

	public ytVideoV2()
	{
	}

	public ytVideoV2(JSONObject json)
	{
		String published = ParseUtilities.doubleJsParse(json, "published", "$t");
		this.published = published;
		String title = ParseUtilities.doubleJsParse(json, "title", "$t");
		this.title = title;
		JSONArray authorArray = json.optJSONArray("author");
		if (Utilities.isValid(authorArray))
		{
			JSONObject jsauthor = authorArray.optJSONObject(0);
			if (jsauthor != null)
			{
				ytAuthor author = new ytAuthor(jsauthor);
				this.author = author;
			}
		}
		JSONObject jscomments = json.optJSONObject("gd$comments");
		if (jscomments != null)
		{
			int comments = ParseUtilities.doubleJsParseInt(jscomments, "gd$feedLink", "countHint");
			this.comments = comments;
		}
		JSONObject jsrating = json.optJSONObject("gd$rating");
		if (jsrating != null)
		{
			int minRating = jsrating.optInt("min", -1);
			this.minRating = minRating;
			int maxRating = jsrating.optInt("max", -1);
			this.maxRating = maxRating;
			int numRaters = jsrating.optInt("numRaters", -1);
			this.numRaters = numRaters;
			double averageRating = jsrating.optDouble("average", -1);
			this.averageRating = averageRating;
		}
		JSONObject jsstatistics = json.optJSONObject("yt$statistics");
		if (jsstatistics != null)
		{
			int favoriteCount = jsstatistics.optInt("favoriteCount", -1);
			this.favoriteCount = favoriteCount;
			int viewCount = jsstatistics.optInt("viewCount", -1);
			this.viewCount = viewCount;
		}
		JSONObject ytRating = json.optJSONObject("yt$rating");
		if (ytRating != null)
		{
			int numDislikes = ytRating.optInt("numDislikes", -1);
			this.numDislikes = numDislikes;
			int numLikes = ytRating.optInt("numLikes", -1);
			this.numLikes = numLikes;
		}
		JSONObject jsgroup = json.optJSONObject("media$group");
		if (jsgroup != null)
		{
			String id = ParseUtilities.doubleJsParse(jsgroup, "yt$videoid", "$t");
			this.id = id;
			String license = ParseUtilities.doubleJsParse(jsgroup, "media$license", "$t");
			this.license = license;
			JSONArray thumbnailsArray = jsgroup.optJSONArray("media$thumbnail");
			if (Utilities.isValid(thumbnailsArray))
			{
				JSONObject jsthumbnail = thumbnailsArray.optJSONObject(0);
				if (jsthumbnail != null)
				{
					String thumbnail = jsthumbnail.optString("url", null);
					this.thumbnail = thumbnail;
				}
			}
			String description = ParseUtilities.doubleJsParse(jsgroup, "media$description", "$t");
			this.description = description;
			int duration = ParseUtilities.doubleJsParseInt(jsgroup, "yt$duration", "seconds");
			this.duration = duration;
			String playerUrl = ParseUtilities.doubleJsParse(jsgroup, "media$player", "url");
			this.playerUrl = playerUrl;
		}
	}
}
