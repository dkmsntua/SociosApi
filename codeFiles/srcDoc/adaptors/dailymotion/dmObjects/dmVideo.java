package adaptors.dailymotion.dmObjects;

import org.json.JSONObject;

public class dmVideo {
	private String access_error;
	private String aspect_ratio;
	private String bookmarks_total;
	private String channel;
	private String comments_total;
	private String country;
	private String created_time;
	private String description;
	private String duration;
	private String embed_html;
	private String embed_url;
	private String genre;
	private String id;
	private String language;
	private String modified_time;
	private String owner;
	private String rating;
	private String ratings_total;
	private String sharing_urls;
	private String soundtrack_info;
	private String stream_source_url;
	private String strongtags;
	private String swf_url;
	private String tags;
	private String taken_time;
	private String thumbnail_60_url;
	private String thumbnail_url;
	private String title;
	private String type;
	private String url;
	private String views_last_day;
	private String views_last_hour;
	private String views_last_month;
	private String views_last_week;
	private String views_total;

	public dmVideo() {
	}

	public dmVideo(JSONObject json) {
		this.setAccess_error(json.optString("access_error", null));
		this.setAspect_ratio(json.optString("aspect_ratio", null));
		this.setBookmarks_total(json.optString("bookmarks_total", null));
		this.setChannel(json.optString("channel", null));
		this.setComments_total(json.optString("comments_total", null));
		this.setCountry(json.optString("country", null));
		this.setCreated_time(json.optString("created_time", null));
		this.setDescription(json.optString("description", null));
		this.setDuration(json.optString("duration", null));
		this.setEmbed_html(json.optString("embed_html", null));
		this.setEmbed_url(json.optString("embed_url", null));
		this.setGenre(json.optString("genre", null));
		this.setId(json.optString("id", null));
		this.setLanguage(json.optString("language", null));
		this.setModified_time(json.optString("modified_time", null));
		this.setOwner(json.optString("owner", null));
		this.setRating(json.optString("rating", null));
		this.setRatings_total(json.optString("ratings_total", null));
		this.setSharing_urls(json.optString("sharing_urls", null));
		this.setSoundtrack_info(json.optString("soundtrack_info", null));
		this.setStream_source_url(json.optString("stream_source_url", null));
		this.setStrongtags(json.optString("strongtags", null));
		this.setSwf_url(json.optString("swf_url", null));
		this.setTags(json.optString("tags", null));
		this.setTaken_time(json.optString("taken_time", null));
		this.setThumbnail_60_url(json.optString("thumbnail_60_url", null));
		this.setThumbnail_url(json.optString("thumbnail_url", null));
		this.setTitle(json.optString("title", null));
		this.setType(json.optString("type", null));
		this.setUrl(json.optString("url", null));
		this.setViews_last_day(json.optString("views_last_day", null));
		this.setViews_last_hour(json.optString("views_last_hour", null));
		this.setViews_last_month(json.optString("views_last_month", null));
		this.setViews_last_week(json.optString("views_last_week", null));
		this.setViews_total(json.optString("views_total", null));
	}

	public String getViews_total() {
		return views_total;
	}

	public void setViews_total(String views_total) {
		this.views_total = views_total;
	}

	public String getViews_last_week() {
		return views_last_week;
	}

	public void setViews_last_week(String views_last_week) {
		this.views_last_week = views_last_week;
	}

	public String getViews_last_month() {
		return views_last_month;
	}

	public void setViews_last_month(String views_last_month) {
		this.views_last_month = views_last_month;
	}

	public String getViews_last_hour() {
		return views_last_hour;
	}

	public void setViews_last_hour(String views_last_hour) {
		this.views_last_hour = views_last_hour;
	}

	public String getViews_last_day() {
		return views_last_day;
	}

	public void setViews_last_day(String views_last_day) {
		this.views_last_day = views_last_day;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getThumbnail_url() {
		return thumbnail_url;
	}

	public void setThumbnail_url(String thumbnail_url) {
		this.thumbnail_url = thumbnail_url;
	}

	public String getThumbnail_60_url() {
		return thumbnail_60_url;
	}

	public void setThumbnail_60_url(String thumbnail_60_url) {
		this.thumbnail_60_url = thumbnail_60_url;
	}

	public String getTaken_time() {
		return taken_time;
	}

	public void setTaken_time(String taken_time) {
		this.taken_time = taken_time;
	}

	public String getTags() {
		return tags;
	}

	public void setTags(String tags) {
		this.tags = tags;
	}

	public String getSwf_url() {
		return swf_url;
	}

	public void setSwf_url(String swf_url) {
		this.swf_url = swf_url;
	}

	public String getStrongtags() {
		return strongtags;
	}

	public void setStrongtags(String strongtags) {
		this.strongtags = strongtags;
	}

	public String getStream_source_url() {
		return stream_source_url;
	}

	public void setStream_source_url(String stream_source_url) {
		this.stream_source_url = stream_source_url;
	}

	public String getSoundtrack_info() {
		return soundtrack_info;
	}

	public void setSoundtrack_info(String soundtrack_info) {
		this.soundtrack_info = soundtrack_info;
	}

	public String getSharing_urls() {
		return sharing_urls;
	}

	public void setSharing_urls(String sharing_urls) {
		this.sharing_urls = sharing_urls;
	}

	public String getRatings_total() {
		return ratings_total;
	}

	public void setRatings_total(String ratings_total) {
		this.ratings_total = ratings_total;
	}

	public String getRating() {
		return rating;
	}

	public void setRating(String rating) {
		this.rating = rating;
	}

	public String getOwner() {
		return owner;
	}

	public void setOwner(String owner) {
		this.owner = owner;
	}

	public String getModified_time() {
		return modified_time;
	}

	public void setModified_time(String modified_time) {
		this.modified_time = modified_time;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getEmbed_url() {
		return embed_url;
	}

	public void setEmbed_url(String embed_url) {
		this.embed_url = embed_url;
	}

	public String getEmbed_html() {
		return embed_html;
	}

	public void setEmbed_html(String embed_html) {
		this.embed_html = embed_html;
	}

	public String getDuration() {
		return duration;
	}

	public void setDuration(String duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCreated_time() {
		return created_time;
	}

	public void setCreated_time(String created_time) {
		this.created_time = created_time;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getComments_total() {
		return comments_total;
	}

	public void setComments_total(String comments_total) {
		this.comments_total = comments_total;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getBookmarks_total() {
		return bookmarks_total;
	}

	public void setBookmarks_total(String bookmarks_total) {
		this.bookmarks_total = bookmarks_total;
	}

	public String getAspect_ratio() {
		return aspect_ratio;
	}

	public void setAspect_ratio(String aspect_ratio) {
		this.aspect_ratio = aspect_ratio;
	}

	public String getAccess_error() {
		return access_error;
	}

	public void setAccess_error(String access_error) {
		this.access_error = access_error;
	}
}
