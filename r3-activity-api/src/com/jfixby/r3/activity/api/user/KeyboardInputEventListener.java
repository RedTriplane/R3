
package com.jfixby.r3.activity.api.user;

import com.jfixby.r3.activity.api.input.CharTypedEvent;
import com.jfixby.r3.activity.api.input.KeyDownEvent;
import com.jfixby.r3.activity.api.input.KeyUpEvent;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.scarabei.api.log.L;

public class KeyboardInputEventListener implements Component {

	public boolean onKeyDown (final KeyDownEvent key) {
		L.d(this + ".onKeyDown", key);
		return false;
	}

	public boolean onKeyUp (final KeyUpEvent key) {
		L.d(this + ".onKeyUp", key);
		return false;
	}

	public boolean onCharTyped (final CharTypedEvent char_typed) {
		L.d(this + ".onCharTyped", char_typed);
		return false;
	}

}
