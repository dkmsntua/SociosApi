package adaptors.flickr;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import helper.utilities.Utilities;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.snException;

public class FlickrCalls {
	private static SocialNetwork sn = SocialNetwork.FLICKR;
	private static String baseUrl = "https://api.flickr.com/services/rest";
	private static String flickrApplicationKey;
	private static String flickrApplicationSecret;
	static {
		InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		Properties properties = new Properties();
		try {
			properties.load(inputStream);
		}
		catch (IOException e) {
			System.out.println(e.getMessage());
		}
		flickrApplicationKey = properties.getProperty("flickrApplicationKey");
		flickrApplicationSecret = properties.getProperty("flickrApplicationSecret");
	}

	public static PersonsContainer getPerson(String id) {
		String requestUrl = baseUrl + "?method=flickr.people.getInfo&api_key=" + flickrApplicationKey + "&user_id=" + id + "&format=json&nojsoncallback=1";
		String response;
		try {
			response = NetworkUtilities.getResponse(requestUrl);
		}
		catch (IOException | snException e) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, e.getMessage(), id, SociosConstants.ERROR_500);
		}
		return FlickrFetchers.fetchPerson(response, "person", id);
	}

	public static PersonsContainer getConnectedPersons(String id) {
		String requestUrl = baseUrl + "?method=flickr.contacts.getPublicList&api_key=" + flickrApplicationKey + "&user_id=" + id
				+ "&format=json&nojsoncallback=1";
		String response;
		try {
			response = NetworkUtilities.getResponse(requestUrl);
		}
		catch (IOException | snException e) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, e.getMessage(), id, SociosConstants.ERROR_500);
		}
		return FlickrFetchers.fetchContacts(response, id);
	}

	public static PersonsContainer getMyConnectedPersons(String accessToken, String accessSecret) {
		String response;
		try {
			response = getAuthorizedData("flickr.contacts.getList", null, accessToken, accessSecret);
		}
		catch (GeneralSecurityException | IOException | snException exc) {
			//TODO
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		return FlickrFetchers.fetchContacts(response, null);
	}

	public static PersonsContainer getRelevantPersons(final String photoId) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer favoritesContainer = callPersons("flickr.photos.getFavorites", photoId);
				ContainerUtilities.merge(result, favoritesContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer shownContainer = callPersons("flickr.photos.people.getList", photoId);
				ContainerUtilities.merge(result, shownContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				PersonsContainer ownerContainer = getMediaItemOwner(photoId);
				ContainerUtilities.merge(result, ownerContainer);
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

	private static PersonsContainer getMediaItemOwner(String id) {
		String requestUrl = baseUrl + "?method=flickr.photos.getInfo&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		String response;
		try {
			response = NetworkUtilities.getResponse(requestUrl);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		return FlickrFetchers.fetchMediaItemOwner(response, id);
	}

	public static PersonsContainer getPersonByUserName(String username) {
		PersonsContainer result;
		String myUsername = username;
		try {
			myUsername = URLEncoder.encode(myUsername, SociosConstants.UTF8);
			String requestUrl = baseUrl + "?method=flickr.people.findByUsername&api_key=" + flickrApplicationKey + "&username=" + myUsername
					+ "&format=json&nojsoncallback=1";
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPerson(response, "user", myUsername);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		return result;
	}

	private static PersonsContainer callPersons(String method, String photoId) {
		PersonsContainer result;
		String requestUrl = baseUrl + "?method=" + method + "&api_key=" + flickrApplicationKey + "&photo_id=" + photoId + "&&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPersons(response, photoId);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), photoId, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "?method=flickr.photos.getInfo&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItem(response, id);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForUser(final String id) {
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MediaItemsContainer myOwnContainer = callMediaItems("flickr.people.getPublicPhotos", id);
				ContainerUtilities.merge(result, myOwnContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MediaItemsContainer myFavoritesContainer = callMediaItems("flickr.favorites.getPublicList", id);
				ContainerUtilities.merge(result, myFavoritesContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MediaItemsContainer ofMeContainer = callMediaItems("flickr.people.getPhotosOf", id);
				ContainerUtilities.merge(result, ofMeContainer);
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

	public static MediaItemsContainer getRandomMedia() {
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MediaItemsContainer interestingContainer = callMediaItems("flickr.interestingness.getList");
				ContainerUtilities.merge(result, interestingContainer);
			}
		});
		pool.submit(new Runnable() {
			@Override
			public void run() {
				MediaItemsContainer recentContainer = callMediaItems("flickr.photos.getRecent");
				ContainerUtilities.merge(result, recentContainer);
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

	private static MediaItemsContainer callMediaItems(String method) {
		MediaItemsContainer result;
		String requestUrl = baseUrl
				+ "?method="
				+ method
				+ "&api_key="
				+ flickrApplicationKey
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, null);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		return result;
	}

	private static MediaItemsContainer callMediaItems(String method, String userId) {
		MediaItemsContainer result;
		String requestUrl = baseUrl
				+ "?method="
				+ method
				+ "&api_key="
				+ flickrApplicationKey
				+ "&user_id="
				+ userId
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, userId);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), userId, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer findRelevantMediaItems(String id) {
		MediaItemsContainer result;
		String requestUrl = baseUrl + "?method=flickr.photos.getContext&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPrevNextPhotos(response, id);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query) {
		MediaItemsContainer result;
		String requestUrl = baseUrl
				+ "?method=flickr.photos.search&api_key="
				+ flickrApplicationKey
				+ "&"
				+ query
				+ "&tag_mode=all"
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, null);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id) {
		CommentsContainer result;
		String requestUrl = baseUrl + "?method=flickr.photos.comments.getList" + "&api_key=" + flickrApplicationKey + "&photo_id=" + id
				+ "&format=json&nojsoncallback=1";
		try {
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchComments(response);
		}
		catch (IOException | snException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, SociosConstants.ERROR_500);
		}
		return result;
	}

	private static String getAuthorizedData(String method, Map<String, String> map, String accessToken, String accessSecret) throws GeneralSecurityException,
			IOException, snException {
		if (map == null) {
			map = new HashMap<String, String>();
		}
		map.put("nojsoncallback", "1");
		String uuid_string = UUID.randomUUID().toString();
		String oauth_nonce = uuid_string.replaceAll("-", "");
		map.put(URLEncoder.encode("oauth_nonce", SociosConstants.UTF8), URLEncoder.encode(oauth_nonce, SociosConstants.UTF8));
		map.put("format", "json");
		map.put(URLEncoder.encode("oauth_consumer_key", SociosConstants.UTF8), URLEncoder.encode(flickrApplicationKey, SociosConstants.UTF8));
		long millis = System.currentTimeMillis() / SociosConstants.ONE_THOUSAND;
		String oauth_timestamp = String.valueOf(millis);
		map.put(URLEncoder.encode("oauth_timestamp", SociosConstants.UTF8), URLEncoder.encode(oauth_timestamp, SociosConstants.UTF8));
		String oauth_signature_method = "HMAC-SHA1";
		map.put(URLEncoder.encode("oauth_signature_method", SociosConstants.UTF8), URLEncoder.encode(oauth_signature_method, SociosConstants.UTF8));
		String oauth_version = "1.0";
		map.put(URLEncoder.encode("oauth_version", SociosConstants.UTF8), URLEncoder.encode(oauth_version, SociosConstants.UTF8));
		map.put(URLEncoder.encode("oauth_token", SociosConstants.UTF8), URLEncoder.encode(accessToken, SociosConstants.UTF8));
		map.put(URLEncoder.encode("method", SociosConstants.UTF8), URLEncoder.encode(method, SociosConstants.UTF8));
		Map<String, String> sortedMap = new TreeMap<String, String>(map);
		String chain = getChain(sortedMap);
		String baseString = "GET&" + URLEncoder.encode(baseUrl, SociosConstants.UTF8) + "&" + URLEncoder.encode(chain, SociosConstants.UTF8);
		String signingKey = URLEncoder.encode(flickrApplicationSecret, SociosConstants.UTF8) + "&" + URLEncoder.encode(accessSecret, SociosConstants.UTF8);
		String signature = Utilities.computeSignature(baseString, signingKey);
		map.put("oauth_signature", signature);
		Map<String, String> finalMap = new TreeMap<String, String>(map);
		String finalChain = getChain(finalMap);
		String requestUrl = baseUrl + "?" + finalChain;
		String result = NetworkUtilities.getResponse(requestUrl);
		return result;
	}

	private static String getChain(Map<String, String> map) {
		String result = "";
		int size = map.size();
		for (Map.Entry<String, String> entry : map.entrySet()) {
			size--;
			String key = entry.getKey();
			String value = entry.getValue();
			result += key + "=" + value;
			if (size != 0) {
				result += "&";
			}
		}
		return result;
	}
}
