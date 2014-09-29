package adaptors.youtube.ytObjects;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "errors", namespace = "http://schemas.google.com/g/2005")
public class ytXmlException
{
	@XmlElement(name = "error", type = ytXmlError.class)
	private List<ytXmlError> error = new ArrayList<ytXmlError>();

	public List<ytXmlError> getError()
	{
		return error;
	}

	public void setError(List<ytXmlError> error)
	{
		this.error = error;
	}
}
