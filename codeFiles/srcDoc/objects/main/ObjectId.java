package objects.main;

import objects.enums.SocialNetwork;

public class ObjectId {
	private String id;
	private SocialNetwork socialNetwork;

	public ObjectId() {
	}

	public String getId() {
		return id;
	}

	public void setId(String value) {
		this.id = value;
	}

	public SocialNetwork getSocialNetwork() {
		return socialNetwork;
	}

	public void setSocialNetwork(SocialNetwork value) {
		this.socialNetwork = value;
	}
}
