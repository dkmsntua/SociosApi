package adaptors.instagram.igObjects;

import org.json.JSONObject;

public class igComment
{
	public long created_time;
	public igUser from;
	public String id;
	public String text;

	public igComment()
	{
	}

	public igComment(JSONObject json)
	{
		long created_time = json.optLong("created_time", -1);
		this.created_time = created_time;
		String text = json.optString("text", null);
		this.text = text;
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jsfrom = json.optJSONObject("from");
		if (jsfrom != null)
		{
			igUser from = new igUser(jsfrom);
			this.from = from;
		}
	}
}
