package com.jfixby.r3.fokker.input;

import com.jfixby.cmns.api.input.MouseButton;
import com.jfixby.cmns.api.input.MouseButtons;

public class FokkerMouseButtons implements MouseButtons {

	final MouseButton LEFT = new FokkerMouseButton("LEFT");

	final MouseButton RIGHT = new FokkerMouseButton("RIGHT");

	final MouseButton MIDDLE = new FokkerMouseButton("MIDDLE");

	@Override
	public MouseButton LEFT() {
		return LEFT;
	}

	@Override
	public MouseButton MIDDLE() {
		return MIDDLE;
	}

	@Override
	public MouseButton RIGHT() {
		return RIGHT;
	}

}
