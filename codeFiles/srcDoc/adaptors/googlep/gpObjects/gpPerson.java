package adaptors.googlep.gpObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class gpPerson
{
	public class GppName
	{
		public String familyName;
		public String formatted;
		public String givenName;
		public String honorificPrefix;
		public String honorificSuffix;
		public String middleName;

		public GppName()
		{
		}

		public GppName(JSONObject json)
		{
			String formatted = json.optString("formatted", null);
			this.formatted = formatted;
			String familyName = json.optString("familyName", null);
			this.familyName = familyName;
			String givenName = json.optString("givenName", null);
			this.givenName = givenName;
			String middleName = json.optString("middleName", null);
			this.middleName = middleName;
			String honorificPrefix = json.optString("honorificPrefix", null);
			this.honorificPrefix = honorificPrefix;
			String honorificSuffix = json.optString("honorificSuffix", null);
			this.honorificSuffix = honorificSuffix;
		}
	}
	public String aboutMe;
	public String birthday;
	public String cover;
	public String currentLocation;
	public String displayName;
	public String gender;
	public String id;
	public String image;
	public boolean isPlusUser;
	public String language;
	public GppName name;
	public String nickname;
	public String objectType;
	public List<String> placesLived;
	public String tagline;
	public String url;
	public List<String> urls;
	public boolean verified;

	public gpPerson()
	{
	}

	public gpPerson(JSONObject json)
	{
		String birthday = json.optString("birthday", null);
		this.birthday = birthday;
		String gender = json.optString("gender", null);
		this.gender = gender;
		JSONArray urlArray = json.optJSONArray("urls");
		if (Utilities.isValid(urlArray))
		{
			this.urls = new ArrayList<String>();
			for (int index = 0; index < urlArray.length(); index++)
			{
				JSONObject jsurl = urlArray.optJSONObject(index);
				if (jsurl != null)
				{
					String url = jsurl.optString("value", null);
					this.urls.add(url);
				}
			}
		}
		String objectType = json.optString("objectType", null);
		this.objectType = objectType;
		String id = json.optString("id", null);
		this.id = id;
		String displayName = json.optString("displayName", null);
		this.displayName = displayName;
		JSONObject jsname = json.optJSONObject("name");
		if (jsname != null)
		{
			GppName name = new GppName(jsname);
			this.name = name;
		}
		String tagline = json.optString("tagline", null);
		this.tagline = tagline;
		String aboutMe = json.optString("aboutMe", null);
		this.aboutMe = aboutMe;
		String currentLocation = json.optString("currentLocation", null);
		this.currentLocation = currentLocation;
		String url = json.optString("url", null);
		this.url = url;
		String image = ParseUtilities.doubleJsParse(json, "image", "url");
		this.image = image;
		JSONArray placesLivedArray = json.optJSONArray("placesLived");
		if (Utilities.isValid(placesLivedArray))
		{
			this.placesLived = new ArrayList<String>();
			for (int index = 0; index < placesLivedArray.length(); index++)
			{
				JSONObject jsplaceLived = placesLivedArray.optJSONObject(index);
				if (jsplaceLived != null)
				{
					String placeLived = jsplaceLived.optString("value", null);
					this.placesLived.add(placeLived);
				}
			}
		}
		boolean isPlusUser = json.optBoolean("isPlusUser");
		this.isPlusUser = isPlusUser;
		String language = json.optString("language", null);
		this.language = language;
		boolean verified = json.optBoolean("verified");
		this.verified = verified;
		JSONObject jscover = json.optJSONObject("cover");
		if (jscover != null)
		{
			String cover = ParseUtilities.doubleJsParse(jscover, "coverPhoto", "url");
			this.cover = cover;
		}
		String nickname = json.optString("nickname", null);
		this.nickname = nickname;
	}
}
