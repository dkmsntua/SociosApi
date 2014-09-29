package adaptors.instagram.igObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class igMediaItem
{
	public class igLocation
	{
		public double latitude;
		public double longitude;
		public String name;

		public igLocation()
		{
		}

		public igLocation(JSONObject json)
		{
			double latitude = json.optDouble("latitude", 0);
			this.latitude = latitude;
			double longitude = json.optDouble("longitude", 0);
			this.longitude = longitude;
			String name = json.optString("name", null);
			this.name = name;
		}
	}
	public class igMediaUrls
	{
		public String lowResolution;
		public String standardResolution;
		public String thumbnail;

		public igMediaUrls()
		{
		}

		public igMediaUrls(JSONObject json)
		{
			String lowResolution = ParseUtilities.doubleJsParse(json, "low_resolution", "url");
			this.lowResolution = lowResolution;
			String thumbnail = ParseUtilities.doubleJsParse(json, "thumbnail", "url");
			this.thumbnail = thumbnail;
			String standardResolution = ParseUtilities.doubleJsParse(json, "standard_resolution", "url");
			this.standardResolution = standardResolution;
		}
	}
	public String caption;
	public List<igComment> comments;
	public long created_time;
	public String filter;
	public String id;
	public igMediaUrls images;
	public List<igUser> likers;
	public String link;
	public igLocation location;
	public int numComments;
	public int numLikes;
	public List<String> tags;
	public String type;
	public igUser user;
	public List<igUser> usersInPhoto;
	public igMediaUrls videos;

	public igMediaItem()
	{
	}

	public igMediaItem(JSONObject json)
	{
		String type = json.optString("type", null);
		this.type = type;
		String filter = json.optString("filter", null);
		this.filter = filter;
		String caption = ParseUtilities.doubleJsParse(json, "caption", "text");
		this.caption = caption;
		String link = json.optString("link", null);
		this.link = link;
		long created_time = json.optLong("created_time", -1);
		this.created_time = created_time;
		String id = json.optString("id", null);
		this.id = id;
		JSONObject jslocation = json.optJSONObject("location");
		if (jslocation != null)
		{
			igLocation location = new igLocation(jslocation);
			this.location = location;
		}
		JSONObject jsuser = json.optJSONObject("user");
		if (jsuser != null)
		{
			igUser user = new igUser(jsuser);
			this.user = user;
		}
		JSONObject jscomments = json.optJSONObject("comments");
		if (jscomments != null)
		{
			int numComments = jscomments.optInt("count", -1);
			this.numComments = numComments;
			JSONArray commentsArray = jscomments.optJSONArray("data");
			if (Utilities.isValid(commentsArray))
			{
				this.comments = new ArrayList<igComment>();
				for (int index = 0; index < commentsArray.length(); index++)
				{
					JSONObject jscomment = commentsArray.optJSONObject(index);
					if (jscomment != null)
					{
						igComment comment = new igComment(jscomment);
						if (comment != null)
						{
							this.comments.add(comment);
						}
					}
				}
			}
		}
		JSONObject jslikers = json.optJSONObject("likes");
		if (jslikers != null)
		{
			int numLikes = jslikers.optInt("count", -1);
			this.numLikes = numLikes;
			JSONArray likersArray = jslikers.optJSONArray("data");
			if (Utilities.isValid(likersArray))
			{
				this.likers = new ArrayList<igUser>();
				for (int index = 0; index < likersArray.length(); index++)
				{
					JSONObject jsliker = likersArray.optJSONObject(index);
					if (jsliker != null)
					{
						igUser liker = new igUser(jsliker);
						if (liker != null)
						{
							this.likers.add(liker);
						}
					}
				}
			}
		}
		JSONArray usersInPhotoArray = json.optJSONArray("users_in_photo");
		if (Utilities.isValid(usersInPhotoArray))
		{
			this.usersInPhoto = new ArrayList<igUser>();
			for (int index = 0; index < usersInPhotoArray.length(); index++)
			{
				JSONObject jsuserInPhotoWrap = usersInPhotoArray.optJSONObject(index);
				if (jsuserInPhotoWrap != null)
				{
					JSONObject jsuserInPhoto = jsuserInPhotoWrap.optJSONObject("user");
					if (jsuserInPhoto != null)
					{
						igUser userInPhoto = new igUser(jsuserInPhoto);
						if (userInPhoto != null)
						{
							this.usersInPhoto.add(userInPhoto);
						}
					}
				}
			}
		}
		JSONObject jsimages = json.optJSONObject("images");
		if (jsimages != null)
		{
			igMediaUrls images = new igMediaUrls(jsimages);
			this.images = images;
		}
		JSONObject jsvideos = json.optJSONObject("videos");
		if (jsvideos != null)
		{
			igMediaUrls videos = new igMediaUrls(jsvideos);
			this.videos = videos;
		}
		JSONArray tagsArray = json.optJSONArray("tags");
		if (Utilities.isValid(tagsArray))
		{
			this.tags = new ArrayList<String>();
			for (int index = 0; index < tagsArray.length(); index++)
			{
				JSONObject jstagHolder = tagsArray.optJSONObject(index);
				if (jstagHolder != null)
				{
					JSONArray jsdata = jstagHolder.optJSONArray("data");
					if (Utilities.isValid(jsdata))
					{
						for (int j = 0; j < jsdata.length(); j++)
						{
							JSONObject jstag = jsdata.optJSONObject(j);
							if (jstag != null)
							{
								String tag = jstag.optString("name", null);
								if (Utilities.isValid(tag))
								{
									this.tags.add(tag);
								}
							}
						}
					}
				}
			}
		}
	}
}
