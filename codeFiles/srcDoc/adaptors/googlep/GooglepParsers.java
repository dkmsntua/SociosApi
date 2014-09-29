package adaptors.googlep;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
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
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.googlep.gpObjects.gpActivity;
import adaptors.googlep.gpObjects.gpActivity.GpaAttachment;
import adaptors.googlep.gpObjects.gpComment;
import adaptors.googlep.gpObjects.gpException;
import adaptors.googlep.gpObjects.gpPerson;

public class GooglepParsers
{
	public static Person parsePerson(JSONObject json)
	{
		Person result = new Person();
		gpPerson person = new gpPerson(json);
		String tagline = person.tagline;
		String aboutMe = person.aboutMe;
		String birthday = person.birthday;
		String currentLocation = person.currentLocation;
		String displayName = person.displayName;
		String gender = person.gender;
		String id = person.id;
		String url = person.url;
		String image = person.image;
		if (tagline != null && aboutMe != null)
		{
			result.setAboutMe(tagline + " \n " + aboutMe);
		}
		else if (tagline != null && aboutMe == null)
		{
			result.setAboutMe(tagline);
		}
		else if (tagline == null && aboutMe != null)
		{
			result.setAboutMe(aboutMe);
		}
		if (person.placesLived != null)
		{
			for (String pl : person.placesLived)
			{
				Address address = new Address();
				address.setExtendedAddress(pl);
				result.getAddresses().add(address);
			}
		}
		XMLGregorianCalendar birthDate = ParseUtilities.getCalendar(birthday, "yyyy-MM-dd");
		result.setBirthday(birthDate);
		if (Utilities.isValid(currentLocation))
		{
			Address currentAddress = new Address();
			currentAddress.setExtendedAddress(currentLocation);
			result.setCurrentLocation(currentAddress);
		}
		result.setUsername(displayName);
		result.setGender(gender);
		result.setSn(SocialNetwork.GOOGLEP);
		result.setId(id);
		if (person.name != null)
		{
			Name name = new Name();
			String familyName = person.name.familyName;
			String formatted = person.name.formatted;
			String givenName = person.name.givenName;
			String middleName = person.name.middleName;
			String nickname = person.nickname;
			name.setLastName(familyName);
			name.setFullName(formatted);
			name.setFirstName(givenName);
			if (!Utilities.isValid(middleName))
			{
				name.setAdditionalName(nickname);
			}
			else
			{
				name.setAdditionalName(middleName);
			}
			result.setName(name);
		}
		result.setProfileUrl(url);
		result.setThumbnailUrl(image);
		return result;
	}

	public static Activity parseActivity(JSONObject json)
	{
		Activity result = new Activity();
		gpActivity activity = new gpActivity(json);
		String id = activity.id;
		String title = activity.title;
		String annotation = activity.annotation;
		String published = activity.published;
		String type = activity.verb;
		result.setSn(SocialNetwork.GOOGLEP);
		result.setId(id);
		result.setTitle(title);
		result.setDescription(annotation);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(published, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlCreated);
		result.setType(type);
		if (activity.actor != null)
		{
			String actorId = activity.actor.id;
			result.setActorId(actorId);
		}
		if (activity.object != null)
		{
			List<GpaAttachment> attachments = activity.object.attachments;
			if (attachments != null && !attachments.isEmpty())
			{
				result.setObjectType(ActivityObjectType.MEDIAITEM);
				for (GpaAttachment attachment : attachments)
				{
					if (attachment != null)
					{
						MediaItem item = new MediaItem();
						//						if (result.getDescription() != null && "POST".equals(result.getType().toUpperCase()))
						//						{
						//							item.setUserId(result.getActorId());
						//						}
						String objectType = attachment.objectType;
						if (objectType != null)
						{
							MediaItemType mit = null;
							if ("VIDEO".equals(objectType.toUpperCase()))
							{
								mit = MediaItemType.VIDEO;
							}
							else if ("PHOTO".equals(objectType.toUpperCase()))
							{
								mit = MediaItemType.IMAGE;
							}
							else if ("ARTICLE".equals(objectType.toUpperCase()))
							{
								mit = MediaItemType.TEXT;
							}
							item.setType(mit);
						}
						String attId = attachment.id;
						String displayName = attachment.displayName;
						String content = attachment.content;
						String attUrl = attachment.url;
						item.setSn(SocialNetwork.GOOGLEP);
						item.setId(attId);
						item.setTitle(displayName);
						item.setDescription(content);
						item.setUrl(attUrl);
						item.setNumPositiveVotes(activity.object.plusoners);
						item.setNumResharings(activity.object.resharers);
						item.setNumComments(activity.object.replies);
						if (activity.object.actor != null)
						{
							item.setUserId(activity.object.actor.id);
						}
						result.getMediaItems().add(item);
					}
				}
			}
		}
		Address address = new Address();
		String extendedAddress = activity.address;
		String placeName = activity.placeName;
		address.setExtendedAddress(extendedAddress);
		address.setRegion(placeName);
		String geocode = activity.geocode;
		if (Utilities.isValid(geocode))
		{
			try
			{
				String[] attributes = geocode.split(" ");
				Double latitude = Double.parseDouble(attributes[0]);
				address.setLatitude(latitude);
				Double longitude = Double.parseDouble(attributes[1]);
				address.setLongitude(longitude);
			}
			catch (Exception exc)
			{
			}
		}
		result.setLocation(address);
		return result;
	}

	public static Comment parseComment(JSONObject json)
	{
		Comment result = new Comment();
		gpComment comment = new gpComment(json);
		String id = comment.id;
		result.setId(id);
		String desrciption = comment.content;
		result.setDescription(desrciption);
		String userId = comment.userId;
		result.setUserId(userId);
		String username = comment.username;
		result.setUsername(username);
		int numPositiveVotes = comment.plusOners;
		result.setNumPositiveVotes(numPositiveVotes);
		String created = comment.published;
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlCreated);
		result.setSn(SocialNetwork.GOOGLEP);
		return result;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.GOOGLEP);
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
		gpException gpExc = new gpException(json);
		result.setFaultCode(gpExc.code);
		result.setMessage(gpExc.message);
		result.setDescription(gpExc.reason);
		return result;
	}
}
