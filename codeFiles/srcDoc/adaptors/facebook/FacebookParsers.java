package adaptors.facebook;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.ActivityObjectType;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;
import objects.main.Activity;
import objects.main.Address;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Name;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.facebook.fbObjects.fbActivity;
import adaptors.facebook.fbObjects.fbComment;
import adaptors.facebook.fbObjects.fbException;
import adaptors.facebook.fbObjects.fbObjectId;
import adaptors.facebook.fbObjects.fbPerson;
import adaptors.facebook.fbObjects.fbPost;
import adaptors.facebook.fbObjects.fbPost.fbPlace;
import adaptors.facebook.fbObjects.fbTag;

public class FacebookParsers
{
	public static Person parsePerson(JSONObject json)
	{
		Person result = new Person();
		fbPerson person = new fbPerson(json);
		String id = person.id;
		String bio = person.bio;
		String gender = person.gender;
		String thumbnailUrl = person.picture;
		String profileUrl = person.link;
		String cover = person.cover;
		String firstName = person.first_name;
		String lastName = person.last_name;
		String fullName = person.name;
		String middleName = person.middle_name;
		String username = person.username;
		int utcOffset = person.timezone;
		String location = person.location;
		String hometown = person.hometown;
		String birthday = person.birthday;
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		result.setAboutMe(bio);
		result.setGender(gender);
		result.setThumbnailUrl(thumbnailUrl);
		result.setProfileUrl(profileUrl);
		if (Utilities.isValid(cover))
		{
			result.getPhotos().add(cover);
		}
		if (Utilities.isValid(firstName) || Utilities.isValid(lastName) || Utilities.isValid(fullName) || Utilities.isValid(middleName))
		{
			Name name = new Name();
			name.setFirstName(firstName);
			name.setLastName(lastName);
			name.setFullName(fullName);
			name.setAdditionalName(middleName);
			result.setName(name);
		}
		result.setUsername(username);
		if (utcOffset != -1)
		{
			result.setUtcOffset(utcOffset);
		}
		if (Utilities.isValid(location))
		{
			Address address = new Address();
			address.setExtendedAddress(location);
			result.setCurrentLocation(address);
		}
		if (Utilities.isValid(hometown))
		{
			Address homeAddress = new Address();
			homeAddress.setExtendedAddress(hometown);
			result.getAddresses().add(homeAddress);
		}
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(birthday, "dd/MM/yyyy");
		result.setBirthday(xmlCreated);
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json, boolean page)
	{
		MediaItem result = new MediaItem();
		fbPost post = new fbPost(json);
		String id = post.id;
		int numResharings = post.shares;
		fbObjectId userFbObjectId = post.from;
		List<fbComment> fbComments = post.comments;
		if (fbComments != null)
		{
			for (fbComment fbcomment : fbComments)
			{
				Comment comment = getComment(fbcomment);
				result.getComments().add(comment);
			}
		}
		String createdTime = post.created_time;
		List<fbObjectId> likes = post.likes;
		fbPlace place = post.place;
		String link = post.link;
		List<fbObjectId> toPeople = post.to;
		String thumbnailUrl = post.picture;
		String type = post.type;
		String description = post.message;
		String statusType = post.status_type;
		String chain = "";
		if (toPeople != null && toPeople.size() > 0)
		{
			int toSize = toPeople.size();
			for (fbObjectId objectId : toPeople)
			{
				chain += objectId.id.trim();
				toSize--;
				if (toSize != 0)
				{
					chain += ",";
				}
			}
		}
		List<fbObjectId> withPeople = post.with_tags;
		if (withPeople != null && withPeople.size() > 0)
		{
			int withSize = withPeople.size();
			for (fbObjectId objectId : withPeople)
			{
				withSize--;
				if (!chain.contains(objectId.id.trim()))
				{
					chain += objectId.id.trim();
					if (withSize != 0)
					{
						chain += ",";
					}
				}
			}
		}
		if (Utilities.isValid(chain))
		{
			result.setTaggedPeople(chain);
		}
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		if (numResharings >= 0)
		{
			result.setNumResharings(numResharings);
		}
		if (userFbObjectId != null)
		{
			String userId = userFbObjectId.id;
			result.setUserId(userId);
		}
		if (fbComments != null)
		{
			int numComments = fbComments.size();
			result.setNumComments(numComments);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlDateCreated);
		if (likes != null)
		{
			int numPositiveVotes = likes.size();
			result.setNumPositiveVotes(numPositiveVotes);
		}
		result.setUrl(link);
		if (place != null)
		{
			String country = place.country;
			String city = place.city;
			String name = place.name;
			String state = place.state;
			String street = place.street;
			String zip = place.zip;
			double latitude = place.latitude;
			double longitude = place.longitude;
			Address address = new Address();
			address.setCountry(country);
			address.setPostalCode(zip);
			address.setStreetAddress(street);
			address.setRegion(state);
			address.setExtendedAddress(city + ", " + name);
			if (latitude != 0 || longitude != 0)
			{
				address.setLatitude(latitude);
				address.setLongitude(longitude);
			}
			result.setLocation(address);
		}
		if (Utilities.isValid(thumbnailUrl))
		{
			try
			{
				thumbnailUrl = URLDecoder.decode(thumbnailUrl, "UTF-8");
				result.setThumbnailUrl(thumbnailUrl);
			}
			catch (Exception e)
			{
			}
		}
		if ("photo".equals(type))
		{
			result.setType(MediaItemType.IMAGE);
		}
		else if ("video".equals(type) || "swf".equals(type))
		{
			if (!Utilities.isValid(description))
			{
				description = post.linkDescription;
			}
			String title = post.linkName;
			result.setTitle(title);
			result.setType(MediaItemType.VIDEO);
		}
		else if ("link".equals(type))
		{
			if (!Utilities.isValid(description))
			{
				description = post.linkDescription;
			}
			if (Utilities.isValid(description))
			{
				String title = post.linkName;
				result.setTitle(title);
				result.setType(MediaItemType.TEXT);
			}
		}
		else if ("status".equals(type))
		{
			description = post.message;
			if (!Utilities.isValid(description))
			{
				description = post.story;
			}
			if (statusType != null && !"approved_friend".equals(statusType))
			{
				result.setType(MediaItemType.TEXT);
			}
			if (page)
			{
				result.setType(MediaItemType.TEXT);
			}
		}
		result.setDescription(description);
		return result;
	}

