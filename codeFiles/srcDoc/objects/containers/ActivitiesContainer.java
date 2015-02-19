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
