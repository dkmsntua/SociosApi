package adaptors.googlep;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;
import objects.main.snException;

public class GooglepCalls {
	private static SocialNetwork sn = SocialNetwork.GOOGLEP;
	private static String baseUrl = "https://www.googleapis.com/plus/v1/";
	private static String gpApplicationKey;
	static {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			gpApplicationKey = properties.getProperty("gpApplicationKey");
		}
		catch (IOException exc) {
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
	}

	public static PersonsContainer getPerson(String id) {
		PersonsContainer result;
		String requestUrl = baseUrl + "people/" + id + "?key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchPerson(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getRelevantPersons(final String activityId) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer plusoners = getRelevantPersons(activityId, "plusoners");
				ContainerUtilities.merge(result, plusoners);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer resharers = getRelevantPersons(activityId, "resharers");
				ContainerUtilities.merge(result, resharers);
			}
		});
		pool.shutdown();
		try {
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdownNow();
		ContainerUtilities.cleanExceptions(result);
		return result;
	}

	private static PersonsContainer getRelevantPersons(String activityId, String type) {
		PersonsContainer result;
		String requestUrl = baseUrl + "activities/" + activityId + "/people/" + type + "?key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchPersons(response, activityId);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), activityId, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, activityId);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(String id, String accessToken) {
		PersonsContainer result;
		String requestUrl = baseUrl + "people/" + id + "/people/visible?key=" + gpApplicationKey + "&access_token=" + accessToken + "&maxResults=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchPersons(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer findPersons(String query) {
		String myQuery = query;
		PersonsContainer result;
		try {
			myQuery = URLEncoder.encode(myQuery, SociosConstants.UTF8);
			String requestUrl = baseUrl + "people?query=" + myQuery + "&maxResults=50&key=" + gpApplicationKey;
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchPersons(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivity(String id) {
		ActivitiesContainer result;
		String requestUrl = baseUrl + "activities/" + id + "?key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchActivity(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static ActivitiesContainer getActivities(String query) {
		ActivitiesContainer result;
		String requestUrl = baseUrl + "activities?" + query + "&maxResults=20&key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchActivities(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String id) {
		ActivitiesContainer result;
		String requestUrl = baseUrl + "people/" + id + "/activities/public?maxResults=100&key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchActivities(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getComment(String id) {
		CommentsContainer result;
		String requestUrl = baseUrl + "comments/" + id + "?key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchComment(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getCommentsForActivity(String id) {
		CommentsContainer result;
		String requestUrl = baseUrl + "activities/" + id + "/comments?maxResults=100&key=" + gpApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = GooglepFetchers.fetchComments(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = GooglepParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}
}
