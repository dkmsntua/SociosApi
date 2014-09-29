package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmVideo
{
	public String access_error;
	public String aspect_ratio;
	public String bookmarks_total;
	public String channel;
	public String comments_total;
	public String country;
	public String created_time;
	public String description;
	public String duration;
	public String embed_html;
	public String embed_url;
	public String genre;
	public String id;
	public String language;
	public String modified_time;
	public String owner;
	public String rating;
	public String ratings_total;
	public String sharing_urls;
	public String soundtrack_info;
	public String stream_source_url;
	public String strongtags;
	public String swf_url;
	public String tags;
	public String taken_time;
	public String thumbnail_60_url;
	public String thumbnail_url;
	public String title;
	public String type;
	public String url;
	public String views_last_day;
	public String views_last_hour;
	public String views_last_month;
	public String views_last_week;
	public String views_total;

	public dmVideo()
	{
	}

	public dmVideo(JSONObject json)
	{
		String access_error = json.optString("access_error", null);
		this.access_error = access_error;
		String aspect_ratio = json.optString("aspect_ratio", null);
		this.aspect_ratio = aspect_ratio;
		String bookmarks_total = json.optString("bookmarks_total", null);
		this.bookmarks_total = bookmarks_total;
		String channel = json.optString("channel", null);
		this.channel = channel;
		String comments_total = json.optString("comments_total", null);
		this.comments_total = comments_total;
		String country = json.optString("country", null);
		this.country = country;
		String created_time = json.optString("created_time", null);
		this.created_time = created_time;
		String description = json.optString("description", null);
		this.description = description;
		String duration = json.optString("duration", null);
		this.duration = duration;
		String embed_html = json.optString("embed_html", null);
		this.embed_html = embed_html;
		String embed_url = json.optString("embed_url", null);
		this.embed_url = embed_url;
		String genre = json.optString("genre", null);
		this.genre = genre;
		String id = json.optString("id", null);
		this.id = id;
		String language = json.optString("language", null);
		this.language = language;
		String modified_time = json.optString("modified_time", null);
		this.modified_time = modified_time;
		String owner = json.optString("owner", null);
		this.owner = owner;
		String rating = json.optString("rating", null);
		this.rating = rating;
		String ratings_total = json.optString("ratings_total", null);
		this.ratings_total = ratings_total;
		String sharing_urls = json.optString("sharing_urls", null);
		this.sharing_urls = sharing_urls;
		String soundtrack_info = json.optString("soundtrack_info", null);
		this.soundtrack_info = soundtrack_info;
		String stream_source_url = json.optString("stream_source_url", null);
		this.stream_source_url = stream_source_url;
		String strongtags = json.optString("strongtags", null);
		this.strongtags = strongtags;
		String swf_url = json.optString("swf_url", null);
		this.swf_url = swf_url;
		String tags = json.optString("tags", null);
		this.tags = tags;
		String taken_time = json.optString("taken_time", null);
		this.taken_time = taken_time;
		String thumbnail_60_url = json.optString("thumbnail_60_url", null);
		this.thumbnail_60_url = thumbnail_60_url;
		String thumbnail_url = json.optString("thumbnail_url", null);
		this.thumbnail_url = thumbnail_url;
		String title = json.optString("title", null);
		this.title = title;
		String type = json.optString("type", null);
		this.type = type;
		String url = json.optString("url", null);
		this.url = url;
		String views_last_day = json.optString("views_last_day", null);
		this.views_last_day = views_last_day;
		String views_last_hour = json.optString("views_last_hour", null);
		this.views_last_hour = views_last_hour;
		String views_last_month = json.optString("views_last_month", null);
		this.views_last_month = views_last_month;
		String views_last_week = json.optString("views_last_week", null);
		this.views_last_week = views_last_week;
		String views_total = json.optString("views_total", null);
		this.views_total = views_total;
	}
}
