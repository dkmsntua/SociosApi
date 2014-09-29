package adaptors.dailymotion;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.HashMap;
import java.util.Map;
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
import adaptors.dailymotion.dmObjects.dmActivity;
import adaptors.dailymotion.dmObjects.dmComment;
import adaptors.dailymotion.dmObjects.dmException;
import adaptors.dailymotion.dmObjects.dmUser;
import adaptors.dailymotion.dmObjects.dmVideo;

public class DailymotionParsers
{
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;

	public static Person parsePerson(JSONObject json)
	{
		dmUser user = new dmUser(json);
		String avatar60 = user.avatar60;
		String avatar720 = user.avatar720;
		String background_url = user.background_url;
		String banner_url = user.banner_url;
		long created_time = user.created_time;
		String description = user.description;
		String gender = user.gender;
		String id = user.id;
		String url = user.url;
		String username = user.username;
		String first_name = user.first_name;
		String last_name = user.last_name;
		String fullname = user.fullname;
		String email = user.email;
		String address = user.address;
		String post_code = user.post_code;
		String city = user.city;
		String country = user.country;
		long birthday = user.birthday;
		String screenname = user.sceenname;
		Person result = new Person();
		result.setSn(sn);
		result.setThumbnailUrl(avatar60);
		if (Utilities.isValid(avatar720))
		{
			result.getPhotos().add(avatar720);
		}
		if (Utilities.isValid(background_url))
		{
			result.getPhotos().add(background_url);
		}
		if (Utilities.isValid(banner_url))
		{
			result.getPhotos().add(banner_url);
		}
		if (created_time != -1)
		{
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created_time * 1000);
			result.setBirthday(xmlDateCreated);
		}
		result.setAboutMe(description);
		result.setGender(gender);
		result.setId(id);
		result.setUsername(username);
		result.setProfileUrl(url);
		if (Utilities.isValid(first_name) || Utilities.isValid(last_name) || Utilities.isValid(fullname) || Utilities.isValid(screenname))
		{
			Name name = new Name();
			name.setFirstName(first_name);
			name.setLastName(last_name);
			name.setFullName(fullname);
			if (!Utilities.isValid(fullname))
			{
				name.setFullName(screenname);
			}
			result.setName(name);
		}
		if (Utilities.isValid(email))
		{
			result.setEmail(email);
		}
		if (Utilities.isValid(address) || Utilities.isValid(post_code) || Utilities.isValid(city) || Utilities.isValid(country))
		{
			Address addr = new Address();
			addr.setCountry(country);
			addr.setExtendedAddress(address);
			addr.setPostalCode(post_code);
			addr.setRegion(city);
			result.setCurrentLocation(addr);
		}
		if (birthday != -1)
		{
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(birthday * 1000);
			result.setMemberSince(xmlDateCreated);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json)
	{
		dmVideo video = new dmVideo(json);
		String id = video.id;
		String title = video.title;
		String created = video.created_time;
		String thumbnailUrl = video.thumbnail_60_url;
		String description = video.description;
		String duration = video.duration;
		String country = video.country;
		String language = video.language;
		String rating = video.rating;
		String numRatings = video.ratings_total;
		String numComments = video.comments_total;
		String numViews = video.views_total;
		String numFavorites = video.bookmarks_total;
		String tags = video.tags;
		String strongTags = video.strongtags;
		String url = video.url;
		String userId = video.owner;
		Map<String, String> map = new HashMap<String, String>();
		map.put("id", id);
		map.put("title", title);
		map.put("created", created);
		map.put("thumbnailUrl", thumbnailUrl);
		map.put("description", description);
		map.put("duration", duration);
		map.put("country", country);
		map.put("language", language);
		map.put("rating", rating);
		map.put("numRatings", numRatings);
		map.put("numComments", numComments);
		map.put("numViews", numViews);
		map.put("numFavorites", numFavorites);
		map.put("tags", tags);
		map.put("strongTags", strongTags);
		map.put("url", url);
		map.put("userId", userId);
		MediaItem result = getMediaItem(map);
		return result;
	}

	private static MediaItem getMediaItem(Map<String, String> map)
	{
		MediaItem result = new MediaItem();
		String id = map.get("id");
		String title = map.get("title");
		String created = map.get("created");
		String thumbnailUrl = map.get("thumbnailUrl");
		String description = map.get("description");
		String duration = map.get("duration");
		String country = map.get("country");
		String language = map.get("language");
		String rating = map.get("rating");
		String numRatings = map.get("numRatings");
		String numComments = map.get("numComments");
		String numViews = map.get("numViews");
		String numFavorites = map.get("numFavorites ");
		String tags = map.get("tags");
		String strongTags = map.get("strongTags");
		String url = map.get("url");
		String userId = map.get("userId");
		result.setSn(sn);
		result.setId(id);
		result.setTitle(title);
		if (Utilities.isValid(created))
		{
			Long millis = Long.parseLong(created);
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(millis * 1000);
			result.setCreated(xmlDateCreated);
		}
		result.setThumbnailUrl(thumbnailUrl);
		result.setDescription(description);
		int dur = ParseUtilities.parseInt(duration);
		result.setDuration(dur);
		if (Utilities.isValid(country))
		{
			Address address = new Address();
			address.setCountry(country);
			result.setLocation(address);
		}
		result.setLanguage(language);
		double rat = ParseUtilities.parseDouble(rating);
		if (rat != -1)
		{
			result.setRating(rat);
		}
		int numRat = ParseUtilities.parseInt(numRatings);
		if (numRat != -1)
		{
			result.setNumRatings(numRat);
		}
		int numComm = ParseUtilities.parseInt(numComments);
		if (numComm != -1)
		{
			result.setNumComments(numComm);
		}
		int numVi = ParseUtilities.parseInt(numViews);
		if (numVi != -1)
		{
			result.setNumViews(numVi);
		}
		int numFav = ParseUtilities.parseInt(numFavorites);
		if (numFav != -1)
		{
			result.setNumFavorites(numFav);
		}
		if (Utilities.isValid(tags) && Utilities.isValid(strongTags))
		{
			result.setTags(tags + ", " + strongTags);
		}
		result.setUrl(url);
		result.setUserId(userId);
		result.setType(MediaItemType.VIDEO);
		return result;
	}

