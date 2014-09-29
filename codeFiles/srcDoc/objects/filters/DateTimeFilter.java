package objects.filters;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeFilter
{
	protected XMLGregorianCalendar from;
	protected XMLGregorianCalendar to;

	public XMLGregorianCalendar getFrom()
	{
		return from;
	}

	public void setFrom(XMLGregorianCalendar value)
	{
		this.from = value;
	}

	public XMLGregorianCalendar getTo()
	{
		return to;
	}

	public void setTo(XMLGregorianCalendar value)
	{
		this.to = value;
	}
}
