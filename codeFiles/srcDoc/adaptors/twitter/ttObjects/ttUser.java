/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package adaptors.twitter.ttObjects;

import org.json.JSONObject;

public class ttUser {
	private String created_at;
	private String description;
	private ttEntities entities;
	private int favourites_count;
	private int followers_count;
	private int friends_count;
	private String id_str;
	private String lang;
	private String location;
	private String name;
	private String profile_background_image_url;
	private String profile_image_url;
	private String screen_name;
	private int statuses_count;
	private String time_zone;
	private String url;
	private int utc_offset;
	private boolean verified;

	public ttUser() {
	}

	public ttUser(JSONObject json) {
		this.setCreated_at(json.optString("created_at", null));
		this.setDescription(json.optString("description", null));
		JSONObject entities = json.optJSONObject("entities");
		if (entities != null) {
			this.setEntities(new ttEntities(entities));
		}
		this.setFavourites_count(json.optInt("favourites_count", -1));
		this.setFollowers_count(json.optInt("followers_count", -1));
		this.setFriends_count(json.optInt("friends_count", -1));
		this.setId_str(json.optString("id_str", null));
		this.setLang(json.optString("lang", null));
		this.setLocation(json.optString("location", null));
		this.setName(json.optString("name", null));
		this.setProfile_background_image_url(json.optString("profile_background_image_url", null));
		this.setProfile_image_url(json.optString("profile_image_url", null));
		this.setScreen_name(json.optString("screen_name", null));
		this.setStatuses_count(json.optInt("statuses_count", -1));
		this.setTime_zone(json.optString("time_zone", null));
		this.setUrl(json.optString("url", null));
		this.setUtc_offset(json.optInt("utc_offset", -100));
		this.setVerified(json.optBoolean("verified"));
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public int getUtc_offset() {
		return utc_offset;
	}

	public void setUtc_offset(int utc_offset) {
		this.utc_offset = utc_offset;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTime_zone() {
		return time_zone;
	}

	public void setTime_zone(String time_zone) {
		this.time_zone = time_zone;
	}

	public int getStatuses_count() {
		return statuses_count;
	}

	public void setStatuses_count(int statuses_count) {
		this.statuses_count = statuses_count;
	}

	public String getScreen_name() {
		return screen_name;
	}

	public void setScreen_name(String screen_name) {
		this.screen_name = screen_name;
	}

	public String getProfile_image_url() {
		return profile_image_url;
	}

	public void setProfile_image_url(String profile_image_url) {
		this.profile_image_url = profile_image_url;
	}

	public String getProfile_background_image_url() {
		return profile_background_image_url;
	}

	public void setProfile_background_image_url(String profile_background_image_url) {
		this.profile_background_image_url = profile_background_image_url;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}

	public int getFriends_count() {
		return friends_count;
	}

	public void setFriends_count(int friends_count) {
		this.friends_count = friends_count;
	}

	public int getFollowers_count() {
		return followers_count;
	}

	public void setFollowers_count(int followers_count) {
		this.followers_count = followers_count;
	}

	public int getFavourites_count() {
		return favourites_count;
	}

	public void setFavourites_count(int favourites_count) {
		this.favourites_count = favourites_count;
	}

	public ttEntities getEntities() {
		return entities;
	}

	public void setEntities(ttEntities entities) {
		this.entities = entities;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
