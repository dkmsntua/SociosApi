package objects.filters;

import java.util.ArrayList;
import java.util.List;
import objects.enums.LicenseType;
import objects.enums.SocialNetwork;

public class MediaItemFilter {
	private DateTimeFilter created;
	private List<String> keywords;
	private LocationFilter location;
	private String language;
	private LicenseType licenseType;
	private List<SocialNetwork> sns;

	public MediaItemFilter() {
	}

	public DateTimeFilter getCreated() {
		return created;
	}

	public void setCreated(DateTimeFilter value) {
		this.created = value;
	}

	public List<String> getKeywords() {
		if (keywords == null) {
			keywords = new ArrayList<String>();
		}
		return this.keywords;
	}

	public void setKeywords(List<String> value) {
		this.keywords = value;
	}

	public LocationFilter getLocation() {
		return location;
	}

	public void setLocation(LocationFilter value) {
		this.location = value;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String value) {
		this.language = value;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType value) {
		this.licenseType = value;
	}

	public List<SocialNetwork> getSns() {
		if (sns == null) {
			sns = new ArrayList<SocialNetwork>();
		}
		return this.sns;
	}

	public void setSns(List<SocialNetwork> value) {
		this.sns = value;
	}
}
