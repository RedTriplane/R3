package com.jfixby.r3.fokker.input;

import com.jfixby.cmns.api.input.MouseButton;

public final class FokkerMouseButton implements MouseButton {

	final String name;

	FokkerMouseButton(String name) {
		this.name = name;

	}

	@Override
	public String toString() {
		return "MouseButton [" + name + "]";
	}

	@Override
	public String getName() {
		return name;
	}

}
