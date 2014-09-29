package adaptors.twitter;

import helper.utilities.ExceptionsUtilities;
import helper.utilities.Utilities;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.MediaItem;
import objects.main.ObjectId;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class TwitterFetchers
{
	private static SocialNetwork sn = SocialNetwork.TWITTER;

	public static PersonsContainer fetchPerson(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject jsperson = new JSONObject(response);
			Person person = TwitterParsers.parsePerson(jsperson);
			result.getPersons().add(person);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchPersonForMediaItem(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONObject jsperson = json.optJSONObject("user");
			Person person = TwitterParsers.parsePerson(jsperson);
			result.getPersons().add(person);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchPersons(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONArray array = new JSONArray(response);
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsperson = array.optJSONObject(index);
				Person person = TwitterParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchFriends(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("users");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsperson = array.getJSONObject(index);
				Person person = TwitterParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItem(String response, String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			MediaItem mediaItem = TwitterParsers.parseMediaItem(json);
			result.getMediaItems().add(mediaItem);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONArray array = new JSONArray(response);
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsmediaItem = array.getJSONObject(index);
				MediaItem mediaItem = TwitterParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchStatuses(String response, String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONArray array = new JSONArray();
			if (response.startsWith("["))
			{
				array = new JSONArray(response);
			}
			else if (response.startsWith("{"))
			{
				JSONObject json = new JSONObject(response);
				array = json.optJSONArray("statuses");
			}
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = TwitterParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static ObjectIdContainer fetchMessageId(String response)
	{
		ObjectIdContainer result = new ObjectIdContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String id = json.optString("id_str", null);
			ObjectId objectId = Utilities.getObjectId(id, "TWITTER");
			result.setObjectId(objectId);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, exc, null, 500);
		}
		return result;
	}
}
