package adaptors.facebook.fbObjects;

import java.util.List;
import org.json.JSONObject;
import adaptors.facebook.FacebookParsers;

public class fbActivity {
	private String actorId;
	private String actorName;
	private String created_time;
	private String id;
	private String status_type;
	private String story;
	private List<fbTag> story_tags;
	private String type;

	public fbActivity() {
	}

	public fbActivity(JSONObject json) {
		this.setId(json.optString("id", null));
		JSONObject jsactor = json.optJSONObject("from");
		if (jsactor != null) {
			this.setActorName(jsactor.optString("name", null));
			this.setActorId(jsactor.optString("id", null));
		}
		this.setStory(json.optString("story", null));
		this.setType(json.optString("type", null));
		this.setStatus_type(json.optString("status_type", null));
		this.setCreated_time(json.optString("created_time", null));
		this.setStory_tags(FacebookParsers.getTags(json, "story_tags"));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<fbTag> getStory_tags() {
		return story_tags;
	}

	public void setStory_tags(List<fbTag> story_tags) {
		this.story_tags = story_tags;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getStatus_type() {
		return status_type;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getActorName() {
		return actorName;
	}

	public void setActorName(String actorName) {
		this.actorName = actorName;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String actorId) {
		this.actorId = actorId;
	}
}
