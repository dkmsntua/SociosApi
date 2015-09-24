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
