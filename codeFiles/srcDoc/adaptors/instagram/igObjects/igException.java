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
