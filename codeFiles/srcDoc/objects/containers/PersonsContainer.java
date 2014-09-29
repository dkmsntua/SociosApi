package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.Person;
import objects.main.SociosException;

public class PersonsContainer
{
	protected List<Person> persons;
	protected List<SociosException> exceptions;
	protected String elapsedTime;

	public List<Person> getPersons()
	{
		if (persons == null)
		{
			persons = new ArrayList<Person>();
		}
		return this.persons;
	}

	public void setPersons(List<Person> value)
	{
		this.persons = value;
	}

	public List<SociosException> getExceptions()
	{
		if (exceptions == null)
		{
			exceptions = new ArrayList<SociosException>();
		}
		return this.exceptions;
	}

	public void setExceptions(List<SociosException> value)
	{
		this.exceptions = value;
	}

	public String getElapsedTime()
	{
		return elapsedTime;
	}

	public void setElapsedTime(String value)
	{
		this.elapsedTime = value;
	}
}
