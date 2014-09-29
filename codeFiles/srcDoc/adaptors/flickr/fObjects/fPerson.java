package adaptors.flickr.fObjects;

import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;

public class fPerson
{
	public class fTimezone
	{
		public String label;
		public String offset;

		public fTimezone()
		{
		}

		public fTimezone(JSONObject json)
		{
			String label = json.optString("label", null);
			this.label = label;
			String offset = json.optString("offset", null);
			this.offset = offset;
		}
	}
	public String description;
	public String iconfarm;
	public String iconserver;
	public String id;
	public String ispro;
	public String location;
	public String mobileurl;
	public String nsid;
	public int numOfPhotos;
	public String path_alias;
	public String photosurl;
	public String profileurl;
	public String realname;
	public fTimezone timezone;
	public String username;

	public fPerson()
	{
	}

	public fPerson(JSONObject json)
	{
		String username = ParseUtilities.doubleOrSimpleJsParse(json, "username");
		this.username = username;
		String realname = ParseUtilities.doubleOrSimpleJsParse(json, "realname");
		this.realname = realname;
		String location = ParseUtilities.doubleOrSimpleJsParse(json, "location");
		this.location = location;
		String description = ParseUtilities.doubleOrSimpleJsParse(json, "description");
		this.description = description;
		String photosurl = ParseUtilities.doubleOrSimpleJsParse(json, "photosurl");
		this.photosurl = photosurl;
		String profileurl = ParseUtilities.doubleOrSimpleJsParse(json, "profileurl");
		this.profileurl = profileurl;
		String mobileurl = ParseUtilities.doubleOrSimpleJsParse(json, "mobileurl");
		this.mobileurl = mobileurl;
		String id = json.optString("id", null);
		String nsid = json.optString("nsid", null);
		if (!Utilities.isValid(id))
		{
			this.id = nsid;
		}
		else
		{
			this.id = id;
		}
		String ispro = json.optString("ispro", null);
		this.ispro = ispro;
		String iconserver = json.optString("iconserver", null);
		this.iconserver = iconserver;
		String iconfarm = json.optString("iconfarm", null);
		this.iconfarm = iconfarm;
		String path_alias = json.optString("path_alias", null);
		this.path_alias = path_alias;
		JSONObject timezone = json.optJSONObject("timezone");
		if (timezone != null)
		{
			fTimezone gptimezone = new fTimezone(timezone);
			this.timezone = gptimezone;
		}
		JSONObject photos = json.optJSONObject("photos");
		if (photos != null)
		{
			int numOfPhotos = ParseUtilities.doubleJsParseInt(photos, "count", "_content");
			this.numOfPhotos = numOfPhotos;
		}
	}
}
