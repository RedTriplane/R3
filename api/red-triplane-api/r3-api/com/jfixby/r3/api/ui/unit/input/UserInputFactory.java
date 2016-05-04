
package com.jfixby.r3.api.ui.unit.input;

public interface UserInputFactory {

	ButtonSpecs newButtonSpecs ();

	Button newButton (ButtonSpecs button_specs);

	TouchArea newTouchArea (TouchAreaSpecs specs);

	TouchAreaSpecs newTouchAreaSpecs ();

	CustomInputSpecs newCustomInputSpecs ();

	CustomInput newCustomInput (CustomInputSpecs button_specs);

}
