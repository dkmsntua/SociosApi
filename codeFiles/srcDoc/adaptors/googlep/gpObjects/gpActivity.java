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
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class gpActivity {
	private GpaActor actor;
	private String address;
	private String annotation;
	private String geocode;
	private String id;
	private GpaObject object;
	private String placeName;
	private String published;
	private String radius;
	private String title;
	private String updated;
	private String url;
	private String verb;

	public gpActivity() {
	}

	public gpActivity(JSONObject json) {
		this.setTitle(json.optString("title", null));
		this.setPublished(json.optString("published", null));
		this.setUpdated(json.optString("updated", null));
		this.setId(json.optString("id", null));
		this.setUrl(json.optString("url", null));
		JSONObject jsactor = json.optJSONObject("actor");
		if (jsactor != null) {
			this.setActor(new GpaActor(jsactor));
		}
		this.setVerb(json.optString("verb", null));
		JSONObject jsobject = json.optJSONObject("object");
		if (jsobject != null) {
			this.setObject(new GpaObject(jsobject));
		}
		this.setAnnotation(json.optString("annotation", null));
		this.setGeocode(json.optString("geocode", null));
		this.setAddress(json.optString("address", null));
		this.setRadius(json.optString("radius", null));
		this.setPlaceName(json.optString("placeName", null));
	}

	public String getVerb() {
		return verb;
	}

	public void setVerb(String verb) {
		this.verb = verb;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getUpdated() {
		return updated;
	}

	public void setUpdated(String updated) {
		this.updated = updated;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getRadius() {
		return radius;
	}

	public void setRadius(String radius) {
		this.radius = radius;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getPlaceName() {
		return placeName;
	}

	public void setPlaceName(String placeName) {
		this.placeName = placeName;
	}

	public GpaObject getObject() {
		return object;
	}

	public void setObject(GpaObject object) {
		this.object = object;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGeocode() {
		return geocode;
	}

	public void setGeocode(String geocode) {
		this.geocode = geocode;
	}

	public String getAnnotation() {
		return annotation;
	}

	public void setAnnotation(String annotation) {
		this.annotation = annotation;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public GpaActor getActor() {
		return actor;
	}

	public void setActor(GpaActor actor) {
		this.actor = actor;
	}
	public class GpaAttachment {
		private String content;
		private String displayName;
		private String embed;
		private String fullImage;
		private String id;
		private String image;
		private String objectType;
		private List<GpaThumbnail> thumbnails;
		private String url;

		public GpaAttachment() {
		}

		public GpaAttachment(JSONObject json) {
			this.setObjectType(json.optString("objectType", null));
			this.setDisplayName(json.optString("displayName", null));
			this.setId(json.optString("id", null));
			this.setContent(json.optString("content", null));
			this.setUrl(json.optString("url", null));
			this.setImage(ParseUtilities.doubleJsParse(json, "image", "url"));
			this.setFullImage(ParseUtilities.doubleJsParse(json, "fullImage", "url"));
			this.setEmbed(ParseUtilities.doubleJsParse(json, "embed", "url"));
			JSONArray thumbnailsArray = json.optJSONArray("thumbnails");
			if (thumbnailsArray != null) {
				this.thumbnails = new ArrayList<GpaThumbnail>();
				for (int index = 0; index < thumbnailsArray.length(); index++) {
					JSONObject jsthumbnail = thumbnailsArray.optJSONObject(index);
					if (jsthumbnail != null) {
						this.thumbnails.add(new GpaThumbnail(jsthumbnail));
					}
				}
			}
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getObjectType() {
			return objectType;
		}

		public void setObjectType(String objectType) {
			this.objectType = objectType;
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

		public String getFullImage() {
			return fullImage;
		}

		public void setFullImage(String fullImage) {
			this.fullImage = fullImage;
		}

		public String getEmbed() {
			return embed;
		}

		public void setEmbed(String embed) {
			this.embed = embed;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public List<GpaThumbnail> getThumbnails() {
			return thumbnails;
		}

		public void setThumbnails(List<GpaThumbnail> thumbnails) {
			this.thumbnails = thumbnails;
		}
	}
	public class GpaActor {
		private String displayName;
		private String familyName;
		private String givenName;
		private String id;
		private String image;
		private String url;

		public GpaActor() {
		}

		public GpaActor(JSONObject json) {
			this.setId(json.optString("id", null));
			this.setDisplayName(json.optString("displayName", null));
			JSONObject jsname = json.optJSONObject("name");
			if (jsname != null) {
				this.setFamilyName(jsname.optString("familyName", null));
				this.setGivenName(jsname.optString("givenName", null));
			}
			this.setUrl(json.optString("url", null));
			this.setImage(ParseUtilities.doubleJsParse(json, "image", "url"));
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
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

		public String getGivenName() {
			return givenName;
		}

		public void setGivenName(String givenName) {
			this.givenName = givenName;
		}

		public String getFamilyName() {
			return familyName;
		}

		public void setFamilyName(String familyName) {
			this.familyName = familyName;
		}

		public String getDisplayName() {
			return displayName;
		}

		public void setDisplayName(String displayName) {
			this.displayName = displayName;
		}
	}
	public class GpaThumbnail {
		private String description;
		private String image;
		private String url;

		public GpaThumbnail() {
		}

		public GpaThumbnail(JSONObject json) {
			this.setUrl(json.optString("url", null));
			this.setDescription(json.optString("description", null));
			this.setImage(ParseUtilities.doubleJsParse(json, "image", "url"));
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getImage() {
			return image;
		}

		public void setImage(String image) {
			this.image = image;
		}

		public String getDescription() {
			return description;
		}

		public void setDescription(String description) {
			this.description = description;
		}
	}
	public class GpaObject {
		private GpaActor actor;
		private List<GpaAttachment> attachments;
		private String content;
		private String id;
		private String objectType;
		private String originalContent;
		private int plusoners;
		private int replies;
		private int resharers;
		private String url;

		public GpaObject() {
		}

		public GpaObject(JSONObject json) {
			this.setObjectType(json.optString("objectType", null));
			this.setId(json.optString("id", null));
			JSONObject jsactor = json.optJSONObject("actor");
			if (jsactor != null) {
				this.setActor(new GpaActor(jsactor));
			}
			this.setContent(json.optString("content", null));
			this.setOriginalContent(json.optString("originalContent", null));
			this.setUrl(json.optString("url", null));
			this.setReplies(ParseUtilities.doubleJsParseInt(json, "replies", "totalItems"));
			this.setPlusoners(ParseUtilities.doubleJsParseInt(json, "plusoners", "totalItems"));
			this.setResharers(ParseUtilities.doubleJsParseInt(json, "resharers", "totalItems"));
			JSONArray attachmentsArray = json.optJSONArray("attachments");
			if (attachmentsArray != null) {
				this.attachments = new ArrayList<GpaAttachment>();
				for (int index = 0; index < attachmentsArray.length(); index++) {
					JSONObject jsattachment = attachmentsArray.optJSONObject(index);
					if (jsattachment != null) {
						this.attachments.add(new GpaAttachment(jsattachment));
					}
				}
			}
		}

		public List<GpaAttachment> getAttachments() {
			return attachments;
		}

		public void setAttachments(List<GpaAttachment> attachments) {
			this.attachments = attachments;
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public int getResharers() {
			return resharers;
		}

		public void setResharers(int resharers) {
			this.resharers = resharers;
		}

		public int getReplies() {
			return replies;
		}

		public void setReplies(int replies) {
			this.replies = replies;
		}

		public int getPlusoners() {
			return plusoners;
		}

		public void setPlusoners(int plusoners) {
			this.plusoners = plusoners;
		}

		public String getOriginalContent() {
			return originalContent;
		}

		public void setOriginalContent(String originalContent) {
			this.originalContent = originalContent;
		}

		public String getObjectType() {
			return objectType;
		}

		public void setObjectType(String objectType) {
			this.objectType = objectType;
		}

		public String getId() {
			return id;
		}

		public void setId(String id) {
			this.id = id;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public GpaActor getActor() {
			return actor;
		}

		public void setActor(GpaActor actor) {
			this.actor = actor;
		}
	}
}
