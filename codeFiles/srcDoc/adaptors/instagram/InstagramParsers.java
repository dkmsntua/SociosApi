package adaptors.instagram;

import helper.misc.SociosConstants;
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

public class InstagramParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		igUser user = new igUser(json);
		String id = user.getId();
		String username = user.getUsername();
		String fullname = user.getFull_name();
		String profilePicture = user.getProfile_picture();
		String bio = user.getBio();
		//		String website = user.website;
		//		int numMedia = user.numMedia;
		int numFollows = user.getNumFollows();
		int numFollowedBy = user.getNumFollowedBy();
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		result.setUsername(username);
		if (Utilities.isValid(fullname)) {
			Name name = new Name();
			name.setFullName(fullname);
			result.setName(name);
		}
		result.setThumbnailUrl(profilePicture);
		result.setAboutMe(bio);
		if (numFollows >= 0) {
			result.setOutDegree(numFollows);
		}
		if (numFollowedBy >= 0) {
			result.setInDegree(numFollowedBy);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json) {
		System.out.println(json);
		MediaItem result = new MediaItem();
		igMediaItem media = new igMediaItem(json);
		String id = media.getId();
		long createdTime = media.getCreated_time();
		String title = media.getCaption();
		String url = media.getLink();
		int numPositiveVotes = media.getNumLikes();
		String description = media.getFilter();
		String type = media.getType();
		igMediaUrls images = media.getImages();
		igUser user = media.getUser();
		igLocation location = media.getLocation();
		List<String> tags = media.getTags();
		List<igUser> taggedPeople = media.getUsersInPhoto();
		List<igComment> comments = media.getComments();
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		if (createdTime != -1) {
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime * SociosConstants.ONE_THOUSAND);
			result.setCreated(xmlDateCreated);
		}
		if (numPositiveVotes >= 0) {
			result.setNumPositiveVotes(numPositiveVotes);
		}
		int numComments = media.getNumComments();
		if (numComments >= 0) {
			result.setNumComments(numComments);
		}
		result.setTitle(title);
		result.setUrl(url);
		result.setDescription(description);
		if ("image".equals(type)) {
			result.setType(MediaItemType.IMAGE);
		}
		else if ("video".equals(type)) {
			result.setType(MediaItemType.VIDEO);
		}
		if (images != null) {
			result.setThumbnailUrl(images.getThumbnail());
		}
		if (user != null) {
			String userId = user.getId();
			result.setUserId(userId);
		}
		if (location != null) {
			String locationName = location.getName();
			double latitude = location.getLatitude();
			double longitude = location.getLongitude();
			Address address = new Address();
			address.setExtendedAddress(locationName);
			address.setLatitude(latitude);
			address.setLongitude(longitude);
			result.setLocation(address);
		}
		if (Utilities.isValid(tags)) {
			String tagChain = Utilities.getChain(tags);
			result.setTags(tagChain);
		}
		if (taggedPeople != null) {
			String peopleChain = "";
			for (igUser taggedUser : taggedPeople) {
				if (taggedUser != null) {
					peopleChain += taggedUser.getId();
				}
			}
			result.setTaggedPeople(peopleChain);
		}
		if (comments != null) {
			for (igComment igcomment : comments) {
				Comment comment = getComment(igcomment);
				result.getComments().add(comment);
			}
		}
		return result;
	}

	private static Comment getComment(igComment comment) {
		Comment result = new Comment();
		String id = comment.getId();
		String description = comment.getText();
		long created = comment.getCreated_time();
		result.setSn(SocialNetwork.INSTAGRAM);
		result.setId(id);
		result.setDescription(description);
		if (created != -1) {
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created * SociosConstants.ONE_THOUSAND);
			result.setCreated(xmlDateCreated);
		}
		if (comment.getFrom() != null) {
			String userId = comment.getFrom().getId();
			String username = comment.getFrom().getUsername();
			result.setUserId(userId);
			result.setUsername(username);
		}
		return result;
	}

	public static Comment parseComments(JSONObject jscomment) {
		igComment igcomment = new igComment(jscomment);
		return getComment(igcomment);
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.INSTAGRAM);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		igException igExc = new igException(json);
		result.setFaultCode(igExc.getCode());
		result.setMessage(igExc.getError_message());
		result.setDescription(igExc.getError_type());
		return result;
	}
}
