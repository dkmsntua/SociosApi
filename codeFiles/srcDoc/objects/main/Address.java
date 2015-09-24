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

public class Address {
	private String country;
	private String extendedAddress;
	private Double latitude;
	private Double longitude;
	private String postalCode;
	private String region;
	private String streetAddress;

	public Address() {
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String value) {
		this.country = value;
	}

	public String getExtendedAddress() {
		return extendedAddress;
	}

	public void setExtendedAddress(String value) {
		this.extendedAddress = value;
	}

	public Double getLatitude() {
		return latitude;
	}

	public void setLatitude(Double value) {
		this.latitude = value;
	}

	public Double getLongitude() {
		return longitude;
	}

	public void setLongitude(Double value) {
		this.longitude = value;
	}

	public String getPostalCode() {
		return postalCode;
	}

	public void setPostalCode(String value) {
		this.postalCode = value;
	}

	public String getRegion() {
		return region;
	}

	public void setRegion(String value) {
		this.region = value;
	}

	public String getStreetAddress() {
		return streetAddress;
	}

	public void setStreetAddress(String value) {
		this.streetAddress = value;
	}
}
