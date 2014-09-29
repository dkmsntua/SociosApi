package objects.main;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.SocialNetwork;

public class Person
{
	protected String id;
	protected SocialNetwork sn;
	protected String aboutMe;
	protected List<Address> addresses;
	protected XMLGregorianCalendar birthday;
	protected Address currentLocation;
	protected String username;
	protected String email;
	protected String gender;
	protected Name name;
	protected List<String> photos;
	protected String profileUrl;
	protected XMLGregorianCalendar memberSince;
	protected String thumbnailUrl;
	protected Integer utcOffset;
	protected Integer numFriends;
	protected Integer inDegree;
	protected Integer outDegree;

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

	public String getAboutMe()
	{
		return aboutMe;
	}

	public void setAboutMe(String value)
	{
		this.aboutMe = value;
	}

	public List<Address> getAddresses()
	{
		if (addresses == null)
		{
			addresses = new ArrayList<Address>();
		}
		return this.addresses;
	}

	public void setAddresses(List<Address> value)
	{
		this.addresses = value;
	}

	public XMLGregorianCalendar getBirthday()
	{
		return birthday;
	}

	public void setBirthday(XMLGregorianCalendar value)
	{
		this.birthday = value;
	}

	public Address getCurrentLocation()
	{
		return currentLocation;
	}

	public void setCurrentLocation(Address value)
	{
		this.currentLocation = value;
	}

	public String getUsername()
	{
		return username;
	}

	public void setUsername(String value)
	{
		this.username = value;
	}

	public String getEmail()
	{
		return email;
	}

	public void setEmail(String value)
	{
		this.email = value;
	}

	public String getGender()
	{
		return gender;
	}

	public void setGender(String value)
	{
		this.gender = value;
	}

	public Name getName()
	{
		return name;
	}

	public void setName(Name value)
	{
		this.name = value;
	}

	public List<String> getPhotos()
	{
		if (photos == null)
		{
			photos = new ArrayList<String>();
		}
		return this.photos;
	}

	public void setPhotos(List<String> photos)
	{
		this.photos = photos;
	}

	public String getProfileUrl()
	{
		return profileUrl;
	}

	public void setProfileUrl(String value)
	{
		this.profileUrl = value;
	}

	public XMLGregorianCalendar getMemberSince()
	{
		return memberSince;
	}

	public void setMemberSince(XMLGregorianCalendar value)
	{
		this.memberSince = value;
	}

	public String getThumbnailUrl()
	{
		return thumbnailUrl;
	}

	public void setThumbnailUrl(String value)
	{
		this.thumbnailUrl = value;
	}

	public Integer getUtcOffset()
	{
		return utcOffset;
	}

	public void setUtcOffset(Integer value)
	{
		this.utcOffset = value;
	}

	public Integer getNumFriends()
	{
		return numFriends;
	}

	public void setNumFriends(Integer value)
	{
		this.numFriends = value;
	}

	public Integer getInDegree()
	{
		return inDegree;
	}

	public void setInDegree(Integer value)
	{
		this.inDegree = value;
	}

	public Integer getOutDegree()
	{
		return outDegree;
	}

	public void setOutDegree(Integer value)
	{
		this.outDegree = value;
	}
}
