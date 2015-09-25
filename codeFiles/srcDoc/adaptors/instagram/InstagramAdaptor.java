package adaptors.instagram;

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
import objects.filters.LocationFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;

public class InstagramAdaptor implements ISnsAdaptor {
	private static SocialNetwork sn = SocialNetwork.INSTAGRAM;

	public InstagramAdaptor() {
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
					PersonsContainer person = InstagramCalls.getPerson(id);
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
			result = InstagramCalls.searchPersons(chain);
		}
		else if (mediaItemId != null) {
			String mediaId = mediaItemId.getId();
			result = InstagramCalls.getRelevantPersons(mediaId);
		}
		else if (activityId != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), SociosConstants.ERROR_501);
		}
		else if (username != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, username.getId(), SociosConstants.ERROR_501);
		}
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId) {
		String id = personId.getId();
		return InstagramCalls.getConnectedPersons(id);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret) {
		return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, personId.getId(), SociosConstants.ERROR_501);
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
					MediaItemsContainer mediaItem = InstagramCalls.getMediaItem(id);
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
			result = InstagramCalls.findMyRecentMediaItems(id);
		}
		else {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, username.getId(), SociosConstants.ERROR_501);
		}
		return result;
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, pageId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter) {
		MediaItemsContainer result = new MediaItemsContainer();
		List<String> tags = FilterUtilities.getKeywords(mediaFilter);
		if (tags != null) {
			for (String tag : tags) {
				MediaItemsContainer myMediaItemsContainer = InstagramCalls.searchMediaItems(tag);
				ContainerUtilities.merge(result, myMediaItemsContainer);
			}
		}
		LocationFilter location = mediaFilter.getLocation();
		String locationQuery = "";
		if (location != null && location.getAreaFilter() != null) {
			Double latitude = location.getAreaFilter().getLatitude();
			Double longitude = location.getAreaFilter().getLongitude();
			Double radius = location.getAreaFilter().getRadius();
			if (longitude != null && latitude != null && latitude >= -90 && latitude <= 90 && longitude >= -180 && longitude <= SociosConstants.MAX_LONGITUDE) {
				locationQuery = "lat=" + latitude + "&lng=" + longitude;
				if (radius != null) {
					if (radius > 5) {
						radius = 5d;
					}
					locationQuery += "&distance=" + radius + "km";
				}
				MediaItemsContainer myMediaItemsContainer = InstagramCalls.searchMediaItemsByLocation(locationQuery);
				ContainerUtilities.merge(result, myMediaItemsContainer);
			}
		}
		if (tags == null && !Utilities.isValid(locationQuery)) {
			MediaItemsContainer myMediaItemsContainer = InstagramCalls.getPopularMediaItems();
			ContainerUtilities.merge(result, myMediaItemsContainer);
		}
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, mediaItemId.getId(), SociosConstants.ERROR_501);
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
		String id = mediaItemId.getId();
		return InstagramCalls.getCommentsForMediaItem(id);
	}

	@Override
	public CommentsContainer getCommentsForActivity(ObjectId activityId) {
		return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, null, activityId.getId(), SociosConstants.ERROR_501);
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
