package objects.main;

import objects.enums.LicenseType;

public class License {
	private LicenseType licenseType;
	private String name;
	private String url;

	public License() {
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType value) {
		this.licenseType = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String value) {
		this.name = value;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String value) {
		this.url = value;
	}
}
