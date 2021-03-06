package adaptors.flickr;

import helper.misc.SociosConstants;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.LicenseType;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;
import objects.main.Address;
import objects.main.Comment;
import objects.main.License;
import objects.main.MediaItem;
import objects.main.Name;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.flickr.fObjects.fComment;
import adaptors.flickr.fObjects.fPerson;
import adaptors.flickr.fObjects.fPhoto;
import adaptors.flickr.fObjects.fPhoto.fLocation;
import adaptors.flickr.fObjects.flickrException;

public class FlickrParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		fPerson person = new fPerson(json);
		String description = person.getDescription();
		String username = person.getUsername();
		String formattedName = person.getRealname();
		String profileUrl = person.getProfileurl();
		String id = person.getId();
		String location = person.getLocation();
		String farm = person.getIconfarm();
		String server = person.getIconserver();
		result.setAboutMe(description);
		result.setUsername(username);
		if (Utilities.isValid(formattedName)) {
			Name name = new Name();
			name.setFullName(formattedName);
			result.setName(name);
		}
		result.setProfileUrl(profileUrl);
		result.setSn(SocialNetwork.FLICKR);
		result.setId(id);
		if (Utilities.isValid(location)) {
			Address address = new Address();
			address.setExtendedAddress(location);
			result.setCurrentLocation(address);
		}
		if (Utilities.isValid(server) && Utilities.isValid(farm) && Utilities.isValid(id)) {
			String thumbnailUrl = "http://farm" + farm + ".staticflickr.com/" + server + "/buddyicons/" + id + ".jpg";
			result.setThumbnailUrl(thumbnailUrl);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json) {
		MediaItem result = new MediaItem();
		fPhoto photo = new fPhoto(json);
		String id = photo.getId();
		String title = photo.getTitle();
		String description = photo.getDescription();
		String comments = photo.getComments();
		String views = photo.getViews();
		String media = photo.getMedia();
		String farm = photo.getFarm();
		String server = photo.getServer();
		String secret = photo.getSecret();
		String created = photo.getDatetaken();
		int licenseCode = photo.getLicense();
		fLocation location = photo.getLocation();
		result.setSn(SocialNetwork.FLICKR);
		result.setId(id);
		result.setTitle(title);
		result.setDescription(description);
		int numComments = ParseUtilities.parseInt(comments);
		if (numComments != -1) {
			result.setNumComments(numComments);
		}
		int numViews = ParseUtilities.parseInt(views);
		if (numViews != -1) {
			result.setNumViews(numViews);
		}
		fPerson owner = photo.getOwner();
		if (owner != null) {
			String ownerId = owner.getId();
			if (Utilities.isValid(ownerId)) {
				result.setUserId(ownerId);
				if (Utilities.isValid(photo.getId())) {
					String url = "http://www.flickr.com/photos/" + ownerId + "/" + photo.getId();
					result.setUrl(url);
				}
			}
		}
		if ("photo".equals(media)) {
			result.setType(MediaItemType.IMAGE);
		}
		List<String> tags = photo.getTags();
		List<String> machineTags = photo.getMachine_tags();
		if (Utilities.isValid(machineTags)) {
			tags.addAll(machineTags);
		}
		if (Utilities.clean(tags)) {
			String chain = Utilities.getChain(tags);
			result.setTags(chain);
		}
		if (Utilities.isValid(farm) && Utilities.isValid(server) && Utilities.isValid(secret) && Utilities.isValid(id)) {
			String thumbnailUrl = "http://farm" + farm + ".staticflickr.com/" + server + "/" + id + "_" + secret + "_s.jpg";
			result.setThumbnailUrl(thumbnailUrl);
		}
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, "yyyy-MM-dd hh:mm:ss");
		result.setCreated(xmlCreated);
		if (location != null) {
			String country = photo.getLocation().getCountry();
			double latitude = photo.getLocation().getLatitude();
			double longitude = photo.getLocation().getLongitude();
			Address address = new Address();
			address.setCountry(country);
			if (latitude * longitude != 0) {
				address.setLatitude(latitude);
				address.setLongitude(longitude);
			}
			result.setLocation(address);
		}
		else {
			double latitude = photo.getLatitude();
			double longitude = photo.getLongitude();
			if (latitude * longitude != 0) {
				Address address = new Address();
				address.setLatitude(latitude);
				address.setLongitude(longitude);
				result.setLocation(address);
			}
		}
		License license = getLicense(licenseCode);
		result.setLicense(license);
		return result;
	}

	public static Comment parseComment(JSONObject json) {
		Comment result = new Comment();
		fComment comment = new fComment(json);
		String id = comment.getId();
		long created = comment.getDatecreate();
		String description = comment.getContent();
		String userId = comment.getAuthor();
		String username = comment.getAuthorname();
		result.setId(id);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created);
		result.setCreated(xmlCreated);
		result.setDescription(description);
		result.setUserId(userId);
		result.setUsername(username);
		result.setSn(SocialNetwork.FLICKR);
		return result;
	}

	private static License getLicense(int code) {
		License result = new License();
		String name = "";
		String url = null;
		LicenseType type = null;
		if (code == 0) {
			name = "All Rights Reserved";
			type = LicenseType.ALL;
		}
		else if (code == 1) {
			name = "Attribution-NonCommercial-ShareAlike License";
			url = "http://creativecommons.org/licenses/by-nc-sa/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 2) {
			name = "Attribution-NonCommercial License";
			url = "http://creativecommons.org/licenses/by-nc/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 3) {
			name = "Attribution-NonCommercial-NoDerivs License";
			url = "http://creativecommons.org/licenses/by-nc-nd/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 4) {
			name = "Attribution License";
			url = "http://creativecommons.org/licenses/by/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 5) {
			name = "Attribution-ShareAlike License";
			url = "http://creativecommons.org/licenses/by-sa/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 6) {
			name = "Attribution-NoDerivs License";
			url = "http://creativecommons.org/licenses/by-nd/2.0/";
			type = LicenseType.CC;
		}
		else if (code == 7) {
			name = "No known copyright restrictions";
			url = "http://flickr.com/commons/usage/";
			type = LicenseType.ALL;
		}
		else if (code == 8) {
			name = "United States Government Work";
			url = "http://www.usa.gov/copyright.shtml";
			type = LicenseType.ALL;
		}
		result.setName(name);
		result.setUrl(url);
		result.setLicenseType(type);
		return result;
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.FLICKR);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		flickrException flickrException = new flickrException(json);
		result.setFaultCode(flickrException.getCode());
		result.setMessage(flickrException.getMessage());
		result.setDescription(flickrException.getStat());
		return result;
	}
}
