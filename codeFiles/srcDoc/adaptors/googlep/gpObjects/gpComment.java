package adaptors.googlep.gpObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class gpComment {
	private String content;
	private String id;
	private int plusOners;
	private String published;
	private String userId;
	private String username;

	public gpComment() {
	}

	public gpComment(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setPublished(json.optString("published", null));
		this.setUserId(ParseUtilities.doubleJsParse(json, "actor", "id"));
		this.setUsername(ParseUtilities.doubleJsParse(json, "actor", "displayName"));
		this.setContent(ParseUtilities.doubleJsParse(json, "object", "content"));
		this.setPlusOners(ParseUtilities.doubleJsParseInt(json, "plusoners", "totalItems"));
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

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public int getPlusOners() {
		return plusOners;
	}

	public void setPlusOners(int plusOners) {
		this.plusOners = plusOners;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
