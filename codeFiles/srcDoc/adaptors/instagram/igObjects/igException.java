package adaptors.instagram.igObjects;

import org.json.JSONObject;

public class igException
{
	public int code;
	public String error_type;
	public String error_message;

	public igException()
	{
	}

	public igException(JSONObject json)
	{
		JSONObject jsMeta = json.optJSONObject("meta");
		if (jsMeta != null)
		{
			int code = jsMeta.optInt("code", 0);
			this.code = code;
			String error_type = jsMeta.optString("error_type", null);
			this.error_type = error_type;
			String error_message = jsMeta.optString("error_message", null);
			this.error_message = error_message;
		}
	}
}
