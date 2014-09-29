package objects.enums;

public enum MediaItemType
{
	IMAGE, VIDEO, TEXT;
	public String value()
	{
		return name();
	}

	public static MediaItemType fromValue(String v)
	{
		return valueOf(v);
	}
}
