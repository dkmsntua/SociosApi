package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmActivity
{
	public long created_time;
	public String from_tile_owner;
	public String id;
	public String object_tile_icon_url;
	public String object_tile_owner_id;
	public String object_tile_title;
	public String object_tile_url;
	public String object_type;
	public String object_video_bookmarks_total;
	public String object_video_channel;
	public String object_video_comments_total;
	public String object_video_country;
	public String object_video_created_time;
	public String object_video_description;
	public String object_video_duration;
	public String object_video_embed_html;
	public String object_video_embed_url;
	public String object_video_id;
	public String object_video_language;
	public String object_video_owner;
	public String object_video_rating;
	public String object_video_ratings_total;
	public String object_video_strongtags;
	public String object_video_swf_url;
	public String object_video_tags;
	public String object_video_thumbnail_60_url;
	public String object_video_title;
	public String object_video_url;
	public String object_video_views_total;
	public String type;

	public dmActivity()
	{
	}

	public dmActivity(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		long created_time = json.optLong("created_time", -1);
		this.created_time = created_time;
		String type = json.optString("type", null);
		this.type = type;
		String object_type = json.optString("object_type", null);
		this.object_type = object_type;
		String from_tile_owner = json.optString("from_tile.owner", null);
		this.from_tile_owner = from_tile_owner;
		String object_tile_owner_id = json.optString("object_tile.owner", null);
		this.object_tile_owner_id = object_tile_owner_id;
		String object_tile_title = json.optString("object_tile.title", null);
		this.object_tile_title = object_tile_title;
		String object_tile_url = json.optString("object_tile.url", null);
		this.object_tile_url = object_tile_url;
		String object_tile_icon_url = json.optString("object_tile.icon_url", null);
		this.object_tile_icon_url = object_tile_icon_url;
		String object_video_id = json.optString("object_video.id", null);
		this.object_video_id = object_video_id;
		String object_video_title = json.optString("object_video.title", null);
		this.object_video_title = object_video_title;
		String object_video_created_time = json.optString("object_video.created_time", null);
		this.object_video_created_time = object_video_created_time;
		String object_video_thumbnail_60_url = json.optString("object_video.thumbnail_60_url", null);
		this.object_video_thumbnail_60_url = object_video_thumbnail_60_url;
		String object_video_description = json.optString("object_video.description", null);
		this.object_video_description = object_video_description;
		String object_video_duration = json.optString("object_video.duration", null);
		this.object_video_duration = object_video_duration;
		String object_video_country = json.optString("object_video.country", null);
		this.object_video_country = object_video_country;
		String object_video_language = json.optString("object_video.language", null);
		this.object_video_language = object_video_language;
		String object_video_rating = json.optString("object_video.rating", null);
		this.object_video_rating = object_video_rating;
		String object_video_ratings_total = json.optString("object_video.ratings_total", null);
		this.object_video_ratings_total = object_video_ratings_total;
		String object_video_comments_total = json.optString("object_video.comments_total", null);
		this.object_video_comments_total = object_video_comments_total;
		String object_video_views_total = json.optString("object_video.views_total", null);
		this.object_video_views_total = object_video_views_total;
		String object_video_bookmarks_total = json.optString("object_video.bookmarks_total", null);
		this.object_video_bookmarks_total = object_video_bookmarks_total;
		String object_video_tags = json.optString("object_video.tags", null);
		this.object_video_tags = object_video_tags;
		String object_video_strongtags = json.optString("object_video.strongtags", null);
		this.object_video_strongtags = object_video_strongtags;
		String object_video_url = json.optString("object_video.url", null);
		this.object_video_url = object_video_url;
		String object_video_owner = json.optString("object_video.owner", null);
		this.object_video_owner = object_video_owner;
		String object_video_channel = json.optString("object_video.channel", null);
		this.object_video_channel = object_video_channel;
		String object_video_embed_html = json.optString("object_video.embed_html", null);
		this.object_video_embed_html = object_video_embed_html;
		String object_video_embed_url = json.optString("object_video.embed_url", null);
		this.object_video_embed_url = object_video_embed_url;
		String object_video_swf_url = json.optString("object_video.swf_url", null);
		this.object_video_swf_url = object_video_swf_url;
	}
}
