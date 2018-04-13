
package com.jfixby.r3.activity.api.input;

public interface UserInputFactory {

	TouchArea newTouchArea (TouchAreaSpecs specs);

	TouchAreaSpecs newTouchAreaSpecs ();

	CustomInputSpecs newCustomInputSpecs ();

	CustomInput newCustomInput (CustomInputSpecs button_specs);

}
