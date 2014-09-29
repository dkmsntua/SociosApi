package adaptors.youtube.ytObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;

public class ytPerson
{
	public static class ytAuthor
	{
		public String name;
		public String url;
		public String userId;

		public ytAuthor()
		{
		}

		public ytAuthor(JSONObject json)
		{
			String name = ParseUtilities.doubleJsParse(json, "name", "$t");
			this.name = name;
			String userId = ParseUtilities.doubleJsParse(json, "yt$userId", "$t");
			this.userId = userId;
			String url = ParseUtilities.doubleJsParse(json, "uri", "$t");
			this.url = url;
		}
	}
	public ytAuthor author;
	public String channelId;
	public String firstName;
	public String googlePlusUserId;
	public String lastName;
	public String location;
	public String published;
	public int subscriberCount;
	public String summary;
	public String thumbnail;
	public String username;

	public ytPerson()
	{
	}

	public ytPerson(JSONObject json)
	{
		String summary = ParseUtilities.doubleJsParse(json, "summary", "$t");
		this.summary = summary;
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
		String channelId = ParseUtilities.doubleJsParse(json, "yt$channelId", "$t");
		this.channelId = channelId;
		String firstName = ParseUtilities.doubleJsParse(json, "yt$firstName", "$t");
		this.firstName = firstName;
		String googlePlusUserId = ParseUtilities.doubleJsParse(json, "yt$googlePlusUserId", "$t");
		this.googlePlusUserId = googlePlusUserId;
		String lastName = ParseUtilities.doubleJsParse(json, "yt$lastName", "$t");
		this.lastName = lastName;
		String location = ParseUtilities.doubleJsParse(json, "yt$location", "$t");
		this.location = location;
		int subscriberCount = ParseUtilities.doubleJsParseInt(json, "yt$statistics", "subscriberCount");
		this.subscriberCount = subscriberCount;
		String thumbnail = ParseUtilities.doubleJsParse(json, "media$thumbnail", "url");
		this.thumbnail = thumbnail;
		String username = ParseUtilities.doubleJsParse(json, "yt$username", "display");
		this.username = username;
		String published = ParseUtilities.doubleJsParse(json, "published", "$t");
		this.published = published;
	}
}
