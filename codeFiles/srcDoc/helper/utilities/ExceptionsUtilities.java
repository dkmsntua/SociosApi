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
package helper.utilities;

import helper.misc.SociosConstants;
import java.util.HashMap;
import java.util.Map;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.enums.SocialNetwork;
import objects.enums.SociosObject;
import objects.main.SociosException;

public class ExceptionsUtilities {
	private static String exc400 = "No valid input was detected.";
	private static String exc500 = "Something went wrong.";
	private static String exc501 = "This method is not implemented for this particular social network.";
	private static Map<Integer, String> map;
	static {
		map = new HashMap<Integer, String>();
		map.put(SociosConstants.ERROR_400, getExc400());
		map.put(SociosConstants.ERROR_500, getExc500());
		map.put(SociosConstants.ERROR_501, getExc501());
	}

	@SuppressWarnings("unchecked")
	public static <T> T getNativeException(SociosObject type, SociosException se, String id) {
		se.setActorId(id);
		if (type == SociosObject.PERSON) {
			PersonsContainer result = new PersonsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.MEDIAITEM) {
			MediaItemsContainer result = new MediaItemsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.ACTIVITY) {
			ActivitiesContainer result = new ActivitiesContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.COMMENT) {
			CommentsContainer result = new CommentsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (type == SociosObject.OBJECTID) {
			ObjectIdContainer result = new ObjectIdContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		return null;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getException(SociosObject objectType, SocialNetwork sn, String excMessage, String actorId, int code) {
		SociosException se = new SociosException(map.get(code), excMessage, code, actorId, sn);
		if (objectType == SociosObject.PERSON) {
			PersonsContainer result = new PersonsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.MEDIAITEM) {
			MediaItemsContainer result = new MediaItemsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.ACTIVITY) {
			ActivitiesContainer result = new ActivitiesContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.COMMENT) {
			CommentsContainer result = new CommentsContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		else if (objectType == SociosObject.OBJECTID) {
			ObjectIdContainer result = new ObjectIdContainer();
			result.getExceptions().add(se);
			return (T) result;
		}
		return null;
	}

	public static String getExc501() {
		return exc501;
	}

	public static void setExc501(String exc501) {
		ExceptionsUtilities.exc501 = exc501;
	}

	public static String getExc500() {
		return exc500;
	}

	public static void setExc500(String exc500) {
		ExceptionsUtilities.exc500 = exc500;
	}

	public static String getExc400() {
		return exc400;
	}

	public static void setExc400(String exc400) {
		ExceptionsUtilities.exc400 = exc400;
	}
}
