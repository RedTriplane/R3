
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.uiact.UIAction;
import com.jfixby.r3.uiact.UIActionStatus;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedActionTask<T> extends UIEvent implements UIActionStatus {

	private final UIAction<T> action;
	private boolean called = false;
	private final RedUIManager redUIManager;
	private UnitManager current_unit_man;
	private T ui;

	public RedActionTask (final RedUIManager redUIManager, final UIAction<T> action) {
		this.action = Debug.checkNull("action", action);
		this.redUIManager = redUIManager;
	}

	@Override
	public void go () {
		this.current_unit_man = this.redUIManager.getCurrent();
		final Unit unit = this.current_unit_man.getUnit();
		if (unit instanceof Unit) {
			try {
				this.ui = (T)unit;
			} catch (final Throwable e) {
				Err.reportError("Unit<" + unit + "> must implement UnitFunctionality");
			}
		} else {
			Err.reportError("Unit<" + unit + "> must implement UnitFunctionality");
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

	public UIActionStatus getStatus () {
		return this;
	}

}
