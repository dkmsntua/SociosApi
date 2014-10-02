package adaptors.facebook;

import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Properties;
import javax.net.ssl.HttpsURLConnection;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;
import objects.main.snException;

public class FacebookCalls
{
	private static SocialNetwork sn = SocialNetwork.FACEBOOK;
	public static String baseUrl = "https://graph.facebook.com/";
	private static String fbApplicationKey;
	private static String fbUserAccessToken;
	private static String fields = "fields=id,name,first_name,middle_name,last_name,gender,locale,link,username,picture,cover";
	static
	{
		final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		final Properties properties = new Properties();
		try
		{
			properties.load(inputStream);
			fbApplicationKey = properties.getProperty("fbApplicationKey");
			fbUserAccessToken = properties.getProperty("fbUserAccessToken");
		}
		catch (Exception exc)
		{
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
	}

	public static PersonsContainer getPerson(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "/" + id + "?" + fields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPerson(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(String id, String accessToken)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + id + "/friends?" + fields + "&access_token=" + accessToken + "&limit=10000";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPersons(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer searchAuthPersons(String chain)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "search?q=" + chain + "&type=user&access_token=" + fbUserAccessToken + "&" + fields + "&limit=200";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPersons(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			String response = getFbObject(id);
			result = FacebookFetchers.fetchMediaItem(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForEntity(String id, boolean page)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		try
		{
			String response = getPostsForEntity(id, page);
			result = FacebookFetchers.fetchMediaItems(response, id, page);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "search?q=" + query + "&type=post&access_token=" + fbApplicationKey + "&limit=200";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchMediaItems(response, null, false);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivity(String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		try
		{
			String response = getFbObject(id);
			result = FacebookFetchers.fetchActivity(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		try
		{
			String response = getPostsForEntity(id, true);
			result = FacebookFetchers.fetchActivities(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getComment(String id)
	{
		CommentsContainer result = new CommentsContainer();
		try
		{
			String response = getFbObject(id);
			result = FacebookFetchers.fetchComment(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id)
	{
		CommentsContainer result = new CommentsContainer();
		try
		{
			String requestUrl = baseUrl + id + "/comments?access_token=" + fbApplicationKey;
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchComments(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	private static String getFbObject(String id) throws snException, IOException
	{
		String requestUrl = baseUrl + id + "?access_token=" + fbApplicationKey;
		String result = NetworkUtilities.getResponse(requestUrl);
		return result;
	}

	private static String getPostsForEntity(String id, boolean page) throws snException, IOException
	{
		String type = "";
		if (page)
		{
			type = "feed";
		}
		else
		{
			type = "posts";
		}
		String requestUrl = baseUrl + id + "/" + type + "?access_token=" + fbApplicationKey + "&limit=10000";
		String result = NetworkUtilities.getResponse(requestUrl);
		return result;
	}

	public static ObjectIdContainer postMessage(String id, String msg, String accessToken)
	{
		ObjectIdContainer result = new ObjectIdContainer();
		String url = baseUrl + id + "/feed";
		String response = "";
		HttpsURLConnection conn = null;
		try
		{
			URL obj = new URL(url);
			conn = (HttpsURLConnection) obj.openConnection();
			conn.setRequestMethod("POST");
			conn.setRequestProperty("User-Agent", "Mozilla/5.0");
			conn.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
			String urlParameters = "message=" + msg + "&access_token=" + accessToken;
			conn.setDoOutput(true);
			NetworkUtilities.writeRequest(conn, urlParameters);
			response = NetworkUtilities.readResponse(conn);
			FacebookFetchers.fetchMessage(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = FacebookParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.OBJECTID, sociosException, id);
		}
		return result;
	}
}
