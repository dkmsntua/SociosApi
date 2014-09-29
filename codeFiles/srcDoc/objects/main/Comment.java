package objects.main;

import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.SocialNetwork;

public class Comment
{
	protected String id;
	protected SocialNetwork sn;
	protected XMLGregorianCalendar created;
	protected String description;
	protected String userId;
	protected String username;
	protected Integer numPositiveVotes;

	public String getId()
	{
		return id;
	}

	public void setId(String value)
	{
		this.id = value;
	}

	public SocialNetwork getSn()
	{
		return sn;
	}

	public void setSn(SocialNetwork value)
	{
		this.sn = value;
	}

	public XMLGregorianCalendar getCreated()
	{
		return created;
	}

	public void setCreated(XMLGregorianCalendar value)
	{
		this.created = value;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String value)
	{
		this.description = value;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String value)
	{
		this.userId = value;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String value)
	{
		this.username = value;
	}

	public Integer getNumPositiveVotes()
	{
		return numPositiveVotes;
	}

	public void setNumPositiveVotes(Integer value)
	{
		this.numPositiveVotes = value;
	}
}
