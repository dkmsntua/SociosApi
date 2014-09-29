package helper.utilities;

import java.util.List;
import javax.xml.datatype.XMLGregorianCalendar;
import objects.enums.LicenseType;
import objects.filters.ActivityFilter;
import objects.filters.AddressFilter;
import objects.filters.AreaFilter;
import objects.filters.DateTimeFilter;
import objects.filters.LocationFilter;
import objects.filters.MediaItemFilter;
import objects.filters.PersonFilter;

public class FilterUtilities
{
	public static PersonFilter getPersonFilter(String chain)
	{
		List<String> keywords = Utilities.getStringList(chain);
		PersonFilter result = new PersonFilter();
		result.getKeywords().addAll(keywords);
		return result;
	}

	public static MediaItemFilter getMediaItemFilter(String from, String to, String keywords, String country, String lat, String lon, String rad, String lang,
			String lic)
	{
		MediaItemFilter result = new MediaItemFilter();
		DateTimeFilter dtf = new DateTimeFilter();
		XMLGregorianCalendar fromCalendar = ParseUtilities.getCalendar(from);
		dtf.setFrom(fromCalendar);
		XMLGregorianCalendar toCalendar = ParseUtilities.getCalendar(to);
		dtf.setFrom(toCalendar);
		result.setCreated(dtf);
		List<String> keywordList = Utilities.getStringList(keywords);
		result.getKeywords().addAll(keywordList);
		LocationFilter lf = new LocationFilter();
		AddressFilter addressFilter = new AddressFilter();
		addressFilter.setCountry(country);
		AreaFilter areaFilter = new AreaFilter();
		try
		{
			Double latDouble = Double.parseDouble(lat);
			areaFilter.setLatitude(latDouble);
			Double lonDouble = Double.parseDouble(lon);
			areaFilter.setLongitude(lonDouble);
			Double radDouble = Double.parseDouble(rad);
			areaFilter.setRadius(radDouble);
		}
		catch (Exception exc)
		{
		}
		lf.setAddressFilter(addressFilter);
		lf.setAreaFilter(areaFilter);
		result.setLocation(lf);
		result.setLanguage(lang);
		if (Utilities.isValid(lic))
		{
			LicenseType lt = LicenseType.fromValue(lic.toUpperCase());
			result.setLicenseType(lt);
		}
		return result;
	}

	public static ActivityFilter getActivityFilter(String chain, String language)
	{
		ActivityFilter result = new ActivityFilter();
		if (Utilities.isValid(language))
		{
			result.setLanguage(language);
		}
		List<String> keywords = Utilities.getStringList(chain);
		if (Utilities.isValid(keywords))
		{
			result.getKeywords().addAll(keywords);
		}
		return result;
	}

	public static XMLGregorianCalendar getFrom(MediaItemFilter mediaFilter)
	{
		if (mediaFilter == null)
		{
			return null;
		}
		DateTimeFilter dateFilter = mediaFilter.getCreated();
		if (dateFilter == null)
		{
			return null;
		}
		XMLGregorianCalendar result = dateFilter.getFrom();
		if (result == null)
		{
			return null;
		}
		return result;
	}

	public static XMLGregorianCalendar getTo(MediaItemFilter mediaFilter)
	{
		if (mediaFilter == null)
		{
			return null;
		}
		DateTimeFilter dateFilter = mediaFilter.getCreated();
		if (dateFilter == null)
		{
			return null;
		}
		XMLGregorianCalendar result = dateFilter.getTo();
		if (result == null)
		{
			return null;
		}
		return result;
	}

	public static String getCountry(MediaItemFilter mediaFilter)
	{
		LocationFilter location = mediaFilter.getLocation();
		if (location == null)
		{
			return null;
		}
		AddressFilter addressFilter = location.getAddressFilter();
		if (addressFilter == null)
		{
			return null;
		}
		String result = addressFilter.getCountry();
		if (!Utilities.isValid(result))
		{
			return null;
		}
		return result;
	}

	public static <T> String getLanguage(T filter)
	{
		String result = null;
		if (filter instanceof MediaItemFilter)
		{
			result = ((MediaItemFilter) filter).getLanguage();
		}
		else if (filter instanceof ActivityFilter)
		{
			result = ((ActivityFilter) filter).getLanguage();
		}
		if (!Utilities.isValid(result))
		{
			return null;
		}
		result = result.trim();
		return result;
	}

	public static <T> List<String> getKeywords(T filter)
	{
		List<String> result = null;
		if (filter instanceof MediaItemFilter)
		{
			result = ((MediaItemFilter) filter).getKeywords();
		}
		else if (filter instanceof PersonFilter)
		{
			result = ((PersonFilter) filter).getKeywords();
		}
		else if (filter instanceof ActivityFilter)
		{
			result = ((ActivityFilter) filter).getKeywords();
		}
		if (!Utilities.clean(result))
		{
			return null;
		}
		return result;
	}

	public static boolean isValid(ActivityFilter activityFilter)
	{
		if (activityFilter == null)
		{
			return false;
		}
		List<String> keywords = activityFilter.getKeywords();
		if (Utilities.clean(keywords))
		{
			return true;
		}
		String language = activityFilter.getLanguage();
		if (Utilities.isValid(language))
		{
			return true;
		}
		return false;
	}

	public static boolean isValid(PersonFilter personFilter)
	{
		if (personFilter == null)
		{
			return false;
		}
		List<String> keywords = personFilter.getKeywords();
		boolean result = Utilities.clean(keywords);
		return result;
	}
}
