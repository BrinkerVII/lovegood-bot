package net.brinkervii.lovegood.exception;

public class InitializationException extends Throwable {
	private final Exception innerException;

	public InitializationException(Exception innerException) {
		this.innerException = innerException;
	}

	@Override
	public void printStackTrace() {
		innerException.printStackTrace();
	}
}
