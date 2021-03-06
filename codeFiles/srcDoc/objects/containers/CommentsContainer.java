package objects.containers;

import java.util.ArrayList;
import java.util.List;
import objects.main.Comment;
import objects.main.SociosException;

public class CommentsContainer {
	private List<Comment> comments;
	private List<SociosException> exceptions;
	private String elapsedTime;

	public CommentsContainer() {
	}

	public List<Comment> getComments() {
		if (comments == null) {
			comments = new ArrayList<Comment>();
		}
		return this.comments;
	}

	public void setComments(List<Comment> value) {
		this.comments = value;
	}

	public List<SociosException> getExceptions() {
		if (exceptions == null) {
			exceptions = new ArrayList<SociosException>();
		}
		return this.exceptions;
	}

	public void setExceptions(List<SociosException> value) {
		this.exceptions = value;
	}

	public String getElapsedTime() {
		return elapsedTime;
	}

	public void setElapsedTime(String value) {
		this.elapsedTime = value;
	}
}
