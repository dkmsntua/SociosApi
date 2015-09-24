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
import org.json.JSONObject;

public class ytChannel {
	private String channelId;
	private String description;
	private String picture;
	private String thumbnail;
	private String title;

	public ytChannel() {
	}

	public ytChannel(JSONObject json) {
		JSONObject jssnippet = json.optJSONObject("snippet");
		if (jssnippet != null) {
			this.setTitle(jssnippet.optString("title", null));
			this.setDescription(jssnippet.optString("description", null));
			this.setChannelId(ParseUtilities.doubleJsParse(jssnippet, "resourceId", "channelId"));
			JSONObject jsthumbnails = jssnippet.optJSONObject("thumbnails");
			if (jsthumbnails != null) {
				this.setThumbnail(ParseUtilities.doubleJsParse(jsthumbnails, "default", "url"));
				this.setPicture(ParseUtilities.doubleJsParse(jsthumbnails, "high", "url"));
			}
		}
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail() {
		return thumbnail;
	}

	public void setThumbnail(String thumbnail) {
		this.thumbnail = thumbnail;
	}

	public String getPicture() {
		return picture;
	}

	public void setPicture(String picture) {
		this.picture = picture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getChannelId() {
		return channelId;
	}

	public void setChannelId(String channelId) {
		this.channelId = channelId;
	}
}
