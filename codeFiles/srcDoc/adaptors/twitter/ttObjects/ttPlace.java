package adaptors.twitter.ttObjects;

import org.json.JSONObject;

public class ttPlace {
	private String country;
	private String country_code;
	private String full_name;
	private String locality;
	private String name;
	private String phone;
	private String place_type;
	private String postal_code;
	private String region;
	private String street_address;
	private String twitter;
	private String url;

	public ttPlace() {
	}

	public ttPlace(JSONObject json) {
		JSONObject jsattributes = json.optJSONObject("attributes");
		if (jsattributes != null) {
			this.setStreet_address(jsattributes.optString("street_address", null));
			this.setLocality(jsattributes.optString("locality", null));
			this.setRegion(jsattributes.optString("region", null));
			this.setPostal_code(jsattributes.optString("postal_code", null));
			String phone = jsattributes.optString("phone", null);
			this.setPhone(phone);
			String twitter = jsattributes.optString("twitter", null);
			this.setTwitter(twitter);
		}
		String country = json.optString("country", null);
		this.setCountry(country);
		this.setCountry_code(json.optString("country_code", null));
		this.setFull_name(json.optString("full_name", null));
		this.setName(json.optString("name", null));
		this.setPlace_type(json.optString("place_type", null));
		String url = json.optString("url", null);
		this.setUrl(url);
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTwitter() {
		return twitter;
	}

	public void setTwitter(String twitter) {
		this.twitter = twitter;
	}

	public String getStreet_address() {
		return street_address;
	}

	public void setStreet_address(String street_address) {
		this.street_address = street_address;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	public String getPostal_code() {
		return postal_code;
	}

	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	public String getPlace_type() {
		return place_type;
	}

	public void setPlace_type(String place_type) {
		this.place_type = place_type;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocality() {
		return locality;
	}

	public void setLocality(String locality) {
		this.locality = locality;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getCountry_code() {
		return country_code;
	}

	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
}
