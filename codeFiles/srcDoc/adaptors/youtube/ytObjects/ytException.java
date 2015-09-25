package adaptors.youtube.ytObjects;

import org.json.JSONObject;

public class ytException {
	private String domain;
	private String code;
	private String internalReason;

	public ytException() {
	}

	public ytException(JSONObject json) {
		JSONObject errors = json.optJSONObject("errors");
		if (errors != null) {
			JSONObject error = errors.optJSONObject("error");
			if (error != null) {
				this.setDomain(error.optString("domain", null));
				this.setCode(error.optString("code", null));
				this.setInternalReason(error.optString("internalReason", null));
			}
		}
	}

	public String getInternalReason() {
		return internalReason;
	}

	public void setInternalReason(String internalReason) {
		this.internalReason = internalReason;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
