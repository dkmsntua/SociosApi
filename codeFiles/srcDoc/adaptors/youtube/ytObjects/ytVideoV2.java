package adaptors.youtube.ytObjects;

import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;
import adaptors.youtube.ytObjects.ytPerson.ytAuthor;

public class ytVideoV2 {
	private ytAuthor author;
	private double averageRating;
	private int comments;
	private String description;
	private int duration;
	private int favoriteCount;
	private String id;
	private String license;
	private int maxRating;
	private int minRating;
	private int numDislikes;
	private int numLikes;
	private int numRaters;
	private String playerUrl;
	private String published;
	private String thumbnail;
	private String title;
	private int viewCount;

	public ytVideoV2() {
	}

	public ytVideoV2(JSONObject json) {
		this.setPublished(ParseUtilities.doubleJsParse(json, "published", "$t"));
		this.setTitle(ParseUtilities.doubleJsParse(json, "title", "$t"));
		JSONArray authorArray = json.optJSONArray("author");
		if (Utilities.isValid(authorArray)) {
			JSONObject jsauthor = authorArray.optJSONObject(0);
			if (jsauthor != null) {
				this.setAuthor(new ytAuthor(jsauthor));
			}
		}
		JSONObject jscomments = json.optJSONObject("gd$comments");
		if (jscomments != null) {
			this.setComments(ParseUtilities.doubleJsParseInt(jscomments, "gd$feedLink", "countHint"));
		}
		JSONObject jsrating = json.optJSONObject("gd$rating");
		if (jsrating != null) {
			this.setMinRating(jsrating.optInt("min", -1));
			this.setMaxRating(jsrating.optInt("max", -1));
			this.setNumRaters(jsrating.optInt("numRaters", -1));
			this.setAverageRating(jsrating.optDouble("average", -1));
		}
		JSONObject jsstatistics = json.optJSONObject("yt$statistics");
		if (jsstatistics != null) {
			this.setFavoriteCount(jsstatistics.optInt("favoriteCount", -1));
			this.setViewCount(jsstatistics.optInt("viewCount", -1));
		}
		JSONObject ytRating = json.optJSONObject("yt$rating");
		if (ytRating != null) {
			this.setNumDislikes(ytRating.optInt("numDislikes", -1));
			this.setNumLikes(ytRating.optInt("numLikes", -1));
		}
		JSONObject jsgroup = json.optJSONObject("media$group");
		if (jsgroup != null) {
			this.setId(ParseUtilities.doubleJsParse(jsgroup, "yt$videoid", "$t"));
			this.setLicense(ParseUtilities.doubleJsParse(jsgroup, "media$license", "$t"));
			JSONArray thumbnailsArray = jsgroup.optJSONArray("media$thumbnail");
			if (Utilities.isValid(thumbnailsArray)) {
				JSONObject jsthumbnail = thumbnailsArray.optJSONObject(0);
				if (jsthumbnail != null) {
					this.setThumbnail(jsthumbnail.optString("url", null));
				}
			}
			this.setDescription(ParseUtilities.doubleJsParse(jsgroup, "media$description", "$t"));
			this.setDuration(ParseUtilities.doubleJsParseInt(jsgroup, "yt$duration", "seconds"));
			this.setPlayerUrl(ParseUtilities.doubleJsParse(jsgroup, "media$player", "url"));
		}
	}

	public int getViewCount() {
		return viewCount;
	}

	public void setViewCount(int viewCount) {
		this.viewCount = viewCount;
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

	public String getPublished() {
		return published;
	}

	public void setPublished(String published) {
		this.published = published;
	}

	public String getPlayerUrl() {
		return playerUrl;
	}

	public void setPlayerUrl(String playerUrl) {
		this.playerUrl = playerUrl;
	}

	public int getNumRaters() {
		return numRaters;
	}

	public void setNumRaters(int numRaters) {
		this.numRaters = numRaters;
	}

	public int getNumLikes() {
		return numLikes;
	}

	public void setNumLikes(int numLikes) {
		this.numLikes = numLikes;
	}

	public int getNumDislikes() {
		return numDislikes;
	}

	public void setNumDislikes(int numDislikes) {
		this.numDislikes = numDislikes;
	}

	public int getMinRating() {
		return minRating;
	}

	public void setMinRating(int minRating) {
		this.minRating = minRating;
	}

	public int getMaxRating() {
		return maxRating;
	}

	public void setMaxRating(int maxRating) {
		this.maxRating = maxRating;
	}

	public String getLicense() {
		return license;
	}

	public void setLicense(String license) {
		this.license = license;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public int getFavoriteCount() {
		return favoriteCount;
	}

	public void setFavoriteCount(int favoriteCount) {
		this.favoriteCount = favoriteCount;
	}

	public int getDuration() {
		return duration;
	}

	public void setDuration(int duration) {
		this.duration = duration;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public int getComments() {
		return comments;
	}

	public void setComments(int comments) {
		this.comments = comments;
	}

	public double getAverageRating() {
		return averageRating;
	}

	public void setAverageRating(double averageRating) {
		this.averageRating = averageRating;
	}

	public ytAuthor getAuthor() {
		return author;
	}

	public void setAuthor(ytAuthor author) {
		this.author = author;
	}
}
