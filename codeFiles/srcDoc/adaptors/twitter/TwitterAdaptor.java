package adaptors.twitter;

import helper.misc.SociosConstants;
import helper.utilities.ContainerUtilities;
import helper.utilities.ExceptionsUtilities;
import helper.utilities.FilterUtilities;
import helper.utilities.Utilities;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import objects.filters.DateTimeFilter;
import objects.filters.LocationFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;

public class TwitterAdaptor implements ISnsAdaptor {
	private static SocialNetwork sn = SocialNetwork.TWITTER;

	public TwitterAdaptor() {
	}

	@Override
	public PersonsContainer getPersons(List<ObjectId> objectIds) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					PersonsContainer person = TwitterCalls.getPersonById(id);
					ContainerUtilities.merge(result, person);
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdownNow();
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId) {
		String id = personId.getId();
		return TwitterCalls.getConnectedPersons(id);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret) {
		return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, personId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username) {
		PersonsContainer result = new PersonsContainer();
		if (personFilter != null) {
			List<String> keywords = FilterUtilities.getKeywords(personFilter);
			String chain = Utilities.getChain(keywords);
			result = TwitterCalls.searchAuthPersons(chain);
		}
		else if (mediaItemId != null) {
			String mediaId = mediaItemId.getId();
			result = TwitterCalls.getPersonForMediaItem(mediaId);
		}
		else if (activityId != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), SociosConstants.ERROR_501);
		}
		else if (username != null) {
			String name = username.getId();
			result = TwitterCalls.getPersonByUsername(name);
		}
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItems(List<ObjectId> objectIds) {
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					MediaItemsContainer mediaItem = TwitterCalls.getMediaItem(id);
					ContainerUtilities.merge(result, mediaItem);
				}
			});
		}
		pool.shutdown();
		try {
			pool.awaitTermination(SociosConstants.timeOut, TimeUnit.SECONDS);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		pool.shutdownNow();
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForUser(ObjectId personId, ObjectId username) {
		MediaItemsContainer result;
		if (personId != null) {
			String id = personId.getId();
			result = TwitterCalls.getMediaItemsForUser(id, "id");
		}
		else {
			String id = username.getId();
			result = TwitterCalls.getMediaItemsForUser(id, "screen_name");
		}
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, pageId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter) {
		List<String> queries = FilterUtilities.getKeywords(mediaFilter);
		String query = "";
		if (queries != null) {
			String chain = Utilities.getChain(queries);
			try {
				chain = URLEncoder.encode(chain, SociosConstants.UTF8);
			}
			catch (UnsupportedEncodingException exc) {
				System.out.println(exc.getMessage());
			}
			query = "q=" + chain;
		}
		String language = FilterUtilities.getLanguage(mediaFilter);
		if (language != null && Utilities.isValidLanguageCode(language)) {
			if (!query.isEmpty()) {
				query += "&";
			}
			else {
				query += "q=\"\"&";
			}
			query += "lang=" + language;
		}
		LocationFilter location = mediaFilter.getLocation();
		String geocode;
		if (location != null && location.getAreaFilter() != null) {
			Double latitude = location.getAreaFilter().getLatitude();
			Double longitude = location.getAreaFilter().getLongitude();
			Double radius = location.getAreaFilter().getRadius();
			if (longitude != null && latitude != null && latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= 180) {
				if (radius == null) {
					radius = 10d;
				}
				geocode = latitude + "," + longitude + "," + radius + "km";
				if (!query.isEmpty()) {
					query += "&";
				}
				else {
					query += "q=\"\"&";
				}
				query += "geocode=" + geocode;
			}
		}
		DateTimeFilter dateFilter = mediaFilter.getCreated();
		if (dateFilter != null) {
			XMLGregorianCalendar xmlCal = dateFilter.getTo();
			if (xmlCal != null) {
				Date dt = xmlCal.toGregorianCalendar().getTime();
				SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
				String until = format.format(dt);
				if (!query.isEmpty()) {
					query += "&until=" + until;
				}
				// de dexetai query mono me "until", prepei na exei kai kati
				// allo oposdipote
				// oute kan to q=""&until="asdfasdf"
			}
		}
		MediaItemsContainer result = new MediaItemsContainer();
		if (!query.isEmpty()) {
			result = TwitterCalls.searchMediaItems(query);
		}
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId) {
		String id = mediaItemId.getId();
		return TwitterCalls.getRetweets(id);
	}

	@Override
	public ActivitiesContainer getActivities(List<ObjectId> activityIds) {
		return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public ActivitiesContainer getActivitiesForUser(ObjectId personId) {
		return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, null, personId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public ActivitiesContainer findActivities(ActivityFilter activityFilter) {
		return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public CommentsContainer getComments(List<ObjectId> objectIds) {
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public CommentsContainer getCommentsForMediaItem(ObjectId mediaItemId) {
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, mediaItemId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public CommentsContainer getCommentsForActivity(ObjectId activityId) {
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, activityId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public ObjectIdContainer postMessage(ObjectId personId, String postText, String accessToken, String accessSecret) {
		if (!Utilities.isValid(accessSecret)) {
			return ExceptionsUtilities.getException(SociosObject.OBJECTID, null, null, null, SociosConstants.ERROR_400);
		}
		return TwitterCalls.postMessage(postText, accessToken, accessSecret);
	}

	@Override
	public String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret) {
		return TwitterCalls.postMessageWithPhoto(postText, fileName, fileData, accessToken, accessSecret);
	}
}
