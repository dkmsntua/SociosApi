package adaptors.facebook.fbObjects;

import org.json.JSONObject;

public class fbException {
	private int code;
	private String message;
	private String type;

	public fbException() {
	}

	public fbException(JSONObject json) {
		JSONObject error = json.optJSONObject("error");
		if (error != null) {
			this.setCode(error.optInt("code", 0));
			this.setMessage(error.optString("message", null));
			this.setType(error.optString("type", null));
		}
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
