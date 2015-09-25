package adaptors.facebook;

import helper.misc.SociosConstants;
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

public class FacebookCalls {
	private static String baseUrl = "https://graph.facebook.com/";
	private static SocialNetwork sn = SocialNetwork.FACEBOOK;
	private static String fbApplicationKey;
	private static String fbUserAccessToken;
	private static String fields = "fields=id,name,first_name,middle_name,last_name,gender,locale,link,picture,cover";
	static {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		}
		catch (IOException exc) {
			System.out.println(exc.getMessage());
		}
		fbApplicationKey = properties.getProperty("fbApplicationKey");
		fbUserAccessToken = properties.getProperty("fbUserAccessToken");
	}

	public static PersonsContainer getPerson(String id) {
		PersonsContainer result;
		String requestUrl = getBaseUrl() + "/" + id + "?" + fields;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPerson(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(String id, String accessToken) {
		PersonsContainer result;
		String requestUrl = getBaseUrl() + id + "/friends?" + fields + "&access_token=" + accessToken + "&limit=10000";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPersons(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer searchAuthPersons(String chain) {
		PersonsContainer result;
		String requestUrl = getBaseUrl() + "search?q=" + chain + "&type=user&access_token=" + fbUserAccessToken + "&" + fields + "&limit=200";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchPersons(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id) {
		MediaItemsContainer result;
		try {
			String response = getFbObject(id);
			result = FacebookFetchers.fetchMediaItem(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForEntity(String id, boolean page) {
		MediaItemsContainer result;
		try {
			String response = getPostsForEntity(id, page);
			result = FacebookFetchers.fetchMediaItems(response, id, page);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query) {
		MediaItemsContainer result;
		String requestUrl = getBaseUrl() + "search?q=" + query + "&type=post&access_token=" + fbApplicationKey + "&limit=200";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchMediaItems(response, null, false);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivity(String id) {
		ActivitiesContainer result;
		try {
			String response = getFbObject(id);
			result = FacebookFetchers.fetchActivity(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String id) {
		ActivitiesContainer result;
		try {
			String response = getPostsForEntity(id, true);
			result = FacebookFetchers.fetchActivities(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getComment(String id) {
		CommentsContainer result;
		try {
			String response = getFbObject(id);
			result = FacebookFetchers.fetchComment(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id) {
		CommentsContainer result;
		try {
			String requestUrl = getBaseUrl() + id + "/comments?access_token=" + fbApplicationKey;
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FacebookFetchers.fetchComments(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	private static String getFbObject(String id) throws snException, IOException {
		String requestUrl = getBaseUrl() + id + "?access_token=" + fbApplicationKey;
		return NetworkUtilities.getResponse(requestUrl);
	}

	private static String getPostsForEntity(String id, boolean page) throws snException, IOException {
		String type;
		if (page) {
			type = "feed";
		}
		else {
			type = "posts";
		}
		String requestUrl = getBaseUrl() + id + "/" + type + "?access_token=" + fbApplicationKey + "&limit=10000";
		return NetworkUtilities.getResponse(requestUrl);
	}

	public static ObjectIdContainer postMessage(String id, String msg, String accessToken) {
		ObjectIdContainer result = new ObjectIdContainer();
		String url = getBaseUrl() + id + "/feed";
		String response;
		HttpsURLConnection conn;
		try {
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
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = FacebookParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.OBJECTID, sociosException, id);
		}
		return result;
	}

	public static String getBaseUrl() {
		return baseUrl;
	}

	public static void setBaseUrl(String baseUrl) {
		FacebookCalls.baseUrl = baseUrl;
	}
}
