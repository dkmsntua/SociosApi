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
package objects.main;

import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.ActivityObjectType;
import objects.enums.SocialNetwork;

public class Activity {
	private String id;
	private SocialNetwork sn;
	private XMLGregorianCalendar created;
	private String title;
	private String description;
	private String type;
	private Address location;
	private String actorId;
	private ActivityObjectType objectType;
	private List<MediaItem> mediaItems;
	private List<Person> persons;
	private List<Activity> activities;

	public Activity() {
	}

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public SocialNetwork getSn() {
		return sn;
	}

	public void setSn(SocialNetwork value) {
		this.sn = value;
	}

	public XMLGregorianCalendar getCreated() {
		return created;
	}

	public void setCreated(XMLGregorianCalendar value) {
		this.created = value;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String value) {
		this.title = value;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String value) {
		this.type = value;
	}

	public Address getLocation() {
		return location;
	}

	public void setLocation(Address value) {
		this.location = value;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String value) {
		this.actorId = value;
	}

	public ActivityObjectType getObjectType() {
		return objectType;
	}

	public void setObjectType(ActivityObjectType value) {
		this.objectType = value;
	}

	public List<MediaItem> getMediaItems() {
		if (mediaItems == null) {
			mediaItems = new ArrayList<MediaItem>();
		}
		return this.mediaItems;
	}

	public void setMediaItems(List<MediaItem> value) {
		this.mediaItems = value;
	}

	public List<Person> getPersons() {
		if (persons == null) {
			persons = new ArrayList<Person>();
		}
		return this.persons;
	}

	public void setPersons(List<Person> value) {
		this.persons = value;
	}

	public List<Activity> getActivities() {
		if (activities == null) {
			activities = new ArrayList<Activity>();
		}
		return this.activities;
	}

	public void setActivities(List<Activity> value) {
		this.activities = value;
	}
}
