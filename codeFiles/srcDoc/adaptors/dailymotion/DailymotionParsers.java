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
package adaptors.dailymotion;

import helper.misc.SociosConstants;
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

public class DailymotionParsers {
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;

	public static Person parsePerson(JSONObject json) {
		dmUser user = new dmUser(json);
		String avatar60 = user.getAvatar60();
		String avatar720 = user.getAvatar720();
		String background_url = user.getBackground_url();
		String banner_url = user.getBanner_url();
		long created_time = user.getCreated_time();
		String description = user.getDescription();
		String gender = user.getGender();
		String id = user.getId();
		String url = user.getUrl();
		String username = user.getUsername();
		String first_name = user.getFirst_name();
		String last_name = user.getLast_name();
		String fullname = user.getFullname();
		String email = user.getEmail();
		String address = user.getAddress();
		String post_code = user.getPost_code();
		String city = user.getCity();
		String country = user.getCountry();
		long birthday = user.getBirthday();
		String screenname = user.getSceenname();
		Person result = new Person();
		result.setSn(sn);
		result.setThumbnailUrl(avatar60);
		if (Utilities.isValid(avatar720)) {
			result.getPhotos().add(avatar720);
		}
		if (Utilities.isValid(background_url)) {
			result.getPhotos().add(background_url);
		}
		if (Utilities.isValid(banner_url)) {
			result.getPhotos().add(banner_url);
		}
		if (created_time != -1) {
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created_time * SociosConstants.ONE_THOUSAND);
			result.setBirthday(xmlDateCreated);
		}
		result.setAboutMe(description);
		result.setGender(gender);
		result.setId(id);
		result.setUsername(username);
		result.setProfileUrl(url);
		if (Utilities.isValid(first_name) || Utilities.isValid(last_name) || Utilities.isValid(fullname) || Utilities.isValid(screenname)) {
			Name name = new Name();
			name.setFirstName(first_name);
			name.setLastName(last_name);
			name.setFullName(fullname);
			if (!Utilities.isValid(fullname)) {
				name.setFullName(screenname);
			}
			result.setName(name);
		}
		if (Utilities.isValid(email)) {
			result.setEmail(email);
		}
		if (Utilities.isValid(address) || Utilities.isValid(post_code) || Utilities.isValid(city) || Utilities.isValid(country)) {
			Address addr = new Address();
			addr.setCountry(country);
			addr.setExtendedAddress(address);
			addr.setPostalCode(post_code);
			addr.setRegion(city);
			result.setCurrentLocation(addr);
		}
		if (birthday != -1) {
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(birthday * SociosConstants.ONE_THOUSAND);
			result.setMemberSince(xmlDateCreated);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json) {
		dmVideo video = new dmVideo(json);
		String id = video.getId();
		String title = video.getTitle();
		String created = video.getCreated_time();
		String thumbnailUrl = video.getThumbnail_60_url();
		String description = video.getDescription();
		String duration = video.getDuration();
		String country = video.getCountry();
		String language = video.getLanguage();
		String rating = video.getRating();
		String numRatings = video.getRatings_total();
		String numComments = video.getComments_total();
		String numViews = video.getViews_total();
		String numFavorites = video.getBookmarks_total();
		String tags = video.getTags();
		String strongTags = video.getStrongtags();
		String url = video.getUrl();
		String userId = video.getOwner();
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
		return getMediaItem(map);
	}

	private static MediaItem getMediaItem(Map<String, String> map) {
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
		if (Utilities.isValid(created)) {
			Long millis = Long.parseLong(created);
			XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(millis * SociosConstants.ONE_THOUSAND);
			result.setCreated(xmlDateCreated);
		}
		result.setThumbnailUrl(thumbnailUrl);
		result.setDescription(description);
		int dur = ParseUtilities.parseInt(duration);
		result.setDuration(dur);
		if (Utilities.isValid(country)) {
			Address address = new Address();
			address.setCountry(country);
			result.setLocation(address);
		}
		result.setLanguage(language);
		double rat = ParseUtilities.parseDouble(rating);
		if (rat != -1) {
			result.setRating(rat);
		}
		int numRat = ParseUtilities.parseInt(numRatings);
		if (numRat != -1) {
			result.setNumRatings(numRat);
		}
		int numComm = ParseUtilities.parseInt(numComments);
		if (numComm != -1) {
			result.setNumComments(numComm);
		}
		int numVi = ParseUtilities.parseInt(numViews);
		if (numVi != -1) {
			result.setNumViews(numVi);
		}
		int numFav = ParseUtilities.parseInt(numFavorites);
		if (numFav != -1) {
			result.setNumFavorites(numFav);
		}
		if (Utilities.isValid(tags) && Utilities.isValid(strongTags)) {
			result.setTags(tags + ", " + strongTags);
		}
		result.setUrl(url);
		result.setUserId(userId);
		result.setType(MediaItemType.VIDEO);
		return result;
	}

