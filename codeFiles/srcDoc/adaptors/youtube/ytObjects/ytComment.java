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
package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class ytComment {
	private String content;
	private String id;
	private String published;
	private String userId;
	private String username;

	public ytComment() {
	}

	public ytComment(JSONObject json) {
		String id = ParseUtilities.doubleJsParse(json, "id", "$t");
		if (id != null) {
			String[] tokens = id.split(":");
			this.setId(tokens[tokens.length - 1]);
		}
		this.setPublished(ParseUtilities.doubleJsParse(json, "published", "$t"));
		this.setContent(ParseUtilities.doubleJsParse(json, "content", "$t"));
		JSONArray jsarray = json.optJSONArray("author");
		if (Utilities.isValid(jsarray)) {
			JSONObject jsauthor = jsarray.optJSONObject(0);
			if (jsauthor != null) {
				this.setUserId(ParseUtilities.doubleJsParse(jsauthor, "yt$userId", "$t"));
				this.setUsername(ParseUtilities.doubleJsParse(jsauthor, "name", "$t"));
			}
		}
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
