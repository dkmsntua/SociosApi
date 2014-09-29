package socios;

import helper.misc.SociosConstants;
import helper.utilities.AdaptorUtilities;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.FilterUtilities;
import helper.utilities.Utilities;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.jws.WebParam;
import javax.jws.WebService;
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

@WebService
public class SociosApiSoap
{
	public PersonsContainer getPersons(@WebParam(name = "personIds") List<ObjectId> personIds)
	{
		long start = System.currentTimeMillis();
		final PersonsContainer result = new PersonsContainer();
		if (!Utilities.cleanObjectIdList(personIds))
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		List<List<ObjectId>> composite = AdaptorUtilities.getLists(personIds);
		ExecutorService pool = Executors.newFixedThreadPool(composite.size());
		for (final List<ObjectId> lista : composite)
		{
			final ObjectId temp = lista.get(0);
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(temp);
					PersonsContainer personsContainer = adaptor.getPersons(lista);
					ContainerUtilities.merge(result, personsContainer);
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
		Utilities.cleanDuplicates(result.getPersons());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public PersonsContainer connectedPersons(@WebParam(name = "personId") ObjectId personId)
	{
		long start = System.currentTimeMillis();
		PersonsContainer result = new PersonsContainer();
		if (!Utilities.clean(personId))
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(personId);
		result = adaptor.connectedPersons(personId);
		Utilities.cleanDuplicates(result.getPersons());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public PersonsContainer myConnectedPersons(@WebParam(name = "personId") ObjectId personId, @WebParam(name = "accessToken") String accessToken,
			@WebParam(name = "accessSecret") String accessSecret)
	{
		long start = System.currentTimeMillis();
		PersonsContainer result = new PersonsContainer();
		if (!Utilities.clean(personId))
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(personId);
		result = adaptor.myConnectedPersons(personId, accessToken, accessSecret);
		Utilities.cleanDuplicates(result.getPersons());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public PersonsContainer findPersons(@WebParam(name = "personFilter") PersonFilter personFilter, @WebParam(name = "mediaItemId") ObjectId mediaItemId,
			@WebParam(name = "activityId") ObjectId activityId, @WebParam(name = "username") ObjectId username)
	{
		long start = System.currentTimeMillis();
		PersonsContainer result = new PersonsContainer();
		if (FilterUtilities.isValid(personFilter))
		{
			List<SocialNetwork> sns = personFilter.getSns();
			if (!Utilities.isValid(sns))
			{
				sns = AdaptorUtilities.getSocialNetworks();
			}
			for (SocialNetwork sn : sns)
			{
				ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
				PersonsContainer myPersonsContainer = adaptor.findPersons(personFilter, null, null, null);
				ContainerUtilities.merge(result, myPersonsContainer);
			}
		}
		else if (Utilities.clean(mediaItemId))
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(mediaItemId);
			result = adaptor.findPersons(null, mediaItemId, null, null);
		}
		else if (Utilities.clean(activityId))
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(activityId);
			result = adaptor.findPersons(null, null, activityId, null);
		}
		else if (Utilities.clean(username))
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(username);
			result = adaptor.findPersons(null, null, null, username);
		}
		else
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		Utilities.cleanDuplicates(result.getPersons());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public MediaItemsContainer getMediaItems(@WebParam(name = "objectIds") List<ObjectId> mediaItemIds)
	{
		long start = System.currentTimeMillis();
		final MediaItemsContainer result = new MediaItemsContainer();
		if (!Utilities.cleanObjectIdList(mediaItemIds))
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		List<List<ObjectId>> composite = AdaptorUtilities.getLists(mediaItemIds);
		ExecutorService pool = Executors.newFixedThreadPool(composite.size());
		for (final List<ObjectId> lista : composite)
		{
			final ObjectId temp = lista.get(0);
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(temp);
					MediaItemsContainer mediaItemsContainer = adaptor.getMediaItems(lista);
					ContainerUtilities.merge(result, mediaItemsContainer);
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
		Utilities.cleanDuplicates(result.getMediaItems());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public MediaItemsContainer getMediaItemsForUser(@WebParam(name = "personId") ObjectId personId, @WebParam(name = "username") ObjectId username)
	{
		long start = System.currentTimeMillis();
		MediaItemsContainer result = new MediaItemsContainer();
		if (Utilities.clean(personId))
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(personId);
			result = adaptor.getMediaItemsForUser(personId, null);
		}
		else if (Utilities.clean(username))
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(username);
			result = adaptor.getMediaItemsForUser(null, username);
		}
		else
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		Utilities.cleanDuplicates(result.getMediaItems());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public MediaItemsContainer getMediaItemsForPage(@WebParam(name = "pageId") ObjectId pageId)
	{
		long start = System.currentTimeMillis();
		MediaItemsContainer result = new MediaItemsContainer();
		if (!Utilities.clean(pageId))
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(pageId);
		result = adaptor.getMediaItemsForPage(pageId);
		Utilities.cleanDuplicates(result.getMediaItems());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public MediaItemsContainer findMediaItems(@WebParam(name = "mediaFilter") MediaItemFilter mediaFilter)
	{
		long start = System.currentTimeMillis();
		MediaItemsContainer result = new MediaItemsContainer();
		if (mediaFilter == null)
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		List<SocialNetwork> sns = mediaFilter.getSns();
		if (!Utilities.isValid(sns))
		{
			sns = AdaptorUtilities.getSocialNetworks();
		}
		for (SocialNetwork sn : sns)
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
			MediaItemsContainer myMediaItemsContainer = adaptor.findMediaItems(mediaFilter);
			ContainerUtilities.merge(result, myMediaItemsContainer);
		}
		Utilities.cleanDuplicates(result.getMediaItems());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public MediaItemsContainer findRelevantMediaItems(@WebParam(name = "mediaItemId") ObjectId mediaItemId)
	{
		long start = System.currentTimeMillis();
		MediaItemsContainer result = new MediaItemsContainer();
		if (!Utilities.clean(mediaItemId))
		{
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(mediaItemId);
		result = adaptor.findRelevantMediaItems(mediaItemId);
		Utilities.cleanDuplicates(result.getMediaItems());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public ActivitiesContainer getActivities(@WebParam(name = "objectIds") List<ObjectId> activityIds)
	{
		long start = System.currentTimeMillis();
		final ActivitiesContainer result = new ActivitiesContainer();
		if (!Utilities.cleanObjectIdList(activityIds))
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, null, null, null, 400);
		}
		List<List<ObjectId>> composite = AdaptorUtilities.getLists(activityIds);
		ExecutorService pool = Executors.newFixedThreadPool(composite.size());
		for (final List<ObjectId> lista : composite)
		{
			final ObjectId temp = lista.get(0);
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(temp);
					ActivitiesContainer myActivitiesContainer = adaptor.getActivities(lista);
					ContainerUtilities.merge(result, myActivitiesContainer);
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
		Utilities.cleanDuplicates(result.getActivities());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public ActivitiesContainer getActivitiesForUser(@WebParam(name = "personId") ObjectId personId)
	{
		long start = System.currentTimeMillis();
		ActivitiesContainer result = new ActivitiesContainer();
		if (!Utilities.clean(personId))
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(personId);
		result = adaptor.getActivitiesForUser(personId);
		Utilities.cleanDuplicates(result.getActivities());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public ActivitiesContainer findActivities(@WebParam(name = "activityFilter") ActivityFilter activityFilter)
	{
		long start = System.currentTimeMillis();
		ActivitiesContainer result = new ActivitiesContainer();
		if (!FilterUtilities.isValid(activityFilter))
		{
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, null, null, null, 400);
		}
		List<SocialNetwork> sns = activityFilter.getSns();
		if (!Utilities.isValid(sns))
		{
			sns = AdaptorUtilities.getSocialNetworks();
		}
		for (SocialNetwork sn : sns)
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
			ActivitiesContainer myActivitiesContainer = adaptor.findActivities(activityFilter);
			ContainerUtilities.merge(result, myActivitiesContainer);
		}
		Utilities.cleanDuplicates(result.getActivities());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public CommentsContainer getComments(@WebParam(name = "objectIds") List<ObjectId> commentIds)
	{
		long start = System.currentTimeMillis();
		final CommentsContainer result = new CommentsContainer();
		if (!Utilities.cleanObjectIdList(commentIds))
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		List<List<ObjectId>> composite = AdaptorUtilities.getLists(commentIds);
		ExecutorService pool = Executors.newFixedThreadPool(composite.size());
		for (final List<ObjectId> lista : composite)
		{
			final ObjectId temp = lista.get(0);
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(temp);
					CommentsContainer commentsContainer = adaptor.getComments(lista);
					ContainerUtilities.merge(result, commentsContainer);
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
		Utilities.cleanDuplicates(result.getComments());
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public CommentsContainer getCommentsForMediaItem(@WebParam(name = "mediaItemId") ObjectId mediaItemId)
	{
		long start = System.currentTimeMillis();
		CommentsContainer result = new CommentsContainer();
		if (!Utilities.clean(mediaItemId))
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(mediaItemId);
		result = adaptor.getCommentsForMediaItem(mediaItemId);
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public CommentsContainer getCommentsForActivity(@WebParam(name = "activityId") ObjectId activityId)
	{
		long start = System.currentTimeMillis();
		CommentsContainer result = new CommentsContainer();
		if (!Utilities.clean(activityId))
		{
			return ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(activityId);
		result = adaptor.getCommentsForActivity(activityId);
		long end = System.currentTimeMillis();
		long timeElapsed = end - start;
		result.setElapsedTime(String.valueOf(timeElapsed));
		return result;
	}

	public ObjectIdContainer postMessage(@WebParam(name = "personId") ObjectId personId, @WebParam(name = "postText") String postText,
			@WebParam(name = "accessToken") String accessToken, @WebParam(name = "accessSecret") String accessSecret)
	{
		ObjectIdContainer result = new ObjectIdContainer();
		if (!Utilities.isValid(postText) || !Utilities.isValid(accessToken) || personId == null || personId.getSocialNetwork() == null)
		{
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, null, null, null, 400);
		}
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(personId);
		result = adaptor.postMessage(personId, postText, accessToken, accessSecret);
		return result;
	}
}
