package adaptors.youtube.ytObjects;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlRootElement;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "error")
public class ytXmlError {
	private String domain;
	private String code;
	private String internalReason;

	public ytXmlError() {
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getInternalReason() {
		return internalReason;
	}

	public void setInternalReason(String internalReason) {
		this.internalReason = internalReason;
	}

	public String getDomain() {
		return domain;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}
}
