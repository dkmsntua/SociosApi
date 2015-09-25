package adaptors.googlep;

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

public class GooglepAdaptor implements ISnsAdaptor {
	private static SocialNetwork sn = SocialNetwork.GOOGLEP;

	public GooglepAdaptor() {
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
					PersonsContainer person = GooglepCalls.getPerson(id);
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
	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username) {
		PersonsContainer result = new PersonsContainer();
		if (personFilter != null) {
			List<String> keywords = FilterUtilities.getKeywords(personFilter);
			String chain = Utilities.getChain(keywords);
			result = GooglepCalls.findPersons(chain);
		}
		else if (mediaItemId != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, mediaItemId.getId(), SociosConstants.ERROR_501);
		}
		else if (activityId != null) {
			String id = activityId.getId();
			result = GooglepCalls.getRelevantPersons(id);
		}
		else if (username != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, username.getId(), SociosConstants.ERROR_501);
		}
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId) {
		return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, personId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret) {
		String id = personId.getId();
		return GooglepCalls.getMyConnectedPersons(id, accessToken);
	}

	@Override
	public MediaItemsContainer getMediaItems(List<ObjectId> objectIds) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer getMediaItemsForUser(ObjectId personId, ObjectId username) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, pageId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, mediaItemId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public ActivitiesContainer getActivities(List<ObjectId> objectIds) {
		final ActivitiesContainer result = new ActivitiesContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					ActivitiesContainer mediaItem = GooglepCalls.getActivity(id);
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
	public ActivitiesContainer getActivitiesForUser(ObjectId personId) {
		String id = personId.getId();
		return GooglepCalls.getActivitiesForUser(id);
	}

	@Override
	public ActivitiesContainer findActivities(ActivityFilter activityFilter) {
		List<String> queries = new ArrayList<String>();
		List<String> keywords = FilterUtilities.getKeywords(activityFilter);
		if (keywords != null) {
			String search = Utilities.getChain(keywords);
			String searchParam = "query=" + search;
			queries.add(searchParam);
		}
		String language = FilterUtilities.getLanguage(activityFilter);
		if (language != null && Utilities.isValidLanguageCode(language)) {
			String languageParam = "language=" + language;
			queries.add(languageParam);
		}
		String chain = Utilities.getChain(queries, "&");
		return GooglepCalls.getActivities(chain);
	}

	@Override
	public CommentsContainer getComments(List<ObjectId> objectIds) {
		final CommentsContainer result = new CommentsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					CommentsContainer activity = GooglepCalls.getComment(id);
					ContainerUtilities.merge(result, activity);
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
	public CommentsContainer getCommentsForMediaItem(ObjectId mediaItemId) {
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, mediaItemId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public CommentsContainer getCommentsForActivity(ObjectId activityId) {
		String id = activityId.getId();
		return GooglepCalls.getCommentsForActivity(id);
	}

	@Override
	public ObjectIdContainer postMessage(ObjectId personId, String postText, String accessToken, String accessSecret) {
		return ExceptionsUtilities.getException(SociosObject.OBJECTID, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret) {
		return ExceptionsUtilities.getExc501();
	}
}
