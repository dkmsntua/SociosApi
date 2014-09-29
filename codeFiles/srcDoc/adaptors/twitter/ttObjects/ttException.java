package adaptors.twitter.ttObjects;

import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class ttException
{
	public int code;
	public String message;

	public ttException()
	{
	}

	public ttException(JSONObject json)
	{
		JSONArray errorsArray = json.optJSONArray("errors");
		if (Utilities.isValid(errorsArray))
		{
			JSONObject jsonFirst = errorsArray.optJSONObject(0);
			if (jsonFirst != null)
			{
				int code = jsonFirst.optInt("code", 0);
				this.code = code;
				String message = jsonFirst.optString("message", null);
				this.message = message;
			}
		}
	}
}
