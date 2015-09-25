package adaptors.instagram.igObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class igUser {
	private String bio;
	private String full_name;
	private String id;
	private int numFollowedBy;
	private int numFollows;
	private int numMedia;
	private String profile_picture;
	private String username;
	private String website;

	public igUser() {
	}

	public igUser(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setUsername(json.optString("username", null));
		this.setFull_name(json.optString("full_name", null));
		this.setProfile_picture(json.optString("profile_picture", null));
		this.setBio(json.optString("bio", null));
		this.setWebsite(json.optString("website", null));
		this.setNumMedia(ParseUtilities.doubleJsParseInt(json, "counts", "media"));
		this.setNumFollows(ParseUtilities.doubleJsParseInt(json, "counts", "follows"));
		this.setNumFollowedBy(ParseUtilities.doubleJsParseInt(json, "counts", "followed_by"));
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public int getNumMedia() {
		return numMedia;
	}

	public void setNumMedia(int numMedia) {
		this.numMedia = numMedia;
	}

	public int getNumFollows() {
		return numFollows;
	}

	public void setNumFollows(int numFollows) {
		this.numFollows = numFollows;
	}

	public int getNumFollowedBy() {
		return numFollowedBy;
	}

	public void setNumFollowedBy(int numFollowedBy) {
		this.numFollowedBy = numFollowedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
