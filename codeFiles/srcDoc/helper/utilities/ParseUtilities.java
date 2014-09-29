package helper.utilities;

import org.json.JSONObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

public class ParseUtilities
{
	public static String doubleJsParse(JSONObject parent, String firstKey, String secondKey)
	{
		if (parent == null)
		{
			return null;
		}
		String result = null;
		JSONObject json = parent.optJSONObject(firstKey);
		if (json != null)
		{
			result = json.optString(secondKey, null);
		}
		return result;
	}

	public static int doubleJsParseInt(JSONObject parent, String firstKey, String secondKey)
	{
		if (parent == null)
		{
			return -1;
		}
		int result = -1;
		JSONObject json = parent.optJSONObject(firstKey);
		if (json != null)
		{
			result = json.optInt(secondKey, -1);
		}
		return result;
	}

	public static String doubleOrSimpleJsParse(JSONObject json, String name)
	{
		String result = doubleJsParse(json, name, "_content");
		if (result == null)
		{
			result = json.optString(name, null);
		}
		return result;
	}

	public static XMLGregorianCalendar getCalendar(long time)
	{
		GregorianCalendar gc = new GregorianCalendar();
		gc.setTimeInMillis(time);
		DatatypeFactory df = null;
		try
		{
			df = DatatypeFactory.newInstance();
		}
		catch (Exception exc)
		{
			return null;
		}
		return df.newXMLGregorianCalendar(gc);
	}

	public static XMLGregorianCalendar getCalendar(String str)
	{
		if (!Utilities.isValid(str))
		{
			return null;
		}
		XMLGregorianCalendar result = null;
		try
		{
			long fromLong = Long.parseLong(str);
			result = getCalendar(fromLong);
		}
		catch (Exception exc)
		{
		}
		if (result == null)
		{
			result = getCalendar(str, "yyyy-MM-dd");
		}
		return result;
	}

	public static XMLGregorianCalendar getCalendar(String str, String format)
	{
		if (!Utilities.isValid(str) || !Utilities.isValid(format))
		{
			return null;
		}
		DatatypeFactory df = null;
		GregorianCalendar gc = new GregorianCalendar();
		try
		{
			Date date = parseDate(str, format);
			gc.setTimeInMillis(date.getTime());
			df = DatatypeFactory.newInstance();
		}
		catch (Exception exc)
		{
			return null;
		}
		return df.newXMLGregorianCalendar(gc);
	}

	private static Date parseDate(String str, String format)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.ENGLISH);
			Date date = sdf.parse(str);
			return date;
		}
		catch (Exception exc)
		{
		}
		return null;
	}

	public static double parseDouble(String str)
	{
		double result = -1;
		try
		{
			result = Double.parseDouble(str);
		}
		catch (Exception exc)
		{
		}
		return result;
	}

	public static int parseInt(String str)
	{
		int result = -1;
		try
		{
			result = Integer.parseInt(str);
		}
		catch (Exception exc)
		{
		}
		return result;
	}
}
