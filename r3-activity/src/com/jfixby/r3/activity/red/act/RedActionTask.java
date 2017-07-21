
package com.jfixby.r3.activity.red.act;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.act.UIAction;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedActionTask<T> extends UIEvent {

	private final UIAction<T> action;
	private boolean called = false;
	private final RedUIManager redUIManager;
	private ActivityManager current_unit_man;
	private T ui;

	public RedActionTask (final RedUIManager redUIManager, final UIAction<T> action) {
		this.action = Debug.checkNull("action", action);
		this.redUIManager = redUIManager;
	}

	@Override
	public void go () {
		this.current_unit_man = this.redUIManager.getCurrent();
		final Activity unit = this.current_unit_man.getActivity();
		if (unit instanceof Activity) {
			try {
				this.ui = (T)unit;
			} catch (final Throwable e) {
				Err.reportError("Activity<" + unit + "> must implement ActivityFunctionality");
			}
		} else {
			Err.reportError("Activity<" + unit + "> must implement ActivityFunctionality");
		}

		this.action.start(this.ui);
		this.action.push(this.ui);
		this.called = true;
	}

	@Override
	public boolean isOver () {
		final boolean done = this.action.isDone(this.ui);
		if (done) {
			return true;
		}
		this.action.push(this.ui);
		return done;
	}

}
