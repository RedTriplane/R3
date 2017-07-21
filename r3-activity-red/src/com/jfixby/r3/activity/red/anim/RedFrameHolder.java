
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.debug.Debug;

public class RedFrameHolder {

	private VisibleComponent component;
	private long from;
	private long to;

	public VisibleComponent getComponent () {
		return this.component;
	}

	public void setComponent (final VisibleComponent component) {
		this.component = component;
	}

	public void setFrameTime (final long from, final long to) {
		Debug.checkTrue("frameTime", to - from > 0);
		this.from = from;
		this.to = to;
	}

	public void show () {
		this.component.show();
	}

	public void hide () {
		this.component.hide();
	}

	public void setup (final long progress) {
		this.component.hide();
		if (this.from <= progress && this.to >= progress) {
			this.component.show();
		}
	}

}
