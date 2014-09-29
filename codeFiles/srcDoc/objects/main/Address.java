package objects.main;

public class Address
{
	protected String country;
	protected String extendedAddress;
	protected Double latitude;
	protected Double longitude;
	protected String postalCode;
	protected String region;
	protected String streetAddress;

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String value)
	{
		this.country = value;
	}

	public String getExtendedAddress()
	{
		return extendedAddress;
	}

	public void setExtendedAddress(String value)
	{
		this.extendedAddress = value;
	}

	public Double getLatitude()
	{
		return latitude;
	}

	public void setLatitude(Double value)
	{
		this.latitude = value;
	}

	public Double getLongitude()
	{
		return longitude;
	}

	public void setLongitude(Double value)
	{
		this.longitude = value;
	}

	public String getPostalCode()
	{
		return postalCode;
	}

	public void setPostalCode(String value)
	{
		this.postalCode = value;
	}

	public String getRegion()
	{
		return region;
	}

	public void setRegion(String value)
	{
		this.region = value;
	}

	public String getStreetAddress()
	{
		return streetAddress;
	}

	public void setStreetAddress(String value)
	{
		this.streetAddress = value;
	}
}
