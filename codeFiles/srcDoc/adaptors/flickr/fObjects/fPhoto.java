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
package adaptors.flickr.fObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class fPhoto {
	private String comments;
	private String datetaken;
	private String dateuploaded;
	private String description;
	private String farm;
	private String id;
	private String ispublic;
	private double latitude;
	private int license;
	private fLocation location;
	private double longitude;
	private List<String> machine_tags;
	private String media;
	private fPerson owner;
	private String rotation;
	private String safety_level;
	private String secret;
	private String server;
	private List<String> tags;
	private String title;
	private List<String> urls;
	private String views;

	public fPhoto() {
	}

	public fPhoto(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setSecret(json.optString("secret", null));
		this.setServer(json.optString("server", null));
		this.setFarm(json.optString("farm", null));
		String dateuploaded = json.optString("dateuploaded", null);
		if (!Utilities.isValid(dateuploaded)) {
			dateuploaded = ParseUtilities.doubleJsParse(json, "dates", "posted");
		}
		this.setDateuploaded(dateuploaded);
		String datetaken = json.optString("datetaken", null);
		if (!Utilities.isValid(datetaken)) {
			datetaken = ParseUtilities.doubleJsParse(json, "dates", "taken");
		}
		this.setDatetaken(datetaken);
		this.setLicense(json.optInt("license", -1));
		JSONObject jsowner = json.optJSONObject("owner");
		if (jsowner != null) {
			this.owner = new fPerson(jsowner);
		}
		if (this.owner == null) {
			fPerson person = new fPerson();
			person.setId(json.optString("owner", null));
			person.setUsername(json.optString("username", null));
			String ownername = json.optString("ownername", null);
			if (!Utilities.isValid(person.getUsername())) {
				person.setUsername(ownername);
			}
			person.setPath_alias(json.optString("pathalias", null));
			person.setIconserver(json.optString("iconserver", null));
			person.setIconfarm(json.optString("iconfarm", null));
			this.owner = person;
		}
		JSONObject jsdescription = json.optJSONObject("description");
		String description = null;
		if (jsdescription != null) {
			description = jsdescription.optString("_content", null);
		}
		if (description == null) {
			description = json.optString("description", null);
		}
		this.setDescription(description);
		JSONObject jstitle = json.optJSONObject("title");
		String title = null;
		if (jstitle != null) {
			title = jstitle.optString("_content", null);
		}
		if (title == null) {
			title = json.optString("title", null);
		}
		this.setTitle(title);
		this.setViews(json.optString("views", null));
		this.setMedia(json.optString("media", null));
		JSONObject jstags = json.optJSONObject("tags");
		if (jstags != null) {
			JSONArray tagsArray = jstags.optJSONArray("tag");
			if (Utilities.isValid(tagsArray)) {
				this.tags = new ArrayList<String>();
				for (int index = 0; index < tagsArray.length(); index++) {
					JSONObject jstag = tagsArray.optJSONObject(index);
					if (jstag != null) {
						this.tags.add(jstag.optString("raw", null));
					}
				}
			}
		}
		else {
			String tagsChain = json.optString("tags", null);
			if (Utilities.isValid(tagsChain)) {
				String[] tags = tagsChain.split(" ");
				this.tags = new ArrayList<String>(Arrays.asList(tags));
			}
		}
		JSONObject urlsHolder = json.optJSONObject("urls");
		if (urlsHolder != null) {
			JSONArray urls = urlsHolder.optJSONArray("url");
			if (urls != null) {
				this.urls = new ArrayList<String>();
				for (int index = 0; index < urls.length(); index++) {
					JSONObject jsurl = urls.optJSONObject(index);
					if (jsurl != null) {
						String url = jsurl.optString("_content", null);
						this.urls.add(url);
					}
				}
			}
		}
		String machineTagHolder = json.optString("machine_tags", null);
		if (Utilities.isValid(machineTagHolder)) {
			String[] machine_tags = machineTagHolder.split(" ");
			this.machine_tags = new ArrayList<String>(Arrays.asList(machine_tags));
		}
		String ispublic = ParseUtilities.doubleJsParse(json, "visibility", "ispublic");
		if (ispublic == null) {
			ispublic = json.optString("ispublic", null);
		}
		this.setIspublic(ispublic);
		String comments = ParseUtilities.doubleJsParse(json, "comments", "_content");
		if (comments == null) {
			comments = json.optString("comments", null);
		}
		this.setComments(comments);
		JSONObject jslocation = json.optJSONObject("location");
		if (jslocation != null) {
			fLocation location = new fLocation(jslocation);
			this.setLocation(location);
		}
		double latitude = json.optDouble("latitude", 0);
		this.setLatitude(latitude);
		double longitude = json.optDouble("longitude", 0);
		this.setLongitude(longitude);
	}

	public List<String> getMachine_tags() {
		return machine_tags;
	}

	public void setMachine_tags(List<String> machine_tags) {
		this.machine_tags = machine_tags;
	}

	public fPerson getOwner() {
		return owner;
	}

	public void setOwner(fPerson owner) {
		this.owner = owner;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public String getViews() {
		return views;
	}

	public void setViews(String views) {
		this.views = views;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getServer() {
		return server;
	}

	public void setServer(String server) {
		this.server = server;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getSafety_level() {
		return safety_level;
	}

	public void setSafety_level(String safety_level) {
		this.safety_level = safety_level;
	}

	public String getRotation() {
		return rotation;
	}

	public void setRotation(String rotation) {
		this.rotation = rotation;
	}

	public String getMedia() {
		return media;
	}

	public void setMedia(String media) {
		this.media = media;
	}

	public double getLongitude() {
		return longitude;
	}

	public void setLongitude(double longitude) {
		this.longitude = longitude;
	}

	public fLocation getLocation() {
		return location;
	}

	public void setLocation(fLocation location) {
		this.location = location;
	}

	public int getLicense() {
		return license;
	}

	public void setLicense(int license) {
		this.license = license;
	}

	public double getLatitude() {
		return latitude;
	}

	public void setLatitude(double latitude) {
		this.latitude = latitude;
	}

	public String getIspublic() {
		return ispublic;
	}

	public void setIspublic(String ispublic) {
		this.ispublic = ispublic;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFarm() {
		return farm;
	}

	public void setFarm(String farm) {
		this.farm = farm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDateuploaded() {
		return dateuploaded;
	}

	public void setDateuploaded(String dateuploaded) {
		this.dateuploaded = dateuploaded;
	}

	public String getDatetaken() {
		return datetaken;
	}

	public void setDatetaken(String datetaken) {
		this.datetaken = datetaken;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}
	public class fLocation {
		private String country;
		private String county;
		private double latitude;
		private String locality;
		private double longitude;
		private String neighbourhood;
		private String region;

		public fLocation() {
		}

		public fLocation(JSONObject json) {
			this.setLatitude(json.optDouble("latitude", 0));
			this.setLongitude(json.optDouble("longitude", 0));
			this.setNeighbourhood(ParseUtilities.doubleOrSimpleJsParse(json, "neighbourhood"));
			this.setLocality(ParseUtilities.doubleOrSimpleJsParse(json, "locality"));
			this.setCounty(ParseUtilities.doubleOrSimpleJsParse(json, "county"));
			this.setRegion(ParseUtilities.doubleOrSimpleJsParse(json, "region"));
			this.setCountry(ParseUtilities.doubleOrSimpleJsParse(json, "country"));
		}

		public String getRegion() {
			return region;
		}

		public void setRegion(String region) {
			this.region = region;
		}

		public String getNeighbourhood() {
			return neighbourhood;
		}

		public void setNeighbourhood(String neighbourhood) {
			this.neighbourhood = neighbourhood;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public String getLocality() {
			return locality;
		}

		public void setLocality(String locality) {
			this.locality = locality;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}

		public String getCounty() {
			return county;
		}

		public void setCounty(String county) {
			this.county = county;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}
	}
}
