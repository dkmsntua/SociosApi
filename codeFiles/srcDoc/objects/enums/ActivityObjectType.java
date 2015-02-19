package objects.enums;

public enum ActivityObjectType {
	PERSON, MEDIAITEM, ACTIVITY, OTHER;
	public String value() {
		return name();
	}

	public static ActivityObjectType fromValue(String v) {
		return valueOf(v);
	}
}
