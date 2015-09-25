package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbTag {
	private String id;
	private String name;
	private String type;

	public fbTag() {
	}

	public fbTag(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setName(json.optString("name", null));
		this.setType(json.optString("type", null));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
