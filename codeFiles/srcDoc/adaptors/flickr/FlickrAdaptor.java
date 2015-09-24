/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package adaptors.flickr;

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
import objects.enums.LicenseType;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.filters.ActivityFilter;
import objects.filters.LocationFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;

public class FlickrAdaptor implements ISnsAdaptor {
	private static SocialNetwork sn = SocialNetwork.FLICKR;

	public FlickrAdaptor() {
	}

	public PersonsContainer getPersons(List<ObjectId> objectIds) {
		final PersonsContainer result = new PersonsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					PersonsContainer person = FlickrCalls.getPerson(id);
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
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, null, SociosConstants.ERROR_501);
		}
		else if (mediaItemId != null) {
			String id = mediaItemId.getId();
			result = FlickrCalls.getRelevantPersons(id);
		}
		else if (activityId != null) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, null, activityId.getId(), SociosConstants.ERROR_501);
		}
		else if (username != null) {
			String name = username.getId();
			result = FlickrCalls.getPersonByUserName(name);
		}
		return result;
	}

	@Override
	public PersonsContainer connectedPersons(ObjectId personId) {
		String id = personId.getId();
		return FlickrCalls.getConnectedPersons(id);
	}

	@Override
	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret) {
		return FlickrCalls.getMyConnectedPersons(accessToken, accessSecret);
	}

	public MediaItemsContainer getMediaItems(List<ObjectId> objectIds) {
		final MediaItemsContainer result = new MediaItemsContainer();
		ExecutorService pool = Executors.newFixedThreadPool(SociosConstants.threads);
		List<String> ids = Utilities.getStringList(objectIds);
		for (final String id : ids) {
			pool.submit(new Runnable() {
				@Override
				public void run() {
					MediaItemsContainer mediaItem = FlickrCalls.getMediaItem(id);
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
			result = FlickrCalls.getMediaItemsForUser(id);
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
		List<String> queries = new ArrayList<String>();
		List<String> keywords = FilterUtilities.getKeywords(mediaFilter);
		if (keywords != null) {
			String tagsParam = "tags=" + Utilities.getChain(keywords);
			queries.add(tagsParam);
		}
		LicenseType licenseType = mediaFilter.getLicenseType();
		if (licenseType != null && licenseType.equals(LicenseType.CC)) {
			String licenseParam = "license=1,2,3,4,5,6";
			queries.add(licenseParam);
		}
		LocationFilter location = mediaFilter.getLocation();
		if (location != null) {
			Double latitude = location.getAreaFilter().getLatitude();
			if (latitude != null && !latitude.toString().isEmpty()) {
				String latitudeParam = "lat=" + latitude;
				queries.add(latitudeParam);
			}
			Double longitude = location.getAreaFilter().getLongitude();
			if (longitude != null && !longitude.toString().isEmpty()) {
				String longitudeParam = "lon=" + longitude;
				queries.add(longitudeParam);
			}
			Double radius = location.getAreaFilter().getRadius();
			if (radius != null && !radius.toString().isEmpty()) {
				String radiusParam = "radius=" + radius;
				queries.add(radiusParam);
			}
		}
		XMLGregorianCalendar from = FilterUtilities.getFrom(mediaFilter);
		if (from != null) {
			long timestamp = from.toGregorianCalendar().getTimeInMillis() / SociosConstants.ONE_THOUSAND;
			String fromParam = "min_upload_date=" + timestamp;
			queries.add(fromParam);
		}
		XMLGregorianCalendar to = FilterUtilities.getTo(mediaFilter);
		if (to != null) {
			long timestamp = to.toGregorianCalendar().getTimeInMillis() / SociosConstants.ONE_THOUSAND;
			String toParam = "max_upload_date=" + timestamp;
			queries.add(toParam);
		}
		String chain = Utilities.getChain(queries, "&");
		MediaItemsContainer result;
		if (Utilities.isValid(chain)) {
			result = FlickrCalls.searchMediaItems(chain);
		}
		else {
			result = FlickrCalls.getRandomMedia();
		}
		return result;
	}

	@Override
	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId) {
		String id = mediaItemId.getId();
		return FlickrCalls.findRelevantMediaItems(id);
	}

	@Override
	public ActivitiesContainer getActivities(List<ObjectId> objectIds) {
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
		return FlickrCalls.getCommentsForMediaItem(id);
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
