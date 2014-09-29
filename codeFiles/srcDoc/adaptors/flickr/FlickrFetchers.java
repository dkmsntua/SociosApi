package adaptors.flickr;

import helper.utilities.ExceptionsUtilities;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Person;
import objects.main.SociosException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class FlickrFetchers
{
	private static SocialNetwork sn = SocialNetwork.FLICKR;

	public static PersonsContainer fetchPerson(String response, String type, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
			}
			JSONObject jsperson = json.optJSONObject(type);
			Person person = FlickrParsers.parsePerson(jsperson);
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
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
			}
			JSONObject personHolder = json.optJSONObject("photo");
			if (personHolder == null)
			{
				personHolder = json.optJSONObject("people");
			}
			JSONArray jspersons = personHolder.optJSONArray("person");
			for (int index = 0; index < jspersons.length(); index++)
			{
				JSONObject jsperson = jspersons.optJSONObject(index);
				Person person = FlickrParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchMediaItemOwner(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
			}
			JSONObject jsphoto = json.optJSONObject("photo");
			JSONObject jsperson = jsphoto.optJSONObject("owner");
			Person person = FlickrParsers.parsePerson(jsperson);
			result.getPersons().add(person);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer fetchContacts(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
			}
			JSONObject jscontactsHolder = json.optJSONObject("contacts");
			JSONArray contacts = jscontactsHolder.optJSONArray("contact");
			for (int index = 0; index < contacts.length(); index++)
			{
				JSONObject jsperson = contacts.optJSONObject(index);
				Person person = FlickrParsers.parsePerson(jsperson);
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
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
			}
			JSONObject jsmediaItem = json.optJSONObject("photo");
			MediaItem mediaItem = FlickrParsers.parseMediaItem(jsmediaItem);
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
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
			}
			JSONObject jsphotoHolder = json.optJSONObject("photos");
			JSONArray photos = jsphotoHolder.optJSONArray("photo");
			for (int index = 0; index < photos.length(); index++)
			{
				JSONObject jsmediaItem = photos.optJSONObject(index);
				MediaItem mediaItem = FlickrParsers.parseMediaItem(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer fetchPrevNextPhotos(String response, String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
			}
			JSONObject jsprevphoto = json.optJSONObject("prevphoto");
			MediaItem prevphoto = FlickrParsers.parseMediaItem(jsprevphoto);
			result.getMediaItems().add(prevphoto);
			JSONObject jsnextphoto = json.optJSONObject("nextphoto");
			MediaItem nextphoto = FlickrParsers.parseMediaItem(jsnextphoto);
			result.getMediaItems().add(nextphoto);
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id)
	{
		CommentsContainer result = new CommentsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			String stat = json.optString("stat");
			if ("fail".equals(stat))
			{
				SociosException sociosException = FlickrParsers.parseNativeException(response);
				return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
			}
			JSONObject jscommentsHolder = json.optJSONObject("comments");
			JSONArray jscomments = jscommentsHolder.optJSONArray("comment");
			for (int index = 0; index < jscomments.length(); index++)
			{
				JSONObject jsmediaItem = jscomments.optJSONObject(index);
				Comment mediaItem = FlickrParsers.parseComment(jsmediaItem);
				result.getComments().add(mediaItem);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc, id, 500);
		}
		return result;
	}
}
