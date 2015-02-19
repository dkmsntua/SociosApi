package objects.filters;

import javax.xml.datatype.XMLGregorianCalendar;

public class DateTimeFilter {
	private XMLGregorianCalendar from;
	private XMLGregorianCalendar to;

	public DateTimeFilter() {
	}

	public XMLGregorianCalendar getFrom() {
		return from;
	}

	public void setFrom(XMLGregorianCalendar value) {
		this.from = value;
	}

	public XMLGregorianCalendar getTo() {
		return to;
	}

	public void setTo(XMLGregorianCalendar value) {
		this.to = value;
	}
}
