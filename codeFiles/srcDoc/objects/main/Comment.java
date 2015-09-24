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

import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.SocialNetwork;

public class Comment {
	private String id;
	private SocialNetwork sn;
	private XMLGregorianCalendar created;
	private String description;
	private String userId;
	private String username;
	private Integer numPositiveVotes;

	public Comment() {
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String value) {
		this.userId = value;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String value) {
		this.username = value;
	}

	public Integer getNumPositiveVotes() {
		return numPositiveVotes;
	}

	public void setNumPositiveVotes(Integer value) {
		this.numPositiveVotes = value;
	}
}
