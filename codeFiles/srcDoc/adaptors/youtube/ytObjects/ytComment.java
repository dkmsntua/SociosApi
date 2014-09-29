package adaptors.youtube.ytObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;

public class ytComment
{
	public String content;
	public String id;
	public String published;
	public String userId;
	public String username;

	public ytComment()
	{
	}

	public ytComment(JSONObject json)
	{
		String id = ParseUtilities.doubleJsParse(json, "id", "$t");
		if (id != null)
		{
			String[] tokens = id.split(":");
			this.id = tokens[tokens.length - 1];
		}
		String published = ParseUtilities.doubleJsParse(json, "published", "$t");
		this.published = published;
		String content = ParseUtilities.doubleJsParse(json, "content", "$t");
		this.content = content;
		JSONArray jsarray = json.optJSONArray("author");
		if (Utilities.isValid(jsarray))
		{
			JSONObject jsauthor = jsarray.optJSONObject(0);
			if (jsauthor != null)
			{
				String userId = ParseUtilities.doubleJsParse(jsauthor, "yt$userId", "$t");
				this.userId = userId;
				String username = ParseUtilities.doubleJsParse(jsauthor, "name", "$t");
				this.username = username;
			}
		}
	}
}
