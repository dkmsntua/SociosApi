package helper.utilities;

import org.json.JSONArray;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map.Entry;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import com.sun.jersey.core.util.Base64;
import objects.enums.SocialNetwork;
import objects.main.Activity;
import objects.main.Comment;
import objects.main.MediaItem;
import objects.main.ObjectId;
import objects.main.Person;

public class Utilities
{
	private static final HashMap<String, String> countries;
	static
	{
		HashMap<String, String> map = new HashMap<String, String>();
		map.put("AF", "Afghanistan");
		map.put("AL", "Albania");
		map.put("DZ", "Algeria");
		map.put("AS", "American Samoa");
		map.put("AD", "Andorra");
		map.put("AO", "Angola");
		map.put("AI", "Anguilla ");
		map.put("AQ", "Antarctica");
		map.put("AG", "Antigua and Barbuda");
		map.put("AR", "Argentina");
		map.put("AM", "Armenia");
		map.put("AW", "Aruba");
		map.put("AU", "Australia");
		map.put("AT", "Austria");
		map.put("AZ", "Azerbaijan");
		map.put("BS", "Bahamas");
		map.put("BH", "Bahrain");
		map.put("BD", "Bangladesh");
		map.put("BB", "Barbados");
		map.put("BY", "Belarus");
		map.put("BE", "Belgium");
		map.put("BZ", "Belize");
		map.put("BJ", "Benin");
		map.put("BM", "Bermuda");
		map.put("BT", "Bhutan");
		map.put("BO", "'Bolivia, Plurinational State Of'  ");
		map.put("BQ", "'Bonaire, Saint Eustatius and Saba'");
		map.put("BA", "Bosnia and Herzegovina");
		map.put("BW", "Botswana");
		map.put("BV", "Bouvet Island");
		map.put("BR", "Brazil");
		map.put("IO", "British Indian Ocean Territory");
		map.put("BN", "Brunei Darussalam");
		map.put("BG", "Bulgaria");
		map.put("BF", "Burkina Faso");
		map.put("BI", "Burundi");
		map.put("KH", "Cambodia");
		map.put("CM", "Cameroon");
		map.put("CA", "Canada");
		map.put("CV", "Cape Verde  ");
		map.put("KY", "Cayman Islands");
		map.put("CF", "Central African Republic");
		map.put("TD", "Chad");
		map.put("CL", "Chile");
		map.put("CN", "China");
		map.put("CX", "Christmas Island");
		map.put("CC", "Cocos (Keeling) Islands");
		map.put("CO", "Colombia");
		map.put("KM", "Comoros");
		map.put("CG", "Congo");
		map.put("CD", "'Congo, The Democratic Republic Of The'");
		map.put("CK", "Cook Islands");
		map.put("CR", "Costa Rica");
		map.put("HR", "Croatia");
		map.put("CU", "Cuba");
		map.put("CW", "Curaçao");
		map.put("CY", "Cyprus");
		map.put("CZ", "Czech Republic");
		map.put("CI", "Côte D\'Ivoire");
		map.put("DK", "Denmark");
		map.put("DJ", "Djibouti");
		map.put("DM", "Dominica");
		map.put("DO", "Dominican Republic");
		map.put("EC", "Ecuador");
		map.put("EG", "Egypt");
		map.put("SV", "El Salvador");
		map.put("GQ", "Equatorial Guinea");
		map.put("ER", "Eritrea");
		map.put("EE", "Estonia");
		map.put("ET", "Ethiopia");
		map.put("FK", "Falkland Islands  (Malvinas)");
		map.put("FO", "Faroe Islands");
		map.put("FJ", "Fiji");
		map.put("FI", "Finland");
		map.put("FR", "France");
		map.put("GF", "French Guiana");
		map.put("PF", "French Polynesia");
		map.put("TF", "French Southern Territories");
		map.put("GA", "Gabon");
		map.put("GM", "Gambia");
		map.put("GE", "Georgia");
		map.put("DE", "Germany");
		map.put("GH", "Ghana ");
		map.put("GI", "Gibraltar");
		map.put("GR", "Greece");
		map.put("GL", "Greenland");
		map.put("GD", "Grenada");
		map.put("GP", "Guadeloupe");
		map.put("GU", "Guam");
		map.put("GT", "Guatemala");
		map.put("GG", "Guernsey");
		map.put("GN", "Guinea");
		map.put("GW", "Guinea-Bissau");
		map.put("GY", "Guyana");
		map.put("HT", "Haiti");
		map.put("HM", "Heard and McDonald Islands");
		map.put("VA", "Holy See (Vatican City State)");
		map.put("HN", "Honduras");
		map.put("HK", "Hong Kong");
		map.put("HU", "Hungary");
		map.put("IS", "Iceland");
		map.put("IN", "India");
		map.put("ID", "Indonesia");
		map.put("IR", "'Iran, Islamic Republic Of'");
		map.put("IQ", "Iraq");
		map.put("IE", "Ireland");
		map.put("IM", "Isle of Man");
		map.put("IL", "Israel");
		map.put("IT", "Italy");
		map.put("JM", "Jamaica");
		map.put("JP", "Japan");
		map.put("JE", "Jersey");
		map.put("JO", "Jordan");
		map.put("KZ", "Kazakhstan");
		map.put("KE", "Kenya");
		map.put("KI", "Kiribati");
		map.put("KP", "'Korea, Democratic People\'s Republic Of'");
		map.put("KR", "'Korea, Republic of'");
		map.put("KW", "Kuwait");
		map.put("KG", "Kyrgyzstan");
		map.put("LA", "Lao People\'s Democratic Republic");
		map.put("LV", "Latvia");
		map.put("LB", "Lebanon");
		map.put("LS", "Lesotho");
		map.put("LR", "Liberia");
		map.put("LY", "Libyan Arab Jamahiriya");
		map.put("LI", "Liechtenstein");
		map.put("LT", "Lithuania");
		map.put("LU", "Luxembourg");
		map.put("MO", "Macao");
		map.put("MK", "'Macedonia, the Former Yugoslav Republic Of'");
		map.put("MG", "Madagascar");
		map.put("MW", "Malawi");
		map.put("MY", "Malaysia");
		map.put("MV", "Maldives");
		map.put("ML", "Mali");
		map.put("MT", "Malta");
		map.put("MH", "Marshall Islands");
		map.put("MQ", "Martinique");
		map.put("MR", "Mauritania");
		map.put("MU", "Mauritius");
		map.put("YT", "Mayotte");
		map.put("MX", "Mexico");
		map.put("FM", "'Micronesia, Federated States Of'");
		map.put("MD", "'Moldova, Republic of'");
		map.put("MC", "Monaco");
		map.put("MN", "Mongolia");
		map.put("ME", "Montenegro");
		map.put("MS", "Montserrat");
		map.put("MA", "Morocco");
		map.put("MZ", "Mozambique");
		map.put("MM", "Myanmar");
		map.put("NA", "Namibia");
		map.put("NR", "Nauru");
		map.put("NP", "Nepal");
		map.put("NL", "Netherlands");
		map.put("AN", "Netherlands Antilles");
		map.put("NC", "New Caledonia");
		map.put("NZ", "New Zealand");
		map.put("NI", "Nicaragua");
		map.put("NE", "Niger");
		map.put("NG", "Nigeria");
		map.put("NU", "Niue");
		map.put("NF", "Norfolk Island");
		map.put("MP", "Northern Mariana Islands");
		map.put("NO", "Norway");
		map.put("OM", "Oman");
		map.put("PK", "Pakistan");
		map.put("PW", "Palau");
		map.put("PS", "'Palestinian Territory, Occupied'");
		map.put("PA", "Panama");
		map.put("PG", "Papua New Guinea");
		map.put("PY", "Paraguay");
		map.put("PE", "Peru");
		map.put("PH", "Philippines");
		map.put("PN", "Pitcairn");
		map.put("PL", "Poland");
		map.put("PT", "Portugal");
		map.put("PR", "Puerto Rico");
		map.put("QA", "Qatar");
		map.put("RO", "Romania");
		map.put("RU", "Russian Federation");
		map.put("RW", "Rwanda");
		map.put("RE", "Réunion");
		map.put("BL", "Saint Barthélemy");
		map.put("SH", "'Saint Helena, Ascension and Tristan Da Cunha'");
		map.put("KN", "Saint Kitts And Nevis");
		map.put("LC", "Saint Lucia");
		map.put("MF", "Saint Martin");
		map.put("PM", "Saint Pierre And Miquelon");
		map.put("VC", "Saint Vincent And The Grenedines");
		map.put("WS", "Samoa");
		map.put("SM", "San Marino");
		map.put("ST", "Sao Tome and Principe");
		map.put("SA", "Saudi Arabia");
		map.put("SN", "Senegal");
		map.put("RS", "Serbia");
		map.put("SC", "Seychelles");
		map.put("SL", "Sierra Leone");
		map.put("SG", "Singapore");
		map.put("SX", "Sint Maarten (Dutch part)");
		map.put("SK", "Slovakia");
		map.put("SI", "Slovenia");
		map.put("SB", "Solomon Islands");
		map.put("SO", "Somalia");
		map.put("ZA", "South Africa");
		map.put("GS", "South Georgia and the South Sandwich Islands");
		map.put("ES", "Spain");
		map.put("LK", "Sri Lanka");
		map.put("SD", "Sudan");
		map.put("SR", "Suriname");
		map.put("SJ", "Svalbard And Jan Mayen");
		map.put("SZ", "Swaziland");
		map.put("SE", "Sweden");
		map.put("CH", "Switzerland");
		map.put("SY", "Syrian Arab Republic  ");
		map.put("TW", "'Taiwan, Province Of China'");
		map.put("TJ", "Tajikistan");
		map.put("TZ", "'Tanzania, United Republic of'");
		map.put("TH", "Thailand");
		map.put("TL", "Timor-Leste");
		map.put("TG", "Togo");
		map.put("TK", "Tokelau");
		map.put("TO", "Tonga");
		map.put("TT", "Trinidad and Tobago");
		map.put("TN", "Tunisia");
		map.put("TR", "Turkey");
		map.put("TM", "Turkmenistan");
		map.put("TC", "Turks and Caicos Islands");
		map.put("TV", "Tuvalu");
		map.put("UG", "Uganda");
		map.put("UA", "Ukraine");
		map.put("AE", "United Arab Emirates");
		map.put("GB", "United Kingdom");
		map.put("US", "United States");
		map.put("UM", "United States Minor Outlying Islands");
		map.put("UY", "Uruguay");
		map.put("UZ", "Uzbekistan");
		map.put("VU", "Vanuatu");
		map.put("VE", "'Venezuela, Bolivarian Republic of' ");
		map.put("VN", "Viet Nam");
		map.put("VG", "'Virgin Islands, British'");
		map.put("VI", "'Virgin Islands, U.S.'");
		map.put("WF", "Wallis and Futuna");
		map.put("EH", "Western Sahara");
		map.put("YE", "Yemen");
		map.put("ZM", "Zambia");
		map.put("ZW", "Zimbabwe");
		map.put("AX", "Åland Islands");
		countries = map;
	}

