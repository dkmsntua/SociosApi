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
package adaptors.instagram.igObjects;

import org.json.JSONObject;

public class igComment {
	private long created_time;
	private igUser from;
	private String id;
	private String text;

	public igComment() {
	}

	public igComment(JSONObject json) {
		this.setCreated_time(json.optLong("created_time", -1));
		this.setText(json.optString("text", null));
		this.setId(json.optString("id", null));
		JSONObject jsfrom = json.optJSONObject("from");
		if (jsfrom != null) {
			this.setFrom(new igUser(jsfrom));
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public igUser getFrom() {
		return from;
	}

	public void setFrom(igUser from) {
		this.from = from;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}
}
