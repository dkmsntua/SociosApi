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

public class ttTweet {
	private String created_at;
	private ttEntities entities;
	private int favorite_count;
	private boolean favorited;
	private String id_str;
	private String in_reply_to_screen_name;
	private String in_reply_to_status_id_str;
	private String in_reply_to_user_id_str;
	private String lang;
	private ttPlace places;
	private int retweet_count;
	private boolean retweeted;
	private String source;
	private String text;
	private ttUser user;

	public ttTweet() {
	}

	public ttTweet(JSONObject json) {
		this.setCreated_at(json.optString("created_at", null));
		JSONObject entities = json.optJSONObject("entities");
		if (entities != null) {
			ttEntities ttentities = new ttEntities(entities);
			this.setEntities(ttentities);
		}
		this.setFavorite_count(json.optInt("favorite_count", -1));
		this.setFavorited(json.optBoolean("favorited"));
		this.setId_str(json.optString("id_str", null));
		this.setIn_reply_to_screen_name(json.optString("in_reply_to_screen_name", null));
		this.setIn_reply_to_status_id_str(json.optString("in_reply_to_status_id_str", null));
		this.setIn_reply_to_user_id_str(json.optString("in_reply_to_user_id_str", null));
		this.setLang(json.optString("lang", null));
		JSONObject places = json.optJSONObject("places");
		if (places != null) {
			this.setPlaces(new ttPlace(places));
		}
		this.setRetweet_count(json.optInt("retweet_count", -1));
		this.setRetweeted(json.optBoolean("retweeted"));
		this.setSource(json.optString("source", null));
		this.setText(json.optString("text", null));
		JSONObject user = json.optJSONObject("user");
		if (user != null) {
			this.setUser(new ttUser(user));
		}
	}

	public ttUser getUser() {
		return user;
	}

	public void setUser(ttUser user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public boolean isRetweeted() {
		return retweeted;
	}

	public void setRetweeted(boolean retweeted) {
		this.retweeted = retweeted;
	}

	public int getRetweet_count() {
		return retweet_count;
	}

	public void setRetweet_count(int retweet_count) {
		this.retweet_count = retweet_count;
	}

	public ttPlace getPlaces() {
		return places;
	}

	public void setPlaces(ttPlace places) {
		this.places = places;
	}

	public String getLang() {
		return lang;
	}

	public void setLang(String lang) {
		this.lang = lang;
	}

	public String getIn_reply_to_user_id_str() {
		return in_reply_to_user_id_str;
	}

	public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
		this.in_reply_to_user_id_str = in_reply_to_user_id_str;
	}

	public String getIn_reply_to_status_id_str() {
		return in_reply_to_status_id_str;
	}

	public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
		this.in_reply_to_status_id_str = in_reply_to_status_id_str;
	}

	public String getIn_reply_to_screen_name() {
		return in_reply_to_screen_name;
	}

	public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
		this.in_reply_to_screen_name = in_reply_to_screen_name;
	}

	public String getId_str() {
		return id_str;
	}

	public void setId_str(String id_str) {
		this.id_str = id_str;
	}

	public boolean isFavorited() {
		return favorited;
	}

	public void setFavorited(boolean favorited) {
		this.favorited = favorited;
	}

	public int getFavorite_count() {
		return favorite_count;
	}

	public void setFavorite_count(int favorite_count) {
		this.favorite_count = favorite_count;
	}

	public ttEntities getEntities() {
		return entities;
	}

	public void setEntities(ttEntities entities) {
		this.entities = entities;
	}

	public String getCreated_at() {
		return created_at;
	}

	public void setCreated_at(String created_at) {
		this.created_at = created_at;
	}
}
