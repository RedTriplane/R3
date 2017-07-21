
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.spawn.ActivityListener;
import com.jfixby.r3.activity.api.spawn.Intent;
import com.jfixby.r3.activity.api.spawn.IntentStack;
import com.jfixby.scarabei.api.assets.ID;

public class RedActivityMachineIntent implements Intent {

	private final ID intent_id;
	private ActivityListener listener;
	private final IntentStack stack;

	public RedActivityMachineIntent (final ID intent_id, final IntentStack stack) {
		this.intent_id = intent_id;
		this.stack = stack;
	}

	@Override
	public ID getActivityClassID () {
		return this.intent_id;
	}

	@Override
	public String toString () {
		return "Intent[" + this.intent_id + "]";
	}

	@Override
	public void setActivityListener (final ActivityListener listener) {
		this.listener = listener;
	}

	@Override
	public ActivityListener getActivityListener () {
		return this.listener;
	}

	@Override
	public IntentStack getStack () {
		return this.stack;
	}

}
