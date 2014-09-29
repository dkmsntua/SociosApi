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

public class YoutubeCalls
{
	private static SocialNetwork sn = SocialNetwork.YOUTUBE;
	private static String baseUrlV2 = "http://gdata.youtube.com/feeds/api/";
	private static String baseUrlV3 = "https://www.googleapis.com/youtube/v3/";
	private static String ytApplicationKey;
	private static String commentFields = "entry(id,published,author,content)";
	private static String personFields = "published,summary,author,yt:channelId,yt:firstName,yt:googlePlusUserId,yt:lastName,yt:location,yt:statistics,media:thumbnail,yt:username";
	private static String videoFields = "published,title,author,gd:comments,media:group(media:description,media:license,media:player,media:thumbnail,yt:duration,yt:videoid),gd:rating,yt:statistics,yt:rating";
	private static String videosFields = "entry(published,title,author,gd:comments,media:group(media:description,media:license,media:player,media:thumbnail,yt:duration,yt:videoid),gd:rating,yt:statistics,yt:rating)";
	static
	{
		final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("socios.properties");
		final Properties properties = new Properties();
		try
		{
			properties.load(inputStream);
			ytApplicationKey = properties.getProperty("ytApplicationKey");
		}
		catch (Exception exc)
		{
			System.out.println("Static initialization error" + sn + ": " + exc.getMessage());
		}
	}

	public static PersonsContainer getPerson(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrlV2 + "users/" + id + "?v=2&alt=json&fields=" + personFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchPerson(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getSubscribers(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrlV2 + "users/" + id + "/subscriptions?v=2&alt=json";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchPersonsFromChannelV2(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getMediaItemOwner(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrlV2 + "videos/" + id + "?v=2&alt=json&fields=author";
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchPerson(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getMyConnectedPersons(final String id, final String accessToken)
	{
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer subscribers = getMyConnectedPersons(id, "mySubscribers=true", accessToken);
				ContainerUtilities.merge(result, subscribers);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer subscriptions = getMyConnectedPersons(id, "channelId=" + id, accessToken);
				ContainerUtilities.merge(result, subscriptions);
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

	private static PersonsContainer getMyConnectedPersons(String id, String token, String accessToken)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUtl = baseUrlV3 + "subscriptions?part=snippet&" + token + "&access_token=" + accessToken + "&maxResults=50";
		try
		{
			String response = NetworkUtilities.getResponse(requestUtl);
			result = YoutubeFetchers.fetchPersonsFromChannel(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrlV2 + "videos/" + id + "?v=2&alt=json&fields=" + videoFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItem(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItemsForUser(final String identifier)
	{
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(2);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer uploads = getMediaItemsForUser(identifier, "uploads");
				ContainerUtilities.merge(result, uploads);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer favorites = getMediaItemsForUser(identifier, "favorites");
				ContainerUtilities.merge(result, favorites);
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

	private static MediaItemsContainer getMediaItemsForUser(String identifier, String type)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrlV2 + "users/" + identifier + "/" + type + "?v=2&alt=json&max-results=50&fields=" + videosFields;
		try
		{
			String uploadsResponse = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItemsV2(uploadsResponse, identifier);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, identifier, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, identifier);
		}
		return result;
	}

	public static MediaItemsContainer getPopularMediaItems(String countryCode)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = "";
		if (!Utilities.isValid(countryCode))
		{
			requestUrl = baseUrlV2 + "standardfeeds/most_popular?v=2&alt=json&maxResults=50";
		}
		else
		{
			requestUrl = baseUrlV2 + "standardfeeds/" + countryCode + "/most_popular?v=2&alt=json&max-results=50&fields=" + videosFields;
		}
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItemsV2(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer getRelatedMediaItems(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrlV2 + "videos/" + id + "/related?v=2&alt=json&max-results=50&fields=" + videosFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItemsV2(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer searchMediaItems(String query)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrlV2 + "videos?v=2&alt=json&" + query + "&max-results=50&fields=" + videosFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchMediaItemsV2(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc, null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String channelId)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		String requestUrl = baseUrlV3 + "activities?key=" + ytApplicationKey + "&part=id,snippet,contentDetails&maxResults=50&channelId=" + channelId;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchActivities(response, channelId);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc, channelId, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, channelId);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id)
	{
		CommentsContainer result = new CommentsContainer();
		String requestUrl = baseUrlV2 + "videos/" + id + "/comments?v=2&alt=json&fields=" + commentFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = YoutubeFetchers.fetchComments(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc, id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = YoutubeParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}
}
