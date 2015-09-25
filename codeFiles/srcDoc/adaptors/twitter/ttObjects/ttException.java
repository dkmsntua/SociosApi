package adaptors.twitter.ttObjects;

import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class ttException {
	private int code;
	private String message;

	public ttException() {
	}

	public ttException(JSONObject json) {
		JSONArray errorsArray = json.optJSONArray("errors");
		if (Utilities.isValid(errorsArray)) {
			JSONObject jsonFirst = errorsArray.optJSONObject(0);
			if (jsonFirst != null) {
				int code = jsonFirst.optInt("code", 0);
				this.setCode(code);
				String message = jsonFirst.optString("message", null);
				this.setMessage(message);
			}
		}
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
