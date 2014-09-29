package adaptors.googlep.gpObjects;

import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class gpException
{
	public int code;
	public String message;
	public String domain;
	public String reason;
	public String secondaryMessage;
	public String locationType;
	public String location;

	public gpException()
	{
	}

	public gpException(JSONObject json)
	{
		JSONObject jsError = json.optJSONObject("error");
		if (jsError != null)
		{
			int code = jsError.optInt("code", 0);
			this.code = code;
			String message = jsError.optString("message", null);
			this.message = message;
			JSONArray errorsArray = jsError.optJSONArray("errors");
			if (Utilities.isValid(errorsArray))
			{
				JSONObject jsonFirst = errorsArray.optJSONObject(0);
				if (jsonFirst != null)
				{
					String domain = jsonFirst.optString("domain", null);
					this.domain = domain;
					String reason = jsonFirst.optString("reason", null);
					this.reason = reason;
					String secondaryMessage = jsonFirst.optString("secondaryMessage", null);
					this.secondaryMessage = secondaryMessage;
					String locationType = jsonFirst.optString("locationType", null);
					this.locationType = locationType;
					String location = jsonFirst.optString("location", null);
					this.location = location;
				}
			}
		}
	}
}
