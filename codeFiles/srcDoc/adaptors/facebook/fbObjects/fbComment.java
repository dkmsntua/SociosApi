package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbComment {
	private String created_time;
	private String id;
	private int like_count;
	private String message;
	private String userId;
	private String username;

	public fbComment() {
	}

	public fbComment(JSONObject json) {
		this.setId(json.optString("id", null));
		JSONObject jsFrom = json.optJSONObject("from");
		if (jsFrom != null) {
			this.setUserId(jsFrom.optString("id", null));
			this.setUsername(jsFrom.optString("name", null));
		}
		this.setMessage(json.optString("message", null));
		this.setCreated_time(json.optString("created_time", null));
		this.setLike_count(json.optInt("like_count", -1));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getLike_count() {
		return like_count;
	}

	public void setLike_count(int like_count) {
		this.like_count = like_count;
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
}
