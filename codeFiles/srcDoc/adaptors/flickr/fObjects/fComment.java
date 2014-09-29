package adaptors.flickr.fObjects;

import org.json.JSONObject;

public class fComment
{
	public String author;
	public String authorname;
	public String content;
	public long datecreate;
	public String id;
	public String realname;

	public fComment()
	{
	}

	public fComment(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String author = json.optString("author", null);
		this.author = author;
		String authorname = json.optString("authorname", null);
		this.authorname = authorname;
		long datecreate = json.optLong("datecreate", -1);
		this.datecreate = datecreate;
		String realname = json.optString("realname", null);
		this.realname = realname;
		String content = json.optString("_content", null);
		this.content = content;
	}
}
