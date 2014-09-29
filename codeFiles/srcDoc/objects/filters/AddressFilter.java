package objects.filters;


public class AddressFilter
{
	protected String country;
	protected String postalCode;
	protected String region;

	public String getCountry()
	{
		return country;
	}

	public void setCountry(String value)
	{
		this.country = value;
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
}
