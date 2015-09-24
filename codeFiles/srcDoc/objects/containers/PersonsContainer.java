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
package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.Person;
import objects.main.SociosException;

public class PersonsContainer {
	private List<Person> persons;
	private List<SociosException> exceptions;
	private String elapsedTime;

	public PersonsContainer() {
	}

	public List<Person> getPersons() {
		if (persons == null) {
			persons = new ArrayList<Person>();
		}
		return this.persons;
	}

	public void setPersons(List<Person> value) {
		this.persons = value;
	}

	public List<SociosException> getExceptions() {
		if (exceptions == null) {
			exceptions = new ArrayList<SociosException>();
		}
		return this.exceptions;
	}

	public void setExceptions(List<SociosException> value) {
		this.exceptions = value;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String value) {
		this.elapsedTime = value;
	}
}
