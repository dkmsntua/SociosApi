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
package adaptors.youtube;

import helper.misc.SociosConstants;
import helper.utilities.ExceptionsUtilities;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Person;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class YoutubeFetchers {
	private static SocialNetwork sn = SocialNetwork.YOUTUBE;

	public static PersonsContainer fetchPerson(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsperson = json.optJSONObject("entry");
			Person person = YoutubeParsers.parsePerson(jsperson);
			result.getPersons().add(person);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static PersonsContainer fetchPersonsFromChannel(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsperson = array.optJSONObject(index);
				Person person = YoutubeParsers.parseChannel(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static PersonsContainer fetchPersonsFromChannelV2(String response, String id) {
		PersonsContainer result = new PersonsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsfeed = json.optJSONObject("feed");
			JSONArray array = jsfeed.optJSONArray("entry");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsperson = array.optJSONObject(index);
				Person person = YoutubeParsers.parseChannelV2(jsperson);
				result.getPersons().add(person);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.PERSON, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItem(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject jsmediaItem = json.optJSONObject("entry");
			MediaItem mediaItem = YoutubeParsers.parseMediaItemV2(jsmediaItem);
			result.getMediaItems().add(mediaItem);
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItems(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = YoutubeParsers.parseMediaItemV3(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static MediaItemsContainer fetchMediaItemsV2(String response, String id) {
		MediaItemsContainer result = new MediaItemsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject feed = json.optJSONObject("feed");
			JSONArray array = feed.optJSONArray("entry");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsmediaItem = array.optJSONObject(index);
				MediaItem mediaItem = YoutubeParsers.parseMediaItemV2(jsmediaItem);
				result.getMediaItems().add(mediaItem);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.MEDIAITEM, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static ActivitiesContainer fetchActivities(String response, String id) {
		ActivitiesContainer result = new ActivitiesContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONArray array = json.optJSONArray("items");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jsactivity = array.getJSONObject(index);
				Activity activity = YoutubeParsers.parseActivity(jsactivity);
				result.getActivities().add(activity);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.ACTIVITY, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}

	public static CommentsContainer fetchComments(String response, String id) {
		CommentsContainer result = new CommentsContainer();
		try {
			JSONObject json = new JSONObject(response);
			JSONObject feed = json.optJSONObject("feed");
			JSONArray array = feed.optJSONArray("entry");
			for (int index = 0; index < array.length(); index++) {
				JSONObject jscomment = array.optJSONObject(index);
				Comment comment = YoutubeParsers.parseComment(jscomment);
				result.getComments().add(comment);
			}
		}
		catch (JSONException exc) {
			return ExceptionsUtilities.getException(SociosObject.COMMENT, sn, exc.getMessage() + " ==> " + response, id, SociosConstants.ERROR_500);
		}
		return result;
	}
}
