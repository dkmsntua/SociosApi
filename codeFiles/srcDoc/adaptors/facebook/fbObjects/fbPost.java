package adaptors.facebook.fbObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import adaptors.facebook.FacebookParsers;

public class fbPost
{
	public class fbPlace
	{
		public String city;
		public String country;
		public String id;
		public double latitude;
		public double longitude;
		public String name;
		public String state;
		public String street;
		public String zip;

		public fbPlace()
		{
		}

		public fbPlace(JSONObject json)
		{
			String id = json.optString("id", null);
			this.id = id;
			String name = json.optString("name", null);
			this.name = name;
			JSONObject jslocation = json.optJSONObject("location");
			if (jslocation != null)
			{
				String street = jslocation.optString("street", null);
				this.street = street;
				String city = jslocation.optString("city", null);
				this.city = city;
				String state = jslocation.optString("state", null);
				this.state = state;
				String country = jslocation.optString("country", null);
				this.country = country;
				String zip = jslocation.optString("zip", null);
				this.zip = zip;
				double latitude = jslocation.optDouble("latitude", 0);
				this.latitude = latitude;
				double longitude = jslocation.optDouble("longitude", 0);
				this.longitude = longitude;
			}
		}
	}
	public String application;
	public List<fbComment> comments;
	public String created_time;
	public fbObjectId from;
	public String icon;
	public String id;
	public List<fbObjectId> likes;
	public String link;
	public String linkCaption;
	public String linkDescription;
	public String linkName;
	public String message;
	public List<fbTag> message_tags;
	public String picture;
	public fbPlace place;
	public int shares;
	public String status_type;
	public String story;
	public List<fbTag> story_tags;
	public List<fbObjectId> to;
	public String type;
	public String updated_time;
	public String videoSource;
	public List<fbObjectId> with_tags;

	public fbPost()
	{
	}

	public fbPost(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jsFrom = json.optJSONObject("from");
		if (jsFrom != null)
		{
			fbObjectId from = new fbObjectId(jsFrom);
			this.from = from;
		}
		this.to = getfbObjects(json, "to");
		this.likes = getfbObjects(json, "likes");
		this.with_tags = getfbObjects(json, "with_tags");
		String message = json.optString("message", null);
		this.message = message;
		this.message_tags = FacebookParsers.getTags(json, "message_tags");
		String picture = json.optString("picture", null);
		this.picture = picture;
		String link = json.optString("link", null);
		this.link = link;
		String linkName = json.optString("name", null);
		this.linkName = linkName;
		String linkCaption = json.optString("caption", null);
		this.linkCaption = linkCaption;
		String linkDescription = json.optString("description", null);
		this.linkDescription = linkDescription;
		String videoSource = json.optString("source", null);
		this.videoSource = videoSource;
		String icon = json.optString("icon", null);
		this.icon = icon;
		String type = json.optString("type", null);
		this.type = type;
		String story = json.optString("story", null);
		this.story = story;
		this.story_tags = FacebookParsers.getTags(json, "story_tags");
		JSONObject jsComments = json.optJSONObject("comments");
		if (jsComments != null)
		{
			this.comments = new ArrayList<fbComment>();
			JSONArray jsArray = jsComments.optJSONArray("data");
			if (Utilities.isValid(jsArray))
			{
				for (int index = 0; index < jsArray.length(); index++)
				{
					JSONObject jscomment = jsArray.optJSONObject(index);
					if (jscomment != null)
					{
						fbComment comment = new fbComment(jscomment);
						if (comment != null)
						{
							this.comments.add(comment);
						}
					}
				}
			}
		}
		String application = json.optString("application", null);
		this.application = application;
		String created_time = json.optString("created_time", null);
		this.created_time = created_time;
		String updated_time = json.optString("updated_time", null);
		this.updated_time = updated_time;
		int shares = json.optInt("shares", -1);
		this.shares = shares;
		String status_type = json.optString("status_type", null);
		this.status_type = status_type;
		JSONObject jsplace = json.optJSONObject("place");
		if (jsplace != null)
		{
			fbPlace place = new fbPlace(jsplace);
			this.place = place;
		}
	}

	private List<fbObjectId> getfbObjects(JSONObject json, String title)
	{
		List<fbObjectId> result = new ArrayList<fbObjectId>();
		JSONObject jsObject = json.optJSONObject(title);
		if (jsObject != null)
		{
			JSONArray jsArray = jsObject.optJSONArray("data");
			if (Utilities.isValid(jsArray))
			{
				for (int index = 0; index < jsArray.length(); index++)
				{
					JSONObject item = jsArray.optJSONObject(index);
					if (item != null)
					{
						fbObjectId objectId = new fbObjectId(item);
						if (objectId != null)
						{
							result.add(objectId);
						}
					}
				}
			}
		}
		return result;
	}
}
