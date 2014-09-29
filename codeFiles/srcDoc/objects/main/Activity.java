package objects.main;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.ActivityObjectType;
import objects.enums.SocialNetwork;

public class Activity
{
	protected String id;
	protected SocialNetwork sn;
	protected XMLGregorianCalendar created;
	protected String title;
	protected String description;
	protected String type;
	protected Address location;
	protected String actorId;
	protected ActivityObjectType objectType;
	protected List<MediaItem> mediaItems;
	protected List<Person> persons;
	protected List<Activity> activities;

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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String value)
	{
		this.title = value;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String value)
	{
		this.description = value;
	}

	public String getType()
	{
		return type;
	}

	public void setType(String value)
	{
		this.type = value;
	}

	public Address getLocation()
	{
		return location;
	}

	public void setLocation(Address value)
	{
		this.location = value;
	}

	public String getActorId()
	{
		return actorId;
	}

	public void setActorId(String value)
	{
		this.actorId = value;
	}

	public ActivityObjectType getObjectType()
	{
		return objectType;
	}

	public void setObjectType(ActivityObjectType value)
	{
		this.objectType = value;
	}

	public List<MediaItem> getMediaItems()
	{
		if (mediaItems == null)
		{
			mediaItems = new ArrayList<MediaItem>();
		}
		return this.mediaItems;
	}

	public void setMediaItems(List<MediaItem> value)
	{
		this.mediaItems = value;
	}

	public List<Person> getPersons()
	{
		if (persons == null)
		{
			persons = new ArrayList<Person>();
		}
		return this.persons;
	}

	public void setPersons(List<Person> value)
	{
		this.persons = value;
	}

	public List<Activity> getActivities()
	{
		if (activities == null)
		{
			activities = new ArrayList<Activity>();
		}
		return this.activities;
	}

	public void setActivities(List<Activity> value)
	{
		this.activities = value;
	}
}
