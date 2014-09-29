package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmException
{
	public int code;
	public String message;
	public String type;

	public dmException()
	{
	}

	public dmException(JSONObject json)
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
