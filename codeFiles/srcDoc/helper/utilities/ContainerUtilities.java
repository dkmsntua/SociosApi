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

import java.util.List;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.PersonsContainer;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.Person;
import objects.main.SociosException;

public class ContainerUtilities {
	public static <T> void cleanExceptions(T container) {
		if (container instanceof PersonsContainer) {
			PersonsContainer pc = (PersonsContainer) container;
			if ((pc.getPersons() == null || pc.getPersons().isEmpty()) && (pc.getExceptions() != null && !pc.getExceptions().isEmpty())) {
				SociosException exc = pc.getExceptions().get(0);
				pc.getExceptions().clear();
				pc.getExceptions().add(exc);
				return;
			}
		}
		else if (container instanceof MediaItemsContainer) {
			MediaItemsContainer mc = (MediaItemsContainer) container;
			if ((mc.getMediaItems() == null || mc.getMediaItems().isEmpty()) && (mc.getExceptions() != null && !mc.getExceptions().isEmpty())) {
				return;
			}
		}
		else if (container instanceof ActivitiesContainer) {
			ActivitiesContainer ac = (ActivitiesContainer) container;
			if ((ac.getActivities() == null || ac.getActivities().isEmpty()) && (ac.getExceptions() != null && !ac.getExceptions().isEmpty())) {
				return;
			}
		}
		else if (container instanceof CommentsContainer) {
			CommentsContainer cc = (CommentsContainer) container;
			if ((cc.getComments() == null || cc.getComments().isEmpty()) && (cc.getExceptions() != null && !cc.getExceptions().isEmpty())) {
				return;
			}
		}
	}

	public static void merge(ActivitiesContainer result, ActivitiesContainer addon) {
		if (addon == null || result == null) {
			return;
		}
		List<Activity> activities = addon.getActivities();
		result.getActivities().addAll(activities);
		List<SociosException> exceptions = addon.getExceptions();
		result.getExceptions().addAll(exceptions);
	}

	public static void merge(CommentsContainer result, CommentsContainer addon) {
		if (addon == null || result == null) {
			return;
		}
		List<Comment> comments = addon.getComments();
		result.getComments().addAll(comments);
		List<SociosException> exceptions = addon.getExceptions();
		result.getExceptions().addAll(exceptions);
	}

	public static void merge(MediaItemsContainer result, MediaItemsContainer addon) {
		if (addon == null || result == null) {
			return;
		}
		List<MediaItem> mediaItems = addon.getMediaItems();
		result.getMediaItems().addAll(mediaItems);
		List<SociosException> exceptions = addon.getExceptions();
		result.getExceptions().addAll(exceptions);
	}

	public static void merge(PersonsContainer result, PersonsContainer addon) {
		if (addon == null || result == null) {
			return;
		}
		List<Person> persons = addon.getPersons();
		result.getPersons().addAll(persons);
		List<SociosException> exceptions = addon.getExceptions();
		result.getExceptions().addAll(exceptions);
	}
}
