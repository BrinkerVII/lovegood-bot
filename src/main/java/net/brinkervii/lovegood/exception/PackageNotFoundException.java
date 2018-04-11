package net.brinkervii.lovegood.exception;

public class PackageNotFoundException extends Throwable {
	public PackageNotFoundException(String path) {
		super(path);
	}
}
