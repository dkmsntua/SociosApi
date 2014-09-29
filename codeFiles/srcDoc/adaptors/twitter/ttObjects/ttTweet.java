package adaptors.twitter.ttObjects;

import org.json.JSONObject;

public class ttTweet
{
	public String created_at;
	public ttEntities entities;
	public int favorite_count;
	public boolean favorited;
	public String id_str;
	public String in_reply_to_screen_name;
	public String in_reply_to_status_id_str;
	public String in_reply_to_user_id_str;
	public String lang;
	public ttPlace places;
	public int retweet_count;
	public boolean retweeted;
	public String source;
	public String text;
	public ttUser user;

	public ttTweet()
	{
	}

	public ttTweet(JSONObject json)
	{
		String created_at = json.optString("created_at", null);
		this.created_at = created_at;
		JSONObject entities = json.optJSONObject("entities");
		if (entities != null)
		{
			ttEntities ttentities = new ttEntities(entities);
			this.entities = ttentities;
		}
		int favorite_count = json.optInt("favorite_count", -1);
		this.favorite_count = favorite_count;
		boolean favorited = json.optBoolean("favorited");
		this.favorited = favorited;
		String id_str = json.optString("id_str", null);
		this.id_str = id_str;
		String in_reply_to_screen_name = json.optString("in_reply_to_screen_name", null);
		this.in_reply_to_screen_name = in_reply_to_screen_name;
		String in_reply_to_status_id_str = json.optString("in_reply_to_status_id_str", null);
		this.in_reply_to_status_id_str = in_reply_to_status_id_str;
		String in_reply_to_user_id_str = json.optString("in_reply_to_user_id_str", null);
		this.in_reply_to_user_id_str = in_reply_to_user_id_str;
		String lang = json.optString("lang", null);
		this.lang = lang;
		JSONObject places = json.optJSONObject("places");
		if (places != null)
		{
			ttPlace ttplaces = new ttPlace(places);
			this.places = ttplaces;
		}
		int retweet_count = json.optInt("retweet_count", -1);
		this.retweet_count = retweet_count;
		boolean retweeted = json.optBoolean("retweeted");
		this.retweeted = retweeted;
		String source = json.optString("source", null);
		this.source = source;
		String text = json.optString("text", null);
		this.text = text;
		JSONObject user = json.optJSONObject("user");
		if (user != null)
		{
			ttUser ttuser = new ttUser(user);
			this.user = ttuser;
		}
	}
}
