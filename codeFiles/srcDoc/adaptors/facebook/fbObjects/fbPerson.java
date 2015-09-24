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
package adaptors.facebook.fbObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class fbPerson {
	private String bio;
	private String birthday;
	private String cover;
	private List<fbSchool> education;
	private String email;
	private String first_name;
	private String gender;
	private String hometown;
	private String id;
	private String last_name;
	private String link;
	private String locale;
	private String location;
	private String middle_name;
	private String name;
	private String picture;
	private String quotes;
	private int timezone;
	private String updated_time;
	private String username;
	private List<String> work;

	public fbPerson() {
	}

	public fbPerson(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setName(json.optString("name", null));
		this.setFirst_name(json.optString("first_name", null));
		this.setMiddle_name(json.optString("middle_name", null));
		this.setLast_name(json.optString("last_name", null));
		this.setLink(json.optString("link", null));
		this.setUsername(json.optString("username", null));
		this.setBirthday(json.optString("birthday", null));
		this.setQuotes(json.optString("quotes", null));
		this.setGender(json.optString("gender", null));
		this.setEmail(json.optString("email", null));
		this.setTimezone(json.optInt("timezone", -1));
		this.setLocale(json.optString("locale", null));
		this.setUpdated_time(json.optString("updated_time", null));
		this.setLocation(ParseUtilities.doubleJsParse(json, "location", "name"));
		this.setBio(json.optString("bio", null));
		JSONObject jsPicture = json.optJSONObject("picture");
		if (jsPicture != null) {
			this.setPicture(ParseUtilities.doubleJsParse(jsPicture, "data", "url"));
		}
		this.setCover(ParseUtilities.doubleJsParse(json, "cover", "source"));
		JSONArray workArray = json.optJSONArray("work");
		if (Utilities.isValid(workArray)) {
			this.work = new ArrayList<String>();
			for (int index = 0; index < workArray.length(); index = index + 1) {
				JSONObject jswork = workArray.optJSONObject(index);
				if (jswork != null) {
					String employer = ParseUtilities.doubleJsParse(jswork, "employer", "name");
					if (Utilities.isValid(employer)) {
						this.work.add(employer);
					}
				}
			}
		}
		JSONArray educationArray = json.optJSONArray("education");
		if (Utilities.isValid(educationArray)) {
			this.education = new ArrayList<fbSchool>();
			for (int index = 0; index < educationArray.length(); index++) {
				JSONObject jsSchool = educationArray.optJSONObject(index);
				if (jsSchool != null) {
					fbSchool school = new fbSchool(jsSchool);
					this.education.add(school);
				}
			}
		}
		this.setHometown(ParseUtilities.doubleJsParse(json, "hometown", "name"));
	}

	public List<fbSchool> getEducation() {
		return education;
	}

	public void setEducation(List<fbSchool> education) {
		this.education = education;
	}

	public List<String> getWork() {
		return work;
	}

	public void setWork(List<String> work) {
		this.work = work;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public int getTimezone() {
		return timezone;
	}

	public void setTimezone(int timezone) {
		this.timezone = timezone;
	}

	public String getQuotes() {
		return quotes;
	}

	public void setQuotes(String quotes) {
		this.quotes = quotes;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMiddle_name() {
		return middle_name;
	}

	public void setMiddle_name(String middle_name) {
		this.middle_name = middle_name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocale() {
		return locale;
	}

	public void setLocale(String locale) {
		this.locale = locale;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public String getLast_name() {
		return last_name;
	}

	public void setLast_name(String last_name) {
		this.last_name = last_name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getHometown() {
		return hometown;
	}

	public void setHometown(String hometown) {
		this.hometown = hometown;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
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

	public String getBio() {
		return bio;
	}

	public void setBio(String bio) {
		this.bio = bio;
	}
	public class fbSchool {
		private String school;
		private String type;

		public fbSchool() {
		}

		public fbSchool(JSONObject json) {
			this.setType(json.optString("type", null));
			JSONObject jsSchool = json.optJSONObject("school");
			if (jsSchool != null) {
				this.setSchool(jsSchool.optString("name", null));
			}
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getSchool() {
			return school;
		}

		public void setSchool(String school) {
			this.school = school;
		}
	}
}
