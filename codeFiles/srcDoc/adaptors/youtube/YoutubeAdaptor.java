package adaptors.youtube;

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
import objects.enums.LicenseType;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.filters.ActivityFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;

public class YoutubeAdaptor implements ISnsAdaptor {
	private static SocialNetwork sn = SocialNetwork.YOUTUBE;

	public YoutubeAdaptor() {
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
					PersonsContainer person = YoutubeCalls.getPerson(id);
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
		return YoutubeCalls.getSubscribers(id);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret) {
		String id = personId.getId();
		String channelId = Utilities.getChannelId(id);
		return YoutubeCalls.getMyConnectedPersons(channelId, accessToken);
	}

	@Override
	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username) {
		PersonsContainer result = new PersonsContainer();
		if (personFilter != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, null, SociosConstants.ERROR_501);
		}
		else if (mediaItemId != null) {
			String mediaId = mediaItemId.getId();
			result = YoutubeCalls.getMediaItemOwner(mediaId);
		}
		else if (activityId != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), SociosConstants.ERROR_501);
		}
		else if (username != null) {
			String name = username.getId();
			result = YoutubeCalls.getPerson(name);
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
					MediaItemsContainer mediaItem = YoutubeCalls.getMediaItem(id);
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
		String identifier;
		if (personId != null) {
			identifier = personId.getId();
		}
		else {
			identifier = username.getId();
		}
		return YoutubeCalls.getMediaItemsForUser(identifier);
	}

	@Override
	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId) {
		return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, null, pageId.getId(), SociosConstants.ERROR_501);
	}

	@Override
	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter) {
		MediaItemsContainer result = new MediaItemsContainer();
		List<String> queries = new ArrayList<String>();
		String language = FilterUtilities.getLanguage(mediaFilter);
		if (Utilities.isValid(language) && Utilities.isValidLanguageCode(language)) {
			String languageParam = "lr=" + language;
			queries.add(languageParam);
		}
		List<String> keywords = FilterUtilities.getKeywords(mediaFilter);
		if (keywords != null) {
			String keywordChain = Utilities.getChain(keywords);
			String searchParam = "q=" + keywordChain;
			queries.add(searchParam);
		}
		LicenseType licenseType = mediaFilter.getLicenseType();
		if (licenseType != null && licenseType.equals(LicenseType.CC)) {
			String licenseParam = "license=cc";
			queries.add(licenseParam);
		}
		String chain = Utilities.getChain(queries, "&");
		if (chain != null) {
			MediaItemsContainer mediaItemsContainer = YoutubeCalls.searchMediaItems(chain);
			ContainerUtilities.merge(result, mediaItemsContainer);
		}
		if (queries.isEmpty()) {
			String country = FilterUtilities.getCountry(mediaFilter);
			String countryCode = Utilities.getCountryCode(country);
			if (countryCode != null) {
				MediaItemsContainer mediaItemsContainer = YoutubeCalls.getPopularMediaItems(countryCode);
				ContainerUtilities.merge(result, mediaItemsContainer);
			}
		}
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId) {
		String id = mediaItemId.getId();
		return YoutubeCalls.getRelatedMediaItems(id);
	}

	@Override
	public ActivitiesContainer getActivities(List<ObjectId> objectIds) {
		return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, null, null, SociosConstants.ERROR_501);
	}

	@Override
	public ActivitiesContainer getActivitiesForUser(ObjectId personId) {
		String id = personId.getId();
		String channelId = Utilities.getChannelId(id);
		return YoutubeCalls.getActivitiesForUser(channelId);
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
		return YoutubeCalls.getCommentsForMediaItem(id);
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
