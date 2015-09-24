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
package objects.main;

public class Name {
	private String additionalName;
	private String lastName;
	private String firstName;
	private String fullName;

	public Name() {
	}

	public String getAdditionalName() {
		return additionalName;
	}

	public void setAdditionalName(String value) {
		this.additionalName = value;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String value) {
		this.lastName = value;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String value) {
		this.firstName = value;
	}

	public String getFullName() {
		return fullName;
	}

	public void setFullName(String value) {
		this.fullName = value;
	}
}
