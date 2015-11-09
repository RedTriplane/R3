package com.jfixby.r3.fokker.input;

import com.jfixby.cmns.api.input.UserInputComponent;

public class FokkerInput implements UserInputComponent {

	final FokkerKeyboard keyboard = new FokkerKeyboard();
	final FokkerMouseButtons mouse = new FokkerMouseButtons();

	@Override
	public com.jfixby.cmns.api.input.Keyboard Keyboard() {
		return keyboard;
	}

	@Override
	public com.jfixby.cmns.api.input.MouseButtons MouseButtons() {
		return mouse;
	}

}
