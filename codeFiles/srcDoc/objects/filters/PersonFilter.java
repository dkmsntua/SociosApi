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
package objects.filters;

import java.util.ArrayList;
import java.util.List;
import objects.enums.SocialNetwork;

public class PersonFilter {
	private List<String> keywords;
	private List<SocialNetwork> sns;

	public PersonFilter() {
	}

	public List<String> getKeywords() {
		if (keywords == null) {
			keywords = new ArrayList<String>();
		}
		return this.keywords;
	}

	public void setKeywords(List<String> value) {
		this.keywords = value;
	}

	public List<SocialNetwork> getSns() {
		if (sns == null) {
			sns = new ArrayList<SocialNetwork>();
		}
		return this.sns;
	}

	public void setSns(List<SocialNetwork> value) {
		this.sns = value;
	}
}
