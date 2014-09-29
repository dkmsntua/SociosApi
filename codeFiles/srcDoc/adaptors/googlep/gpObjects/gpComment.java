package adaptors.googlep.gpObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;

public class gpComment
{
	public String content;
	public String id;
	public int plusOners;
	public String published;
	public String userId;
	public String username;

	public gpComment()
	{
	}

	public gpComment(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String published = json.optString("published", null);
		this.published = published;
		String userId = ParseUtilities.doubleJsParse(json, "actor", "id");
		this.userId = userId;
		String username = ParseUtilities.doubleJsParse(json, "actor", "displayName");
		this.username = username;
		String content = ParseUtilities.doubleJsParse(json, "object", "content");
		this.content = content;
		int plusOners = ParseUtilities.doubleJsParseInt(json, "plusoners", "totalItems");
		this.plusOners = plusOners;
	}
}
