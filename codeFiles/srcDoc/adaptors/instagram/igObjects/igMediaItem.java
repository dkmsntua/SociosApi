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
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class igMediaItem {
	private String caption;
	private List<igComment> comments;
	private long created_time;
	private String filter;
	private String id;
	private igMediaUrls images;
	private List<igUser> likers;
	private String link;
	private igLocation location;
	private int numComments;
	private int numLikes;
	private List<String> tags;
	private String type;
	private igUser user;
	private List<igUser> usersInPhoto;
	private igMediaUrls videos;

	public igMediaItem() {
	}

	public igMediaItem(JSONObject json) {
		this.setType(json.optString("type", null));
		this.setFilter(json.optString("filter", null));
		this.setCaption(ParseUtilities.doubleJsParse(json, "caption", "text"));
		this.setLink(json.optString("link", null));
		this.setCreated_time(json.optLong("created_time", -1));
		this.setId(json.optString("id", null));
		JSONObject jslocation = json.optJSONObject("location");
		if (jslocation != null) {
			this.setLocation(new igLocation(jslocation));
		}
		JSONObject jsuser = json.optJSONObject("user");
		if (jsuser != null) {
			this.setUser(new igUser(jsuser));
		}
		JSONObject jscomments = json.optJSONObject("comments");
		if (jscomments != null) {
			this.setNumComments(jscomments.optInt("count", -1));
			JSONArray commentsArray = jscomments.optJSONArray("data");
			if (Utilities.isValid(commentsArray)) {
				this.comments = new ArrayList<igComment>();
				for (int index = 0; index < commentsArray.length(); index++) {
					JSONObject jscomment = commentsArray.optJSONObject(index);
					if (jscomment != null) {
						igComment comment = new igComment(jscomment);
						this.comments.add(comment);
					}
				}
			}
		}
		JSONObject jslikers = json.optJSONObject("likes");
		if (jslikers != null) {
			this.setNumLikes(jslikers.optInt("count", -1));
			JSONArray likersArray = jslikers.optJSONArray("data");
			if (Utilities.isValid(likersArray)) {
				this.likers = new ArrayList<igUser>();
				for (int index = 0; index < likersArray.length(); index++) {
					JSONObject jsliker = likersArray.optJSONObject(index);
					if (jsliker != null) {
						igUser liker = new igUser(jsliker);
						this.likers.add(liker);
					}
				}
			}
		}
		JSONArray usersInPhotoArray = json.optJSONArray("users_in_photo");
		if (Utilities.isValid(usersInPhotoArray)) {
			this.usersInPhoto = new ArrayList<igUser>();
			for (int index = 0; index < usersInPhotoArray.length(); index++) {
				JSONObject jsuserInPhotoWrap = usersInPhotoArray.optJSONObject(index);
				if (jsuserInPhotoWrap != null) {
					JSONObject jsuserInPhoto = jsuserInPhotoWrap.optJSONObject("user");
					if (jsuserInPhoto != null) {
						igUser userInPhoto = new igUser(jsuserInPhoto);
						this.usersInPhoto.add(userInPhoto);
					}
				}
			}
		}
		JSONObject jsimages = json.optJSONObject("images");
		if (jsimages != null) {
			this.setImages(new igMediaUrls(jsimages));
		}
		JSONObject jsvideos = json.optJSONObject("videos");
		if (jsvideos != null) {
			this.setVideos(new igMediaUrls(jsvideos));
		}
		JSONArray tagsArray = json.optJSONArray("tags");
		if (Utilities.isValid(tagsArray)) {
			this.tags = new ArrayList<String>();
			for (int index = 0; index < tagsArray.length(); index++) {
				JSONObject jstagHolder = tagsArray.optJSONObject(index);
				if (jstagHolder != null) {
					JSONArray jsdata = jstagHolder.optJSONArray("data");
					if (Utilities.isValid(jsdata)) {
						for (int j = 0; j < jsdata.length(); j++) {
							JSONObject jstag = jsdata.optJSONObject(j);
							if (jstag != null) {
								String tag = jstag.optString("name", null);
								if (Utilities.isValid(tag)) {
									this.tags.add(tag);
								}
							}
						}
					}
				}
			}
		}
	}

	public List<igComment> getComments() {
		return comments;
	}

	public void setComments(List<igComment> comments) {
		this.comments = comments;
	}

	public List<igUser> getLikers() {
		return likers;
	}

	public void setLikers(List<igUser> likers) {
		this.likers = likers;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public List<igUser> getUsersInPhoto() {
		return usersInPhoto;
	}

	public void setUsersInPhoto(List<igUser> usersInPhoto) {
		this.usersInPhoto = usersInPhoto;
	}

	public igMediaUrls getVideos() {
		return videos;
	}

	public void setVideos(igMediaUrls videos) {
		this.videos = videos;
	}

	public igUser getUser() {
		return user;
	}

	public void setUser(igUser user) {
		this.user = user;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public int getNumComments() {
		return numComments;
	}

	public void setNumComments(int numComments) {
		this.numComments = numComments;
	}

	public igLocation getLocation() {
		return location;
	}

	public void setLocation(igLocation location) {
		this.location = location;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public igMediaUrls getImages() {
		return images;
	}

	public void setImages(igMediaUrls images) {
		this.images = images;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFilter() {
		return filter;
	}

	public void setFilter(String filter) {
		this.filter = filter;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}
	public class igLocation {
		private double latitude;
		private double longitude;
		private String name;

		public igLocation() {
		}

		public igLocation(JSONObject json) {
			this.setLatitude(json.optDouble("latitude", 0));
			this.setLongitude(json.optDouble("longitude", 0));
			this.setName(json.optString("name", null));
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public double getLongitude() {
			return longitude;
		}

		public void setLongitude(double longitude) {
			this.longitude = longitude;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
	}
	public class igMediaUrls {
		private String lowResolution;
		private String standardResolution;
		private String thumbnail;

		public igMediaUrls() {
		}

		public igMediaUrls(JSONObject json) {
			this.setLowResolution(ParseUtilities.doubleJsParse(json, "low_resolution", "url"));
			this.setThumbnail(ParseUtilities.doubleJsParse(json, "thumbnail", "url"));
			this.setStandardResolution(ParseUtilities.doubleJsParse(json, "standard_resolution", "url"));
		}

		public String getThumbnail() {
			return thumbnail;
		}

		public void setThumbnail(String thumbnail) {
			this.thumbnail = thumbnail;
		}

		public String getStandardResolution() {
			return standardResolution;
		}

		public void setStandardResolution(String standardResolution) {
			this.standardResolution = standardResolution;
		}

		public String getLowResolution() {
			return lowResolution;
		}

		public void setLowResolution(String lowResolution) {
			this.lowResolution = lowResolution;
		}
	}
}
