package adaptors.youtube.ytObjects;

import org.json.JSONObject;

public class ytException
{
	public String domain;
	public String code;
	public String internalReason;

	public ytException()
	{
	}

	public ytException(JSONObject json)
	{
		JSONObject errors = json.optJSONObject("errors");
		if (errors != null)
		{
			JSONObject error = errors.optJSONObject("error");
			if (error != null)
			{
				String domain = error.optString("domain", null);
				this.domain = domain;
				String code = error.optString("code", null);
				this.code = code;
				String internalReason = error.optString("internalReason", null);
				this.internalReason = internalReason;
			}
		}
	}
}
