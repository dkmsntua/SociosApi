package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbException
{
	public int code;
	public String message;
	public String type;

	public fbException()
	{
	}

	public fbException(JSONObject json)
	{
		JSONObject error = json.optJSONObject("error");
		if (error != null)
		{
			int code = error.optInt("code", 0);
			this.code = code;
			String message = error.optString("message", null);
			this.message = message;
			String type = error.optString("type", null);
			this.type = type;
		}
	}
}
