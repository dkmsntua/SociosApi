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
package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class ytPerson {
	private ytAuthor author;
	private String channelId;
	private String firstName;
	private String googlePlusUserId;
	private String lastName;
	private String location;
	private String published;
	private int subscriberCount;
	private String summary;
	private String thumbnail;
	private String username;

	public ytPerson() {
	}

	public ytPerson(JSONObject json) {
		this.setSummary(ParseUtilities.doubleJsParse(json, "summary", "$t"));
		JSONArray authorArray = json.optJSONArray("author");
		if (Utilities.isValid(authorArray)) {
			JSONObject jsauthor = authorArray.optJSONObject(0);
			if (jsauthor != null) {
				this.setAuthor(new ytAuthor(jsauthor));
			}
		}
		this.setChannelId(ParseUtilities.doubleJsParse(json, "yt$channelId", "$t"));
		this.setFirstName(ParseUtilities.doubleJsParse(json, "yt$firstName", "$t"));
		this.setGooglePlusUserId(ParseUtilities.doubleJsParse(json, "yt$googlePlusUserId", "$t"));
		this.setLastName(ParseUtilities.doubleJsParse(json, "yt$lastName", "$t"));
		this.setLocation(ParseUtilities.doubleJsParse(json, "yt$location", "$t"));
		this.setSubscriberCount(ParseUtilities.doubleJsParseInt(json, "yt$statistics", "subscriberCount"));
		this.setThumbnail(ParseUtilities.doubleJsParse(json, "media$thumbnail", "url"));
		this.setUsername(ParseUtilities.doubleJsParse(json, "yt$username", "display"));
		this.setPublished(ParseUtilities.doubleJsParse(json, "published", "$t"));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getSummary() {
		return summary;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public int getSubscriberCount() {
		return subscriberCount;
	}

	public void setSubscriberCount(int subscriberCount) {
		this.subscriberCount = subscriberCount;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGooglePlusUserId() {
		return googlePlusUserId;
	}

	public void setGooglePlusUserId(String googlePlusUserId) {
		this.googlePlusUserId = googlePlusUserId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}

	public ytAuthor getAuthor() {
		return author;
	}

	public void setAuthor(ytAuthor author) {
		this.author = author;
	}
	public static class ytAuthor {
		private String name;
		private String url;
		private String userId;

		public ytAuthor() {
		}

		public ytAuthor(JSONObject json) {
			this.setName(ParseUtilities.doubleJsParse(json, "name", "$t"));
			this.setUserId(ParseUtilities.doubleJsParse(json, "yt$userId", "$t"));
			this.setUrl(ParseUtilities.doubleJsParse(json, "uri", "$t"));
		}

		public String getUserId() {
			return userId;
		}

		public void setUserId(String userId) {
			this.userId = userId;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}
	}
}
