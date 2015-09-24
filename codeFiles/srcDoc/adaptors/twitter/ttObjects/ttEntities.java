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
package adaptors.twitter.ttObjects;

import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

public class ttEntities {
	private List<String> hashtags;
	private List<ttMedia> media;
	private List<String> urls;
	private List<String> user_mentions_ids;
	private List<String> user_mentions_names;

	public ttEntities() {
	}

	public ttEntities(JSONObject json) {
		JSONArray hashtagsArray = json.optJSONArray("hashtags");
		if (Utilities.isValid(hashtagsArray)) {
			this.hashtags = new ArrayList<String>();
			for (int index = 0; index < hashtagsArray.length(); index++) {
				JSONObject jshashtag = hashtagsArray.optJSONObject(index);
				if (jshashtag != null) {
					String hashtag = jshashtag.optString("text", null);
					this.hashtags.add(hashtag);
				}
			}
		}
		JSONArray mediaArray = json.optJSONArray("media");
		if (Utilities.isValid(mediaArray)) {
			this.media = new ArrayList<ttMedia>();
			for (int index = 0; index < mediaArray.length(); index++) {
				JSONObject jsmedia = mediaArray.optJSONObject(index);
				if (jsmedia != null) {
					ttMedia media = new ttMedia(jsmedia);
					this.media.add(media);
				}
			}
		}
		JSONArray urlsArray = json.optJSONArray("urls");
		if (Utilities.isValid(urlsArray)) {
			this.urls = new ArrayList<String>();
			for (int index = 0; index < urlsArray.length(); index++) {
				JSONObject jsurl = urlsArray.optJSONObject(index);
				if (jsurl != null) {
					String url = jsurl.optString("expanded_url", null);
					this.urls.add(url);
				}
			}
		}
		JSONArray user_mentionsArray = json.optJSONArray("user_mentions");
		if (Utilities.isValid(user_mentionsArray)) {
			this.user_mentions_names = new ArrayList<String>();
			this.user_mentions_ids = new ArrayList<String>();
			for (int index = 0; index < user_mentionsArray.length(); index++) {
				JSONObject jsuser_mention = user_mentionsArray.optJSONObject(index);
				if (jsuser_mention != null) {
					String user_mention_name = jsuser_mention.optString("screen_name", null);
					if (Utilities.isValid(user_mention_name)) {
						this.user_mentions_names.add(user_mention_name);
					}
					String user_mention_id = jsuser_mention.optString("id_str", null);
					if (Utilities.isValid(user_mention_id)) {
						this.user_mentions_ids.add(user_mention_id);
					}
				}
			}
		}
	}

	public List<String> getHashtags() {
		return hashtags;
	}

	public void setHashtags(List<String> hashtags) {
		this.hashtags = hashtags;
	}

	public List<ttMedia> getMedia() {
		return media;
	}

	public void setMedia(List<ttMedia> media) {
		this.media = media;
	}

	public List<String> getUrls() {
		return urls;
	}

	public void setUrls(List<String> urls) {
		this.urls = urls;
	}

	public List<String> getUser_mentions_names() {
		return user_mentions_names;
	}

	public void setUser_mentions_names(List<String> user_mentions_names) {
		this.user_mentions_names = user_mentions_names;
	}

	public List<String> getUser_mentions_ids() {
		return user_mentions_ids;
	}

	public void setUser_mentions_ids(List<String> user_mentions_ids) {
		this.user_mentions_ids = user_mentions_ids;
	}
	public class ttMedia {
		private String display_url;
		private String expanded_url;
		private String media_url;
		private String type;
		private String url;

		public ttMedia() {
		}

		public ttMedia(JSONObject json) {
			this.setDisplay_url(json.optString("display_url", null));
			this.setExpanded_url(json.optString("expanded_url", null));
			this.setMedia_url(json.optString("media_url", null));
			this.setType(json.optString("type", null));
			this.setUrl(json.optString("url", null));
		}

		public String getUrl() {
			return url;
		}

		public void setUrl(String url) {
			this.url = url;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public String getMedia_url() {
			return media_url;
		}

		public void setMedia_url(String media_url) {
			this.media_url = media_url;
		}

		public String getExpanded_url() {
			return expanded_url;
		}

		public void setExpanded_url(String expanded_url) {
			this.expanded_url = expanded_url;
		}

		public String getDisplay_url() {
			return display_url;
		}

		public void setDisplay_url(String display_url) {
			this.display_url = display_url;
		}
	}
}
