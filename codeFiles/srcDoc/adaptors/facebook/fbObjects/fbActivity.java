package adaptors.facebook.fbObjects;

import org.json.JSONObject;
import java.util.List;
import adaptors.facebook.FacebookParsers;

public class fbActivity
{
	public String actorId;
	public String actorName;
	public String created_time;
	public String id;
	public String status_type;
	public String story;
	public List<fbTag> story_tags;
	public String type;

	public fbActivity()
	{
	}

	public fbActivity(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jsactor = json.optJSONObject("from");
		if (jsactor != null)
		{
			String actorName = jsactor.optString("name", null);
			this.actorName = actorName;
			String actorId = jsactor.optString("id", null);
			this.actorId = actorId;
		}
		String story = json.optString("story", null);
		this.story = story;
		String type = json.optString("type", null);
		this.type = type;
		String status_type = json.optString("status_type", null);
		this.status_type = status_type;
		String created_time = json.optString("created_time", null);
		this.created_time = created_time;
		this.story_tags = FacebookParsers.getTags(json, "story_tags");
	}
}
