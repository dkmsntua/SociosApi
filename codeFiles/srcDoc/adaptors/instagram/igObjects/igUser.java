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
package adaptors.instagram.igObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class igUser {
	private String bio;
	private String full_name;
	private String id;
	private int numFollowedBy;
	private int numFollows;
	private int numMedia;
	private String profile_picture;
	private String username;
	private String website;

	public igUser() {
	}

	public igUser(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setUsername(json.optString("username", null));
		this.setFull_name(json.optString("full_name", null));
		this.setProfile_picture(json.optString("profile_picture", null));
		this.setBio(json.optString("bio", null));
		this.setWebsite(json.optString("website", null));
		this.setNumMedia(ParseUtilities.doubleJsParseInt(json, "counts", "media"));
		this.setNumFollows(ParseUtilities.doubleJsParseInt(json, "counts", "follows"));
		this.setNumFollowedBy(ParseUtilities.doubleJsParseInt(json, "counts", "followed_by"));
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfile_picture() {
		return profile_picture;
	}

	public void setProfile_picture(String profile_picture) {
		this.profile_picture = profile_picture;
	}

	public int getNumMedia() {
		return numMedia;
	}

	public void setNumMedia(int numMedia) {
		this.numMedia = numMedia;
	}

	public int getNumFollows() {
		return numFollows;
	}

	public void setNumFollows(int numFollows) {
		this.numFollows = numFollows;
	}

	public int getNumFollowedBy() {
		return numFollowedBy;
	}

	public void setNumFollowedBy(int numFollowedBy) {
		this.numFollowedBy = numFollowedBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFull_name() {
		return full_name;
	}

	public void setFull_name(String full_name) {
		this.full_name = full_name;
	}

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
}
