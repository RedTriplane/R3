package com.jfixby.r3.fokker.input;

import com.jfixby.cmns.api.input.Key;

public final class FokkerKeyCode implements Key {
	final String name;

	FokkerKeyCode(String name, FokkerKeyboard keys) {
		this.name = name;
		final FokkerKeysList list = (FokkerKeysList) keys.KEYS_LIST;
		list.add(this);
	}

	@Override
	public String toString() {
		return "KeyCode [" + name + "]";
	}

	public String getName() {
		return this.name;
	}

}