	public static Activity parseActivity(JSONObject json)
	{
		Activity result = new Activity();
		fbActivity activity = new fbActivity(json);
		if (activity.status_type != null && !"approved_friend".equals(activity.status_type))
		{
			return null;
		}
		String id = activity.id;
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		String actorId = activity.actorId;
		result.setActorId(actorId);
		String story = activity.story;
		result.setTitle(story);
		String createdTime = activity.created_time;
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlDateCreated);
		String statusType = activity.status_type;
		result.setType(statusType);
		List<fbTag> taggedPeople = activity.story_tags;
		if (taggedPeople != null && !taggedPeople.isEmpty())
		{
			int counter = 0;
			for (fbTag tag : taggedPeople)
			{
				if (counter++ == 0)
				{
					continue;
				}
				Person person = new Person();
				String personName = tag.name;
				if (Utilities.isValid(personName))
				{
					Name name = new Name();
					name.setFullName(personName);
					person.setName(name);
				}
				String personId = tag.id;
				person.setSn(SocialNetwork.FACEBOOK);
				person.setId(personId);
				result.getPersons().add(person);
			}
		}
		result.setObjectType(ActivityObjectType.PERSON);
		return result;
	}

	private static Comment getComment(fbComment comment)
	{
		Comment result = new Comment();
		String id = comment.id;
		String userId = comment.userId;
		String username = comment.username;
		String description = comment.message;
		String createdTime = comment.created_time;
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		result.setUserId(userId);
		result.setUsername(username);
		result.setDescription(description);
		int numPositiveVotes = comment.like_count;
		if (numPositiveVotes != -1)
		{
			result.setNumPositiveVotes(numPositiveVotes);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlDateCreated);
		return result;
	}

	public static Comment parseComment(JSONObject json)
	{
		fbComment comment = new fbComment(json);
		Comment result = getComment(comment);
		return result;
	}

	public static List<fbTag> getTags(JSONObject json, String title)
	{
		List<fbTag> result = null;
		JSONObject tag_holder = json.optJSONObject(title);
		if (tag_holder != null)
		{
			result = new ArrayList<fbTag>();
			JSONArray arrayNames = tag_holder.names();
			if (Utilities.isValid(arrayNames))
			{
				for (int index = 0; index < arrayNames.length(); index++)
				{
					String name = arrayNames.optString(index);
					if (name != null)
					{
						JSONArray tags = tag_holder.optJSONArray(name);
						if (Utilities.isValid(tags))
						{
							for (int i = 0; i < tags.length(); i++)
							{
								JSONObject tag = tags.optJSONObject(i);
								if (tag != null)
								{
									fbTag fbtag = new fbTag(tag);
									if (fbtag != null)
									{
										result.add(fbtag);
									}
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.FACEBOOK);
		JSONObject json = null;
		try
		{
			json = new JSONObject(data);
		}
		catch (JSONException exc)
		{
			result.setFaultCode(500);
			result.setMessage(exc.getMessage());
			return result;
		}
		fbException fbException = new fbException(json);
		result.setFaultCode(fbException.code);
		result.setMessage(fbException.message);
		result.setDescription(fbException.type);
		return result;
	}
}
