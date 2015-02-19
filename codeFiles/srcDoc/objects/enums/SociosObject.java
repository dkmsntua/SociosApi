package objects.enums;

public enum SociosObject {
	PERSON, MEDIAITEM, ACTIVITY, COMMENT, OBJECTID;
	public String value() {
		return name();
	}

	public static SociosObject fromValue(String v) {
		return valueOf(v);
	}
}
