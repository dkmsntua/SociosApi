package adaptors.twitter;

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

public class TwitterParsers
{
	public static Person parsePerson(JSONObject json)
	{
		Person result = new Person();
		ttUser user = new ttUser(json);
		String created = user.created_at;
		String description = user.description;
		int followersCount = user.followers_count;
		int friendsCount = user.friends_count;
		String id = user.id_str;
		String location = user.location;
		String name = user.name;
		String screenName = user.screen_name;
		String profileImageUrl = user.profile_image_url;
		String url = "";
		if (Utilities.isValid(screenName))
		{
			url = "https://twitter.com/" + screenName;
		}
		else
		{
			url = user.url;
		}
		int utcOffset = user.utc_offset;
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, "EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		result.setMemberSince(xmlDateCreated);
		result.setAboutMe(description);
		if (followersCount != -1)
		{
			result.setInDegree(followersCount);
		}
		if (friendsCount != -1)
		{
			result.setOutDegree(friendsCount);
		}
		result.setSn(SocialNetwork.TWITTER);
		result.setId(id);
		if (Utilities.isValid(location))
		{
			Address address = new Address();
			address.setExtendedAddress(location);
			result.setCurrentLocation(address);
		}
		result.setUsername(screenName);
		if (Utilities.isValid(name))
		{
			Name personName = new Name();
			personName.setFullName(name);
			result.setName(personName);
		}
		result.setThumbnailUrl(profileImageUrl);
		result.setProfileUrl(url);
		if (utcOffset != -100)
		{
			result.setUtcOffset(utcOffset);
		}
		return result;
	}

	public static MediaItem parseMediaItem(JSONObject json)
	{
		ttTweet tweet = new ttTweet(json);
		MediaItem result = new MediaItem();
		String tweetId = tweet.id_str;
		String tweetText = tweet.text;
		String created = tweet.created_at;
		String lang = tweet.lang;
		int retweets = tweet.retweet_count;
		int favoriteCount = tweet.favorite_count;
		result.setSn(SocialNetwork.TWITTER);
		result.setId(tweetId);
		result.setDescription(tweetText);
		XMLGregorianCalendar xmlDateCreated = ParseUtilities.getCalendar(created, "EEE MMM dd HH:mm:ss ZZZZZ yyyy");
		result.setCreated(xmlDateCreated);
		result.setLanguage(lang);
		result.setNumResharings(retweets);
		result.setNumFavorites(favoriteCount);
		String name = "";
		if (tweet.user != null)
		{
			name = tweet.user.screen_name;
			String tweetUserId = tweet.user.id_str;
			result.setUserId(tweetUserId);
		}
		List<String> tags = new ArrayList<String>();
		if (Utilities.isValid(name))
		{
			tags.add(name);
		}
		if (tweet.entities != null)
		{
			List<String> hashTags = tweet.entities.hashtags;
			List<String> userMentions = tweet.entities.user_mentions_ids;
			List<ttMedia> media = tweet.entities.media;
			if (Utilities.isValid(hashTags))
			{
				tags.addAll(hashTags);
			}
			String tagChain = Utilities.getChain(tags);
			result.setTags(tagChain);
			if (Utilities.isValid(userMentions))
			{
				String mentionedPeopleChain = Utilities.getChain(userMentions);
				result.setTaggedPeople(mentionedPeopleChain);
			}
			if (media != null && !media.isEmpty())
			{
				ttMedia firstMediaItem = media.get(0);
				if (firstMediaItem.type != null)
				{
					if ("PHOTO".equals(firstMediaItem.type.toUpperCase()))
					{
						result.setType(MediaItemType.IMAGE);
						result.setThumbnailUrl(firstMediaItem.media_url);
					}
				}
			}
		}
		if (result.getType() == null)
		{
			result.setType(MediaItemType.TEXT);
		}
		if (tweet.places != null)
		{
			Address address = new Address();
			String country = tweet.places.country;
			String region = tweet.places.region;
			String postalCode = tweet.places.postal_code;
			String streetAddress = tweet.places.street_address;
			address.setCountry(country);
			address.setRegion(region);
			address.setPostalCode(postalCode);
			address.setStreetAddress(streetAddress);
			result.setLocation(address);
		}
		return result;
	}

	public static SociosException parseNativeException(String data)
	{
		SociosException result = new SociosException();
		result.setSocialNetwork(SocialNetwork.TWITTER);
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
		ttException ttExc = new ttException(json);
		result.setFaultCode(ttExc.code);
		result.setMessage(ttExc.message);
		return result;
	}
}
