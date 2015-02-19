package adaptors.facebook.fbObjects;

import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import adaptors.facebook.FacebookParsers;

public class fbPost {
	private List<fbComment> comments;
	private String application;
	private String created_time;
	private fbObjectId from;
	private String icon;
	private String id;
	private List<fbObjectId> likes;
	private String link;
	private String linkCaption;
	private String linkDescription;
	private String linkName;
	private String message;
	private List<fbTag> message_tags;
	private String picture;
	private fbPlace place;
	private int shares;
	private String status_type;
	private String story;
	private List<fbTag> story_tags;
	private List<fbObjectId> to;
	private String type;
	private String updated_time;
	private String videoSource;
	private List<fbObjectId> with_tags;

	public fbPost() {
	}

	public fbPost(JSONObject json) {
		this.setId(json.optString("id", null));
		JSONObject jsFrom = json.optJSONObject("from");
		if (jsFrom != null) {
			this.setFrom(new fbObjectId(jsFrom));
		}
		this.setTo(this.getfbObjects(json, "to"));
		this.setLikes(this.getfbObjects(json, "likes"));
		this.setWith_tags(this.getfbObjects(json, "with_tags"));
		this.setMessage(json.optString("message", null));
		this.setMessage_tags(FacebookParsers.getTags(json, "message_tags"));
		this.setPicture(json.optString("picture", null));
		this.setLink(json.optString("link", null));
		this.setLinkName(json.optString("name", null));
		this.setLinkCaption(json.optString("caption", null));
		this.setLinkDescription(json.optString("description", null));
		this.setVideoSource(json.optString("source", null));
		this.setIcon(json.optString("icon", null));
		this.setType(json.optString("type", null));
		this.setStory(json.optString("story", null));
		this.setStory_tags(FacebookParsers.getTags(json, "story_tags"));
		JSONObject jsComments = json.optJSONObject("comments");
		if (jsComments != null) {
			this.comments = new ArrayList<fbComment>();
			JSONArray jsArray = jsComments.optJSONArray("data");
			if (Utilities.isValid(jsArray)) {
				for (int index = 0; index < jsArray.length(); index++) {
					JSONObject jscomment = jsArray.optJSONObject(index);
					if (jscomment != null) {
						fbComment comment = new fbComment(jscomment);
						this.comments.add(comment);
					}
				}
			}
		}
		this.setApplication(json.optString("application", null));
		this.setCreated_time(json.optString("created_time", null));
		this.setUpdated_time(json.optString("updated_time", null));
		this.setShares(json.optInt("shares", -1));
		this.setStatus_type(json.optString("status_type", null));
		JSONObject jsplace = json.optJSONObject("place");
		if (jsplace != null) {
			this.setPlace(new fbPlace(jsplace));
		}
	}

	private List<fbObjectId> getfbObjects(JSONObject json, String title) {
		List<fbObjectId> result = new ArrayList<fbObjectId>();
		JSONObject jsObject = json.optJSONObject(title);
		if (jsObject != null) {
			JSONArray jsArray = jsObject.optJSONArray("data");
			if (Utilities.isValid(jsArray)) {
				for (int index = 0; index < jsArray.length(); index++) {
					JSONObject item = jsArray.optJSONObject(index);
					if (item != null) {
						fbObjectId objectId = new fbObjectId(item);
						result.add(objectId);
					}
				}
			}
		}
		return result;
	}

	public List<fbComment> getComments() {
		return comments;
	}

	public void setComments(List<fbComment> comments) {
		this.comments = comments;
	}

	public List<fbObjectId> getWith_tags() {
		return with_tags;
	}

	public void setWith_tags(List<fbObjectId> with_tags) {
		this.with_tags = with_tags;
	}

	public String getVideoSource() {
		return videoSource;
	}

	public void setVideoSource(String videoSource) {
		this.videoSource = videoSource;
	}

	public String getUpdated_time() {
		return updated_time;
	}

	public void setUpdated_time(String updated_time) {
		this.updated_time = updated_time;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<fbObjectId> getTo() {
		return to;
	}

	public void setTo(List<fbObjectId> to) {
		this.to = to;
	}

	public List<fbTag> getStory_tags() {
		return story_tags;
	}

	public void setStory_tags(List<fbTag> story_tags) {
		this.story_tags = story_tags;
	}

	public String getStory() {
		return story;
	}

	public void setStory(String story) {
		this.story = story;
	}

	public String getStatus_type() {
		return status_type;
	}

	public void setStatus_type(String status_type) {
		this.status_type = status_type;
	}

	public int getShares() {
		return shares;
	}

	public void setShares(int shares) {
		this.shares = shares;
	}

	public fbPlace getPlace() {
		return place;
	}

	public void setPlace(fbPlace place) {
		this.place = place;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public List<fbTag> getMessage_tags() {
		return message_tags;
	}

	public void setMessage_tags(List<fbTag> message_tags) {
		this.message_tags = message_tags;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getLinkName() {
		return linkName;
	}

	public void setLinkName(String linkName) {
		this.linkName = linkName;
	}

	public String getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(String linkDescription) {
		this.linkDescription = linkDescription;
	}

	public String getLinkCaption() {
		return linkCaption;
	}

	public void setLinkCaption(String linkCaption) {
		this.linkCaption = linkCaption;
	}

	public String getLink() {
		return link;
	}

	public void setLink(String link) {
		this.link = link;
	}

	public List<fbObjectId> getLikes() {
		return likes;
	}

	public void setLikes(List<fbObjectId> likes) {
		this.likes = likes;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIcon() {
		return icon;
	}

	public void setIcon(String icon) {
		this.icon = icon;
	}

	public fbObjectId getFrom() {
		return from;
	}

	public void setFrom(fbObjectId from) {
		this.from = from;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}
	public class fbPlace {
		private String city;
		private String country;
		private String id;
		private double latitude;
		private double longitude;
		private String name;
		private String state;
		private String street;
		private String zip;

		public fbPlace() {
		}

		public fbPlace(JSONObject json) {
			this.setId(json.optString("id", null));
			this.setName(json.optString("name", null));
			JSONObject jslocation = json.optJSONObject("location");
			if (jslocation != null) {
				this.setStreet(jslocation.optString("street", null));
				this.setCity(jslocation.optString("city", null));
				this.setState(jslocation.optString("state", null));
				this.setCountry(jslocation.optString("country", null));
				this.setZip(jslocation.optString("zip", null));
				this.setLatitude(jslocation.optDouble("latitude", 0));
				this.setLongitude(jslocation.optDouble("longitude", 0));
			}
		}

		public String getZip() {
			return zip;
		}

		public void setZip(String zip) {
			this.zip = zip;
		}

		public String getStreet() {
			return street;
		}

		public void setStreet(String street) {
			this.street = street;
		}

		public String getState() {
			return state;
		}

		public void setState(String state) {
			this.state = state;
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

		public String getCity() {
			return city;
		}

		public void setCity(String city) {
			this.city = city;
		}

		public String getCountry() {
			return country;
		}

		public void setCountry(String country) {
			this.country = country;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public double getLatitude() {
			return latitude;
		}

		public void setLatitude(double latitude) {
			this.latitude = latitude;
		}
	}
}
