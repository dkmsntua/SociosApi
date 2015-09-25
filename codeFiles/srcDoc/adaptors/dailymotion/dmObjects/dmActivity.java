package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmActivity {
	private long created_time;
	private String from_tile_owner;
	private String id;
	private String object_tile_icon_url;
	private String object_tile_owner_id;
	private String object_tile_title;
	private String object_tile_url;
	private String object_type;
	private String object_video_bookmarks_total;
	private String object_video_channel;
	private String object_video_comments_total;
	private String object_video_country;
	private String object_video_created_time;
	private String object_video_description;
	private String object_video_duration;
	private String object_video_embed_html;
	private String object_video_embed_url;
	private String object_video_id;
	private String object_video_language;
	private String object_video_owner;
	private String object_video_rating;
	private String object_video_ratings_total;
	private String object_video_strongtags;
	private String object_video_swf_url;
	private String object_video_tags;
	private String object_video_thumbnail_60_url;
	private String object_video_title;
	private String object_video_url;
	private String object_video_views_total;
	private String type;

	public dmActivity() {
	}

	public dmActivity(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setCreated_time(json.optLong("created_time", -1));
		this.setType(json.optString("type", null));
		this.setObject_type(json.optString("object_type", null));
		this.setFrom_tile_owner(json.optString("from_tile.owner", null));
		this.setObject_tile_owner_id(json.optString("object_tile.owner", null));
		this.setObject_tile_title(json.optString("object_tile.title", null));
		this.setObject_tile_url(json.optString("object_tile.url", null));
		this.setObject_tile_icon_url(json.optString("object_tile.icon_url", null));
		this.setObject_video_id(json.optString("object_video.id", null));
		this.setObject_video_title(json.optString("object_video.title", null));
		this.setObject_video_created_time(json.optString("object_video.created_time", null));
		this.setObject_video_thumbnail_60_url(json.optString("object_video.thumbnail_60_url", null));
		this.setObject_video_description(json.optString("object_video.description", null));
		this.setObject_video_duration(json.optString("object_video.duration", null));
		this.setObject_video_country(json.optString("object_video.country", null));
		this.setObject_video_language(json.optString("object_video.language", null));
		this.setObject_video_rating(json.optString("object_video.rating", null));
		this.setObject_video_ratings_total(json.optString("object_video.ratings_total", null));
		this.setObject_video_comments_total(json.optString("object_video.comments_total", null));
		this.setObject_video_views_total(json.optString("object_video.views_total", null));
		this.setObject_video_bookmarks_total(json.optString("object_video.bookmarks_total", null));
		this.setObject_video_tags(json.optString("object_video.tags", null));
		this.setObject_video_strongtags(json.optString("object_video.strongtags", null));
		this.setObject_video_url(json.optString("object_video.url", null));
		this.setObject_video_owner(json.optString("object_video.owner", null));
		this.setObject_video_channel(json.optString("object_video.channel", null));
		this.setObject_video_embed_html(json.optString("object_video.embed_html", null));
		this.setObject_video_embed_url(json.optString("object_video.embed_url", null));
		this.setObject_video_swf_url(json.optString("object_video.swf_url", null));
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getObject_video_views_total() {
		return object_video_views_total;
	}

	public void setObject_video_views_total(String object_video_views_total) {
		this.object_video_views_total = object_video_views_total;
	}

	public String getObject_video_url() {
		return object_video_url;
	}

	public void setObject_video_url(String object_video_url) {
		this.object_video_url = object_video_url;
	}

	public String getObject_video_title() {
		return object_video_title;
	}

	public void setObject_video_title(String object_video_title) {
		this.object_video_title = object_video_title;
	}

	public String getObject_video_thumbnail_60_url() {
		return object_video_thumbnail_60_url;
	}

	public void setObject_video_thumbnail_60_url(String object_video_thumbnail_60_url) {
		this.object_video_thumbnail_60_url = object_video_thumbnail_60_url;
	}

	public String getObject_video_tags() {
		return object_video_tags;
	}

	public void setObject_video_tags(String object_video_tags) {
		this.object_video_tags = object_video_tags;
	}

	public String getObject_video_swf_url() {
		return object_video_swf_url;
	}

	public void setObject_video_swf_url(String object_video_swf_url) {
		this.object_video_swf_url = object_video_swf_url;
	}

	public String getObject_video_strongtags() {
		return object_video_strongtags;
	}

	public void setObject_video_strongtags(String object_video_strongtags) {
		this.object_video_strongtags = object_video_strongtags;
	}

	public String getObject_video_ratings_total() {
		return object_video_ratings_total;
	}

	public void setObject_video_ratings_total(String object_video_ratings_total) {
		this.object_video_ratings_total = object_video_ratings_total;
	}

	public String getObject_video_rating() {
		return object_video_rating;
	}

	public void setObject_video_rating(String object_video_rating) {
		this.object_video_rating = object_video_rating;
	}

	public String getObject_video_owner() {
		return object_video_owner;
	}

	public void setObject_video_owner(String object_video_owner) {
		this.object_video_owner = object_video_owner;
	}

	public String getObject_video_language() {
		return object_video_language;
	}

	public void setObject_video_language(String object_video_language) {
		this.object_video_language = object_video_language;
	}

	public String getObject_video_id() {
		return object_video_id;
	}

	public void setObject_video_id(String object_video_id) {
		this.object_video_id = object_video_id;
	}

	public String getObject_video_embed_url() {
		return object_video_embed_url;
	}

	public void setObject_video_embed_url(String object_video_embed_url) {
		this.object_video_embed_url = object_video_embed_url;
	}

	public String getObject_video_embed_html() {
		return object_video_embed_html;
	}

	public void setObject_video_embed_html(String object_video_embed_html) {
		this.object_video_embed_html = object_video_embed_html;
	}

	public String getObject_video_duration() {
		return object_video_duration;
	}

	public void setObject_video_duration(String object_video_duration) {
		this.object_video_duration = object_video_duration;
	}

	public String getObject_video_description() {
		return object_video_description;
	}

	public void setObject_video_description(String object_video_description) {
		this.object_video_description = object_video_description;
	}

	public String getObject_video_created_time() {
		return object_video_created_time;
	}

	public void setObject_video_created_time(String object_video_created_time) {
		this.object_video_created_time = object_video_created_time;
	}

	public String getObject_video_country() {
		return object_video_country;
	}

	public void setObject_video_country(String object_video_country) {
		this.object_video_country = object_video_country;
	}

	public String getObject_video_comments_total() {
		return object_video_comments_total;
	}

	public void setObject_video_comments_total(String object_video_comments_total) {
		this.object_video_comments_total = object_video_comments_total;
	}

	public String getObject_video_channel() {
		return object_video_channel;
	}

	public void setObject_video_channel(String object_video_channel) {
		this.object_video_channel = object_video_channel;
	}

	public String getObject_video_bookmarks_total() {
		return object_video_bookmarks_total;
	}

	public void setObject_video_bookmarks_total(String object_video_bookmarks_total) {
		this.object_video_bookmarks_total = object_video_bookmarks_total;
	}

	public String getObject_type() {
		return object_type;
	}

	public void setObject_type(String object_type) {
		this.object_type = object_type;
	}

	public String getObject_tile_url() {
		return object_tile_url;
	}

	public void setObject_tile_url(String object_tile_url) {
		this.object_tile_url = object_tile_url;
	}

	public String getObject_tile_title() {
		return object_tile_title;
	}

	public void setObject_tile_title(String object_tile_title) {
		this.object_tile_title = object_tile_title;
	}

	public String getObject_tile_owner_id() {
		return object_tile_owner_id;
	}

	public void setObject_tile_owner_id(String object_tile_owner_id) {
		this.object_tile_owner_id = object_tile_owner_id;
	}

	public String getObject_tile_icon_url() {
		return object_tile_icon_url;
	}

	public void setObject_tile_icon_url(String object_tile_icon_url) {
		this.object_tile_icon_url = object_tile_icon_url;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getFrom_tile_owner() {
		return from_tile_owner;
	}

	public void setFrom_tile_owner(String from_tile_owner) {
		this.from_tile_owner = from_tile_owner;
	}

	public long getCreated_time() {
		return created_time;
	}

	public void setCreated_time(long created_time) {
		this.created_time = created_time;
	}
}
