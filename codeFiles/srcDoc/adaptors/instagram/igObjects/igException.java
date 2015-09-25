package adaptors.instagram.igObjects;

import org.json.JSONObject;

public class igException {
	private int code;
	private String error_type;
	private String error_message;

	public igException() {
	}

	public igException(JSONObject json) {
		JSONObject jsMeta = json.optJSONObject("meta");
		if (jsMeta != null) {
			this.setCode(jsMeta.optInt("code", 0));
			this.setError_type(jsMeta.optString("error_type", null));
			this.setError_message(jsMeta.optString("error_message", null));
		}
	}

	public String getError_message() {
		return error_message;
	}

	public void setError_message(String error_message) {
		this.error_message = error_message;
	}

	public String getError_type() {
		return error_type;
	}

	public void setError_type(String error_type) {
		this.error_type = error_type;
	}

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}
}
