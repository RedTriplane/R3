
package com.jfixby.r3.ui.api.activity.spawn;

public class ActivitySpawningException extends Exception {

	public ActivitySpawningException (final Throwable e) {
		super(e);
	}

	public ActivitySpawningException (final String message) {
		super(message);
	}

	public ActivitySpawningException (final String message, final IntentStack stack) {
		super(message, stack);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -5040594272969667398L;

}
