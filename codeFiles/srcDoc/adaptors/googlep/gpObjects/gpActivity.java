package adaptors.googlep.gpObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import java.util.ArrayList;
import java.util.List;

public class gpActivity
{
	public class GpaActor
	{
		public String displayName;
		public String familyName;
		public String givenName;
		public String id;
		public String image;
		public String url;

		public GpaActor()
		{
		}

		public GpaActor(JSONObject json)
		{
			String id = json.optString("id", null);
			this.id = id;
			String displayName = json.optString("displayName", null);
			this.displayName = displayName;
			JSONObject jsname = json.optJSONObject("name");
			if (jsname != null)
			{
				String familyName = jsname.optString("familyName", null);
				this.familyName = familyName;
				String givenName = jsname.optString("givenName", null);
				this.givenName = givenName;
			}
			String url = json.optString("url", null);
			this.url = url;
			String image = ParseUtilities.doubleJsParse(json, "image", "url");
			this.image = image;
		}
	}
	public class GpaAttachment
	{
		public String content;
		public String displayName;
		public String embed;
		public String fullImage;
		public String id;
		public String image;
		public String objectType;
		public List<GpaThumbnail> thumbnails;
		public String url;

		public GpaAttachment()
		{
		}

		public GpaAttachment(JSONObject json)
		{
			String objectType = json.optString("objectType", null);
			this.objectType = objectType;
			String displayName = json.optString("displayName", null);
			this.displayName = displayName;
			String id = json.optString("id", null);
			this.id = id;
			String content = json.optString("content", null);
			this.content = content;
			String url = json.optString("url", null);
			this.url = url;
			String image = ParseUtilities.doubleJsParse(json, "image", "url");
			this.image = image;
			String fullImage = ParseUtilities.doubleJsParse(json, "fullImage", "url");
			this.fullImage = fullImage;
			String embed = ParseUtilities.doubleJsParse(json, "embed", "url");
			this.embed = embed;
			JSONArray thumbnailsArray = json.optJSONArray("thumbnails");
			if (thumbnailsArray != null)
			{
				this.thumbnails = new ArrayList<GpaThumbnail>();
				for (int index = 0; index < thumbnailsArray.length(); index++)
				{
					JSONObject jsthumbnail = thumbnailsArray.optJSONObject(index);
					if (jsthumbnail != null)
					{
						GpaThumbnail thumbnail = new GpaThumbnail(jsthumbnail);
						this.thumbnails.add(thumbnail);
					}
				}
			}
		}
	}
	public class GpaObject
	{
		public GpaActor actor;
		public List<GpaAttachment> attachments;
		public String content;
		public String id;
		public String objectType;
		public String originalContent;
		public int plusoners;
		public int replies;
		public int resharers;
		public String url;

		public GpaObject()
		{
		}

		public GpaObject(JSONObject json)
		{
			String objectType = json.optString("objectType", null);
			this.objectType = objectType;
			String id = json.optString("id", null);
			this.id = id;
			JSONObject jsactor = json.optJSONObject("actor");
			if (jsactor != null)
			{
				GpaActor actor = new GpaActor(jsactor);
				this.actor = actor;
			}
			String content = json.optString("content", null);
			this.content = content;
			String originalContent = json.optString("originalContent", null);
			this.originalContent = originalContent;
			String url = json.optString("url", null);
			this.url = url;
			int replies = ParseUtilities.doubleJsParseInt(json, "replies", "totalItems");
			this.replies = replies;
			int plusoners = ParseUtilities.doubleJsParseInt(json, "plusoners", "totalItems");
			this.plusoners = plusoners;
			int resharers = ParseUtilities.doubleJsParseInt(json, "resharers", "totalItems");
			this.resharers = resharers;
			JSONArray attachmentsArray = json.optJSONArray("attachments");
			if (attachmentsArray != null)
			{
				this.attachments = new ArrayList<GpaAttachment>();
				for (int index = 0; index < attachmentsArray.length(); index++)
				{
					JSONObject jsattachment = attachmentsArray.optJSONObject(index);
					if (jsattachment != null)
					{
						GpaAttachment attachment = new GpaAttachment(jsattachment);
						this.attachments.add(attachment);
					}
				}
			}
		}
	}
	public class GpaThumbnail
	{
		public String description;
		public String image;
		public String url;

		public GpaThumbnail()
		{
		}

		public GpaThumbnail(JSONObject json)
		{
			String url = json.optString("url", null);
			this.url = url;
			String description = json.optString("description", null);
			this.description = description;
			String image = ParseUtilities.doubleJsParse(json, "image", "url");
			this.image = image;
		}
	}
	public GpaActor actor;
	public String address;
	public String annotation;
	public String geocode;
	public String id;
	public GpaObject object;
	public String placeName;
	public String published;
	public String radius;
	public String title;
	public String updated;
	public String url;
	public String verb;

	public gpActivity()
	{
	}

	public gpActivity(JSONObject json)
	{
		String title = json.optString("title", null);
		this.title = title;
		String published = json.optString("published", null);
		this.published = published;
		String updated = json.optString("updated", null);
		this.updated = updated;
		String id = json.optString("id", null);
		this.id = id;
		String url = json.optString("url", null);
		this.url = url;
		JSONObject jsactor = json.optJSONObject("actor");
		if (jsactor != null)
		{
			GpaActor actor = new GpaActor(jsactor);
			this.actor = actor;
		}
		String verb = json.optString("verb", null);
		this.verb = verb;
		JSONObject jsobject = json.optJSONObject("object");
		if (jsobject != null)
		{
			GpaObject object = new GpaObject(jsobject);
			this.object = object;
		}
		String annotation = json.optString("annotation", null);
		this.annotation = annotation;
		String geocode = json.optString("geocode", null);
		this.geocode = geocode;
		String address = json.optString("address", null);
		this.address = address;
		String radius = json.optString("radius", null);
		this.radius = radius;
		String placeName = json.optString("placeName", null);
		this.placeName = placeName;
	}
}