	private static String capitalizeFirst(String str)
	{
		if (!isValid(str))
		{
			return null;
		}
		String result = "";
		String base = str.trim().toLowerCase();
		String[] tokens = base.split(" ");
		for (String token : tokens)
		{
			if (!token.isEmpty())
			{
				String first = token.substring(0, 1);
				first = first.toUpperCase();
				String second = token.substring(1, token.length());
				String total = first + second;
				result += total + " ";
			}
		}
		result = result.trim();
		return result;
	}

	public static boolean clean(List<String> lista)
	{
		if (!isValid(lista))
		{
			return false;
		}
		List<String> existing = new ArrayList<String>();
		for (String str : lista)
		{
			if (isValid(str) && !existing.contains(str.trim()))
			{
				existing.add(str.trim());
			}
		}
		lista.removeAll(lista);
		lista.addAll(existing);
		return true;
	}

	public static boolean clean(ObjectId objectId)
	{
		if (objectId == null || !isValid(objectId.getId()) || objectId.getSocialNetwork() == null)
		{
			return false;
		}
		objectId.setId(objectId.getId().trim());
		return true;
	}

	@SuppressWarnings("unchecked")
	public static <T> boolean cleanDuplicates(List<T> lista)
	{
		if (!isValid(lista))
		{
			return false;
		}
		List<String> existingIds = new ArrayList<String>();
		List<T> existingItems = new ArrayList<T>();
		String id = "";
		for (Object item : lista)
		{
			if (item instanceof MediaItem)
			{
				MediaItem mediaItem = (MediaItem) item;
				id = mediaItem.getId();
			}
			else if (item instanceof Person)
			{
				Person person = (Person) item;
				id = person.getId();
			}
			else if (item instanceof Activity)
			{
				Activity activity = (Activity) item;
				id = activity.getId();
			}
			else if (item instanceof Comment)
			{
				Comment comment = (Comment) item;
				id = comment.getId();
			}
			else
			{
				continue;
			}
			if (!isValid(id))
			{
				existingItems.add((T) item);
			}
			else
			{
				if (!existingIds.contains(id))
				{
					existingIds.add(id);
					existingItems.add((T) item);
				}
			}
		}
		lista.removeAll(lista);
		lista.addAll(existingItems);
		return true;
	}

