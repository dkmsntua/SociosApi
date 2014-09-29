package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.ObjectId;
import objects.main.SociosException;

public class ObjectIdContainer
{
	protected ObjectId objectId;
	protected List<SociosException> exceptions;
	protected String elapsedTime;

	public ObjectId getObjectId()
	{
		return objectId;
	}

	public void setObjectId(ObjectId value)
	{
		this.objectId = value;
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
