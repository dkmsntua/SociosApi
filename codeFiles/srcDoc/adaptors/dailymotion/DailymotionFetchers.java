package adaptors.dailymotion;

import helper.misc.SociosConstants;
import helper.utilities.ExceptionsUtilities;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DailymotionFetchers {
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;

	public static PersonsContainer fetchPerson(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			Person person = DailymotionParsers.parsePerson(json);
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
			JSONArray array = json.optJSONArray("list");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsperson = array.optJSONObject(index);
				Person person = DailymotionParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItem(String response) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			MediaItem mediaItem = DailymotionParsers.parseMediaItem(json);
			result.getMediaItems().add(mediaItem);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, null, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("list");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = DailymotionParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivity(String response, String id) {
		ActivitiesContainer result = new ActivitiesContainer();
		try {
			JSONObject json = new JSONObject(response);
			Activity activity = DailymotionParsers.parseActivity(json);
			result.getActivities().add(activity);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivities(String response, String id) {
		ActivitiesContainer result = new ActivitiesContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("list");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsactivity = array.optJSONObject(index);
				Activity activity = DailymotionParsers.parseActivity(jsactivity);
				result.getActivities().add(activity);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer fetchComment(String response, String id) {
		CommentsContainer result = new CommentsContainer();
		try {
			JSONObject json = new JSONObject(response);
			Comment comment = DailymotionParsers.parseComment(json);
			result.getComments().add(comment);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id) {
		CommentsContainer result = new CommentsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("list");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jscomment = array.optJSONObject(index);
				Comment comment = DailymotionParsers.parseComment(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}
}
