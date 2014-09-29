package adaptors.dailymotion;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.FilterUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.xml.datatype.XMLGregorianCalendar;
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

public class DailymotionAdaptor implements ISnsAdaptor
{
	private static SocialNetwork sn = SocialNetwork.DAILYMOTION;

	@Override
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
					PersonsContainer person = DailymotionCalls.getPerson(id);
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

	@Override
	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username)
	{
		PersonsContainer result = new PersonsContainer();
		if (personFilter != null)
		{
			List<String> keywords = FilterUtilities.getKeywords(personFilter);
			String chain = Utilities.getChain(keywords);
			result = DailymotionCalls.searchPersons(chain);
		}
		else if (mediaItemId != null)
		{
			String id = mediaItemId.getId();
			result = DailymotionCalls.getMediaItemOwner(id);
		}
		else if (username != null)
		{
			String name = username.getId();
			result = DailymotionCalls.getPerson(name);
		}
		else if (activityId != null)
		{
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), 501);
		}
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId)
	{
		String id = personId.getId();
		PersonsContainer result = DailymotionCalls.getConnectedPersons(id);
		return result;
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret)
	{
		return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, personId.getId(), 501);
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
					MediaItemsContainer mediaItem = DailymotionCalls.getMediaItem(id);
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
		MediaItemsContainer result = null;
		String id = null;
		if (personId != null)
		{
			id = personId.getId();
		}
		else if (username != null)
		{
			id = username.getId();
		}
		result = DailymotionCalls.getMediaItemsForUser(id);
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId)
	{
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, pageId.getId(), 501);
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter)
	{
		List<String> queries = new ArrayList<String>();
		String language = FilterUtilities.getLanguage(mediaFilter);
		if (Utilities.isValid(language))
		{
			if (Utilities.isValidLanguageCode(language))
			{
				String languageParam = "language=" + language;
				queries.add(languageParam);
			}
		}
		String country = FilterUtilities.getCountry(mediaFilter);
		if (Utilities.isValid(country))
		{
			String countryCode = Utilities.getCountryCode(country);
			String countryParam = "country=" + countryCode;
			queries.add(countryParam);
		}
		List<String> keywords = FilterUtilities.getKeywords(mediaFilter);
		if (keywords != null)
		{
			String keywordChain = Utilities.getChain(keywords);
			String searchParam = "search=" + keywordChain;
			queries.add(searchParam);
		}
		XMLGregorianCalendar from = FilterUtilities.getFrom(mediaFilter);
		if (from != null)
		{
			long timestamp = from.toGregorianCalendar().getTimeInMillis() / 1000;
			String fromParam = "created_after=" + timestamp;
			queries.add(fromParam);
		}
		XMLGregorianCalendar to = FilterUtilities.getTo(mediaFilter);
		if (to != null)
		{
			long timestamp = to.toGregorianCalendar().getTimeInMillis() / 1000;
			String toParam = "created_before=" + timestamp;
			queries.add(toParam);
		}
		String chain = Utilities.getChain(queries, "&");
		if (Utilities.isValid(chain))
		{
			chain += "&";
		}
		else
		{
			chain = "";
		}
		MediaItemsContainer result = DailymotionCalls.searchMediaItems(chain);
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId)
	{
		String id = mediaItemId.getId();
		MediaItemsContainer result = DailymotionCalls.findRelevantMediaItems(id);
		return result;
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
					ActivitiesContainer mediaItem = DailymotionCalls.getActivity(id);
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
		ActivitiesContainer result = DailymotionCalls.getActivitiesForUser(id);
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
					CommentsContainer activity = DailymotionCalls.getComment(id);
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
		CommentsContainer result = DailymotionCalls.getCommentsForMediaItem(id);
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
		return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, null, null, 501);
	}

	@Override
	public String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret)
	{
		return ExceptionsUtilities.exc501;
	}
}
