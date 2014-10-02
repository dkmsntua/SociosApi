package adaptors.dailymotion;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.NetworkUtilities;
import java.io.IOException;
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

public class DailymotionCalls
{
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;
	private static String baseUrl = "https://api.dailymotion.com/";
	private static String activityFields = "id,created_time,type,from_tile.owner,object_type,object_tile.title,object_tile.url,object_tile.owner,object_tile.icon_url,object_video.id,object_video.title,object_video.created_time,object_video.thumbnail_60_url,object_video.description,object_video.duration,object_video.country,object_video.language,object_video.rating,object_video.ratings_total,object_video.comments_total,object_video.views_total,object_video.bookmarks_total,object_video.tags,object_video.strongtags,object_video.url,object_video.owner,object_video.channel,object_video.embed_html,object_video.embed_url,object_video.swf_url";
	private static String commentFields = "id,created_time,message,video.id,video.title,video.description,owner.id,owner.username,owner.screenname,language";
	private static String userPublicFields = "avatar_120_url,avatar_190_url,avatar_240_url,avatar_360_url,avatar_480_url,avatar_60_url,avatar_720_url,background_url,banner_url,created_time,description,gender,id,screenname,status,url,username,videos_total,videostar.url,views_total,language,parent";
	private static String videoFields = "access_error,aspect_ratio,bookmarks_total,channel,comments_total,country,created_time,description,duration,embed_html,embed_url,genre,id,language,modified_time,owner,rating,ratings_total,sharing_urls,soundtrack_info,stream_source_url,strongtags,swf_url,tags,taken_time,thumbnail_60_url,thumbnail_url,title,type,url,views_last_day,views_last_hour,views_last_month,views_last_week,views_total";
	private static String videoOwnerPublicFields = "owner.avatar_60_url,owner.avatar_120_url,owner.avatar_190_url,owner.avatar_240_url,owner.avatar_360_url,owner.avatar_480_url,owner.avatar_720_url,owner.background_url,owner.banner_url,owner.created_time,owner.description,owner.gender,owner.id,owner.screenname,owner.status,owner.url,owner.username,owner.videos_total,owner.views_total,owner.language,owner.parent";

	public static PersonsContainer getPerson(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "user/" + id + "?fields=" + userPublicFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchPerson(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getLocalizedMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer getMediaItemOwner(String id)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "video/" + id + "?fields=" + videoOwnerPublicFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchPerson(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static PersonsContainer searchPersons(String chain)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "users?search=" + chain + "&sort=relevance&limit=100&fields=" + userPublicFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchPersons(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, null);
		}
		return result;
	}

	public static PersonsContainer getConnectedPersons(final String id)
	{
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(3);
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer fansContainer = callPersons(id, "fans");
				ContainerUtilities.merge(result, fansContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer followingContainer = callPersons(id, "following");
				ContainerUtilities.merge(result, followingContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				PersonsContainer friendsContainer = callPersons(id, "friends");
				ContainerUtilities.merge(result, friendsContainer);
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

	private static PersonsContainer callPersons(String id, String type)
	{
		PersonsContainer result = new PersonsContainer();
		String requestUrl = baseUrl + "user/" + id + "/" + type + "?limit=100&fields=" + userPublicFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchPersons(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.PERSON, sociosException, id);
		}
		return result;
	}

	public static MediaItemsContainer getMediaItem(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "video/" + id + "&fields=" + videoFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchMediaItem(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
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
				MediaItemsContainer favoritesContainer = callMediaItems(identifier, "favorites");
				ContainerUtilities.merge(result, favoritesContainer);
				return;
			}
		});
		pool.submit(new Runnable()
		{
			@Override
			public void run()
			{
				MediaItemsContainer videosContainer = callMediaItems(identifier, "videos");
				ContainerUtilities.merge(result, videosContainer);
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

	public static MediaItemsContainer searchMediaItems(String query)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "videos?" + query + "limit=100&fields=" + videoFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchMediaItems(response, null);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), null, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, null);
		}
		return result;
	}

	public static MediaItemsContainer findRelevantMediaItems(String id)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "video/" + id + "/related?limit=100&fields=" + videoFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchMediaItems(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	private static MediaItemsContainer callMediaItems(String id, String type)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String requestUrl = baseUrl + "user/" + id + "/" + type + "?limit=100&fields=" + videoFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchMediaItems(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.MEDIAITEM, sociosException, id);
		}
		return result;
	}

	public static ActivitiesContainer getActivity(String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		String requestUrl = baseUrl + "activity/" + id + "?fields=" + activityFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchActivity(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static ActivitiesContainer getActivitiesForUser(String id)
	{
		ActivitiesContainer result = new ActivitiesContainer();
		String requestUrl = baseUrl + "user/" + id + "/activities?limit=100&fields=" + activityFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchActivities(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.ACTIVITY, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getComment(String id)
	{
		CommentsContainer result = new CommentsContainer();
		String requestUrl = baseUrl + "comment/" + id + "?fields=" + commentFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchComment(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}

	public static CommentsContainer getCommentsForMediaItem(String id)
	{
		CommentsContainer result = new CommentsContainer();
		String requestUrl = baseUrl + "video/" + id + "/comments?limit=100&fields=" + commentFields;
		try
		{
			String response = NetworkUtilities.getResponse(requestUrl);
			result = DailymotionFetchers.fetchComments(response, id);
		}
		catch (IOException exc)
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage(), id, 500);
		}
		catch (snException exc)
		{
			SociosException sociosException = DailymotionParsers.parseNativeException(exc.data);
			return ExceptionsUtilities.getNativeException(SociosObject.COMMENT, sociosException, id);
		}
		return result;
	}
}