	public static boolean cleanObjectIdList(List<ObjectId> lista)
	{
		if (!isValid(lista))
		{
			return false;
		}
		List<ObjectId> objectIds = new ArrayList<ObjectId>();
		for (ObjectId objectId : lista)
		{
			if (!clean(objectId))
			{
				continue;
			}
			else
			{
				objectIds.add(objectId);
			}
		}
		lista.removeAll(lista);
		lista.addAll(objectIds);
		if (lista.isEmpty())
		{
			return false;
		}
		return true;
	}

	public static String computeSignature(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException, Exception
	{
		SecretKey secretKey = null;
		byte[] keyBytes = keyString.getBytes();
		secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");
		Mac mac = Mac.getInstance("HmacSHA1");
		mac.init(secretKey);
		byte[] text = baseString.getBytes();
		byte[] foo = mac.doFinal(text);
		String result = new String(Base64.encode(foo)).trim();
		return result;
	}

	public static String encodeKeys(String consumerKey, String consumerSecret)
	{
		try
		{
			String encodedConsumerKey = URLEncoder.encode(consumerKey, "UTF-8");
			String encodedConsumerSecret = URLEncoder.encode(consumerSecret, "UTF-8");
			String fullKey = encodedConsumerKey + ":" + encodedConsumerSecret;
			String encodedBytes = new String(Base64.encode(fullKey.getBytes()));
			return encodedBytes;
		}
		catch (Exception exc)
		{
			return null;
		}
	}

	public static String getChain(List<String> tokens)
	{
		return getChain(tokens, ",");
	}

	public static String getChain(List<String> tokens, String link)
	{
		if (!clean(tokens))
		{
			return null;
		}
		String result = "";
		int size = tokens.size();
		for (String token : tokens)
		{
			result += token;
			size--;
			if (size != 0)
			{
				result += link;
			}
		}
		return result;
	}

	public static String getChannelId(String id)
	{
		String result = null;
		if (id.length() == 22 && !id.startsWith("UC"))
		{
			result = "UC" + id;
		}
		return result;
	}

	public static String getCountryCode(String country)
	{
		if (!isValid(country))
		{
			return null;
		}
		String result = null;
		HashMap<String, String> countries = Utilities.countries;
		if (countries.containsKey(country.toUpperCase()))
		{
			result = country.toUpperCase();
			return result;
		}
		if (countries.containsValue(country))
		{
			result = Utilities.getKeyByValue(countries, country);
		}
		else
		{
			String capd = Utilities.capitalizeFirst(country);
			if (countries.containsValue(capd))
			{
				result = Utilities.getKeyByValue(countries, capd);
			}
		}
		return result;
	}

	private static String getKeyByValue(HashMap<String, String> map, String value)
	{
		for (Entry<String, String> entry : map.entrySet())
		{
			String thisValue = entry.getValue();
			if (thisValue.equals(value))
			{
				String key = entry.getKey();
				return key;
			}
		}
		return null;
	}

	public static ObjectId getObjectId(String id, String snName)
	{
		if (!isValid(id) || !isValid(snName))
		{
			return null;
		}
		ObjectId result = new ObjectId();
		SocialNetwork sn = AdaptorUtilities.getSocialNetwork(snName);
		if (sn == null)
		{
			return null;
		}
		result.setId(id);
		result.setSocialNetwork(sn);
		return result;
	}

	public static List<ObjectId> getObjectIds(ObjectId objectId)
	{
		if (!clean(objectId))
		{
			return null;
		}
		List<ObjectId> result = new ArrayList<ObjectId>();
		result.add(objectId);
		return result;
	}

	public static List<String> getStringList(List<ObjectId> objectIds)
	{
		List<String> result = new ArrayList<String>();
		for (ObjectId objectId : objectIds)
		{
			String id = objectId.getId();
			if (!result.contains(id))
			{
				result.add(id);
			}
		}
		return result;
	}

	public static List<String> getStringList(String chain)
	{
		if (!isValid(chain))
		{
			return null;
		}
		List<String> result = new ArrayList<String>();
		chain = chain.trim();
		String[] tokens = chain.split(",");
		for (String token : tokens)
		{
			result.add(token);
		}
		if (!clean(result))
		{
			return null;
		}
		return result;
	}

	public static boolean isValid(JSONArray array)
	{
		return (array != null && array.length() != 0);
	}

	public static <T> boolean isValid(List<T> lista)
	{
		if (lista == null || lista.size() == 0)
		{
			return false;
		}
		else
		{
			for (T object : lista)
			{
				if (object != null && object.toString().trim().length() != 0)
				{
					return true;
				}
			}
		}
		return false;
	}

	public static boolean isValid(String str)
	{
		boolean result = (str != null && !str.trim().isEmpty());
		return result;
	}

	public static boolean isValidLanguageCode(String code)
	{
		if (code.length() != 2)
		{
			return false;
		}
		String[] langsArray = Locale.getISOLanguages();
		List<String> langs = Arrays.asList(langsArray);
		code = code.toLowerCase();
		if (langs.contains(code))
		{
			return true;
		}
		return false;
	}
}
