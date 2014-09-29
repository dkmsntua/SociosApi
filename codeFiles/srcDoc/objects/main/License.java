package objects.main;

import objects.enums.LicenseType;

public class License
{
	protected LicenseType licenseType;
	protected String name;
	protected String url;

	public LicenseType getLicenseType()
	{
		return licenseType;
	}

	public void setLicenseType(LicenseType value)
	{
		this.licenseType = value;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String value)
	{
		this.name = value;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String value)
	{
		this.url = value;
	}
}
