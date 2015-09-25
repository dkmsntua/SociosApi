package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbObjectId {
	private String id;
	private String name;

	public fbObjectId() {
	}

	public fbObjectId(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setName(json.optString("name", null));
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
