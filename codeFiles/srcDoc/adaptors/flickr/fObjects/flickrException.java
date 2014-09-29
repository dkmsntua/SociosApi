package adaptors.flickr.fObjects;

import org.json.JSONObject;

public class flickrException
{
	public int code;
	public String stat;
	public String message;

	public flickrException()
	{
	}

	public flickrException(JSONObject json)
	{
		int code = json.optInt("code", 0);
		this.code = code;
		String message = json.optString("message", null);
		this.message = message;
		String stat = json.optString("stat", null);
		this.stat = stat;
	}
}
