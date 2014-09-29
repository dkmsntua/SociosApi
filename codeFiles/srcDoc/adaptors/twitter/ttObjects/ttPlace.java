package adaptors.twitter.ttObjects;

import org.json.JSONObject;

public class ttPlace
{
	public String country;
	public String country_code;
	public String full_name;
	public String locality;
	public String name;
	public String phone;
	public String place_type;
	public String postal_code;
	public String region;
	public String street_address;
	public String twitter;
	public String url;

	public ttPlace()
	{
	}

	public ttPlace(JSONObject json)
	{
		JSONObject jsattributes = json.optJSONObject("attributes");
		if (jsattributes != null)
		{
			String street_address = jsattributes.optString("street_address", null);
			this.street_address = street_address;
			String locality = jsattributes.optString("locality", null);
			this.locality = locality;
			String region = jsattributes.optString("region", null);
			this.region = region;
			String postal_code = jsattributes.optString("postal_code", null);
			this.postal_code = postal_code;
			String phone = jsattributes.optString("phone", null);
			this.phone = phone;
			String twitter = jsattributes.optString("twitter", null);
			this.twitter = twitter;
		}
		String country = json.optString("country", null);
		this.country = country;
		String country_code = json.optString("country_code", null);
		this.country_code = country_code;
		String full_name = json.optString("full_name", null);
		this.full_name = full_name;
		String name = json.optString("name", null);
		this.name = name;
		String place_type = json.optString("place_type", null);
		this.place_type = place_type;
		String url = json.optString("url", null);
		this.url = url;
	}
}
