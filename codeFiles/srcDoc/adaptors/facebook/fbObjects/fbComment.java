package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbComment
{
	public String created_time;
	public String id;
	public int like_count;
	public String message;
	public String userId;
	public String username;

	public fbComment()
	{
	}

	public fbComment(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jsFrom = json.optJSONObject("from");
		if (jsFrom != null)
		{
			String userId = jsFrom.optString("id", null);
			this.userId = userId;
			String username = jsFrom.optString("name", null);
			this.username = username;
		}
		String message = json.optString("message", null);
		this.message = message;
		String created_time = json.optString("created_time", null);
		this.created_time = created_time;
		int like_count = json.optInt("like_count", -1);
		this.like_count = like_count;
	}
}
