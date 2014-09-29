package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbTag
{
	public String id;
	public String name;
	public String type;

	public fbTag()
	{
	}

	public fbTag(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String name = json.optString("name", null);
		this.name = name;
		String type = json.optString("type", null);
		this.type = type;
	}
}
