package adaptors.flickr.fObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class fPhoto
{
	public class fLocation
	{
		public String country;
		public String county;
		public double latitude;
		public String locality;
		public double longitude;
		public String neighbourhood;
		public String region;

		public fLocation()
		{
		}

		public fLocation(JSONObject json)
		{
			double latitude = json.optDouble("latitude", 0);
			this.latitude = latitude;
			double longitude = json.optDouble("longitude", 0);
			this.longitude = longitude;
			String neighbourhood = ParseUtilities.doubleOrSimpleJsParse(json, "neighbourhood");
			this.neighbourhood = neighbourhood;
			String locality = ParseUtilities.doubleOrSimpleJsParse(json, "locality");
			this.locality = locality;
			String county = ParseUtilities.doubleOrSimpleJsParse(json, "county");
			this.county = county;
			String region = ParseUtilities.doubleOrSimpleJsParse(json, "region");
			this.region = region;
			String country = ParseUtilities.doubleOrSimpleJsParse(json, "country");
			this.country = country;
		}
	}
	public String comments;
	public String datetaken;
	public String dateuploaded;
	public String description;
	public String farm;
	public String id;
	public String ispublic;
	public double latitude;
	public int license;
	public fLocation location;
	public double longitude;
	public List<String> machine_tags;
	public String media;
	public fPerson owner;
	public String rotation;
	public String safety_level;
	public String secret;
	public String server;
	public List<String> tags;
	public String title;
	public List<String> urls;
	public String views;

	public fPhoto()
	{
	}

	public fPhoto(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String secret = json.optString("secret", null);
		this.secret = secret;
		String server = json.optString("server", null);
		this.server = server;
		String farm = json.optString("farm", null);
		this.farm = farm;
		String dateuploaded = json.optString("dateuploaded", null);
		if (!Utilities.isValid(dateuploaded))
		{
			dateuploaded = ParseUtilities.doubleJsParse(json, "dates", "posted");
		}
		this.dateuploaded = dateuploaded;
		String datetaken = json.optString("datetaken", null);
		if (!Utilities.isValid(datetaken))
		{
			datetaken = ParseUtilities.doubleJsParse(json, "dates", "taken");
		}
		this.datetaken = datetaken;
		int license = json.optInt("license", -1);
		this.license = license;
		JSONObject jsowner = json.optJSONObject("owner");
		if (jsowner != null)
		{
			fPerson fowner = new fPerson(jsowner);
			this.owner = fowner;
		}
		if (this.owner == null)
		{
			fPerson person = new fPerson();
			String owner = json.optString("owner", null);
			person.id = owner;
			String username = json.optString("username", null);
			person.username = username;
			String ownername = json.optString("ownername", null);
			if (!Utilities.isValid(person.username))
			{
				person.username = ownername;
			}
			String pathalias = json.optString("pathalias", null);
			person.path_alias = pathalias;
			String iconserver = json.optString("iconserver", null);
			person.iconserver = iconserver;
			String iconfarm = json.optString("iconfarm", null);
			person.iconfarm = iconfarm;
			this.owner = person;
		}
		JSONObject jsdescription = json.optJSONObject("description");
		String description = null;
		if (jsdescription != null)
		{
			description = jsdescription.optString("_content", null);
		}
		if (description == null)
		{
			description = json.optString("description", null);
		}
		this.description = description;
		JSONObject jstitle = json.optJSONObject("title");
		String title = null;
		if (jstitle != null)
		{
			title = jstitle.optString("_content", null);
		}
		if (title == null)
		{
			title = json.optString("title", null);
		}
		this.title = title;
		String views = json.optString("views", null);
		this.views = views;
		String media = json.optString("media", null);
		this.media = media;
		JSONObject jstags = json.optJSONObject("tags");
		if (jstags != null)
		{
			JSONArray tagsArray = jstags.optJSONArray("tag");
			if (Utilities.isValid(tagsArray))
			{
				this.tags = new ArrayList<String>();
				for (int index = 0; index < tagsArray.length(); index++)
				{
					JSONObject jstag = tagsArray.optJSONObject(index);
					if (jstag != null)
					{
						String tag = jstag.optString("raw", null);
						this.tags.add(tag);
					}
				}
			}
		}
		else
		{
			String tagsChain = json.optString("tags", null);
			if (Utilities.isValid(tagsChain))
			{
				String[] tags = tagsChain.split(" ");
				this.tags = new ArrayList<String>();
				if (tags != null && tags.length > 0)
				{
					for (String tag : tags)
					{
						this.tags.add(tag);
					}
				}
			}
		}
		JSONObject urlsHolder = json.optJSONObject("urls");
		if (urlsHolder != null)
		{
			JSONArray urls = urlsHolder.optJSONArray("url");
			if (urls != null)
			{
				this.urls = new ArrayList<String>();
				for (int index = 0; index < urls.length(); index++)
				{
					JSONObject jsurl = urls.optJSONObject(index);
					if (jsurl != null)
					{
						String url = jsurl.optString("_content", null);
						this.urls.add(url);
					}
				}
			}
		}
		String machineTagHolder = json.optString("machine_tags", null);
		if (Utilities.isValid(machineTagHolder))
		{
			this.machine_tags = new ArrayList<String>();
			String[] machine_tags = machineTagHolder.split(" ");
			for (String machine_tag : machine_tags)
			{
				this.machine_tags.add(machine_tag);
			}
		}
		String ispublic = ParseUtilities.doubleJsParse(json, "visibility", "ispublic");
		if (ispublic == null)
		{
			ispublic = json.optString("ispublic", null);
		}
		this.ispublic = ispublic;
		String comments = ParseUtilities.doubleJsParse(json, "comments", "_content");
		if (comments == null)
		{
			comments = json.optString("comments", null);
		}
		this.comments = comments;
		JSONObject jslocation = json.optJSONObject("location");
		if (jslocation != null)
		{
			fLocation location = new fLocation(jslocation);
			this.location = location;
		}
		double latitude = json.optDouble("latitude", 0);
		this.latitude = latitude;
		double longitude = json.optDouble("longitude", 0);
		this.longitude = longitude;
	}
}
