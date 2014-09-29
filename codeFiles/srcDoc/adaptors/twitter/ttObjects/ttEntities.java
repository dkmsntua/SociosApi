package adaptors.twitter.ttObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class ttEntities
{
	public class ttMedia
	{
		public String display_url;
		public String expanded_url;
		public String media_url;
		public String type;
		public String url;

		public ttMedia()
		{
		}

		public ttMedia(JSONObject json)
		{
			String display_url = json.optString("display_url", null);
			this.display_url = display_url;
			String expanded_url = json.optString("expanded_url", null);
			this.expanded_url = expanded_url;
			String media_url = json.optString("media_url", null);
			this.media_url = media_url;
			String type = json.optString("type", null);
			this.type = type;
			String url = json.optString("url", null);
			this.url = url;
		}
	}
	public List<String> hashtags;
	public List<ttMedia> media;
	public List<String> urls;
	public List<String> user_mentions_ids;
	public List<String> user_mentions_names;

	public ttEntities()
	{
	}

	public ttEntities(JSONObject json)
	{
		JSONArray hashtagsArray = json.optJSONArray("hashtags");
		if (Utilities.isValid(hashtagsArray))
		{
			this.hashtags = new ArrayList<String>();
			for (int index = 0; index < hashtagsArray.length(); index++)
			{
				JSONObject jshashtag = hashtagsArray.optJSONObject(index);
				if (jshashtag != null)
				{
					String hashtag = jshashtag.optString("text", null);
					this.hashtags.add(hashtag);
				}
			}
		}
		JSONArray mediaArray = json.optJSONArray("media");
		if (Utilities.isValid(mediaArray))
		{
			this.media = new ArrayList<ttMedia>();
			for (int index = 0; index < mediaArray.length(); index++)
			{
				JSONObject jsmedia = mediaArray.optJSONObject(index);
				if (jsmedia != null)
				{
					ttMedia media = new ttMedia(jsmedia);
					this.media.add(media);
				}
			}
		}
		JSONArray urlsArray = json.optJSONArray("urls");
		if (Utilities.isValid(urlsArray))
		{
			this.urls = new ArrayList<String>();
			for (int index = 0; index < urlsArray.length(); index++)
			{
				JSONObject jsurl = urlsArray.optJSONObject(index);
				if (jsurl != null)
				{
					String url = jsurl.optString("expanded_url", null);
					this.urls.add(url);
				}
			}
		}
		JSONArray user_mentionsArray = json.optJSONArray("user_mentions");
		if (Utilities.isValid(user_mentionsArray))
		{
			this.user_mentions_names = new ArrayList<String>();
			this.user_mentions_ids = new ArrayList<String>();
			for (int index = 0; index < user_mentionsArray.length(); index++)
			{
				JSONObject jsuser_mention = user_mentionsArray.optJSONObject(index);
				if (jsuser_mention != null)
				{
					String user_mention_name = jsuser_mention.optString("screen_name", null);
					if (Utilities.isValid(user_mention_name))
					{
						this.user_mentions_names.add(user_mention_name);
					}
					String user_mention_id = jsuser_mention.optString("id_str", null);
					if (Utilities.isValid(user_mention_id))
					{
						this.user_mentions_ids.add(user_mention_id);
					}
				}
			}
		}
	}
}
