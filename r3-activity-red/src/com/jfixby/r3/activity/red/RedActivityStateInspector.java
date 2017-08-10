
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.Activity;
import com.jfixby.r3.activity.api.ActivityStateInspector;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.util.EvaluationResult;
import com.jfixby.scarabei.api.util.StateSwitcher;
import com.jfixby.scarabei.api.util.Utils;

public class RedActivityStateInspector implements ActivityStateInspector {

	private final StateSwitcher<UNIT_STATES> states;
	private Activity unit;
	private EvaluationResult check;

	RedActivityStateInspector () {
		this.states = Utils.newStateSwitcher(UNIT_STATES.NULL);
		this.states.setDebugFlag(!true);
		if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
			this.states.setThrowErrorOnUnexpectedState(false);
		} else {
			this.states.setThrowErrorOnUnexpectedState(!false);
		}
	}

	public void setActivity (final Activity unit) {
		Debug.checkNull("unit", unit);
		this.unit = unit;
		this.states.setDebugName(unit.toString());
	}

	@Override
	public void onInit () {
		this.check = this.states.expectState(UNIT_STATES.NULL);
		// ckeck();
		this.states.switchState(UNIT_STATES.INIT_WAS_CALLED);
	}

	@Override
	public void onCreate () {
		this.check = this.states.expectState(UNIT_STATES.INIT_WAS_CALLED);
		// ckeck();
		this.states.switchState(UNIT_STATES.CREATE_WAS_CALLED);
	}

	@Override
	public void onStart () {
		this.check = this.states.expectState(UNIT_STATES.CREATE_WAS_CALLED);
		// if (!this.check.isOK()) {
		// String error_message = this.check.getErrorMessage();
		// L.d("Activity is in a wrong state", this.unit);
		// L.d(" ", error_message);
		// Err.reportError(
		// "Missing Activity.onCreate() call before Activity.onStart(). Ensure that
		// corresponding Activity<"
		// + unit
		// + "> has super().onCreate() call inside its onCreate() method.");
		// }
		this.states.switchState(UNIT_STATES.START_WAS_CALLED);
	}

	@Override
	public void onResume () {
		this.check = this.states.expectState(UNIT_STATES.PAUSE_WAS_CALLED);
		// ckeck();
		this.states.switchState(UNIT_STATES.START_WAS_CALLED);
	}

	// private void ckeck() {
	// if (!this.check.isOK()) {
	// String error_message = this.check.getErrorMessage();
	// L.d("Activity is in a wrong state", this.unit);
	// L.d(" ", error_message);
	// Err.reportError("Some unit method was called unexpectedly");
	// }
	// }

	@Override
	public void onPause () {
		this.check = this.states.expectState(UNIT_STATES.START_WAS_CALLED);
		this.states.switchState(UNIT_STATES.PAUSE_WAS_CALLED);
	}

	@Override
	public void onDestroy () {
		this.check = this.states.expectState(UNIT_STATES.PAUSE_WAS_CALLED);
		// ckeck();
		this.states.switchState(UNIT_STATES.DESTROY_WAS_CALLED);
	}

}
