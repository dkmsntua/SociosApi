/*******************************************************************************
 * Copyright 2015 National Technical University of Athens
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package objects.main;

import objects.enums.SocialNetwork;

public class SociosException {
	private String description;
	private String message;
	private Integer faultCode;
	private String actorId;
	private SocialNetwork socialNetwork;

	public SociosException() {
	}

	public SociosException(String description, String message, int faultCode, String actorId, SocialNetwork sn) {
		this.description = description;
		this.message = message;
		this.faultCode = faultCode;
		this.actorId = actorId;
		this.socialNetwork = sn;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String value) {
		this.description = value;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String value) {
		this.message = value;
	}

	public Integer getFaultCode() {
		return faultCode;
	}

	public void setFaultCode(Integer value) {
		this.faultCode = value;
	}

	public String getActorId() {
		return actorId;
	}

	public void setActorId(String value) {
		this.actorId = value;
	}

	public SocialNetwork getSocialNetwork() {
		return socialNetwork;
	}

	public void setSocialNetwork(SocialNetwork value) {
		this.socialNetwork = value;
	}
}
