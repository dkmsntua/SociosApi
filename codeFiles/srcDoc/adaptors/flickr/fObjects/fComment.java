package adaptors.flickr.fObjects;

import org.json.JSONObject;

public class fComment {
	private String author;
	private String authorname;
	private String content;
	private long datecreate;
	private String id;
	private String realname;

	public fComment() {
	}

	public fComment(JSONObject json) {
		this.setId(json.optString("id", null));
		this.setAuthor(json.optString("author", null));
		this.setAuthorname(json.optString("authorname", null));
		this.setDatecreate(json.optLong("datecreate", -1));
		this.setRealname(json.optString("realname", null));
		this.setContent(json.optString("_content", null));
	}

	public String getRealname() {
		return realname;
	}

	public void setRealname(String realname) {
		this.realname = realname;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public long getDatecreate() {
		return datecreate;
	}

	public void setDatecreate(long datecreate) {
		this.datecreate = datecreate;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAuthorname() {
		return authorname;
	}

	public void setAuthorname(String authorname) {
		this.authorname = authorname;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}
}
