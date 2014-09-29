package objects.main;

import objects.enums.SocialNetwork;

public class ObjectId
{
	protected String id;
	protected SocialNetwork socialNetwork;

	public String getId()
	{
		return id;
	}

	public void setId(String value)
	{
		this.id = value;
	}

	public SocialNetwork getSocialNetwork()
	{
		return socialNetwork;
	}

	public void setSocialNetwork(SocialNetwork value)
	{
		this.socialNetwork = value;
	}
}
