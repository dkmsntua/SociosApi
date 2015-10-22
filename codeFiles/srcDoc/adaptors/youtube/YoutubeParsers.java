package adaptors.youtube;

import helper.misc.SociosConstants;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.ActivityObjectType;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;
import objects.main.Activity;
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
import adaptors.youtube.ytObjects.ytComment;
import adaptors.youtube.ytObjects.ytException;
import adaptors.youtube.ytObjects.ytVideo;

public class YoutubeParsers {
	public static Person parseChannel(JSONObject json) {
		Person result = new Person();
		ytChannel channel = new ytChannel(json);
		String aboutMe = channel.getDescription();
		String title = channel.getTitle();
		String id = channel.getChannelId();
		String thumbnail = channel.getThumbnail();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		result.setAboutMe(aboutMe);
		if (Utilities.isValid(title)) {
			Name name = new Name();
			name.setFullName(title);
			result.setName(name);
		}
		result.setInDegree(channel.getSubscriberCount());
		result.setThumbnailUrl(thumbnail);
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json) {
		MediaItem result = new MediaItem();
		ytVideo video = new ytVideo(json);
		String videoId = video.getId();
		String durationStr = video.getDuration();
		String licenseName = video.getLicense();
		long fileSize = video.getFilesize();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(videoId);
		Integer duration = parseDuration(durationStr);
		result.setDuration(duration);
		License license = new License();
		license.setName(licenseName);
		license.setUrl("http://www.youtube.com/t/terms");
		result.setLicense(license);
		if (fileSize > 0) {
			result.setFileSize(fileSize);
		}
		String url = "http://www.youtube.com/watch?v=" + videoId;
		result.setUrl(url);
		result.setType(MediaItemType.VIDEO);
		if (video.getSnippet() != null) {
			String title = video.getSnippet().getTitle();
			String channelId = video.getSnippet().getChannelId();
			String description = video.getSnippet().getDescription();
			String published = video.getSnippet().getPublishedAt();
			List<String> tags = video.getSnippet().getTags();
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(published, SociosConstants.DATE_TIME_FORMAT);
			result.setCreated(xmlDateCreated);
			result.setTitle(title);
			result.setUserId(channelId);
			result.setDescription(description);
			if (video.getSnippet().getThumbnails() != null) {
				String thumbnail = video.getSnippet().getThumbnails().getDefaultThumb();
				result.setThumbnailUrl(thumbnail);
			}
			String tagsChain = Utilities.getChain(tags);
			result.setTags(tagsChain);
		}
		if (video.getStatistics() != null) {
			int likes = video.getStatistics().getLikeCount();
			int dislikes = video.getStatistics().getDislikeCount();
			int comments = video.getStatistics().getCommentCount();
			int views = video.getStatistics().getViewCount();
			int favorites = video.getStatistics().getFavoriteCount();
			result.setNumPositiveVotes(likes);
			result.setNumNegativeVotes(dislikes);
			result.setNumComments(comments);
			result.setNumViews(views);
			result.setNumFavorites(favorites);
		}
		return result;
	}

	public static Comment parseComment(JSONObject jscomment) {
		Comment result = new Comment();
		ytComment comment = new ytComment(jscomment);
		String id = comment.getId();
		String description = comment.getDescription();
		String created = comment.getPublishedAt();
		String userId = comment.getAuthorChannelId();
		String username = comment.getAuthorDisplayName();
		Integer numPositiveVotes = comment.getLikeCount();
		result.setId(id);
		result.setDescription(description);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlCreated);
		result.setUserId(userId);
		result.setUsername(username);
		result.setSn(SocialNetwork.YOUTUBE);
		result.setNumPositiveVotes(numPositiveVotes);
		return result;
	}

	public static Activity parseActivity(JSONObject json) {
		Activity result = new Activity();
		ytActivity activity = new ytActivity(json);
		String id = activity.getId();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		if (activity.getSnippet() != null) {
			String publishedAt = activity.getSnippet().getPublishedAt();
			String title = activity.getSnippet().getTitle();
			String channelId = activity.getSnippet().getChannelId();
			String description = activity.getSnippet().getDescription();
			String type = activity.getSnippet().getType();
			String itemVideoId = activity.getItemVideoId();
			String itemChannelId = activity.getItemChannelId();
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(publishedAt, SociosConstants.DATE_TIME_FORMAT);
			result.setCreated(xmlCreated);
			result.setTitle(title);
			result.setActorId(channelId);
			result.setDescription(description);
			result.setType(type);
			if (Utilities.isValid(itemVideoId)) {
				MediaItem item = new MediaItem();
				item.setSn(SocialNetwork.YOUTUBE);
				item.setId(itemVideoId);
				result.getMediaItems().add(item);
				result.setObjectType(ActivityObjectType.MEDIAITEM);
			}
			else if (Utilities.isValid(itemChannelId)) {
				Person person = new Person();
				person.setSn(SocialNetwork.YOUTUBE);
				person.setId(channelId);
				result.getPersons().add(person);
				result.setObjectType(ActivityObjectType.PERSON);
			}
		}
		return result;
	}

	private static Integer parseDuration(String time) {
		if (!Utilities.isValid(time)) {
			return null;
		}
		int numHours = 0;
		int numMinutes = 0;
		int numSeconds = 0;
		int prod = 0;
		String myTime = time;
		myTime = myTime.toUpperCase();
		if (myTime.isEmpty() || !myTime.startsWith("PT")) {
			return 0;
		}
		myTime = myTime.substring(2);
		if (myTime.contains("H")) {
			int hpos = myTime.indexOf('H');
			String hours = myTime.substring(0, hpos);
			numHours = ParseUtilities.parseInt(hours);
			myTime = myTime.substring(hpos + 1, myTime.length());
		}
		if (myTime.contains("M")) {
			int mpos = myTime.indexOf('M');
			String minutes = myTime.substring(0, mpos);
			numMinutes = ParseUtilities.parseInt(minutes);
			myTime = myTime.substring(mpos + 1, myTime.length());
		}
		if (myTime.contains("S")) {
			int spos = myTime.indexOf('S');
			String seconds = myTime.substring(0, spos);
			numSeconds = ParseUtilities.parseInt(seconds);
			myTime = myTime.substring(spos + 1, myTime.length());
		}
		prod = 3600 * numHours + 60 * numMinutes + numSeconds;
		return prod;
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.YOUTUBE);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			try {
				json = XML.toJSONObject(data);
				ytException ytExc = new ytException(json);
				result.setMessage(ytExc.getInternalReason());
				result.setDescription(ytExc.getCode());
				return result;
			}
			catch (JSONException exception) {
				result.setDescription(exception.getMessage());
			}
			result.setMessage(exc.getMessage());
			result.setFaultCode(SociosConstants.ERROR_500);
			return result;
		}
		gpException gpExc = new gpException(json);
		result.setFaultCode(gpExc.getCode());
		result.setMessage(gpExc.getMessage());
		result.setDescription(gpExc.getReason());
		return result;
	}
}
