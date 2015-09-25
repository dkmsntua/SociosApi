package adaptors.instagram;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;
import objects.main.snException;

public class InstagramCalls {
	private static SocialNetwork sn = SocialNetwork.INSTAGRAM;
	private static String baseUrl = "https://api.instagram.com/v1";
	private static String igApplicationKey;
	static {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		}
		catch (IOException exc) {
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
		igApplicationKey = properties.getProperty("igApplicationKey");
	}

	public static PersonsContainer getPerson(String id) {
		PersonsContainer result;
		String requestUrl = baseUrl + "/users/" + id + "?client_id=" + igApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchPerson(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getConnectedPersons(final String id) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer followingContainer = getConnectedPersonsByType(id, "follows");
				ContainerUtilities.merge(result, followingContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer followedByContainer = InstagramCalls.getConnectedPersonsByType(id, "followed-by");
				ContainerUtilities.merge(result, followedByContainer);
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

	public static PersonsContainer getRelevantPersons(String mediaId) {
		PersonsContainer result;
		String requestUrl = baseUrl + "/media/" + mediaId + "?client_id=" + igApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchRelevantPersons(response, mediaId);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), mediaId, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, mediaId);
		}
		return result;
	}

	public static PersonsContainer searchPersons(String username) {
		PersonsContainer result;
		String requestUrl = baseUrl + "/users/search?q=" + username + "&client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchPersons(response, username);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, null);
		}
		return result;
	}

	private static PersonsContainer getConnectedPersonsByType(String id, String type) {
		PersonsContainer result;
		String requestUrl = baseUrl + "/users/" + id + "/" + type + "?client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchPersons(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "/media/" + id + "?client_id=" + igApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchMediaItem(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer findMyRecentMediaItems(String id) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "/users/" + id + "/media/recent?client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchMediaItems(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getPopularMediaItems() {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "/media/popular?client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String tag) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "/tags/" + tag + "/media/recent?client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, tag);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItemsByLocation(String locationQuery) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "/media/search?" + locationQuery + "&client_id=" + igApplicationKey + "&count=100";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id) {
		CommentsContainer result;
		String requestUrl = baseUrl + "/media/" + id + "/comments?client_id=" + igApplicationKey;
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = InstagramFetchers.fetchComments(response, id);
		}
		catch (IOException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		catch (snException exc) {
			SociosException sociosException = InstagramParsers.parseNativeException(exc.getData());
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}
	//unused code maybe in the future
	/* public static MediaItemsContainer findMyMediaItemsAuthorized(final String
	 * id) { final MediaItemsContainer result = new MediaItemsContainer();
	 * ExecutorService pool = Executors.newFixedThreadPool(3); pool.submit(new
	 * Runnable() {
	 * 
	 * @Override public void run() { MediaItemsContainer myFeedContainer =
	 * myFeedAuthorized(); ContainerUtilities.merge(result, myFeedContainer);
	 * return; } }); pool.submit(new Runnable() {
	 * 
	 * @Override public void run() { MediaItemsContainer myRecentContainer =
	 * myRecentAuthorized(id); ContainerUtilities.merge(result,
	 * myRecentContainer); return; } }); pool.submit(new Runnable() {
	 * 
	 * @Override public void run() { MediaItemsContainer myLiked =
	 * myLikedAuthorized(); ContainerUtilities.merge(result, myLiked); return; }
	 * }); pool.shutdown(); try { pool.awaitTermination(SociosConstants.timeOut,
	 * TimeUnit.SECONDS); } catch (InterruptedException e) {
	 * e.printStackTrace(); } pool.shutdownNow();
	 * ContainerUtilities.cleanExceptions(result); return result; }
	 * 
	 * private static MediaItemsContainer myFeedAuthorized() {
	 * MediaItemsContainer result = new MediaItemsContainer(); String requestUrl
	 * = baseUrl + "/users/self/feed?access_token=" + igUserAccessToken +
	 * "&count=100"; try { String response =
	 * NetworkUtilities.getResponse(requestUrl); result =
	 * InstagramFetchers.fetchMediaItems(response, null); } catch (IOException
	 * exc) { return ExceptionsUtilities.getException(SociosObject.MEDIAITEM,
	 * sn, exc.getMessage(), null, SociosConstants.ERROR_500); } catch
	 * (snException exc) { SociosException sociosException =
	 * InstagramParsers.parseNativeException(exc.data); return
	 * ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM,
	 * sociosException, null); } return result; }
	 * 
	 * private static MediaItemsContainer myLikedAuthorized() {
	 * MediaItemsContainer result = new MediaItemsContainer(); String requestUrl
	 * = baseUrl + "/users/self/media/liked?access_token=" + igUserAccessToken +
	 * "&count=100"; try { String response =
	 * NetworkUtilities.getResponse(requestUrl); result =
	 * InstagramFetchers.fetchMediaItems(response, null); } catch (IOException
	 * exc) { return ExceptionsUtilities.getException(SociosObject.MEDIAITEM,
	 * sn, exc, null, SociosConstants.ERROR_500); } catch (snException exc) {
	 * SociosException sociosException =
	 * InstagramParsers.parseNativeException(exc.data); return
	 * ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM,
	 * sociosException, null); } return result; }
	 * 
	 * private static MediaItemsContainer myRecentAuthorized(String id) {
	 * MediaItemsContainer result = new MediaItemsContainer(); String requestUrl
	 * = baseUrl + "/users/" + id + "/media/recent?access_token=" +
	 * igUserAccessToken + "&count=100"; try { String response =
	 * NetworkUtilities.getResponse(requestUrl); result =
	 * InstagramFetchers.fetchMediaItems(response, id); } catch (IOException
	 * exc) { return ExceptionsUtilities.getException(SociosObject.MEDIAITEM,
	 * sn, exc, id, SociosConstants.ERROR_500); } catch (snException exc) {
	 * SociosException sociosException =
	 * InstagramParsers.parseNativeException(exc.data); return
	 * ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM,
	 * sociosException, id); } return result; } */
}
