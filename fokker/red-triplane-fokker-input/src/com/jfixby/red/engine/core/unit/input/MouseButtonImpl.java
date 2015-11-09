package com.jfixby.red.engine.core.unit.input;

import com.jfixby.cmns.api.input.MouseButton;

public final class MouseButtonImpl implements MouseButton {

	final String name;

	MouseButtonImpl(String name) {
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
