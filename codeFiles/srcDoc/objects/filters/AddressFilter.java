package objects.filters;

public class AddressFilter
{
	protected String country;
	protected String postalCode;

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
}
