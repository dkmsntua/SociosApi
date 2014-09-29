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

public interface ISnsAdaptor
{
	public PersonsContainer connectedPersons(ObjectId personId);

	public ActivitiesContainer findActivities(ActivityFilter activityFilter);

	public MediaItemsContainer findMediaItems(MediaItemFilter mediaFilter);

	public PersonsContainer findPersons(PersonFilter personFilter, ObjectId mediaItemId, ObjectId activityId, ObjectId username);

	public MediaItemsContainer findRelevantMediaItems(ObjectId mediaItemId);

	public ActivitiesContainer getActivities(List<ObjectId> activityIds);

	public ActivitiesContainer getActivitiesForUser(ObjectId personId);

	public CommentsContainer getComments(List<ObjectId> objectIds);

	public CommentsContainer getCommentsForActivity(ObjectId activityId);

	public CommentsContainer getCommentsForMediaItem(ObjectId mediaItemId);

	public MediaItemsContainer getMediaItems(List<ObjectId> objectIds);

	public MediaItemsContainer getMediaItemsForPage(ObjectId pageId);

	public MediaItemsContainer getMediaItemsForUser(ObjectId personId, ObjectId username);

	public PersonsContainer getPersons(List<ObjectId> objectIds);

	public PersonsContainer myConnectedPersons(ObjectId personId, String accessToken, String accessSecret);

	public ObjectIdContainer postMessage(ObjectId personId, String postText, String accessToken, String accessSecret);

	public String postMessageWithPhoto(String postText, String fileName, String fileData, String accessToken, String accessSecret);
}
