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
package adaptors.googlep.gpObjects;

import helper.utilities.ParseUtilities;
import org.json.JSONObject;

public class gpComment {
	private String content;
	private String id;
	private int plusOners;
	private String published;
	private String userId;
	private String username;

	public gpComment() {
	}

	public gpComment(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setPublished(json.optString("published", null));
		this.setUserId(ParseUtilities.doubleJsParse(json, "actor", "id"));
		this.setUsername(ParseUtilities.doubleJsParse(json, "actor", "displayName"));
		this.setContent(ParseUtilities.doubleJsParse(json, "object", "content"));
		this.setPlusOners(ParseUtilities.doubleJsParseInt(json, "plusoners", "totalItems"));
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public int getPlusOners() {
		return plusOners;
	}

	public void setPlusOners(int plusOners) {
		this.plusOners = plusOners;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}
}
