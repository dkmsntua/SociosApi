package adaptors.youtube;

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

public class YoutubeFetchers {
	private static SocialNetwork sn = SocialNetwork.YOUTUBE;

	public static PersonsContainer fetchPersons(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jschannel = array.optJSONObject(index);
				Person person = YoutubeParsers.parseChannel(jschannel);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = YoutubeParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivities(String response, String id) {
		ActivitiesContainer result = new ActivitiesContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsactivity = array.getJSONObject(index);
				Activity activity = YoutubeParsers.parseActivity(jsactivity);
				result.getActivities().add(activity);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id) {
		CommentsContainer result = new CommentsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jscomment = array.optJSONObject(index);
				Comment comment = YoutubeParsers.parseComment(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}
}
