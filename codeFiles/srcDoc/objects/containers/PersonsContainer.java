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
