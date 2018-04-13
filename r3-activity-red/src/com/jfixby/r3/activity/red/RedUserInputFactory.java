
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.input.CustomInput;
import com.jfixby.r3.activity.api.input.CustomInputSpecs;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.red.input.InputSpecs;
import com.jfixby.r3.activity.red.input.RedButton;
import com.jfixby.r3.activity.red.input.RedTouchArea;
import com.jfixby.r3.activity.red.input.RedTouchAreaSpecs;
import com.jfixby.r3.material.api.Button;
import com.jfixby.r3.material.api.ButtonSpecs;

public class RedUserInputFactory implements UserInputFactory {

	private final RedComponentsFactory master;

	public RedUserInputFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public ButtonSpecs newButtonSpecs () {
		return new InputSpecs();
	}

	@Override
	public Button newButton (final ButtonSpecs button_specs) {
		return new RedButton(button_specs, this.master);
	}

	@Override
	public TouchArea newTouchArea (final TouchAreaSpecs specs) {
		return new RedTouchArea(specs, this.master);
	}

	@Override
	public TouchAreaSpecs newTouchAreaSpecs () {
		return new RedTouchAreaSpecs();
	}

	@Override
	public CustomInputSpecs newCustomInputSpecs () {
		return new InputSpecs();
	}

	@Override
	public CustomInput newCustomInput (final CustomInputSpecs specs) {
		return new RedCustomInput(specs, this.master);
	}
}
