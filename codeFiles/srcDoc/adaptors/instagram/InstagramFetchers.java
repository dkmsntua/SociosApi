package adaptors.instagram;

import helper.misc.SociosConstants;
import helper.utilities.ExceptionsUtilities;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class InstagramFetchers {
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;

	public static PersonsContainer fetchPerson(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsperson = json.optJSONObject("data");
			Person person = InstagramParsers.parsePerson(jsperson);
			result.getPersons().add(person);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static PersonsContainer fetchPersons(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray personArray = json.optJSONArray("data");
			for (int index = 0; index < personArray.length(); index++) {
				JSONObject jsperson = personArray.optJSONObject(index);
				Person person = InstagramParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static PersonsContainer fetchRelevantPersons(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsmediaItem = json.optJSONObject("data");
			JSONObject jsuser = jsmediaItem.optJSONObject("user");
			Person user = InstagramParsers.parsePerson(jsuser);
			result.getPersons().add(user);
			JSONObject jslikers = jsmediaItem.optJSONObject("likes");
			JSONArray likersArray = jslikers.optJSONArray("data");
			for (int index = 0; index < likersArray.length(); index++) {
				JSONObject jsliker = likersArray.optJSONObject(index);
				Person liker = InstagramParsers.parsePerson(jsliker);
				result.getPersons().add(liker);
			}
			JSONArray taggedArray = jsmediaItem.optJSONArray("users_in_photo");
			for (int index = 0; index < taggedArray.length(); index++) {
				JSONObject jstagged = taggedArray.optJSONObject(index);
				JSONObject jstaggedUser = jstagged.optJSONObject("user");
				Person taggedUser = InstagramParsers.parsePerson(jstaggedUser);
				result.getPersons().add(taggedUser);
			}
			JSONObject jscomments = jsmediaItem.optJSONObject("comments");
			JSONArray commentsArray = jscomments.optJSONArray("data");
			for (int index = 0; index < commentsArray.length(); index++) {
				JSONObject jscomment = commentsArray.optJSONObject(index);
				JSONObject jscommenter = jscomment.optJSONObject("from");
				Person commenter = InstagramParsers.parsePerson(jscommenter);
				result.getPersons().add(commenter);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItem(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsmediaItem = json.optJSONObject("data");
			MediaItem mediaItem = InstagramParsers.parseMediaItem(jsmediaItem);
			result.getMediaItems().add(mediaItem);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("data");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = InstagramParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id) {
		CommentsContainer result = new CommentsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray commentArray = json.optJSONArray("data");
			for (int index = 0; index < commentArray.length(); index++) {
				JSONObject jscomment = commentArray.optJSONObject(index);
				Comment comment = InstagramParsers.parseComments(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}
}
