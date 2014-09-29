package helper.utilities;

import java.util.HashMap;
import java.util.Map;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;

public class ExceptionsUtilities
{
	public static String exc400 = "No valid input was detected.";
	public static String exc500 = "Something went wrong.";
	public static String exc501 = "This method is not implemented for this particular social network.";
	private static Map<Integer, String> map;
	static
	{
		map = new HashMap<Integer, String>();
		map.put(400, exc400);
		map.put(500, exc500);
		map.put(501, exc501);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNativeException(SociosObject type, SociosException se, String id)
	{
		se.setActorId(id);
		if (type == SociosObject.PERSON)
		{
			PersonsContainer result = new PersonsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.MEDIAITEM)
		{
			MediaItemsContainer result = new MediaItemsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.ACTIVITY)
		{
			ActivitiesContainer result = new ActivitiesContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.COMMENT)
		{
			CommentsContainer result = new CommentsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.OBJECTID)
		{
			ObjectIdContainer result = new ObjectIdContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getException(SociosObject objectType, SocialNetwork sn, Exception exc, String actorId, int code)
	{
		String excMessage = "";
		if (exc != null)
		{
			excMessage = exc.getMessage();
		}
		SociosException se = new SociosException(map.get(code), excMessage, code, actorId, sn);
		if (objectType == SociosObject.PERSON)
		{
			PersonsContainer result = new PersonsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.MEDIAITEM)
		{
			MediaItemsContainer result = new MediaItemsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.ACTIVITY)
		{
			ActivitiesContainer result = new ActivitiesContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.COMMENT)
		{
			CommentsContainer result = new CommentsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.OBJECTID)
		{
			ObjectIdContainer result = new ObjectIdContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		return null;
	}
}
