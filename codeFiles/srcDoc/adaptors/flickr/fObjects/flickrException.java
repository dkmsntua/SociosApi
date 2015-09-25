package adaptors.flickr.fObjects;

import org.json.JSONObject;

public class flickrException {
	private int code;
	private String stat;
	private String message;

	public flickrException() {
	}

	public flickrException(JSONObject json) {
		this.setCode(json.optInt("code", 0));
		this.setMessage(json.optString("message", null));
		this.setStat(json.optString("stat", null));
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getStat() {
		return stat;
	}

	public void setStat(String stat) {
		this.stat = stat;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
