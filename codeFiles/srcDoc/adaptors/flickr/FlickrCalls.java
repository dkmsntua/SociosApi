package adaptors.flickr;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import helper.utilities.Utilities;
import java.io.InputStream;
import java.net.URLEncoder;
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

public class FlickrCalls
{
	private static SocialNetwork sn = SocialNetwork.FLICKR;
	private static String baseUrl = "https://api.flickr.com/services/rest";
	private static String flickrApplicationKey;
	private static String flickrApplicationSecret;
	static
	{
		final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		final Properties properties = new Properties();
		try
		{
			properties.load(inputStream);
			flickrApplicationKey = properties.getProperty("flickrApplicationKey");
			flickrApplicationSecret = properties.getProperty("flickrApplicationSecret");
		}
		catch (Exception exc)
		{
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
	}

	public static PersonsContainer getPerson(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "?method=flickr.people.getInfo&api_key=" + flickrApplicationKey + "&user_id=" + id + "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPerson(response, "person", id);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer getConnectedPersons(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "?method=flickr.contacts.getPublicList&api_key=" + flickrApplicationKey + "&user_id=" + id
				+ "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchContacts(response, id);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(String accessToken, String accessSecret)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			String response = getAuthorizedData("flickr.contacts.getList", null, accessToken, accessSecret);
			result = FlickrFetchers.fetchContacts(response, null);
		}
		catch (Exception exc)
		{
			//TODO 
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, null, 500);
		}
		return result;
	}

	public static PersonsContainer getRelevantPersons(final String photoId)
	{
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer favoritesContainer = callPersons("flickr.photos.getFavorites", photoId);
				ContainerUtilities.merge(result, favoritesContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer shownContainer = callPersons("flickr.photos.people.getList", photoId);
				ContainerUtilities.merge(result, shownContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer ownerContainer = getMediaItemOwner(photoId);
				ContainerUtilities.merge(result, ownerContainer);
				return;
			}
		});
		pool.shutdown();
		try
		{
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		ContainerUtilities.cleanExceptions(result);
		return result;
	}

	private static PersonsContainer getMediaItemOwner(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "?method=flickr.photos.getInfo&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItemOwner(response, id);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		return result;
	}

	public static PersonsContainer getPersonByUserName(String username)
	{
		PersonsContainer result = new PersonsContainer();
		try
		{
			username = URLEncoder.encode(username, "UTF-8");
			String requestUrl = baseUrl + "?method=flickr.people.findByUsername&api_key=" + flickrApplicationKey + "&username=" + username
					+ "&format=json&nojsoncallback=1";
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPerson(response, "user", username);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, null, 500);
		}
		return result;
	}

	private static PersonsContainer callPersons(String method, String photoId)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "?method=" + method + "&api_key=" + flickrApplicationKey + "&photo_id=" + photoId + "&&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPersons(response, photoId);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, photoId, 500);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "?method=flickr.photos.getInfo&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItem(response, id);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForUser(final String id)
	{
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer myOwnContainer = callMediaItems("flickr.people.getPublicPhotos", id);
				ContainerUtilities.merge(result, myOwnContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer myFavoritesContainer = callMediaItems("flickr.favorites.getPublicList", id);
				ContainerUtilities.merge(result, myFavoritesContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer ofMeContainer = callMediaItems("flickr.people.getPhotosOf", id);
				ContainerUtilities.merge(result, ofMeContainer);
				return;
			}
		});
		pool.shutdown();
		try
		{
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		ContainerUtilities.cleanExceptions(result);
		return result;
	}

	public static MediaItemsContainer getRandomMedia()
	{
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer interestingContainer = callMediaItems("flickr.interestingness.getList");
				ContainerUtilities.merge(result, interestingContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer recentContainer = callMediaItems("flickr.photos.getRecent");
				ContainerUtilities.merge(result, recentContainer);
				return;
			}
		});
		pool.shutdown();
		try
		{
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		ContainerUtilities.cleanExceptions(result);
		return result;
	}

	private static MediaItemsContainer callMediaItems(String method)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl
				+ "?method="
				+ method
				+ "&api_key="
				+ flickrApplicationKey
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, null);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, null, 500);
		}
		return result;
	}

	private static MediaItemsContainer callMediaItems(String method, String userId)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl
				+ "?method="
				+ method
				+ "&api_key="
				+ flickrApplicationKey
				+ "&user_id="
				+ userId
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, userId);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, userId, 500);
		}
		return result;
	}

	public static MediaItemsContainer findRelevantMediaItems(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "?method=flickr.photos.getContext&api_key=" + flickrApplicationKey + "&photo_id=" + id + "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchPrevNextPhotos(response, id);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl
				+ "?method=flickr.photos.search&api_key="
				+ flickrApplicationKey
				+ "&"
				+ query
				+ "&tag_mode=all"
				+ "&extras=description%2C+license%2C+date_upload%2C+date_taken%2C+owner_name%2C+icon_server%2C+last_update%2C+geo%2C+tags%2C+machine_tags%2C+views%2C+media%2C+path_alias&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchMediaItems(response, null);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, null, 500);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id)
	{
		CommentsContainer result = new CommentsContainer();
		String requestUrl = baseUrl + "?method=flickr.photos.comments.getList" + "&api_key=" + flickrApplicationKey + "&photo_id=" + id
				+ "&format=json&nojsoncallback=1";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = FlickrFetchers.fetchComments(response, null);
		}
		catch (Exception exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc, id, 500);
		}
		return result;
	}

	private static String getAuthorizedData(String method, Map<String, String> map, String accessToken, String accessSecret) throws Exception
	{
		if (map == null)
		{
			map = new HashMap<String, String>();
		}
		map.put("nojsoncallback", "1");
		String uuid_string = UUID.randomUUID().toString();
		String oauth_nonce = uuid_string.replaceAll("-", "");
		map.put(URLEncoder.encode("oauth_nonce", "UTF-8"), URLEncoder.encode(oauth_nonce, "UTF-8"));
		map.put("format", "json");
		map.put(URLEncoder.encode("oauth_consumer_key", "UTF-8"), URLEncoder.encode(flickrApplicationKey, "UTF-8"));
		long millis = System.currentTimeMillis() / 1000;
		String oauth_timestamp = String.valueOf(millis);
		map.put(URLEncoder.encode("oauth_timestamp", "UTF-8"), URLEncoder.encode(oauth_timestamp, "UTF-8"));
		String oauth_signature_method = "HMAC-SHA1";
		map.put(URLEncoder.encode("oauth_signature_method", "UTF-8"), URLEncoder.encode(oauth_signature_method, "UTF-8"));
		String oauth_version = "1.0";
		map.put(URLEncoder.encode("oauth_version", "UTF-8"), URLEncoder.encode(oauth_version, "UTF-8"));
		map.put(URLEncoder.encode("oauth_token", "UTF-8"), URLEncoder.encode(accessToken, "UTF-8"));
		map.put(URLEncoder.encode("method", "UTF-8"), URLEncoder.encode(method, "UTF-8"));
		Map<String, String> sortedMap = new TreeMap<String, String>(map);
		String chain = getChain(sortedMap);
		String baseString = "GET&" + URLEncoder.encode(baseUrl, "UTF-8") + "&" + URLEncoder.encode(chain, "UTF-8");
		String signingKey = URLEncoder.encode(flickrApplicationSecret, "UTF-8") + "&" + URLEncoder.encode(accessSecret, "UTF-8");
		String signature = Utilities.computeSignature(baseString, signingKey);
		map.put("oauth_signature", signature.toString());
		Map<String, String> finalMap = new TreeMap<String, String>(map);
		String finalChain = getChain(finalMap);
		String requestUrl = baseUrl + "?" + finalChain;
		String result = NetworkUtilities.getResponse(requestUrl);
		return result;
	}

	private static String getChain(Map<String, String> map)
	{
		String result = "";
		int size = map.size();
		for (Map.Entry<String, String> entry : map.entrySet())
		{
			size--;
			String key = entry.getKey().toString();
			String value = entry.getValue().toString();
			result += key + "=" + value;
			if (size != 0)
			{
				result += "&";
			}
		}
		return result;
	}
}
