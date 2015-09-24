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
import objects.enums.LicenseType;
import objects.enums.SocialNetwork;

public class MediaItemFilter {
	private DateTimeFilter created;
	private List<String> keywords;
	private LocationFilter location;
	private String language;
	private LicenseType licenseType;
	private List<SocialNetwork> sns;

	public MediaItemFilter() {
	}

	public DateTimeFilter getCreated() {
		return created;
	}

	public void setCreated(DateTimeFilter value) {
		this.created = value;
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

	public LocationFilter getLocation() {
		return location;
	}

	public void setLocation(LocationFilter value) {
		this.location = value;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String value) {
		this.language = value;
	}

	public LicenseType getLicenseType() {
		return licenseType;
	}

	public void setLicenseType(LicenseType value) {
		this.licenseType = value;
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
