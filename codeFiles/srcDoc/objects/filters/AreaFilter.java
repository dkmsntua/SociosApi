package objects.filters;

public class AreaFilter {
	private Double latitude;
	private Double longitude;
	private Double radius;

	public AreaFilter() {
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

	public Double getRadius() {
		return radius;
	}

	public void setRadius(Double value) {
		this.radius = value;
	}
}
