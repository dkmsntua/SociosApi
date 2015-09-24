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
package adaptors.googlep.gpObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class gpPerson {
	private String aboutMe;
	private String birthday;
	private String cover;
	private String currentLocation;
	private String displayName;
	private String gender;
	private String id;
	private String image;
	private boolean isPlusUser;
	private String language;
	private GppName name;
	private String nickname;
	private String objectType;
	private List<String> placesLived;
	private String tagline;
	private String url;
	private List<String> urls;
	private boolean verified;

	public gpPerson() {
	}

	public gpPerson(JSONObject json) {
		this.setBirthday(json.optString("birthday", null));
		this.setGender(json.optString("gender", null));
		JSONArray urlArray = json.optJSONArray("urls");
		if (Utilities.isValid(urlArray)) {
			this.urls = new ArrayList<String>();
			for (int index = 0; index < urlArray.length(); index++) {
				JSONObject jsurl = urlArray.optJSONObject(index);
				if (jsurl != null) {
					this.urls.add(jsurl.optString("value", null));
				}
			}
		}
		this.setObjectType(json.optString("objectType", null));
		this.setId(json.optString("id", null));
		this.setDisplayName(json.optString("displayName", null));
		JSONObject jsname = json.optJSONObject("name");
		if (jsname != null) {
			this.setName(new GppName(jsname));
		}
		this.setTagline(json.optString("tagline", null));
		this.setAboutMe(json.optString("aboutMe", null));
		this.setCurrentLocation(json.optString("currentLocation", null));
		this.setUrl(json.optString("url", null));
		this.setImage(ParseUtilities.doubleJsParse(json, "image", "url"));
		JSONArray placesLivedArray = json.optJSONArray("placesLived");
		if (Utilities.isValid(placesLivedArray)) {
			this.placesLived = new ArrayList<String>();
			for (int index = 0; index < placesLivedArray.length(); index++) {
				JSONObject jsplaceLived = placesLivedArray.optJSONObject(index);
				if (jsplaceLived != null) {
					this.placesLived.add(jsplaceLived.optString("value", null));
				}
			}
		}
		this.setPlusUser(json.optBoolean("isPlusUser"));
		this.setLanguage(json.optString("language", null));
		this.setVerified(json.optBoolean("verified"));
		JSONObject jscover = json.optJSONObject("cover");
		if (jscover != null) {
			this.setCover(ParseUtilities.doubleJsParse(jscover, "coverPhoto", "url"));
		}
		this.setNickname(json.optString("nickname", null));
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public List<String> getPlacesLived() {
		return placesLived;
	}

	public void setPlacesLived(List<String> placesLived) {
		this.placesLived = placesLived;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getTagline() {
		return tagline;
	}

	public void setTagline(String tagline) {
		this.tagline = tagline;
	}

	public String getObjectType() {
		return objectType;
	}

	public void setObjectType(String objectType) {
		this.objectType = objectType;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

	public GppName getName() {
		return name;
	}

	public void setName(GppName name) {
		this.name = name;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public boolean isPlusUser() {
		return isPlusUser;
	}

	public void setPlusUser(boolean isPlusUser) {
		this.isPlusUser = isPlusUser;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
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

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getCurrentLocation() {
		return currentLocation;
	}

	public void setCurrentLocation(String currentLocation) {
		this.currentLocation = currentLocation;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getBirthday() {
		return birthday;
	}

	public void setBirthday(String birthday) {
		this.birthday = birthday;
	}

	public String getAboutMe() {
		return aboutMe;
	}

	public void setAboutMe(String aboutMe) {
		this.aboutMe = aboutMe;
	}
	public class GppName {
		private String familyName;
		private String formatted;
		private String givenName;
		private String honorificPrefix;
		private String honorificSuffix;
		private String middleName;

		public GppName() {
		}

		public GppName(JSONObject json) {
			this.setFormatted(json.optString("formatted", null));
			this.setFamilyName(json.optString("familyName", null));
			this.setGivenName(json.optString("givenName", null));
			this.setMiddleName(json.optString("middleName", null));
			this.setHonorificPrefix(json.optString("honorificPrefix", null));
			this.setHonorificSuffix(json.optString("honorificSuffix", null));
		}

		public String getMiddleName() {
			return middleName;
		}

		public void setMiddleName(String middleName) {
			this.middleName = middleName;
		}

		public String getHonorificSuffix() {
			return honorificSuffix;
		}

		public void setHonorificSuffix(String honorificSuffix) {
			this.honorificSuffix = honorificSuffix;
		}

		public String getHonorificPrefix() {
			return honorificPrefix;
		}

		public void setHonorificPrefix(String honorificPrefix) {
			this.honorificPrefix = honorificPrefix;
		}

		public String getGivenName() {
			return givenName;
		}

		public void setGivenName(String givenName) {
			this.givenName = givenName;
		}

		public String getFormatted() {
			return formatted;
		}

		public void setFormatted(String formatted) {
			this.formatted = formatted;
		}

		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}
	}
}
