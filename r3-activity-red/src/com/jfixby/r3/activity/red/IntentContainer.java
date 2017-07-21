
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.spawn.Intent;

public class IntentContainer {

	private final Intent intent;

	public IntentContainer (final Intent intent) {
		this.intent = intent;
	}

	public Intent intent () {
		return this.intent;
	}

}
