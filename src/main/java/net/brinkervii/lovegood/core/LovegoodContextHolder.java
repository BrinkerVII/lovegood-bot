package net.brinkervii.lovegood.core;

public class LovegoodContextHolder {
	private final static LovegoodContextHolder instance = new LovegoodContextHolder();
	private LovegoodContext context;

	public static LovegoodContextHolder getInstance() {
		return instance;
	}

	private LovegoodContextHolder() {
		this.context = new LovegoodContext();
	}

	public LovegoodContext getContext() {
		return context;
	}
}
