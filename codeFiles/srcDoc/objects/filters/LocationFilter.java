package objects.filters;

public class LocationFilter {
	private AddressFilter addressFilter;
	private AreaFilter areaFilter;

	public LocationFilter() {
	}

	public AddressFilter getAddressFilter() {
		return addressFilter;
	}

	public void setAddressFilter(AddressFilter value) {
		this.addressFilter = value;
	}

	public AreaFilter getAreaFilter() {
		return areaFilter;
	}

	public void setAreaFilter(AreaFilter value) {
		this.areaFilter = value;
	}
}
