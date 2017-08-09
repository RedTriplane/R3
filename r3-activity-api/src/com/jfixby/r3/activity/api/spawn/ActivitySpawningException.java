
package com.jfixby.r3.activity.api.spawn;

public class ActivitySpawningException extends Exception {

	public ActivitySpawningException (final Throwable e) {
		super(e);
	}

	public ActivitySpawningException (final String message) {
		super(message);
	}

	private static final long serialVersionUID = -5040594272969667398L;

}
