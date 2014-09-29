package adaptors.twitter.ttObjects;

import org.json.JSONObject;

public class ttUser
{
	public String created_at;
	public String description;
	public ttEntities entities;
	public int favourites_count;
	public int followers_count;
	public int friends_count;
	public String id_str;
	public String lang;
	public String location;
	public String name;
	public String profile_background_image_url;
	public String profile_image_url; // avatar image
	public String screen_name; //nickname
	public int statuses_count;
	public String time_zone;
	public String url;
	public int utc_offset;
	public boolean verified;

	public ttUser()
	{
	}

	public ttUser(JSONObject json)
	{
		String created_at = json.optString("created_at", null);
		this.created_at = created_at;
		String description = json.optString("description", null);
		this.description = description;
		JSONObject entities = json.optJSONObject("entities");
		if (entities != null)
		{
			ttEntities ttentities = new ttEntities(entities);
			this.entities = ttentities;
		}
		int favourites_count = json.optInt("favourites_count", -1);
		this.favourites_count = favourites_count;
		int followers_count = json.optInt("followers_count", -1);
		this.followers_count = followers_count;
		int friends_count = json.optInt("friends_count", -1);
		this.friends_count = friends_count;
		String id_str = json.optString("id_str", null);
		this.id_str = id_str;
		String lang = json.optString("lang", null);
		this.lang = lang;
		String location = json.optString("location", null);
		this.location = location;
		String name = json.optString("name", null);
		this.name = name;
		String profile_background_image_url = json.optString("profile_background_image_url", null);
		this.profile_background_image_url = profile_background_image_url;
		String profile_image_url = json.optString("profile_image_url", null);
		this.profile_image_url = profile_image_url;
		String screen_name = json.optString("screen_name", null);
		this.screen_name = screen_name;
		int statuses_count = json.optInt("statuses_count", -1);
		this.statuses_count = statuses_count;
		String time_zone = json.optString("time_zone", null);
		this.time_zone = time_zone;
		String url = json.optString("url", null);
		this.url = url;
		int utc_offset = json.optInt("utc_offset", -100);
		this.utc_offset = utc_offset;
		boolean verified = json.optBoolean("verified");
		this.verified = verified;
	}
}
