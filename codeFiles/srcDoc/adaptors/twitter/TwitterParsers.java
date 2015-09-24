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
package adaptors.twitter;

import helper.misc.SociosConstants;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.MediaItemType;
import objects.enums.SocialNetwork;
import objects.main.Address;
import objects.main.MediaItem;
import objects.main.Name;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONException;
import org.json.JSONObject;
import adaptors.twitter.ttObjects.ttEntities.ttMedia;
import adaptors.twitter.ttObjects.ttException;
import adaptors.twitter.ttObjects.ttTweet;
import adaptors.twitter.ttObjects.ttUser;

public class TwitterParsers {
	public static Person parsePerson(JSONObject json) {
		Person result = new Person();
		ttUser user = new ttUser(json);
		String created = user.getCreated_at();
		String description = user.getDescription();
		int followersCount = user.getFollowers_count();
		int friendsCount = user.getFriends_count();
		String id = user.getId_str();
		String location = user.getLocation();
		String name = user.getName();
		String screenName = user.getScreen_name();
		String profileImageUrl = user.getProfile_image_url();
		String url;
		if (Utilities.isValid(screenName)) {
			url = "https://twitter.com/" + screenName;
		}
		else {
			url = user.getUrl();
		}
		int utcOffset = user.getUtc_offset();
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, "EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		result.setMemberSince(xmlDateCreated);
		result.setAboutMe(description);
		if (followersCount != -1) {
			result.setInDegree(followersCount);
		}
		if (friendsCount != -1) {
			result.setOutDegree(friendsCount);
		}
		result.setSn(SocialNetwork.TWITTER);
		result.setId(id);
		if (Utilities.isValid(location)) {
			Address address = new Address();
			address.setExtendedAddress(location);
			result.setCurrentLocation(address);
		}
		result.setUsername(screenName);
		if (Utilities.isValid(name)) {
			Name personName = new Name();
			personName.setFullName(name);
			result.setName(personName);
		}
		result.setThumbnailUrl(profileImageUrl);
		result.setProfileUrl(url);
		if (utcOffset != -100) {
			result.setUtcOffset(utcOffset);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json) {
		ttTweet tweet = new ttTweet(json);
		MediaItem result = new MediaItem();
		String tweetId = tweet.getId_str();
		String tweetText = tweet.getText();
		String created = tweet.getCreated_at();
		String lang = tweet.getLang();
		int retweets = tweet.getRetweet_count();
		int favoriteCount = tweet.getFavorite_count();
		result.setSn(SocialNetwork.TWITTER);
		result.setId(tweetId);
		result.setDescription(tweetText);
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, "EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		result.setCreated(xmlDateCreated);
		result.setLanguage(lang);
		result.setNumResharings(retweets);
		result.setNumFavorites(favoriteCount);
		String name = "";
		if (tweet.getUser() != null) {
			name = tweet.getUser().getScreen_name();
			String tweetUserId = tweet.getUser().getId_str();
			result.setUserId(tweetUserId);
		}
		List<String> tags = new ArrayList<String>();
		if (Utilities.isValid(name)) {
			tags.add(name);
		}
		if (tweet.getEntities() != null) {
			List<String> hashTags = tweet.getEntities().getHashtags();
			List<String> userMentions = tweet.getEntities().getUser_mentions_ids();
			List<ttMedia> media = tweet.getEntities().getMedia();
			if (Utilities.isValid(hashTags)) {
				tags.addAll(hashTags);
			}
			String tagChain = Utilities.getChain(tags);
			result.setTags(tagChain);
			if (Utilities.isValid(userMentions)) {
				String mentionedPeopleChain = Utilities.getChain(userMentions);
				result.setTaggedPeople(mentionedPeopleChain);
			}
			if (media != null && !media.isEmpty()) {
				ttMedia firstMediaItem = media.get(0);
				if (firstMediaItem.getType() != null) {
					if ("PHOTO".equals(firstMediaItem.getType().toUpperCase())) {
						result.setType(MediaItemType.IMAGE);
						result.setThumbnailUrl(firstMediaItem.getMedia_url());
					}
				}
			}
		}
		if (result.getType() == null) {
			result.setType(MediaItemType.TEXT);
		}
		if (tweet.getPlaces() != null) {
			Address address = new Address();
			String country = tweet.getPlaces().getCountry();
			String region = tweet.getPlaces().getRegion();
			String postalCode = tweet.getPlaces().getPostal_code();
			String streetAddress = tweet.getPlaces().getStreet_address();
			address.setCountry(country);
			address.setRegion(region);
			address.setPostalCode(postalCode);
			address.setStreetAddress(streetAddress);
			result.setLocation(address);
		}
		return result;
	}

	public static SociosException parseNativeException(String data) {
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.TWITTER);
		JSONObject json;
		try {
			json = new JSONObject(data);
		}
		catch (JSONException exc) {
			result.setFaultCode(SociosConstants.ERROR_500);
			result.setMessage(exc.getMessage());
			return result;
		}
		ttException ttExc = new ttException(json);
		result.setFaultCode(ttExc.getCode());
		result.setMessage(ttExc.getMessage());
		return result;
	}
}
