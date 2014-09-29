package objects.enums;


public enum SocialNetwork
{
	FLICKR, FACEBOOK, TWITTER, YOUTUBE, DAILYMOTION, GOOGLEP, INSTAGRAM;
	public String value()
	{
		return name();
	}

	public static SocialNetwork fromValue(String v)
	{
		return valueOf(v);
	}
}
