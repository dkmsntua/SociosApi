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
import objects.main.Activity;
import objects.main.SociosException;

public class ActivitiesContainer {
	private List<Activity> activities;
	private List<SociosException> exceptions;
	private String elapsedTime;

	public ActivitiesContainer() {
	}

	public List<Activity> getActivities() {
		if (activities == null) {
			activities = new ArrayList<Activity>();
		}
		return this.activities;
	}

	public void setActivities(List<Activity> value) {
		this.activities = value;
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
