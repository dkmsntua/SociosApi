/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
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

public class YoutubeParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		ytPerson person = new ytPerson(json);
		String summary = person.getSummary();
		String firstName = person.getFirstName();
		String lastName = person.getLastName();
		String thumbnail = person.getThumbnail();
		String location = person.getLocation();
		String username = person.getUsername();
		int subscriberCount = person.getSubscriberCount();
		String published = person.getPublished();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setAboutMe(summary);
		Name name = new Name();
		name.setFirstName(firstName);
		name.setLastName(lastName);
		if (person.getAuthor() != null) {
			String id = person.getAuthor().getUserId();
			String authorName = person.getAuthor().getName();
			String url = person.getAuthor().getUrl();
			result.setId(id);
			if (!Utilities.isValid(name.getFullName())) {
				name.setFullName(authorName);
			}
			result.setProfileUrl(url);
		}
		result.setName(name);
		result.setThumbnailUrl(thumbnail);
		if (Utilities.isValid(location)) {
			Address adress = new Address();
			adress.setCountry(location);
			result.getAddresses().add(adress);
		}
		result.setUsername(username);
		if (subscriberCount > 0) {
			result.setInDegree(subscriberCount);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(published, SociosConstants.DATE_TIME_FORMAT);
		result.setMemberSince(xmlDateCreated);
		return result;
	}

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
		result.setThumbnailUrl(thumbnail);
		return result;
	}

	public static Person parseChannelV2(JSONObject json) {
		Person result = new Person();
		ytChannelV2 channel = new ytChannelV2(json);
		String id = channel.getChannelId();
		String thumbnail = channel.getThumbnail();
		String username = channel.getUsername();
		String name = channel.getName();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		result.setUsername(username);
		if (Utilities.isValid(name)) {
			Name fullname = new Name();
			fullname.setFullName(name);
			result.setName(fullname);
		}
		result.setThumbnailUrl(thumbnail);
		return result;
	}

	public static MediaItem parseMediaItemV2(JSONObject json) {
		MediaItem result = new MediaItem();
		ytVideoV2 video = new ytVideoV2(json);
		String title = video.getTitle();
		int comments = video.getComments();
		String description = video.getDescription();
		int duration = video.getDuration();
		String url = video.getPlayerUrl();
		String thumbnail = video.getThumbnail();
		int numFavorites = video.getFavoriteCount();
		int numViews = video.getViewCount();
		int numRatings = video.getNumRaters();
		int numNegativeVotes = video.getNumDislikes();
		int numPositiveVotes = video.getNumLikes();
		double rating = video.getAverageRating();
		String created = video.getPublished();
		String licenseName = video.getLicense();
		String id = video.getId();
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
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlDateCreated);
		License license = new License();
		license.setName(licenseName);
		license.setUrl("http://www.youtube.com/t/terms");
		result.setLicense(license);
		result.setType(MediaItemType.VIDEO);
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(id);
		if (video.getAuthor() != null) {
			String userId = video.getAuthor().getUserId();
			result.setUserId(userId);
		}
		return result;
	}

	public static MediaItem parseMediaItemV3(JSONObject json) {
		MediaItem result = new MediaItem();
		ytVideo video = new ytVideo(json);
		String videoId = video.getId();
		String durationStr = video.getDuration();
		String licenseName = video.getLicense();
		long fileSize = video.getFilesize();
		result.setSn(SocialNetwork.YOUTUBE);
		result.setId(videoId);
		int duration = parseDuration(durationStr);
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
		String description = comment.getContent();
		String created = comment.getPublished();
		String userId = comment.getUserId();
		String username = comment.getUsername();
		result.setId(id);
		result.setDescription(description);
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlCreated);
		result.setUserId(userId);
		result.setUsername(username);
		result.setSn(SocialNetwork.YOUTUBE);
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

	private static int parseDuration(String time) {
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
