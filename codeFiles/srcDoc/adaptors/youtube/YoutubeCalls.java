package adaptors.youtube;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import helper.utilities.Utilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;
import objects.main.snException;

public class YoutubeCalls {
	private static SocialNetwork sn = SocialNetwork.YOUTUBE;
	private static String baseUrlV3 = "https://www.googleapis.com/youtube/v3/";
	private static String ytApplicationKey;
	static {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
			ytApplicationKey = properties.getProperty("ytApplicationKey");
		}
		catch (IOException exc) {
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
	}

	public static PersonsContainer getPerson(String identifier, String type) {
		PersonsContainer result;
		String requestUrl = null;
		if ("id".equals(type)) {
			String channelId = Utilities.getChannelId(identifier);
			requestUrl = baseUrlV3 + "channels?part=snippet,statistics&id=" + channelId + "&key=" + ytApplicationKey;
		}
		else if ("username".equals(type)) {
			requestUrl = baseUrlV3 + "channels?part=snippet,statistics&forUsername=" + identifier + "&key=" + ytApplicationKey;
		}
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchPersons(response, identifier);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), identifier, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, identifier);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(final String id, final String accessToken) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer subscribers = getMyConnectedPersons(id, "mySubscribers=true", accessToken);
				ContainerUtilities.merge(result, subscribers);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer subscriptions = getMyConnectedPersons(id, "channelId=" + id, accessToken);
				ContainerUtilities.merge(result, subscriptions);
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

	private static PersonsContainer getMyConnectedPersons(String id, String token, String accessToken) {
		PersonsContainer result;
		String requestUtl = baseUrlV3 + "subscriptions?part=snippet&" + token + "&access_token=" + accessToken + "&maxResults=50";
		try {
			String response = NetworkUtilities.getResponse(requestUtl);
			result = YoutubeFetchers.fetchPersons(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getSubscriptions(String id) {
		PersonsContainer result;
		String requestUtl = baseUrlV3 + "subscriptions?part=contentDetails,id,snippet&channelId=" + id + "&key=" + ytApplicationKey + "&maxResults=50";
		try {
			String response = NetworkUtilities.getResponse(requestUtl);
			result = YoutubeFetchers.fetchPersons(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id) {
		MediaItemsContainer result = null;
		String requestUrl = baseUrlV3 + "videos?part=snippet,contentDetails,statistics,status&id=" + id + "&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItems(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForUser(String id) {
		MediaItemsContainer result;
		String channelId = Utilities.getChannelId(id);
		String requestUrl = baseUrlV3 + "search?part=snippet&channelId=" + channelId + "&maxResults=50&type=video&key=" + ytApplicationKey;
		try {
			String uploadsResponse = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItems(uploadsResponse, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getPopularMediaItems() {
		MediaItemsContainer result;
		String requestUrl = baseUrlV3 + "search?part=snippet&maxResults=50&type=video&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer getRelatedMediaItems(String id) {
		MediaItemsContainer result;
		String requestUrl = baseUrlV3 + "search?part=snippet&maxResults=50&relatedToVideoId=" + id + "&type=video&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItems(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query) {
		MediaItemsContainer result;
		String requestUrl = baseUrlV3 + "search?part=snippet&maxResults=50&" + query + "&type=video&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String channelId) {
		ActivitiesContainer result;
		String requestUrl = baseUrlV3 + "activities?key=" + ytApplicationKey + "&part=id,snippet,contentDetails&maxResults=50&channelId=" + channelId;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchActivities(response, channelId);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), channelId, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, channelId);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id) {
		CommentsContainer result;
		String requestUrl = baseUrlV3 + "commentThreads?part=id,replies,snippet&videoId=" + id + "&maxResults=100&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchComments(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getComment(String id) {
		CommentsContainer result;
		String requestUrl = baseUrlV3 + "comments?part=id,snippet&id=" + id + "&key=" + ytApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchComments(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}
}