	public static Activity parseActivity(JSONObject json) {
		Activity result = new Activity();
		dmActivity activity = new dmActivity(json);
		String id = activity.getId();
		long created = activity.getCreated_time();
		String type = activity.getType();
		String objectType = activity.getObject_type();
		result.setSn(sn);
		result.setId(id);
		if (created != -1) {
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created * SociosConstants.ONE_THOUSAND);
			result.setCreated(xmlCreated);
		}
		result.setType(type);
		String actor = activity.getFrom_tile_owner();
		result.setActorId(actor);
		ActivityObjectType aot = getObjectType(objectType);
		result.setObjectType(aot);
		if (aot == ActivityObjectType.MEDIAITEM) {
			result.setObjectType(ActivityObjectType.MEDIAITEM);
			String videoId = activity.getObject_video_id();
			String videoTitle = activity.getObject_video_title();
			String videoCreated = activity.getObject_video_created_time();
			String videoThumbnail = activity.getObject_video_thumbnail_60_url();
			String videoDescription = activity.getObject_video_description();
			String videoUrl = activity.getObject_video_url();
			String videoLanguage = activity.getObject_video_language();
			String videoTags = activity.getObject_video_tags();
			String videoStrongTags = activity.getObject_video_strongtags();
			String videoOwner = activity.getObject_video_owner();
			String videoCountry = activity.getObject_video_country();
			String videoDuration = activity.getObject_video_duration();
			String videoRating = activity.getObject_video_rating();
			String videoNumRatings = activity.getObject_video_ratings_total();
			String videoNumComments = activity.getObject_video_comments_total();
			String videoNumViews = activity.getObject_video_views_total();
			String videoNumFavorites = activity.getObject_video_bookmarks_total();
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
		else if (aot == ActivityObjectType.PERSON) {
			String userId = activity.getObject_tile_owner_id();
			String userUrl = activity.getObject_tile_url();
			String userThumbnail = activity.getObject_tile_icon_url();
			String userTitle = activity.getObject_tile_title();
			Person user = new Person();
			user.setId(userId);
			user.setProfileUrl(userUrl);
			user.setThumbnailUrl(userThumbnail);
			if (Utilities.isValid(userTitle)) {
				Name name = new Name();
				name.setFullName(userTitle);
				user.setName(name);
			}
			result.getPersons().add(user);
		}
		return result;
	}

	public static Comment parseComment(JSONObject json) {
		Comment result = new Comment();
		dmComment comment = new dmComment(json);
		String id = comment.getId();
		String description = comment.getMessage();
		String userId = comment.getUserId();
		String username = comment.getUsername();
		long created = comment.getCreatedTime();
		result.setSn(sn);
		result.setId(id);
		result.setDescription(description);
		result.setUserId(userId);
		result.setUsername(username);
		if (created != -1) {
			XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(created * SociosConstants.ONE_THOUSAND);
			result.setCreated(xmlCreated);
		}
		return result;
	}

	private static ActivityObjectType getObjectType(String objectType) {
		if ("video".equals(objectType)) {
			return ActivityObjectType.MEDIAITEM;
		}
		else if ("user".equals(objectType)) {
			return ActivityObjectType.PERSON;
		}
		else {
			return ActivityObjectType.OTHER;
		}
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.DAILYMOTION);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		dmException dmExc = new dmException(json);
		result.setFaultCode(dmExc.getCode());
		result.setMessage(dmExc.getMessage());
		result.setDescription(dmExc.getType());
		return result;
	}
}
