package objects.enums;

public enum LicenseType {
	ALL, CC;
	public String value() {
		return name();
	}

	public static LicenseType fromValue(String v) {
		return valueOf(v);
	}
}
