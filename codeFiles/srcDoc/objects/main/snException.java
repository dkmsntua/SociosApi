package objects.main;

public class snException extends Exception {
	private static final long serialVersionUID = 1L;
	private String data;

	public snException() {
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
}
