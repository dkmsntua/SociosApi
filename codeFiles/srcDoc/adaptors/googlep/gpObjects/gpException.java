/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package adaptors.googlep.gpObjects;

import helper.utilities.Utilities;
import org.json.JSONArray;
import org.json.JSONObject;

public class gpException {
	private int code;
	private String message;
	private String domain;
	private String reason;
	private String secondaryMessage;
	private String locationType;
	private String location;

	public gpException() {
	}

	public gpException(JSONObject json) {
		JSONObject jsError = json.optJSONObject("error");
		if (jsError != null) {
			this.setCode(jsError.optInt("code", 0));
			this.setMessage(jsError.optString("message", null));
			JSONArray errorsArray = jsError.optJSONArray("errors");
			if (Utilities.isValid(errorsArray)) {
				JSONObject jsonFirst = errorsArray.optJSONObject(0);
				if (jsonFirst != null) {
					this.setDomain(jsonFirst.optString("domain", null));
					this.setReason(jsonFirst.optString("reason", null));
					this.setSecondaryMessage(jsonFirst.optString("secondaryMessage", null));
					this.setLocationType(jsonFirst.optString("locationType", null));
					this.setLocation(jsonFirst.optString("location", null));
				}
			}
		}
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLocationType() {
		return locationType;
	}

	public void setLocationType(String locationType) {
		this.locationType = locationType;
	}

	public String getSecondaryMessage() {
		return secondaryMessage;
	}

	public void setSecondaryMessage(String secondaryMessage) {
		this.secondaryMessage = secondaryMessage;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
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
