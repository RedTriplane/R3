
package com.jfixby.r3.activity.red.input;

import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.input.Key;
import com.jfixby.scarabei.api.input.KeysList;
import com.jfixby.scarabei.api.input.UserInput;

public class PressedKeys {
	final boolean[] pressed;
	private int indexOf;

	PressedKeys () {
		final KeysList allKeys = UserInput.Keyboard().listAllKeys();
		this.pressed = new boolean[allKeys.size()];
		this.releaseAllKeys();
	}

	public boolean isKeyPressed (final Key key) {
		Debug.checkNull("key", key);
		final KeysList allKeys = UserInput.Keyboard().listAllKeys();
		this.indexOf = allKeys.indexOf(key);
		Debug.checkTrue("index of " + key, this.indexOf != -1);
		return this.pressed[this.indexOf];
	}

	public void press (final Key key) {
		Debug.checkNull("key", key);
		final KeysList allKeys = UserInput.Keyboard().listAllKeys();
		this.indexOf = allKeys.indexOf(key);
		Debug.checkTrue("index of " + key, this.indexOf != -1);
		this.pressed[this.indexOf] = true;
	}

	public void release (final Key key) {
		Debug.checkNull("key", key);
		final KeysList allKeys = UserInput.Keyboard().listAllKeys();
		this.indexOf = allKeys.indexOf(key);
		Debug.checkTrue("index of " + key, this.indexOf != -1);
		this.pressed[this.indexOf] = false;
	}

	public void releaseAllKeys () {
		for (; this.indexOf < this.pressed.length; this.indexOf++) {
			this.pressed[this.indexOf] = false;
		}
	}

}
