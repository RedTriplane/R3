
package com.jfixby.r3.fokker.api;

import com.jfixby.r3.api.ui.IntentStack;

public class UnitsSpawningException extends Exception {

	public UnitsSpawningException (final Throwable e) {
		super(e);
	}

	public UnitsSpawningException (final String message) {
		super(message);
	}

	public UnitsSpawningException (final String message, final IntentStack stack) {
		super(message, stack);
	}

	/**
	 *
	 */
	private static final long serialVersionUID = -5040594272969667398L;

}
