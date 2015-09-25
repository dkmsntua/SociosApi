package adaptors.flickr.fObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import org.json.JSONObject;

public class fPerson {
	private String description;
	private String iconfarm;
	private String iconserver;
	private String id;
	private String ispro;
	private String location;
	private String mobileurl;
	private String nsid;
	private int numOfPhotos;
	private String path_alias;
	private String photosurl;
	private String profileurl;
	private String realname;
	private fTimezone timezone;
	private String username;

	public fPerson() {
	}

	public fPerson(JSONObject json) {
		this.setUsername(ParseUtilities.doubleOrSimpleJsParse(json, "username"));
		this.setRealname(ParseUtilities.doubleOrSimpleJsParse(json, "realname"));
		this.setLocation(ParseUtilities.doubleOrSimpleJsParse(json, "location"));
		this.setDescription(ParseUtilities.doubleOrSimpleJsParse(json, "description"));
		this.setPhotosurl(ParseUtilities.doubleOrSimpleJsParse(json, "photosurl"));
		this.setProfileurl(ParseUtilities.doubleOrSimpleJsParse(json, "profileurl"));
		this.setMobileurl(ParseUtilities.doubleOrSimpleJsParse(json, "mobileurl"));
		String id = json.optString("id", null);
		String nsid = json.optString("nsid", null);
		if (!Utilities.isValid(id)) {
			this.setId(nsid);
		}
		else {
			this.setId(id);
		}
		this.setIspro(json.optString("ispro", null));
		this.setIconserver(json.optString("iconserver", null));
		this.setIconfarm(json.optString("iconfarm", null));
		this.setPath_alias(json.optString("path_alias", null));
		JSONObject timezone = json.optJSONObject("timezone");
		if (timezone != null) {
			this.setTimezone(new fTimezone(timezone));
		}
		JSONObject photos = json.optJSONObject("photos");
		if (photos != null) {
			this.setNumOfPhotos(ParseUtilities.doubleJsParseInt(photos, "count", "_content"));
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public fTimezone getTimezone() {
		return timezone;
	}

	public void setTimezone(fTimezone timezone) {
		this.timezone = timezone;
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getProfileurl() {
		return profileurl;
	}

	public void setProfileurl(String profileurl) {
		this.profileurl = profileurl;
	}

	public String getPhotosurl() {
		return photosurl;
	}

	public void setPhotosurl(String photosurl) {
		this.photosurl = photosurl;
	}

	public String getPath_alias() {
		return path_alias;
	}

	public void setPath_alias(String path_alias) {
		this.path_alias = path_alias;
	}

	public int getNumOfPhotos() {
		return numOfPhotos;
	}

	public void setNumOfPhotos(int numOfPhotos) {
		this.numOfPhotos = numOfPhotos;
	}

	public String getNsid() {
		return nsid;
	}

	public void setNsid(String nsid) {
		this.nsid = nsid;
	}

	public String getMobileurl() {
		return mobileurl;
	}

	public void setMobileurl(String mobileurl) {
		this.mobileurl = mobileurl;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getIspro() {
		return ispro;
	}

	public void setIspro(String ispro) {
		this.ispro = ispro;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIconserver() {
		return iconserver;
	}

	public void setIconserver(String iconserver) {
		this.iconserver = iconserver;
	}

	public String getIconfarm() {
		return iconfarm;
	}

	public void setIconfarm(String iconfarm) {
		this.iconfarm = iconfarm;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
	public class fTimezone {
		private String label;
		private String offset;

		public fTimezone() {
		}

		public fTimezone(JSONObject json) {
			this.setLabel(json.optString("label", null));
			this.setOffset(json.optString("offset", null));
		}

		public String getOffset() {
			return offset;
		}

		public void setOffset(String offset) {
			this.offset = offset;
		}

		public String getLabel() {
			return label;
		}

		public void setLabel(String label) {
			this.label = label;
		}
	}
}
