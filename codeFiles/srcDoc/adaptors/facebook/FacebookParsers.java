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
package adaptors.facebook;

import helper.misc.SociosConstants;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.facebook.fbObjects.fbActivity;
import adaptors.facebook.fbObjects.fbComment;
import adaptors.facebook.fbObjects.fbException;
import adaptors.facebook.fbObjects.fbObjectId;
import adaptors.facebook.fbObjects.fbPerson;
import adaptors.facebook.fbObjects.fbPost;
import adaptors.facebook.fbObjects.fbPost.fbPlace;
import adaptors.facebook.fbObjects.fbTag;

public class FacebookParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		fbPerson person = new fbPerson(json);
		String id = person.getId();
		String bio = person.getBio();
		String gender = person.getGender();
		String thumbnailUrl = person.getPicture();
		String profileUrl = person.getLink();
		String cover = person.getCover();
		String firstName = person.getFirst_name();
		String lastName = person.getLast_name();
		String fullName = person.getName();
		String middleName = person.getMiddle_name();
		String username = person.getUsername();
		int utcOffset = person.getTimezone();
		String location = person.getLocation();
		String hometown = person.getHometown();
		String birthday = person.getBirthday();
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		result.setAboutMe(bio);
		result.setGender(gender);
		result.setThumbnailUrl(thumbnailUrl);
		result.setProfileUrl(profileUrl);
		if (Utilities.isValid(cover)) {
			result.getPhotos().add(cover);
		}
		if (Utilities.isValid(firstName) || Utilities.isValid(lastName) || Utilities.isValid(fullName) || Utilities.isValid(middleName)) {
			Name name = new Name();
			name.setFirstName(firstName);
			name.setLastName(lastName);
			name.setFullName(fullName);
			name.setAdditionalName(middleName);
			result.setName(name);
		}
		result.setUsername(username);
		if (utcOffset != -1) {
			result.setUtcOffset(utcOffset);
		}
		if (Utilities.isValid(location)) {
			Address address = new Address();
			address.setExtendedAddress(location);
			result.setCurrentLocation(address);
		}
		if (Utilities.isValid(hometown)) {
			Address homeAddress = new Address();
			homeAddress.setExtendedAddress(hometown);
			result.getAddresses().add(homeAddress);
		}
		XMLGregorianCalendar xmlCreated = ParseUtilities.getCalendar(birthday, "dd/MM/yyyy");
		result.setBirthday(xmlCreated);
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json, boolean page) {
		MediaItem result = new MediaItem();
		fbPost post = new fbPost(json);
		String id = post.getId();
		int numResharings = post.getShares();
		fbObjectId userFbObjectId = post.getFrom();
		List<fbComment> fbComments = post.getComments();
		if (fbComments != null) {
			for (fbComment fbcomment : fbComments) {
				Comment comment = getComment(fbcomment);
				result.getComments().add(comment);
			}
		}
		String createdTime = post.getCreated_time();
		List<fbObjectId> likes = post.getLikes();
		fbPlace place = post.getPlace();
		String link = post.getLink();
		List<fbObjectId> toPeople = post.getTo();
		String thumbnailUrl = post.getPicture();
		String type = post.getType();
		String description = post.getMessage();
		String statusType = post.getStatus_type();
		String chain = "";
		if (toPeople != null && toPeople.size() > 0) {
			int toSize = toPeople.size();
			for (fbObjectId objectId : toPeople) {
				chain += objectId.getId().trim();
				toSize--;
				if (toSize != 0) {
					chain += ",";
				}
			}
		}
		List<fbObjectId> withPeople = post.getWith_tags();
		if (withPeople != null && withPeople.size() > 0) {
			int withSize = withPeople.size();
			for (fbObjectId objectId : withPeople) {
				withSize--;
				if (!chain.contains(objectId.getId().trim())) {
					chain += objectId.getId().trim();
					if (withSize != 0) {
						chain += ",";
					}
				}
			}
		}
		if (Utilities.isValid(chain)) {
			result.setTaggedPeople(chain);
		}
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		if (numResharings >= 0) {
			result.setNumResharings(numResharings);
		}
		if (userFbObjectId != null) {
			String userId = userFbObjectId.getId();
			result.setUserId(userId);
		}
		if (fbComments != null) {
			int numComments = fbComments.size();
			result.setNumComments(numComments);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlDateCreated);
		if (likes != null) {
			int numPositiveVotes = likes.size();
			result.setNumPositiveVotes(numPositiveVotes);
		}
		result.setUrl(link);
		if (place != null) {
			String country = place.getCountry();
			String city = place.getCity();
			String name = place.getName();
			String state = place.getState();
			String street = place.getStreet();
			String zip = place.getZip();
			double latitude = place.getLatitude();
			double longitude = place.getLongitude();
			Address address = new Address();
			address.setCountry(country);
			address.setPostalCode(zip);
			address.setStreetAddress(street);
			address.setRegion(state);
			address.setExtendedAddress(city + ", " + name);
			if (latitude != 0 || longitude != 0) {
				address.setLatitude(latitude);
				address.setLongitude(longitude);
			}
			result.setLocation(address);
		}
		if (Utilities.isValid(thumbnailUrl)) {
			try {
				thumbnailUrl = URLDecoder.decode(thumbnailUrl, SociosConstants.UTF8);
				result.setThumbnailUrl(thumbnailUrl);
			}
			catch (UnsupportedEncodingException exc) {
				System.out.println(exc.getMessage());
			}
		}
		if ("photo".equals(type)) {
			result.setType(MediaItemType.IMAGE);
		}
		else if ("video".equals(type) || "swf".equals(type)) {
			if (!Utilities.isValid(description)) {
				description = post.getLinkDescription();
			}
			String title = post.getLinkName();
			result.setTitle(title);
			result.setType(MediaItemType.VIDEO);
		}
		else if ("link".equals(type)) {
			if (!Utilities.isValid(description)) {
				description = post.getLinkDescription();
			}
			if (Utilities.isValid(description)) {
				String title = post.getLinkName();
				result.setTitle(title);
				result.setType(MediaItemType.TEXT);
			}
		}
		else if ("status".equals(type)) {
			description = post.getMessage();
			if (!Utilities.isValid(description)) {
				description = post.getStory();
			}
			if (statusType != null && !"approved_friend".equals(statusType)) {
				result.setType(MediaItemType.TEXT);
			}
			if (page) {
				result.setType(MediaItemType.TEXT);
			}
		}
		result.setDescription(description);
		return result;
	}

	public static Activity parseActivity(JSONObject json) {
		Activity result = new Activity();
		fbActivity activity = new fbActivity(json);
		if (activity.getStatus_type() != null && !"approved_friend".equals(activity.getStatus_type())) {
			return null;
		}
		String id = activity.getId();
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		String actorId = activity.getActorId();
		result.setActorId(actorId);
		String story = activity.getStory();
		result.setTitle(story);
		String createdTime = activity.getCreated_time();
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlDateCreated);
		String statusType = activity.getStatus_type();
		result.setType(statusType);
		List<fbTag> taggedPeople = activity.getStory_tags();
		if (taggedPeople != null && !taggedPeople.isEmpty()) {
			int counter = 0;
			for (fbTag tag : taggedPeople) {
				if (counter++ == 0) {
					continue;
				}
				Person person = new Person();
				String personName = tag.getName();
				if (Utilities.isValid(personName)) {
					Name name = new Name();
					name.setFullName(personName);
					person.setName(name);
				}
				String personId = tag.getId();
				person.setSn(SocialNetwork.FACEBOOK);
				person.setId(personId);
				result.getPersons().add(person);
			}
		}
		result.setObjectType(ActivityObjectType.PERSON);
		return result;
	}

	private static Comment getComment(fbComment comment) {
		Comment result = new Comment();
		String id = comment.getId();
		String userId = comment.getUserId();
		String username = comment.getUsername();
		String description = comment.getMessage();
		String createdTime = comment.getCreated_time();
		result.setSn(SocialNetwork.FACEBOOK);
		result.setId(id);
		result.setUserId(userId);
		result.setUsername(username);
		result.setDescription(description);
		int numPositiveVotes = comment.getLike_count();
		if (numPositiveVotes != -1) {
			result.setNumPositiveVotes(numPositiveVotes);
		}
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(createdTime, SociosConstants.DATE_TIME_FORMAT);
		result.setCreated(xmlDateCreated);
		return result;
	}

	public static Comment parseComment(JSONObject json) {
		fbComment comment = new fbComment(json);
		return getComment(comment);
	}

	public static List<fbTag> getTags(JSONObject json, String title) {
		List<fbTag> result = null;
		JSONObject tag_holder = json.optJSONObject(title);
		if (tag_holder != null) {
			result = new ArrayList<fbTag>();
			JSONArray arrayNames = tag_holder.names();
			if (Utilities.isValid(arrayNames)) {
				for (int index = 0; index < arrayNames.length(); index++) {
					String name = arrayNames.optString(index);
					if (name != null) {
						JSONArray tags = tag_holder.optJSONArray(name);
						if (Utilities.isValid(tags)) {
							for (int i = 0; i < tags.length(); i++) {
								JSONObject tag = tags.optJSONObject(i);
								if (tag != null) {
									result.add(new fbTag(tag));
								}
							}
						}
					}
				}
			}
		}
		return result;
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.FACEBOOK);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		fbException fbException = new fbException(json);
		result.setFaultCode(fbException.getCode());
		result.setMessage(fbException.getMessage());
		result.setDescription(fbException.getType());
		return result;
	}
}
