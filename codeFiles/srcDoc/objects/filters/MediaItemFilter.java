package objects.filters;

import java.util.ArrayList;
import java.util.List;
import objects.enums.LicenseType;
import objects.enums.SocialNetwork;

public class MediaItemFilter
{
	protected DateTimeFilter created;
	protected List<String> keywords;
	protected LocationFilter location;
	protected String language;
	protected LicenseType licenseType;
	protected List<SocialNetwork> sns;

	public DateTimeFilter getCreated()
	{
		return created;
	}

	public void setCreated(DateTimeFilter value)
	{
		this.created = value;
	}

	public List<String> getKeywords()
	{
		if (keywords == null)
		{
			keywords = new ArrayList<String>();
		}
		return this.keywords;
	}

	public void setKeywords(List<String> value)
	{
		this.keywords = value;
	}

	public LocationFilter getLocation()
	{
		return location;
	}

	public void setLocation(LocationFilter value)
	{
		this.location = value;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String value)
	{
		this.language = value;
	}

	public LicenseType getLicenseType()
	{
		return licenseType;
	}

	public void setLicenseType(LicenseType value)
	{
		this.licenseType = value;
	}

	public List<SocialNetwork> getSns()
	{
		if (sns == null)
		{
			sns = new ArrayList<SocialNetwork>();
		}
		return this.sns;
	}

	public void setSns(List<SocialNetwork> value)
	{
		this.sns = value;
	}
}
