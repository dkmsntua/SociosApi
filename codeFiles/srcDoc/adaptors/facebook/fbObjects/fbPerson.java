package adaptors.facebook.fbObjects;

import org.json.JSONArray;
import org.json.JSONObject;
import helper.utilities.ParseUtilities;
import helper.utilities.Utilities;
import java.util.ArrayList;
import java.util.List;

public class fbPerson
{
	public class fbSchool
	{
		public String school;
		public String type;

		public fbSchool()
		{
		}

		public fbSchool(JSONObject json)
		{
			String type = json.optString("type", null);
			this.type = type;
			JSONObject jsSchool = json.optJSONObject("school");
			if (jsSchool != null)
			{
				String school = jsSchool.optString("name", null);
				this.school = school;
			}
		}
	}
	public String bio;
	public String birthday;
	public String cover;
	public List<fbSchool> education;
	public String email;
	public String first_name;
	public String gender;
	public String hometown;
	public String id;
	public String last_name;
	public String link;
	public String locale;
	public String location;
	public String middle_name;
	public String name;
	public String picture;
	public String quotes;
	public int timezone;
	public String updated_time;
	public String username;
	public List<String> work;

	public fbPerson()
	{
	}

	public fbPerson(JSONObject json)
	{
		String id = json.optString("id", null);
		this.id = id;
		String name = json.optString("name", null);
		this.name = name;
		String first_name = json.optString("first_name", null);
		this.first_name = first_name;
		String middle_name = json.optString("middle_name", null);
		this.middle_name = middle_name;
		String last_name = json.optString("last_name", null);
		this.last_name = last_name;
		String link = json.optString("link", null);
		this.link = link;
		String username = json.optString("username", null);
		this.username = username;
		String birthday = json.optString("birthday", null);
		this.birthday = birthday;
		String quotes = json.optString("quotes", null);
		this.quotes = quotes;
		String gender = json.optString("gender", null);
		this.gender = gender;
		String email = json.optString("email", null);
		this.email = email;
		int timezone = json.optInt("timezone", -1);
		this.timezone = timezone;
		String locale = json.optString("locale", null);
		this.locale = locale;
		String updated_time = json.optString("updated_time", null);
		this.updated_time = updated_time;
		String location = ParseUtilities.doubleJsParse(json, "location", "name");
		this.location = location;
		String bio = json.optString("bio", null);
		this.bio = bio;
		JSONObject jsPicture = json.optJSONObject("picture");
		if (jsPicture != null)
		{
			String picture = ParseUtilities.doubleJsParse(jsPicture, "data", "url");
			this.picture = picture;
		}
		String cover = ParseUtilities.doubleJsParse(json, "cover", "source");
		this.cover = cover;
		JSONArray workArray = json.optJSONArray("work");
		if (Utilities.isValid(workArray))
		{
			this.work = new ArrayList<String>();
			for (int index = 0; index < workArray.length(); index++)
			{
				JSONObject jswork = workArray.optJSONObject(index);
				if (jswork != null)
				{
					String employer = ParseUtilities.doubleJsParse(jswork, "employer", "name");
					if (Utilities.isValid(employer))
					{
						this.work.add(employer);
					}
				}
			}
		}
		JSONArray educationArray = json.optJSONArray("education");
		if (Utilities.isValid(educationArray))
		{
			this.education = new ArrayList<fbSchool>();
			for (int index = 0; index < educationArray.length(); index++)
			{
				JSONObject jsSchool = educationArray.optJSONObject(index);
				if (jsSchool != null)
				{
					fbSchool school = new fbSchool(jsSchool);
					if (school != null)
					{
						this.education.add(school);
					}
				}
			}
		}
		String hometown = ParseUtilities.doubleJsParse(json, "hometown", "name");
		this.hometown = hometown;
	}
}
