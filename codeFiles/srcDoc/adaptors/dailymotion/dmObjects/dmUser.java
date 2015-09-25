package adaptors.dailymotion.dmObjects;

import helper.utilities.Utilities;
import org.json.JSONObject;

public class dmUser {
	private String address;
	private String avatar120;
	private String avatar190;
	private String avatar240;
	private String avatar360;
	private String avatar480;
	private String avatar60;
	private String avatar720;
	private String background_url;
	private String banner_url;
	private long birthday;
	private String city;
	private String country;
	private long created_time;
	private String description;
	private String email;
	private String first_name;
	private String fullname;
	private String gender;
	private String id;
	private String language;
	private String last_name;
	private String parent;
	private String post_code;
	private String sceenname;
	private String status;
	private String url;
	private String username;
	private String videos_total;
	private String videostar_url;
	private String views_total;

	public dmUser() {
	}

	public dmUser(JSONObject json) {
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
		if (Utilities.isValid(json.optString("id", null))) {
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
		else if (Utilities.isValid(json.optString("owner.id", null))) {
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
		this.setPost_code(post_code);
		this.setAvatar60(avatar60);
		this.setAvatar120(avatar120);
		this.setAvatar190(avatar190);
		this.setAvatar240(avatar240);
		this.setAvatar360(avatar360);
		this.setAvatar480(avatar480);
		this.setAvatar720(avatar720);
		this.setBackground_url(background_url);
		this.setBanner_url(banner_url);
		this.setCreated_time(created_time);
		this.setDescription(description);
		this.setGender(gender);
		this.setId(id);
		this.setStatus(status);
		this.setUrl(url);
		this.setUsername(username);
		this.setVideos_total(videos_total);
		this.setVideostar_url(videostar_url);
		this.setViews_total(views_total);
		this.setLanguage(language);
		this.setParent(parent);
		this.setAddress(address);
		this.setBirthday(birthday);
		this.setCity(city);
		this.setCountry(country);
		this.setEmail(email);
		this.setFirst_name(first_name);
		this.setFullname(fullname);
		this.setLast_name(last_name);
		this.setSceenname(screenname);
	}

	public String getViews_total() {
		return views_total;
	}

	public void setViews_total(String views_total) {
		this.views_total = views_total;
	}

	public String getVideostar_url() {
		return videostar_url;
	}

	public void setVideostar_url(String videostar_url) {
		this.videostar_url = videostar_url;
	}

	public String getVideos_total() {
		return videos_total;
	}

	public void setVideos_total(String videos_total) {
		this.videos_total = videos_total;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getSceenname() {
		return sceenname;
	}

	public void setSceenname(String sceenname) {
		this.sceenname = sceenname;
	}

	public String getPost_code() {
		return post_code;
	}

	public void setPost_code(String post_code) {
		this.post_code = post_code;
	}

	public String getParent() {
		return parent;
	}

	public void setParent(String parent) {
		this.parent = parent;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getFullname() {
		return fullname;
	}

	public void setFullname(String fullname) {
		this.fullname = fullname;
	}

	public String getFirst_name() {
		return first_name;
	}

	public void setFirst_name(String first_name) {
		this.first_name = first_name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public long getBirthday() {
		return birthday;
	}

	public void setBirthday(long birthday) {
		this.birthday = birthday;
	}

	public String getBanner_url() {
		return banner_url;
	}

	public void setBanner_url(String banner_url) {
		this.banner_url = banner_url;
	}

	public String getBackground_url() {
		return background_url;
	}

	public void setBackground_url(String background_url) {
		this.background_url = background_url;
	}

	public String getAvatar720() {
		return avatar720;
	}

	public void setAvatar720(String avatar720) {
		this.avatar720 = avatar720;
	}

	public String getAvatar60() {
		return avatar60;
	}

	public void setAvatar60(String avatar60) {
		this.avatar60 = avatar60;
	}

	public String getAvatar480() {
		return avatar480;
	}

	public void setAvatar480(String avatar480) {
		this.avatar480 = avatar480;
	}

	public String getAvatar360() {
		return avatar360;
	}

	public void setAvatar360(String avatar360) {
		this.avatar360 = avatar360;
	}

	public String getAvatar240() {
		return avatar240;
	}

	public void setAvatar240(String avatar240) {
		this.avatar240 = avatar240;
	}

	public String getAvatar190() {
		return avatar190;
	}

	public void setAvatar190(String avatar190) {
		this.avatar190 = avatar190;
	}

	public String getAvatar120() {
		return avatar120;
	}

	public void setAvatar120(String avatar120) {
		this.avatar120 = avatar120;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}
}
