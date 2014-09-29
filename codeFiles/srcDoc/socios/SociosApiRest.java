package socios;

import helper.misc.SociosConstants;
import helper.utilities.AdaptorUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.FilterUtilities;
import helper.utilities.Utilities;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import javax.ejb.Stateless;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
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
import objects.main.Activity;
import objects.main.MediaItem;
import objects.main.ObjectId;
import objects.main.Person;
import com.sun.org.glassfish.gmbal.Description;

@Path("/socios")
@Stateless
public class SociosApiRest
{
	@GET
	@Path("hello")
	@Description("this is a cool method")
	public String hello()
	{
		return "hello!!";
	}

	@GET
	@Path("/getPerson")
	public Response getPerson(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			List<ObjectId> objectIds = Utilities.getObjectIds(objectId);
			personsContainer = adaptor.getPersons(objectIds);
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/connectedPersons")
	public Response connectedPersons(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			personsContainer = adaptor.connectedPersons(objectId);
			Utilities.cleanDuplicates(personsContainer.getPersons());
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/myConnectedPersons")
	public Response myConnectedPersons(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format,
			@QueryParam("accessToken") String accessToken, @QueryParam("accessSecret") String accessSecret)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			personsContainer = adaptor.myConnectedPersons(objectId, accessToken, accessSecret);
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/findPersonsByKeyword")
	public Response findPersonsByKeyword(@QueryParam("keywords") String keywords, @QueryParam("sns") String sns, @QueryParam("format") String format)
	{
		if (!Utilities.isValid(keywords))
		{
			PersonsContainer personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
			Response result = AdaptorUtilities.getResponse(personsContainer, format);
			return result;
		}
		List<SocialNetwork> networks = AdaptorUtilities.getSocialNetworks(sns);
		final PersonFilter personFilter = FilterUtilities.getPersonFilter(keywords);
		final PersonsContainer personsContainer = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		for (final SocialNetwork sn : networks)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
					PersonsContainer myPersonContainer = adaptor.findPersons(personFilter, null, null, null);
					List<Person> persons = myPersonContainer.getPersons();
					if (Utilities.isValid(persons))
					{
						personsContainer.getPersons().addAll(persons);
					}
					return;
				}
			});
		}
		pool.shutdown();
		try
		{
			pool.awaitTermination(180, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/findPersonsByUsername")
	public Response findPersonsByUsername(@QueryParam("username") String username, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(username, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			personsContainer = adaptor.findPersons(null, null, null, objectId);
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/findPersonsByMediaItem")
	public Response findPersonsByMediaItem(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			personsContainer = adaptor.findPersons(null, objectId, null, null);
			Utilities.cleanDuplicates(personsContainer.getPersons());
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/findPersonsByActivity")
	public Response findPersonsByActivity(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		PersonsContainer personsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			personsContainer = ExceptionsUtilities.getException(SociosObject.PERSON, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			personsContainer = adaptor.findPersons(null, null, objectId, null);
		}
		Response result = AdaptorUtilities.getResponse(personsContainer, format);
		return result;
	}

	@GET
	@Path("/getMediaItem")
	public Response getMediaItem(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		MediaItemsContainer mediaItemsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			mediaItemsContainer = ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			List<ObjectId> objectIds = Utilities.getObjectIds(objectId);
			mediaItemsContainer = adaptor.getMediaItems(objectIds);
		}
		Response result = AdaptorUtilities.getResponse(mediaItemsContainer, format);
		return result;
	}

	@GET
	@Path("/getMediaItemsForUser")
	public Response getMediaItemsForUser(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("username") String username,
			@QueryParam("format") String format)
	{
		MediaItemsContainer mediaItemsContainer = null;
		if (!Utilities.isValid(sn))
		{
			mediaItemsContainer = ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		SocialNetwork network = AdaptorUtilities.getSocialNetwork(sn);
		if (network == null)
		{
			mediaItemsContainer = ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(network);
			ObjectId objectId = new ObjectId();
			objectId.setId(id);
			objectId.setSocialNetwork(network);
			ObjectId usernameId = new ObjectId();
			usernameId.setId(username);
			mediaItemsContainer = adaptor.getMediaItemsForUser(objectId, usernameId);
			Utilities.cleanDuplicates(mediaItemsContainer.getMediaItems());
		}
		Response result = AdaptorUtilities.getResponse(mediaItemsContainer, format);
		return result;
	}

	@GET
	@Path("/getMediaItemsForPage")
	public Response getMediaItemsForPage(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		MediaItemsContainer mediaItemsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			mediaItemsContainer = ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			mediaItemsContainer = adaptor.getMediaItemsForPage(objectId);
		}
		Response result = AdaptorUtilities.getResponse(mediaItemsContainer, format);
		return result;
	}

	@GET
	@Path("/findMediaItems")
	public Response findMediaItems(@QueryParam("from") String from, @QueryParam("to") String to, @QueryParam("keywords") String keywords,
			@QueryParam("country") String country, @QueryParam("lat") String lat, @QueryParam("lon") String lon, @QueryParam("rad") String rad,
			@QueryParam("lang") String lang, @QueryParam("lic") String lic, @QueryParam("sns") String sns, @QueryParam("format") String format)
	{
		final MediaItemFilter mediaItemFilter = FilterUtilities.getMediaItemFilter(from, to, keywords, country, lat, lon, rad, lang, lic);
		List<SocialNetwork> networks = AdaptorUtilities.getSocialNetworks(sns);
		final MediaItemsContainer mediaItemsContainer = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		for (final SocialNetwork sn : networks)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
					MediaItemsContainer myMediaItemsContainer = adaptor.findMediaItems(mediaItemFilter);
					List<MediaItem> mediaItems = myMediaItemsContainer.getMediaItems();
					if (mediaItems != null)
					{
						mediaItemsContainer.getMediaItems().addAll(mediaItems);
					}
					return;
				}
			});
		}
		pool.shutdown();
		try
		{
			pool.awaitTermination(180, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		Response result = AdaptorUtilities.getResponse(mediaItemsContainer, format);
		return result;
	}

	@GET
	@Path("/findRelevantMediaItems")
	public Response findRelevantMediaItems(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		MediaItemsContainer mediaItemsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			mediaItemsContainer = ExceptionsUtilities.getException(SociosObject.MEDIAITEM, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			mediaItemsContainer = adaptor.findRelevantMediaItems(objectId);
			Utilities.cleanDuplicates(mediaItemsContainer.getMediaItems());
		}
		Response result = AdaptorUtilities.getResponse(mediaItemsContainer, format);
		return result;
	}

	@GET
	@Path("/getActivity")
	public Response getActivity(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		ActivitiesContainer activitiesContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			activitiesContainer = ExceptionsUtilities.getException(SociosObject.ACTIVITY, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			List<ObjectId> objectIds = Utilities.getObjectIds(objectId);
			activitiesContainer = adaptor.getActivities(objectIds);
		}
		Response result = AdaptorUtilities.getResponse(activitiesContainer, format);
		return result;
	}

	@GET
	@Path("/getActivitiesForUser")
	public Response getActivitiesForUser(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		ActivitiesContainer activitiesContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			activitiesContainer = ExceptionsUtilities.getException(SociosObject.ACTIVITY, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			activitiesContainer = adaptor.getActivitiesForUser(objectId);
		}
		Response result = AdaptorUtilities.getResponse(activitiesContainer, format);
		return result;
	}

	@GET
	@Path("/findActivities")
	public Response findActivities(@QueryParam("keywords") String keywords, @QueryParam("lang") String lang, @QueryParam("sns") String sns,
			@QueryParam("format") String format)
	{
		final ActivitiesContainer activitiesContainer = new ActivitiesContainer();
		final ActivityFilter activityFilter = FilterUtilities.getActivityFilter(keywords, lang);
		List<SocialNetwork> networks = AdaptorUtilities.getSocialNetworks(sns);
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		for (final SocialNetwork sn : networks)
		{
			pool.submit(new Runnable()
			{
				@Override
				public void run()
				{
					ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(sn);
					ActivitiesContainer myActivitiesContainer = adaptor.findActivities(activityFilter);
					List<Activity> activities = myActivitiesContainer.getActivities();
					if (activities != null)
					{
						activitiesContainer.getActivities().addAll(activities);
					}
					return;
				}
			});
		}
		pool.shutdown();
		try
		{
			pool.awaitTermination(180, TimeUnit.SECONDS);
		}
		catch (InterruptedException e)
		{
			e.printStackTrace();
		}
		pool.shutdownNow();
		Response result = AdaptorUtilities.getResponse(activitiesContainer, format);
		return result;
	}

	@GET
	@Path("/getComment")
	public Response getComment(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		CommentsContainer commentsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			commentsContainer = ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			List<ObjectId> objectIds = Utilities.getObjectIds(objectId);
			commentsContainer = adaptor.getComments(objectIds);
		}
		Response result = AdaptorUtilities.getResponse(commentsContainer, format);
		return result;
	}

	@GET
	@Path("/getCommentsForMediaItem")
	public Response getCommentsForMediaItem(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		CommentsContainer commentsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			commentsContainer = ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			commentsContainer = adaptor.getCommentsForMediaItem(objectId);
		}
		Response result = AdaptorUtilities.getResponse(commentsContainer, format);
		return result;
	}

	@GET
	@Path("/getCommentsForActivity")
	public Response getCommentsForActivity(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("format") String format)
	{
		CommentsContainer commentsContainer = null;
		ObjectId objectId = Utilities.getObjectId(id, sn);
		if (!Utilities.clean(objectId))
		{
			commentsContainer = ExceptionsUtilities.getException(SociosObject.COMMENT, null, null, null, 400);
		}
		else
		{
			ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
			commentsContainer = adaptor.getCommentsForActivity(objectId);
		}
		Response result = AdaptorUtilities.getResponse(commentsContainer, format);
		return result;
	}

	@GET
	@Path("/postMessage")
	public Response postMessage(@QueryParam("id") String id, @QueryParam("sn") String sn, @QueryParam("msg") String msg, @QueryParam("format") String format,
			@QueryParam("accessToken") String accessToken, @QueryParam("accessSecret") String accessSecret)
	{
		ObjectIdContainer postMessageContainer = null;
		if (!Utilities.isValid(msg) || !Utilities.isValid(accessToken) || !Utilities.isValid(sn))
		{
			postMessageContainer = ExceptionsUtilities.getException(SociosObject.OBJECTID, null, null, null, 400);
		}
		ObjectId objectId = new ObjectId();
		objectId.setId(id);
		objectId.setSocialNetwork(AdaptorUtilities.getSocialNetwork(sn));
		ISnsAdaptor adaptor = AdaptorUtilities.getAdaptor(objectId);
		postMessageContainer = adaptor.postMessage(objectId, msg, accessToken, accessSecret);
		Response result = AdaptorUtilities.getResponse(postMessageContainer, format);
		return result;
	}
}
