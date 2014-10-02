package adaptors.facebook;

import helper.utilities.ExceptionsUtilities;
import helper.utilities.Utilities;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.ObjectId;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FacebookFetchers
{
	private static SocialNetwork sn = SocialNetwork.FACEBOOK;

	public static PersonsContainer fetchPerson(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			Person person = FacebookParsers.parsePerson(json);
			result.getPersons().add(person);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchPersons(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray jsArray = json.optJSONArray("data");
			for (int index = 0; index < jsArray.length(); index++)
			{
				JSONObject fbPerson = jsArray.optJSONObject(index);
				Person person = FacebookParsers.parsePerson(fbPerson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchPersonsTwo(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray jsNames = json.names();
			for (int index = 0; index < jsNames.length(); index++)
			{
				String name = jsNames.optString(index, null);
				JSONObject jsPerson = json.optJSONObject(name);
				Person person = FacebookParsers.parsePerson(jsPerson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItem(String response, String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			MediaItem mediaItem = FacebookParsers.parseMediaItem(json, false);
			result.getMediaItems().add(mediaItem);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id, boolean page)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("data");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = FacebookParsers.parseMediaItem(jsmediaItem, page);
				if (mediaItem != null && mediaItem.getType() != null)
				{
					result.getMediaItems().add(mediaItem);
				}
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivity(String response, String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			Activity activity = FacebookParsers.parseActivity(json);
			result.getActivities().add(activity);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivities(String response, String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("data");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsactivity = array.optJSONObject(index);
				Activity activity = FacebookParsers.parseActivity(jsactivity);
				result.getActivities().add(activity);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static CommentsContainer fetchComment(String response, String id)
	{
		CommentsContainer result = new CommentsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			Comment comment = FacebookParsers.parseComment(json);
			result.getComments().add(comment);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id)
	{
		CommentsContainer result = new CommentsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("data");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jscomment = array.optJSONObject(index);
				Comment comment = FacebookParsers.parseComment(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static ObjectIdContainer fetchMessage(String response, String userId)
	{
		ObjectIdContainer result = new ObjectIdContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String id = json.optString("id", null);
			ObjectId objectId = Utilities.getObjectId(id, "FACEBOOK");
			result.setObjectId(objectId);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, exc.getMessage() + " ==> " + response, userId, 500);
		}
		return result;
	}
}
