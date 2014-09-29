package objects.filters;

import java.util.ArrayList;
import java.util.List;
import objects.enums.SocialNetwork;

public class ActivityFilter
{
	protected List<String> keywords;
	protected String language;
	protected List<SocialNetwork> sns;

	public List<String> getKeywords()
	{
		if (keywords == null)
		{
			keywords = new ArrayList<String>();
		}
		return this.keywords;
	}

	public void setKeywords(List<String> value)
	{
		this.keywords = value;
	}

	public String getLanguage()
	{
		return language;
	}

	public void setLanguage(String value)
	{
		this.language = value;
	}

	public List<SocialNetwork> getSns()
	{
		if (sns == null)
		{
			sns = new ArrayList<SocialNetwork>();
		}
		return this.sns;
	}

	public void setSns(List<SocialNetwork> value)
	{
		this.sns = value;
	}
}
