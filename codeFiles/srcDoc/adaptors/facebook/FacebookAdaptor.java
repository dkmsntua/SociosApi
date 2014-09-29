package adaptors.facebook;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.FilterUtilities;
import helper.utilities.Utilities;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.filters.ActivityFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;

public class FacebookAdaptor implements ISnsAdaptor
{
	private static SocialNetwork sn = SocialNetwork.FACEBOOK;

	public FacebookAdaptor()
	{
	}

	public PersonsContainer getPersons(List<ObjectId> objectIds)
	{
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					PersonsContainer person = FacebookCalls.getPerson(id);
					ContainerUtilities.merge(result, person);
					return;
				}
			});
		}
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
		return result;
	}

	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username)
	{
		PersonsContainer result = null;
		if (personFilter != null)
		{
			List<String> keywords = FilterUtilities.getKeywords(personFilter);
			String chain = Utilities.getChain(keywords);
			result = FacebookCalls.searchAuthPersons(chain);
		}
		else if (mediaItemId != null)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, mediaItemId.getId(), 501);
		}
		else if (activityId != null)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), 501);
		}
		else if (username != null)
		{
			String name = username.getId();
			result = FacebookCalls.getPerson(name);
		}
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId)
	{
		return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, personId.getId(), 501);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret)
	{
		String id = personId.getId();
		PersonsContainer result = FacebookCalls.getMyConnectedPersons(id, accessToken);
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItems(List<ObjectId> objectIds)
	{
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					MediaItemsContainer mediaItem = FacebookCalls.getMediaItem(id);
					ContainerUtilities.merge(result, mediaItem);
					return;
				}
			});
		}
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
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForUser(ObjectId personId, ObjectId username)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		String id = null;
		if (personId != null)
		{
			id = personId.getId();
		}
		else
		{
			id = username.getId();
		}
		result = FacebookCalls.getMediaItemsForEntity(id, false);
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId)
	{
		String id = pageId.getId();
		MediaItemsContainer result = FacebookCalls.getMediaItemsForEntity(id, true);
		return result;
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter)
	{
		MediaItemsContainer result = new MediaItemsContainer();
		List<String> keywords = FilterUtilities.getKeywords(mediaFilter);
		if (keywords != null)
		{
			String query = Utilities.getChain(keywords);
			result = FacebookCalls.searchMediaItems(query);
		}
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId)
	{
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, mediaItemId.getId(), 501);
	}

	@Override
	public ActivitiesContainer getActivities(List<ObjectId> objectIds)
	{
		final ActivitiesContainer result = new ActivitiesContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ActivitiesContainer mediaItem = FacebookCalls.getActivity(id);
					ContainerUtilities.merge(result, mediaItem);
					return;
				}
			});
		}
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
		return result;
	}

	@Override
	public ActivitiesContainer getActivitiesForUser(ObjectId personId)
	{
		String id = personId.getId();
		ActivitiesContainer result = FacebookCalls.getActivitiesForUser(id);
		return result;
	}

	@Override
	public ActivitiesContainer findActivities(ActivityFilter activityFilter)
	{
		return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, null, null, 501);
	}

	@Override
	public CommentsContainer getComments(List<ObjectId> objectIds)
	{
		final CommentsContainer result = new CommentsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					CommentsContainer activity = FacebookCalls.getComment(id);
					ContainerUtilities.merge(result, activity);
					return;
				}
			});
		}
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
		return result;
	}

	@Override
	public CommentsContainer getCommentsForMediaItem(ObjectId mediaItemId)
	{
		String id = mediaItemId.getId();
		CommentsContainer result = FacebookCalls.getCommentsForMediaItem(id);
		return result;
	}

	@Override
	public CommentsContainer getCommentsForActivity(ObjectId activityId)
	{
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, activityId.getId(), 501);
	}

	@Override
	public ObjectIdContainer postMessage(ObjectId personId, String postText, String accessToken, String accessSecret)
	{
		if (!Utilities.clean(personId))
		{
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, null, null, 400);
		}
		String id = personId.getId();
		ObjectIdContainer result = FacebookCalls.postMessage(id, postText, accessToken);
		return result;
	}

	@Override
	public String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret)
	{
		return ExceptionsUtilities.exc501;
	}
}
