
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.input.Button;
import com.jfixby.r3.activity.api.input.ButtonSpecs;
import com.jfixby.r3.activity.api.input.CustomInput;
import com.jfixby.r3.activity.api.input.CustomInputSpecs;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.red.input.RedTouchArea;
import com.jfixby.r3.activity.red.input.RedTouchAreaSpecs;

public class RedUserInputFactory implements UserInputFactory {

	private final RedComponentsFactory master;

	public RedUserInputFactory (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
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
		return new CustomInputSpecs();
	}

	@Override
	public CustomInput newCustomInput (final CustomInputSpecs specs) {
		return new RedCustomInput(specs, this.master);
	}

	@Override
	public ButtonSpecs newButtonSpecs () {
		return new ButtonSpecs();
	}

	@Override
	public Button newButton (final ButtonSpecs specs) {
		return new RedButton(this.master, specs);
	}
}
