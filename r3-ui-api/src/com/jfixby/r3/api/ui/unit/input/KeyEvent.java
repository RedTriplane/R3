
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.scarabei.api.input.Key;

public interface KeyEvent {
	Key getKey ();

	boolean is (Key other);

	boolean isKeyPressed (Key otherKey);
}
