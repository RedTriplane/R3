
package com.jfixby.r3.ui;

import com.jfixby.r3.api.ui.unit.Unit;
import com.jfixby.r3.api.ui.unit.camera.Shadow;
import com.jfixby.r3.uiact.ShadowStateListener;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.scarabei.api.sys.Sys;

public class FadeEvent extends UIEvent {
	public enum FADE_MODE {
		FADE_IN, FADE_OUT
	}

	private final FADE_MODE fadeInOrOut;
	private final long period;
	private final RedUIManager tintoUIManager;
	private UnitManager current_unit;

	float value_begin;
	float value_end;
	float value_current;

	@Override
	public String toString () {
		return "FadeEvent [" + this.fadeInOrOut + "] period=" + this.period + "";
	}

	public FadeEvent (final long period, final FADE_MODE fadeInOrOut, final RedUIManager tintoUIManager) {
		this.period = period;
		this.fadeInOrOut = fadeInOrOut;
		this.tintoUIManager = tintoUIManager;

	}

	ShadowStateListener shadow_state_listener = null;
	private long timestamp_begin;
	private long timestamp_end;
	private long timestamp_current;

	@Override
	public void go () {
		this.current_unit = this.tintoUIManager.getCurrent();
		if (this.current_unit == null) {
			Err.reportError("Current unit is null. Task failed " + this);
		}
		final Unit unit = this.current_unit.getUnit();
		if (unit instanceof ShadowStateListener) {
			this.shadow_state_listener = (ShadowStateListener)unit;
		} else {
			Err.reportError("Unit<" + unit + "> must implement ShadowStateListener interface");
		}
		if (this.fadeInOrOut == FADE_MODE.FADE_OUT) {
			this.value_begin = Shadow.ABSOLUTE_CLEAR;
			this.value_end = Shadow.ABSOLUTE_DARKNESS;
		} else {
			this.value_begin = Shadow.ABSOLUTE_DARKNESS;
			this.value_end = Shadow.ABSOLUTE_CLEAR;
		}
		this.value_current = this.value_begin;
		this.shadow_state_listener.beginShadowing(this.value_begin, this.value_end);
		this.shadow_state_listener.updateShadow(this.value_current);
		this.timestamp_begin = Sys.SystemTime().currentTimeMillis();
		this.timestamp_end = this.timestamp_begin + this.period;

		// L.d("Fade Event: " + timestamp_begin, timestamp_end);
		// L.d(" " + value_begin, value_end);

		this.updateShadow();
	}

	private void updateShadow () {
		this.timestamp_current = Sys.SystemTime().currentTimeMillis();
		if (this.timestamp_current > this.timestamp_end) {
			this.is_over = true;
		}
		this.timestamp_current = IntegerMath.limit(this.timestamp_begin, this.timestamp_current, this.timestamp_end);
		float alpha = (this.timestamp_current - this.timestamp_begin) * 1f / this.period;
		alpha = FloatMath.limit(0, alpha, 1);
		this.value_current = this.value_begin + (this.value_end - this.value_begin) * alpha;
		this.shadow_state_listener.updateShadow(this.value_current);

		// L.d("updateShadow", value_current);
	}

	boolean is_over = false;

	@Override
	public boolean isOver () {
		if (this.is_over) {
			this.shadow_state_listener.endShadowing(this.value_begin, this.value_end);
			return true;
		}
		this.updateShadow();
		return false;
	}

}
