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
package objects.interfaces;

import java.util.List;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.filters.ActivityFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;
import objects.main.ObjectId;

public interface ISnsAdaptor {
	PersonsContainer connectedPersons(ObjectId personId);

	ActivitiesContainer findActivities(ActivityFilter activityFilter);

	MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter);

	PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username);

	MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId);

	ActivitiesContainer getActivities(List<ObjectId> activityIds);

	ActivitiesContainer getActivitiesForUser(ObjectId personId);

	CommentsContainer getComments(List<ObjectId> objectIds);

	CommentsContainer getCommentsForActivity(ObjectId activityId);

	CommentsContainer getCommentsForMediaItem(ObjectId mediaItemId);

	MediaItemsContainer getMediaItems(List<ObjectId> objectIds);

	MediaItemsContainer getMediaItemsForPage(ObjectId pageId);

	MediaItemsContainer getMediaItemsForUser(ObjectId personId, ObjectId username);

	PersonsContainer getPersons(List<ObjectId> objectIds);

	PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret);

	ObjectIdContainer postMessage(ObjectId personId, String postText, String accessToken, String accessSecret);

	String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret);
}
