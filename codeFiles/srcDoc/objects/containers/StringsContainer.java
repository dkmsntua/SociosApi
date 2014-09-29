package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.SociosException;

public class StringsContainer
{
	protected List<String> strings;
	protected List<SociosException> exceptions;
	protected String elapsedTime;

	public List<String> getStrings()
	{
		if (strings == null)
		{
			strings = new ArrayList<String>();
		}
		return this.strings;
	}

	public void setStrings(List<String> value)
	{
		this.strings = value;
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
