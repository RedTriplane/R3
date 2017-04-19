
package com.jfixby.r3.api.ui.unit.user;

import com.jfixby.r3.api.ui.unit.layer.Component;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.log.L;

public class KeyboardInputEventListener implements Component {

	public boolean onKeyDown (final Key key) {
		L.d(this + ".onKeyDown", key);
		return false;
	}

	public boolean onKeyUp (final Key key) {
		L.d(this + ".onKeyUp", key);
		return false;
	}

	public boolean onCharTyped (final char char_typed) {
		L.d(this + ".onCharTyped", char_typed);
		return false;
	}

}
