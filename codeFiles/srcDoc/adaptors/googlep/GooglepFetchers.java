package adaptors.googlep;

import helper.utilities.ExceptionsUtilities;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GooglepFetchers
{
	private static SocialNetwork sn = SocialNetwork.GOOGLEP;

	public static PersonsContainer fetchPerson(String response, String id)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			Person person = GooglepParsers.parsePerson(json);
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
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsperson = array.optJSONObject(index);
				Person person = GooglepParsers.parsePerson(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivity(String response, String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		try
		{
			JSONObject json = new JSONObject(response);
			Activity activity = GooglepParsers.parseActivity(json);
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
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jsactivity = array.optJSONObject(index);
				Activity activity = GooglepParsers.parseActivity(jsactivity);
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
			Comment comment = GooglepParsers.parseComment(json);
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
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++)
			{
				JSONObject jscomment = array.optJSONObject(index);
				Comment comment = GooglepParsers.parseComment(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, 500);
		}
		return result;
	}
}
