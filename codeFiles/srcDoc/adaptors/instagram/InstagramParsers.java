package adaptors.instagram;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;
import objects.main.Address;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Name;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.instagram.igObjects.igComment;
import adaptors.instagram.igObjects.igException;
import adaptors.instagram.igObjects.igMediaItem;
import adaptors.instagram.igObjects.igMediaItem.igLocation;
import adaptors.instagram.igObjects.igMediaItem.igMediaUrls;
import adaptors.instagram.igObjects.igUser;

public class InstagramParsers
{
	public static Person parsePerson(JSONObject json)
	{
		Person result = new Person();
		igUser user = new igUser(json);
		String id = user.id;
		String username = user.username;
		String fullname = user.full_name;
		String profilePicture = user.profile_picture;
		String bio = user.bio;
		//		String website = user.website;
		//		int numMedia = user.numMedia;
		int numFollows = user.numFollows;
		int numFollowedBy = user.numFollowedBy;
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		result.setUsername(username);
		if (Utilities.isValid(fullname))
		{
			Name name = new Name();
			name.setFullName(fullname);
			result.setName(name);
		}
		result.setThumbnailUrl(profilePicture);
		result.setAboutMe(bio);
		if (numFollows >= 0)
		{
			result.setOutDegree(numFollows);
		}
		if (numFollowedBy >= 0)
		{
			result.setInDegree(numFollowedBy);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json)
	{
		MediaItem result = new MediaItem();
		igMediaItem media = new igMediaItem(json);
		String id = media.id;
		long createdTime = media.created_time;
		String title = media.caption;
		String url = media.link;
		int numPositiveVotes = media.numLikes;
		String description = media.filter;
		String type = media.type;
		igMediaUrls images = media.images;
		igUser user = media.user;
		igLocation location = media.location;
		List<String> tags = media.tags;
		List<igUser> taggedPeople = media.usersInPhoto;
		List<igComment> comments = media.comments;
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		if (createdTime != -1)
		{
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime * 1000);
			result.setCreated(xmlDateCreated);
		}
		if (numPositiveVotes >= 0)
		{
			result.setNumPositiveVotes(numPositiveVotes);
		}
		int numComments = media.numComments;
		if (numComments >= 0)
		{
			result.setNumComments(numComments);
		}
		result.setTitle(title);
		result.setUrl(url);
		result.setDescription(description);
		if ("image".equals(type))
		{
			result.setType(MediaItemType.IMAGE);
		}
		else if ("video".equals(type))
		{
			result.setType(MediaItemType.VIDEO);
		}
		if (images != null)
		{
			result.setThumbnailUrl(images.thumbnail);
		}
		if (user != null)
		{
			String userId = user.id;
			result.setUserId(userId);
		}
		if (location != null)
		{
			String locationName = location.name;
			double latitude = location.latitude;
			double longitude = location.longitude;
			Address address = new Address();
			address.setExtendedAddress(locationName);
			address.setLatitude(latitude);
			address.setLongitude(longitude);
			result.setLocation(address);
		}
		if (Utilities.isValid(tags))
		{
			String tagChain = Utilities.getChain(tags);
			result.setTags(tagChain);
		}
		if (taggedPeople != null)
		{
			String peopleChain = "";
			for (igUser taggedUser : taggedPeople)
			{
				if (taggedUser != null)
				{
					peopleChain += taggedUser.id;
				}
			}
			result.setTaggedPeople(peopleChain);
		}
		if (comments != null)
		{
			for (igComment igcomment : comments)
			{
				Comment comment = getComment(igcomment);
				result.getComments().add(comment);
			}
		}
		return result;
	}

	private static Comment getComment(igComment comment)
	{
		Comment result = new Comment();
		String id = comment.id;
		String description = comment.text;
		long created = comment.created_time;
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		result.setDescription(description);
		if (created != -1)
		{
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created * 1000);
			result.setCreated(xmlDateCreated);
		}
		if (comment.from != null)
		{
			String userId = comment.from.id;
			String username = comment.from.username;
			result.setUserId(userId);
			result.setUsername(username);
		}
		return result;
	}

	public static Comment parseComments(JSONObject jscomment)
	{
		igComment igcomment = new igComment(jscomment);
		Comment result = getComment(igcomment);
		return result;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.INSTAGRAM);
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
		igException igExc = new igException(json);
		result.setFaultCode(igExc.code);
		result.setMessage(igExc.error_message);
		result.setDescription(igExc.error_type);
		return result;
	}
}
