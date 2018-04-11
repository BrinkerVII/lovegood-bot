package net.brinkervii.lovegood.exception;

public class NotAnAnnotationException extends Throwable {
	private final Exception innerException;

	public NotAnAnnotationException(Exception innerException) {
		this.innerException = innerException;
	}
}
