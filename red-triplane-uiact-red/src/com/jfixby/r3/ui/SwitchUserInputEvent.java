
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.input.InputManager;
import com.jfixby.scarabei.api.err.Err;

public class SwitchUserInputEvent extends UIEvent {

	public static final String ALLOW = "ALLOW";
	public static final String DISABLE = "DISABLE";

	private final RedUIManager tintoUIManager;
	private UnitManager current_unit;
	private InputManager input_manager;
	private final String action;

	public SwitchUserInputEvent (final RedUIManager tintoUIManager, final String action) {
		this.tintoUIManager = tintoUIManager;
		this.action = action;

		if (this.action.equals(ALLOW)) {

		} else if (this.action.equals(DISABLE)) {

		} else {
			Err.reportError("Bad action: " + action);
		}
	}

	@Override
	public void go () {
		this.current_unit = this.tintoUIManager.getCurrent();
		final Unit unit = this.current_unit.getUnit();
		if (unit instanceof InputManager) {
			this.input_manager = (InputManager)unit;
		} else {
			Err.reportError("Unit<" + unit + "> must implement InputManager interface");
		}
		if (this.action.equals(ALLOW)) {
			this.input_manager.enableInput();
		} else if (this.action.equals(DISABLE)) {
			this.input_manager.enableInput();
		}
	}

	@Override
	public boolean isOver () {
		return true;
	}

}
