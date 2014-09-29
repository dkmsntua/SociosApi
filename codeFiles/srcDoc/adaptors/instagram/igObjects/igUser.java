package adaptors.instagram.igObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;

public class igUser
{
	public String bio;
	public String full_name;
	public String id;
	public int numFollowedBy;
	public int numFollows;
	public int numMedia;
	public String profile_picture;
	public String username;
	public String website;

	public igUser()
	{
	}

	public igUser(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String username = json.optString("username", null);
		this.username = username;
		String full_name = json.optString("full_name", null);
		this.full_name = full_name;
		String profile_picture = json.optString("profile_picture", null);
		this.profile_picture = profile_picture;
		String bio = json.optString("bio", null);
		this.bio = bio;
		String website = json.optString("website", null);
		this.website = website;
		int numMedia = ParseUtilities.doubleJsParseInt(json, "counts", "media");
		this.numMedia = numMedia;
		int numFollows = ParseUtilities.doubleJsParseInt(json, "counts", "follows");
		this.numFollows = numFollows;
		int numFollowedBy = ParseUtilities.doubleJsParseInt(json, "counts", "followed_by");
		this.numFollowedBy = numFollowedBy;
	}
}
