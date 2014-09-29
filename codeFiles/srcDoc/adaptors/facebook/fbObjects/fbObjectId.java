package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbObjectId
{
	public String id;
	public String name;

	public fbObjectId()
	{
	}

	public fbObjectId(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String name = json.optString("name", null);
		this.name = name;
	}
}
