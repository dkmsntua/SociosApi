package adaptors.youtube;

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
import objects.main.License;
import objects.main.MediaItem;
import objects.main.Name;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.XML;
import adaptors.googlep.gpObjects.gpException;
import adaptors.youtube.ytObjects.ytActivity;
import adaptors.youtube.ytObjects.ytChannel;
import adaptors.youtube.ytObjects.ytChannelV2;
import adaptors.youtube.ytObjects.ytComment;
import adaptors.youtube.ytObjects.ytException;
import adaptors.youtube.ytObjects.ytPerson;
import adaptors.youtube.ytObjects.ytVideo;
import adaptors.youtube.ytObjects.ytVideoV2;

public class YoutubeParsers
{
	public static Person parsePerson(JSONObject json)
	{
		Person result = new Person();
		ytPerson person = new ytPerson(json);
		String summary = person.summary;
		String firstName = person.firstName;
		String lastName = person.lastName;
		String thumbnail = person.thumbnail;
		String location = person.location;
		String username = person.username;
		int subscriberCount = person.subscriberCount;
		String published = person.published;
		result.setSn(SocialNetwork.YOUTUBE);
		result.setAboutMe(summary);
		Name name = new Name();
		name.setFirstName(firstName);
		name.setLastName(lastName);
		if (person.author != null)
		{
			String id = person.author.userId;
			String authorName = person.author.name;
			String url = person.author.url;
			result.setId(id);
			if (!Utilities.isValid(name.getFullName()))
			{
				name.setFullName(authorName);
			}
			result.setProfileUrl(url);
		}
		result.setName(name);
		result.setThumbnailUrl(thumbnail);
		if (Utilities.isValid(location))
		{
			Address adress = new Address();
			adress.setCountry(location);
			result.getAddresses().add(adress);
		}
		result.setUsername(username);
		if (subscriberCount > 0)
		{
			result.setInDegree(subscriberCount);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(published, "yyyy-MM-dd'T'HH:mm:ss");
		result.setMemberSince(xmlDateCreated);
		return result;
	}

	public static Person parseChannel(JSONObject json)
	{
		Person result = new Person();
		ytChannel channel = new ytChannel(json);
		String aboutMe = channel.description;
		String title = channel.title;
		String id = channel.channelId;
		String thumbnail = channel.thumbnail;
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		result.setAboutMe(aboutMe);
		if (Utilities.isValid(title))
		{
			Name name = new Name();
			name.setFullName(title);
			result.setName(name);
		}
		result.setThumbnailUrl(thumbnail);
		return result;
	}

	public static Person parseChannelV2(JSONObject json)
	{
		Person result = new Person();
		ytChannelV2 channel = new ytChannelV2(json);
		String id = channel.channelId;
		String thumbnail = channel.thumbnail;
		String username = channel.username;
		String name = channel.name;
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		result.setUsername(username);
		if (Utilities.isValid(name))
		{
			Name fullname = new Name();
			fullname.setFullName(name);
			result.setName(fullname);
		}
		result.setThumbnailUrl(thumbnail);
		return result;
	}

	public static MediaItem parseMediaItemV2(JSONObject json)
	{
		MediaItem result = new MediaItem();
		ytVideoV2 video = new ytVideoV2(json);
		String title = video.title;
		int comments = video.comments;
		String description = video.description;
		int duration = video.duration;
		String url = video.playerUrl;
		String thumbnail = video.thumbnail;
		int numFavorites = video.favoriteCount;
		int numViews = video.viewCount;
		int numRatings = video.numRaters;
		int numNegativeVotes = video.numDislikes;
		int numPositiveVotes = video.numLikes;
		double rating = video.averageRating;
		String created = video.published;
		String licenseName = video.license;
		String id = video.id;
		result.setTitle(title);
		result.setNumComments(comments);
		result.setDescription(description);
		result.setDuration(duration);
		result.setUrl(url);
		result.setThumbnailUrl(thumbnail);
		result.setNumFavorites(numFavorites);
		result.setNumViews(numViews);
		result.setNumRatings(numRatings);
		result.setNumNegativeVotes(numNegativeVotes);
		result.setNumPositiveVotes(numPositiveVotes);
		result.setRating(rating);
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlDateCreated);
		License license = new License();
		license.setName(licenseName);
		license.setUrl("http://www.youtube.com/t/terms");
		result.setLicense(license);
		result.setType(MediaItemType.VIDEO);
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		if (video.author != null)
		{
			String userId = video.author.userId;
			result.setUserId(userId);
		}
		return result;
	}

