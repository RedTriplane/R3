
package com.jfixby.r3.activity.red.layers;

import com.jfixby.r3.activity.api.user.ScreenChangeListener;
import com.jfixby.r3.api.screen.Screen;
import com.jfixby.r3.api.screen.ScreenDimentionsChecker;
import com.jfixby.scarabei.api.err.Err;

public class ScreenDimentionsUpdate {

	final private ScreenDimentionsChecker checker;

	final private ScreenChangeListener listener;

	final private RedLayer redLayer;

	public ScreenDimentionsUpdate (final ScreenChangeListener listener) {
		if (listener == null) {
			Err.throwNotImplementedYet();
		}
		this.listener = listener;
		this.redLayer = null;
		this.checker = Screen.newScreenDimentionsChecker();

	}

	public ScreenDimentionsUpdate (final RedLayer redLayer) {
		this.redLayer = redLayer;
		this.checker = null;
		this.listener = null;

	}

	public RedLayer getLayer () {
		return this.redLayer;
	}

	public ScreenChangeListener getListener () {
		return this.listener;
	}

	public void updateListener () {
		if (this.listener == null) {
			Err.throwNotImplementedYet();
		}
		if (this.checker.screenDimentionsHaveChanged()) {
			this.holder.updateScreenDimentions();
			this.listener.onScreenChanged(this.holder);
			this.checker.okGotIt();
		}
	}

	final ScreenDimentionsHolder holder = new ScreenDimentionsHolder();

}
