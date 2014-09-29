package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;
import helper.utilities.Utilities;

public class dmUser
{
	public String address;
	public String avatar120;
	public String avatar190;
	public String avatar240;
	public String avatar360;
	public String avatar480;
	public String avatar60;
	public String avatar720;
	public String background_url;
	public String banner_url;
	public long birthday;
	public String city;
	public String country;
	public long created_time;
	public String description;
	public String email;
	public String first_name;
	public String fullname;
	public String gender;
	public String id;
	public String language;
	public String last_name;
	public String parent;
	public String post_code;
	public String sceenname;
	public String status;
	public String url;
	public String username;
	public String videos_total;
	public String videostar_url;
	public String views_total;

	public dmUser()
	{
	}

	public dmUser(JSONObject json)
	{
		String avatar60 = "";
		String avatar120 = "";
		String avatar190 = "";
		String avatar240 = "";
		String avatar360 = "";
		String avatar480 = "";
		String avatar720 = "";
		String background_url = "";
		String banner_url = "";
		long created_time = -1;
		String description = "";
		String gender = "";
		String id = "";
		String status = "";
		String url = "";
		String username = "";
		String videos_total = "";
		String videostar_url = "";
		String views_total = "";
		String language = "";
		String parent = "";
		String address = "";
		long birthday = -1;
		String city = "";
		String country = "";
		String email = "";
		String first_name = "";
		String fullname = "";
		String last_name = "";
		String post_code = "";
		String screenname = "";
		if (Utilities.isValid(json.optString("id", null)))
		{
			avatar60 = json.optString("avatar_60_url", null);
			avatar120 = json.optString("avatar_120_url", null);
			avatar190 = json.optString("avatar_190_url", null);
			avatar240 = json.optString("avatar_240_url", null);
			avatar360 = json.optString("avatar_360_url", null);
			avatar480 = json.optString("avatar_480_url", null);
			avatar720 = json.optString("avatar_720_url", null);
			background_url = json.optString("background_url", null);
			banner_url = json.optString("banner_url", null);
			created_time = json.optLong("created_time", -1);
			description = json.optString("description", null);
			gender = json.optString("gender", null);
			id = json.optString("id", null);
			status = json.optString("status", null);
			url = json.optString("url", null);
			username = json.optString("username", null);
			videos_total = json.optString("videos_total", null);
			videostar_url = json.optString("videostar.url", null);
			views_total = json.optString("views_total", null);
			language = json.optString("language", null);
			parent = json.optString("parent", null);
			address = json.optString("address", null);
			birthday = json.optLong("birthday", -1);
			city = json.optString("city", null);
			country = json.optString("country", null);
			email = json.optString("email", null);
			first_name = json.optString("first_name", null);
			fullname = json.optString("fullname", null);
			last_name = json.optString("last_name", null);
			post_code = json.optString("post_code", null);
			screenname = json.optString("screenname", null);
		}
		else if (Utilities.isValid(json.optString("owner.id", null)))
		{
			avatar60 = json.optString("owner.avatar_60_url", null);
			avatar120 = json.optString("owner.avatar_120_url", null);
			avatar190 = json.optString("owner.avatar_190_url", null);
			avatar240 = json.optString("owner.avatar_240_url", null);
			avatar360 = json.optString("owner.avatar_360_url", null);
			avatar480 = json.optString("owner.avatar_480_url", null);
			avatar720 = json.optString("owner.avatar_720_url", null);
			background_url = json.optString("owner.background_url", null);
			banner_url = json.optString("owner.banner_url", null);
			created_time = json.optLong("owner.created_time", -1);
			description = json.optString("owner.description", null);
			gender = json.optString("owner.gender", null);
			id = json.optString("owner.id", null);
			status = json.optString("owner.status", null);
			url = json.optString("owner.url", null);
			username = json.optString("owner.username", null);
			videos_total = json.optString("owner.videos_total", null);
			videostar_url = json.optString("owner.videostar.url", null);
			views_total = json.optString("owner.views_total", null);
			language = json.optString("owner.language", null);
			parent = json.optString("owner.parent", null);
			address = json.optString("owner.address", null);
			birthday = json.optLong("owner.birthday", -1);
			city = json.optString("owner.city", null);
			country = json.optString("owner.country", null);
			email = json.optString("owner.email", null);
			first_name = json.optString("owner.first_name", null);
			fullname = json.optString("owner.fullname", null);
			last_name = json.optString("owner.last_name", null);
			post_code = json.optString("owner.post_code", null);
			screenname = json.optString("owner.screenname", null);
		}
		this.post_code = post_code;
		this.avatar60 = avatar60;
		this.avatar120 = avatar120;
		this.avatar190 = avatar190;
		this.avatar240 = avatar240;
		this.avatar360 = avatar360;
		this.avatar480 = avatar480;
		this.avatar720 = avatar720;
		this.background_url = background_url;
		this.banner_url = banner_url;
		this.created_time = created_time;
		this.description = description;
		this.gender = gender;
		this.id = id;
		this.status = status;
		this.url = url;
		this.username = username;
		this.videos_total = videos_total;
		this.videostar_url = videostar_url;
		this.views_total = views_total;
		this.language = language;
		this.parent = parent;
		this.address = address;
		this.birthday = birthday;
		this.city = city;
		this.country = country;
		this.email = email;
		this.first_name = first_name;
		this.fullname = fullname;
		this.last_name = last_name;
		this.sceenname = screenname;
	}
}