	public static MediaItem parseMediaItemV3(JSONObject json)
	{
		MediaItem result = new MediaItem();
		ytVideo video = new ytVideo(json);
		String videoId = video.id;
		String durationStr = video.duration;
		String licenseName = video.license;
		long fileSize = video.filesize;
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(videoId);
		int duration = parseDuration(durationStr);
		result.setDuration(duration);
		License license = new License();
		license.setName(licenseName);
		license.setUrl("http://www.youtube.com/t/terms");
		result.setLicense(license);
		if (fileSize > 0)
		{
			result.setFileSize(fileSize);
		}
		String url = "http://www.youtube.com/watch?v=" + videoId;
		result.setUrl(url);
		result.setType(MediaItemType.VIDEO);
		if (video.snippet != null)
		{
			String title = video.snippet.title;
			String channelId = video.snippet.channelId;
			String description = video.snippet.description;
			String published = video.snippet.publishedAt;
			List<String> tags = video.snippet.tags;
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(published, "yyyy-MM-dd'T'HH:mm:ss");
			result.setCreated(xmlDateCreated);
			result.setTitle(title);
			result.setUserId(channelId);
			result.setDescription(description);
			if (video.snippet.thumbnails != null)
			{
				String thumbnail = video.snippet.thumbnails.defaultThumb;
				result.setThumbnailUrl(thumbnail);
			}
			String tagsChain = Utilities.getChain(tags);
			result.setTags(tagsChain);
		}
		if (video.statistics != null)
		{
			int likes = video.statistics.likeCount;
			int dislikes = video.statistics.dislikeCount;
			int comments = video.statistics.commentCount;
			int views = video.statistics.viewCount;
			int favorites = video.statistics.favoriteCount;
			result.setNumPositiveVotes(likes);
			result.setNumNegativeVotes(dislikes);
			result.setNumComments(comments);
			result.setNumViews(views);
			result.setNumFavorites(favorites);
		}
		return result;
	}

	public static Comment parseComment(JSONObject jscomment)
	{
		Comment result = new Comment();
		ytComment comment = new ytComment(jscomment);
		String id = comment.id;
		String description = comment.content;
		String created = comment.published;
		String userId = comment.userId;
		String username = comment.username;
		result.setId(id);
		result.setDescription(description);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, "yyyy-MM-dd'T'HH:mm:ss");
		result.setCreated(xmlCreated);
		result.setUserId(userId);
		result.setUsername(username);
		result.setSn(SocialNetwork.YOUTUBE);
		return result;
	}

	public static Activity parseActivity(JSONObject json)
	{
		Activity result = new Activity();
		ytActivity activity = new ytActivity(json);
		String id = activity.id;
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		if (activity.snippet != null)
		{
			String publishedAt = activity.snippet.publishedAt;
			String title = activity.snippet.title;
			String channelId = activity.snippet.channelId;
			String description = activity.snippet.description;
			String type = activity.snippet.type;
			String itemVideoId = activity.itemVideoId;
			String itemChannelId = activity.itemChannelId;
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(publishedAt, "yyyy-MM-dd'T'HH:mm:ss");
			result.setCreated(xmlCreated);
			result.setTitle(title);
			result.setActorId(channelId);
			result.setDescription(description);
			result.setType(type);
			if (Utilities.isValid(itemVideoId))
			{
				MediaItem item = new MediaItem();
				item.setSn(SocialNetwork.YOUTUBE);
				item.setId(itemVideoId);
				result.getMediaItems().add(item);
				result.setObjectType(ActivityObjectType.MEDIAITEM);
			}
			else if (Utilities.isValid(itemChannelId))
			{
				Person person = new Person();
				person.setSn(SocialNetwork.YOUTUBE);
				person.setId(channelId);
				result.getPersons().add(person);
				result.setObjectType(ActivityObjectType.PERSON);
			}
		}
		return result;
	}

	private static int parseDuration(String time)
	{
		int numHours = 0;
		int numMinutes = 0;
		int numSeconds = 0;
		int prod = 0;
		time = time.toUpperCase();
		if (time == null || time.isEmpty() || !time.startsWith("PT"))
		{
			return 0;
		}
		time = time.substring(2);
		if (time.contains("H"))
		{
			int hpos = time.indexOf('H');
			String hours = time.substring(0, hpos);
			numHours = ParseUtilities.parseInt(hours);
			time = time.substring(hpos + 1, time.length());
		}
		if (time.contains("M"))
		{
			int mpos = time.indexOf('M');
			String minutes = time.substring(0, mpos);
			numMinutes = ParseUtilities.parseInt(minutes);
			time = time.substring(mpos + 1, time.length());
		}
		if (time.contains("S"))
		{
			int spos = time.indexOf('S');
			String seconds = time.substring(0, spos);
			numSeconds = ParseUtilities.parseInt(seconds);
			time = time.substring(spos + 1, time.length());
		}
		prod = 3600 * numHours + 60 * numMinutes + numSeconds;
		return prod;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.YOUTUBE);
		JSONObject json = null;
		try
		{
			json = new JSONObject(data);
		}
		catch (JSONException exc)
		{
			try
			{
				json = XML.toJSONObject(data);
				ytException ytExc = new ytException(json);
				result.setMessage(ytExc.internalReason);
				result.setDescription(ytExc.code);
				return result;
			}
			catch (Exception exception)
			{
				result.setDescription(exception.getMessage());
			}
			result.setMessage(exc.getMessage());
			result.setFaultCode(500);
			return result;
		}
		gpException gpExc = new gpException(json);
		result.setFaultCode(gpExc.code);
		result.setMessage(gpExc.message);
		result.setDescription(gpExc.reason);
		return result;
	}
}
