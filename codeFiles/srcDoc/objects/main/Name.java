package objects.main;

public class Name {
	private String additionalName;
	private String lastName;
	private String firstName;
	private String fullName;

	public Name() {
	}

	public String getAdditionalName() {
		return additionalName;
	}

	public void setAdditionalName(String value) {
		this.additionalName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String value) {
		this.fullName = value;
	}
}
