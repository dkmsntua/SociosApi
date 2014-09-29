package objects.main;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;

public class MediaItem
{
	protected String id;
	protected SocialNetwork sn;
	protected String title;
	protected XMLGregorianCalendar created;
	protected String thumbnailUrl;
	protected String description;
	protected Integer duration;
	protected Address location;
	protected String language;
	protected License license;
	protected Long fileSize;
	protected Double rating;
	protected Integer numRatings;
	protected Integer numPositiveVotes;
	protected Integer numNegativeVotes;
	protected Integer numComments;
	protected Integer numViews;
	protected Integer numResharings;
	protected Integer numFavorites;
	protected String tags;
	protected String taggedPeople;
	protected MediaItemType type;
	protected String url;
	protected String userId;
	protected List<Comment> comments;

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

	public String getTitle()
	{
		return title;
	}

	public void setTitle(String value)
	{
		this.title = value;
	}

	public XMLGregorianCalendar getCreated()
	{
		return created;
	}

	public void setCreated(XMLGregorianCalendar value)
	{
		this.created = value;
	}

	public String getThumbnailUrl()
	{
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String value)
	{
		this.thumbnailUrl = value;
	}

	public String getDescription()
	{
		return description;
	}

	public void setDescription(String value)
	{
		this.description = value;
	}

	public Integer getDuration()
	{
		return duration;
	}

	public void setDuration(Integer value)
	{
		this.duration = value;
	}

	public Address getLocation()
	{
		return location;
	}

	public void setLocation(Address value)
	{
		this.location = value;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String value)
	{
		this.language = value;
	}

	public License getLicense()
	{
		return license;
	}

	public void setLicense(License value)
	{
		this.license = value;
	}

	public Long getFileSize()
	{
		return fileSize;
	}

	public void setFileSize(Long value)
	{
		this.fileSize = value;
	}

	public Double getRating()
	{
		return rating;
	}

	public void setRating(Double value)
	{
		this.rating = value;
	}

	public Integer getNumRatings()
	{
		return numRatings;
	}

	public void setNumRatings(Integer value)
	{
		this.numRatings = value;
	}

	public Integer getNumPositiveVotes()
	{
		return numPositiveVotes;
	}

	public void setNumPositiveVotes(Integer value)
	{
		this.numPositiveVotes = value;
	}

	public Integer getNumNegativeVotes()
	{
		return numNegativeVotes;
	}

	public void setNumNegativeVotes(Integer value)
	{
		this.numNegativeVotes = value;
	}

	public Integer getNumComments()
	{
		return numComments;
	}

	public void setNumComments(Integer value)
	{
		this.numComments = value;
	}

	public Integer getNumViews()
	{
		return numViews;
	}

	public void setNumViews(Integer value)
	{
		this.numViews = value;
	}

	public Integer getNumResharings()
	{
		return numResharings;
	}

	public void setNumResharings(Integer value)
	{
		this.numResharings = value;
	}

	public Integer getNumFavorites()
	{
		return numFavorites;
	}

	public void setNumFavorites(Integer value)
	{
		this.numFavorites = value;
	}

	public String getTags()
	{
		return tags;
	}

	public void setTags(String value)
	{
		this.tags = value;
	}

	public String getTaggedPeople()
	{
		return taggedPeople;
	}

	public void setTaggedPeople(String value)
	{
		this.taggedPeople = value;
	}

	public MediaItemType getType()
	{
		return type;
	}

	public void setType(MediaItemType value)
	{
		this.type = value;
	}

	public String getUrl()
	{
		return url;
	}

	public void setUrl(String value)
	{
		this.url = value;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String value)
	{
		this.userId = value;
	}

	public List<Comment> getComments()
	{
		if (comments == null)
		{
			comments = new ArrayList<Comment>();
		}
		return this.comments;
	}

	public void setComments(List<Comment> value)
	{
		this.comments = value;
	}
}
