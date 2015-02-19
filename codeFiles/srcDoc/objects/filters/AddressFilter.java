package objects.filters;

public class AddressFilter {
	private String country;
	private String postalCode;

	public AddressFilter() {
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String value) {
		this.postalCode = value;
	}
}