	public static Activity parseActivity(JSONObject json)
	{
		Activity result = new Activity();
		dmActivity activity = new dmActivity(json);
		String id = activity.id;
		long created = activity.created_time;
		String type = activity.type;
		String actor = activity.from_tile_owner;
		String objectType = activity.object_type;
		result.setSn(sn);
		result.setId(id);
		if (created != -1)
		{
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created * 1000);
			result.setCreated(xmlCreated);
		}
		result.setType(type);
		result.setActorId(actor);
		ActivityObjectType aot = getObjectType(objectType);
		result.setObjectType(aot);
		if (aot == ActivityObjectType.MEDIAITEM)
		{
			result.setObjectType(ActivityObjectType.MEDIAITEM);
			String videoId = activity.object_video_id;
			String videoTitle = activity.object_video_title;
			String videoCreated = activity.object_video_created_time;
			String videoThumbnail = activity.object_video_thumbnail_60_url;
			String videoDescription = activity.object_video_description;
			String videoUrl = activity.object_video_url;
			String videoLanguage = activity.object_video_language;
			String videoTags = activity.object_video_tags;
			String videoStrongTags = activity.object_video_strongtags;
			String videoOwner = activity.object_video_owner;
			String videoCountry = activity.object_video_country;
			String videoDuration = activity.object_video_duration;
			String videoRating = activity.object_video_rating;
			String videoNumRatings = activity.object_video_ratings_total;
			String videoNumComments = activity.object_video_comments_total;
			String videoNumViews = activity.object_video_views_total;
			String videoNumFavorites = activity.object_video_bookmarks_total;
			Map<String, String> map = new HashMap<String, String>();
			map.put("id", videoId);
			map.put("title", videoTitle);
			map.put("created", videoCreated);
			map.put("thumbnailUrl", videoThumbnail);
			map.put("description", videoDescription);
			map.put("duration", videoDuration);
			map.put("country", videoCountry);
			map.put("language", videoLanguage);
			map.put("rating", videoRating);
			map.put("numRatings", videoNumRatings);
			map.put("numComments", videoNumComments);
			map.put("numViews", videoNumViews);
			map.put("numFavorites", videoNumFavorites);
			map.put("tags", videoTags);
			map.put("strongTags", videoStrongTags);
			map.put("url", videoUrl);
			map.put("userId", videoOwner);
			MediaItem video = getMediaItem(map);
			result.getMediaItems().add(video);
		}
		else if (aot == ActivityObjectType.PERSON)
		{
			String userId = activity.object_tile_owner_id;
			String userUrl = activity.object_tile_url;
			String userThumbnail = activity.object_tile_icon_url;
			String userTitle = activity.object_tile_title;
			Person user = new Person();
			user.setId(userId);
			user.setProfileUrl(userUrl);
			user.setThumbnailUrl(userThumbnail);
			if (Utilities.isValid(userTitle))
			{
				Name name = new Name();
				name.setFullName(userTitle);
				user.setName(name);
			}
			result.getPersons().add(user);
		}
		return result;
	}

	public static Comment parseComment(JSONObject json)
	{
		Comment result = new Comment();
		dmComment comment = new dmComment(json);
		String id = comment.id;
		String description = comment.message;
		String userId = comment.userId;
		String username = comment.username;
		long created = comment.createdTime;
		result.setSn(sn);
		result.setId(id);
		result.setDescription(description);
		result.setUserId(userId);
		result.setUsername(username);
		if (created != -1)
		{
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created * 1000);
			result.setCreated(xmlCreated);
		}
		return result;
	}

	private static ActivityObjectType getObjectType(String objectType)
	{
		if ("video".equals(objectType))
		{
			return ActivityObjectType.MEDIAITEM;
		}
		else if ("user".equals(objectType))
		{
			return ActivityObjectType.PERSON;
		}
		else return ActivityObjectType.OTHER;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.DAILYMOTION);
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
		dmException dmExc = new dmException(json);
		result.setFaultCode(dmExc.code);
		result.setMessage(dmExc.message);
		result.setDescription(dmExc.type);
		return result;
	}
}
