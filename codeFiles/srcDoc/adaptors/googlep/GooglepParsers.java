package adaptors.googlep;

import helper.misc.SociosConstants;
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

public class GooglepParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		gpPerson person = new gpPerson(json);
		String tagline = person.getTagline();
		String aboutMe = person.getAboutMe();
		String birthday = person.getBirthday();
		String currentLocation = person.getCurrentLocation();
		String displayName = person.getDisplayName();
		String gender = person.getGender();
		String id = person.getId();
		String url = person.getUrl();
		String image = person.getImage();
		if (tagline != null && aboutMe != null) {
			result.setAboutMe(tagline + " \n " + aboutMe);
		}
		else if (tagline != null) {
			result.setAboutMe(tagline);
		}
		else if (aboutMe != null) {
			result.setAboutMe(aboutMe);
		}
		if (person.getPlacesLived() != null) {
			for (String pl : person.getPlacesLived()) {
				Address address = new Address();
				address.setExtendedAddress(pl);
				result.getAddresses().add(address);
			}
		}
		XMLGregorianCalendar birthDate = ParseUtilities.getCalendar(birthday, "yyyy-MM-dd");
		result.setBirthday(birthDate);
		if (Utilities.isValid(currentLocation)) {
			Address currentAddress = new Address();
			currentAddress.setExtendedAddress(currentLocation);
			result.setCurrentLocation(currentAddress);
		}
		result.setUsername(displayName);
		result.setGender(gender);
		result.setSn(SocialNetwork.GOOGLEP);
		result.setId(id);
		if (person.getName() != null) {
			Name name = new Name();
			String familyName = person.getName().getFamilyName();
			String formatted = person.getName().getFormatted();
			String givenName = person.getName().getGivenName();
			String middleName = person.getName().getMiddleName();
			String nickname = person.getNickname();
			name.setLastName(familyName);
			name.setFullName(formatted);
			name.setFirstName(givenName);
			if (!Utilities.isValid(middleName)) {
				name.setAdditionalName(nickname);
			}
			else {
				name.setAdditionalName(middleName);
			}
			result.setName(name);
		}
		result.setProfileUrl(url);
		result.setThumbnailUrl(image);
		return result;
	}

	public static Activity parseActivity(JSONObject json) {
		Activity result = new Activity();
		gpActivity activity = new gpActivity(json);
		String id = activity.getId();
		String title = activity.getTitle();
		String annotation = activity.getAnnotation();
		String published = activity.getPublished();
		String type = activity.getVerb();
		result.setSn(SocialNetwork.GOOGLEP);
		result.setId(id);
		result.setTitle(title);
		result.setDescription(annotation);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(published, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlCreated);
		result.setType(type);
		if (activity.getActor() != null) {
			String actorId = activity.getActor().getId();
			result.setActorId(actorId);
		}
		if (activity.getObject() != null) {
			List<GpaAttachment> attachments = activity.getObject().getAttachments();
			if (attachments != null && !attachments.isEmpty()) {
				result.setObjectType(ActivityObjectType.MEDIAITEM);
				for (GpaAttachment attachment : attachments) {
					if (attachment != null) {
						MediaItem item = new MediaItem();
						//						if (result.getDescription() != null && "POST".equals(result.getType().toUpperCase()))
						//						{
						//							item.setUserId(result.getActorId());
						//						}
						String objectType = attachment.getObjectType();
						if (objectType != null) {
							MediaItemType mit = null;
							if ("VIDEO".equals(objectType.toUpperCase())) {
								mit = MediaItemType.VIDEO;
							}
							else if ("PHOTO".equals(objectType.toUpperCase())) {
								mit = MediaItemType.IMAGE;
							}
							else if ("ARTICLE".equals(objectType.toUpperCase())) {
								mit = MediaItemType.TEXT;
							}
							item.setType(mit);
						}
						String attId = attachment.getId();
						String displayName = attachment.getDisplayName();
						String content = attachment.getContent();
						String attUrl = attachment.getUrl();
						item.setSn(SocialNetwork.GOOGLEP);
						item.setId(attId);
						item.setTitle(displayName);
						item.setDescription(content);
						item.setUrl(attUrl);
						item.setNumPositiveVotes(activity.getObject().getPlusoners());
						item.setNumResharings(activity.getObject().getResharers());
						item.setNumComments(activity.getObject().getReplies());
						if (activity.getObject().getActor() != null) {
							item.setUserId(activity.getObject().getActor().getId());
						}
						result.getMediaItems().add(item);
					}
				}
			}
		}
		Address address = new Address();
		String extendedAddress = activity.getAddress();
		String placeName = activity.getPlaceName();
		address.setExtendedAddress(extendedAddress);
		address.setRegion(placeName);
		String geocode = activity.getGeocode();
		if (Utilities.isValid(geocode)) {
			String[] attributes = geocode.split(" ");
			try {
				Double latitude = Double.parseDouble(attributes[0]);
				address.setLatitude(latitude);
				Double longitude = Double.parseDouble(attributes[1]);
				address.setLongitude(longitude);
			}
			catch (NumberFormatException exc) {
				System.out.println(exc.getMessage());
			}
		}
		result.setLocation(address);
		return result;
	}

	public static Comment parseComment(JSONObject json) {
		Comment result = new Comment();
		gpComment comment = new gpComment(json);
		String id = comment.getId();
		result.setId(id);
		String desrciption = comment.getContent();
		result.setDescription(desrciption);
		String userId = comment.getUserId();
		result.setUserId(userId);
		String username = comment.getUsername();
		result.setUsername(username);
		int numPositiveVotes = comment.getPlusOners();
		result.setNumPositiveVotes(numPositiveVotes);
		String created = comment.getPublished();
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlCreated);
		result.setSn(SocialNetwork.GOOGLEP);
		return result;
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.GOOGLEP);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		gpException gpExc = new gpException(json);
		result.setFaultCode(gpExc.getCode());
		result.setMessage(gpExc.getMessage());
		result.setDescription(gpExc.getReason());
		return result;
	}
}
