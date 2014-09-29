package objects.main;

import objects.enums.SocialNetwork;

public class SociosException
{
	protected String description;
	protected String message;
	protected Integer faultCode;
	protected String actorId;
	protected SocialNetwork socialNetwork;

	public SociosException()
	{
	}

	public SociosException(String description, String message, int faultCode, String actorId, SocialNetwork sn)
	{
		this.description = description;
		this.message = message;
		this.faultCode = faultCode;
		this.actorId = actorId;
		this.socialNetwork = sn;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String value)
	{
		this.description = value;
	}

	public String getMessage()
	{
		return message;
	}

	public void setMessage(String value)
	{
		this.message = value;
	}

	public Integer getFaultCode()
	{
		return faultCode;
	}

	public void setFaultCode(Integer value)
	{
		this.faultCode = value;
	}

	public String getActorId()
	{
		return actorId;
	}

	public void setActorId(String value)
	{
		this.actorId = value;
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
