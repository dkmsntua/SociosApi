package objects.filters;

public class LocationFilter
{
	protected AddressFilter addressFilter;
	protected AreaFilter areaFilter;

	public AddressFilter getAddressFilter()
	{
		return addressFilter;
	}

	public void setAddressFilter(AddressFilter value)
	{
		this.addressFilter = value;
	}

	public AreaFilter getAreaFilter()
	{
		return areaFilter;
	}

	public void setAreaFilter(AreaFilter value)
	{
		this.areaFilter = value;
	}
}
