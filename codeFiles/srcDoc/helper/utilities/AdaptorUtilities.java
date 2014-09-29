package helper.utilities;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.xml.bind.JAXBElement;
import javax.xml.namespace.QName;
import objects.containers.ActivitiesContainer;
import objects.containers.CommentsContainer;
import objects.containers.MediaItemsContainer;
import objects.containers.ObjectIdContainer;
import objects.containers.PersonsContainer;
import objects.containers.StringsContainer;
import objects.enums.SocialNetwork;
import objects.interfaces.ISnsAdaptor;
import objects.main.ObjectId;
import adaptors.dailymotion.DailymotionAdaptor;
import adaptors.facebook.FacebookAdaptor;
import adaptors.flickr.FlickrAdaptor;
import adaptors.googlep.GooglepAdaptor;
import adaptors.instagram.InstagramAdaptor;
import adaptors.twitter.TwitterAdaptor;
import adaptors.youtube.YoutubeAdaptor;

public class AdaptorUtilities
{
	private static QName qname = new QName("http://sociosproject.eu", "SocIos");

	public static ISnsAdaptor getAdaptor(ObjectId objectId)
	{
		SocialNetwork network = objectId.getSocialNetwork();
		ISnsAdaptor result = getAdaptor(network);
		return result;
	}

	public static ISnsAdaptor getAdaptor(SocialNetwork sn)
	{
		if (sn.equals(SocialNetwork.FLICKR))
		{
			return new FlickrAdaptor();
		}
		if (sn.equals(SocialNetwork.FACEBOOK))
		{
			return new FacebookAdaptor();
		}
		else if (sn.equals(SocialNetwork.DAILYMOTION))
		{
			return new DailymotionAdaptor();
		}
		else if (sn.equals(SocialNetwork.INSTAGRAM))
		{
			return new InstagramAdaptor();
		}
		else if (sn.equals(SocialNetwork.TWITTER))
		{
			return new TwitterAdaptor();
		}
		else if (sn.equals(SocialNetwork.GOOGLEP))
		{
			return new GooglepAdaptor();
		}
		else if (sn.equals(SocialNetwork.YOUTUBE))
		{
			return new YoutubeAdaptor();
		}
		else return null;
	}

	public static List<List<ObjectId>> getLists(List<ObjectId> objectIds)
	{
		Map<SocialNetwork, List<ObjectId>> map = new HashMap<SocialNetwork, List<ObjectId>>();
		List<List<ObjectId>> result = new ArrayList<List<ObjectId>>();
		SocialNetwork network = null;
		for (ObjectId objectId : objectIds)
		{
			network = objectId.getSocialNetwork();
			if (!map.containsKey(network))
			{
				List<ObjectId> lista = new ArrayList<ObjectId>();
				lista.add(objectId);
				map.put(network, lista);
			}
			else
			{
				map.get(network).add(objectId);
			}
		}
		List<ObjectId> temp = null;
		for (Map.Entry<SocialNetwork, List<ObjectId>> entry : map.entrySet())
		{
			temp = entry.getValue();
			result.add(temp);
		}
		return result;
	}

	public static <T> Response getResponse(T container, String format)
	{
		ResponseBuilder builder = null;
		if (container instanceof PersonsContainer)
		{
			JAXBElement<PersonsContainer> jax = new JAXBElement<PersonsContainer>(qname, PersonsContainer.class, (PersonsContainer) container);
			builder = Response.ok(jax);
		}
		else if (container instanceof MediaItemsContainer)
		{
			JAXBElement<MediaItemsContainer> jax = new JAXBElement<MediaItemsContainer>(qname, MediaItemsContainer.class, (MediaItemsContainer) container);
			builder = Response.ok(jax);
		}
		else if (container instanceof ActivitiesContainer)
		{
			JAXBElement<ActivitiesContainer> jax = new JAXBElement<ActivitiesContainer>(qname, ActivitiesContainer.class, (ActivitiesContainer) container);
			builder = Response.ok(jax);
		}
		else if (container instanceof CommentsContainer)
		{
			JAXBElement<CommentsContainer> jax = new JAXBElement<CommentsContainer>(qname, CommentsContainer.class, (CommentsContainer) container);
			builder = Response.ok(jax);
		}
		else if (container instanceof ObjectIdContainer)
		{
			JAXBElement<ObjectIdContainer> jax = new JAXBElement<ObjectIdContainer>(qname, ObjectIdContainer.class, (ObjectIdContainer) container);
			builder = Response.ok(jax);
		}
		Response result = getResponse(builder, format);
		return result;
	}

	public static Response getResponse(StringsContainer stringsContainer, String format)
	{
		// th xreiazomai gia to allo project
		JAXBElement<StringsContainer> jax = new JAXBElement<StringsContainer>(qname, StringsContainer.class, stringsContainer);
		ResponseBuilder builder = Response.ok(jax);
		Response result = getResponse(builder, format);
		return result;
	}

	private static Response getResponse(ResponseBuilder builder, String format)
	{
		if (format != null && "JSON".equals(format.toUpperCase()))
		{
			builder.type(MediaType.APPLICATION_JSON);
		}
		else
		{
			builder.type(MediaType.APPLICATION_XML);
		}
		Response result = builder.build();
		return result;
	}

	public static SocialNetwork getSocialNetwork(String str)
	{
		SocialNetwork sn = null;
		try
		{
			sn = SocialNetwork.valueOf(str.trim().toUpperCase());
		}
		catch (Exception exc)
		{
		}
		return sn;
	}

	public static List<SocialNetwork> getSocialNetworks()
	{
		List<SocialNetwork> result = new ArrayList<SocialNetwork>();
		for (SocialNetwork socialNetwork : SocialNetwork.values())
		{
			result.add(socialNetwork);
		}
		return result;
	}

	public static List<SocialNetwork> getSocialNetworks(String sns)
	{
		if (!Utilities.isValid(sns))
		{
			return getSocialNetworks();
		}
		List<SocialNetwork> result = new ArrayList<SocialNetwork>();
		List<String> lista = Utilities.getStringList(sns);
		SocialNetwork temp = null;
		for (String str : lista)
		{
			temp = getSocialNetwork(str);
			if (temp != null)
			{
				result.add(temp);
			}
		}
		if (!Utilities.isValid(result))
		{
			return null;
		}
		return result;
	}
}
