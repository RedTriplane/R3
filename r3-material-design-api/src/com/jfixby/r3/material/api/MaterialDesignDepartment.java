
package com.jfixby.r3.material.api;

public interface MaterialDesignDepartment {

	DrawerSpecs newDrawerSpecs ();

	ButtonListSpecs newButtonListSpecs ();

	Drawer newDrawer (DrawerSpecs mtds);

	ButtonList newButtonList (ButtonListSpecs mtds);

	ButtonSpecs newButtonSpecs ();

	Button newButton (ButtonSpecs button_specs);

// @Override
// public ButtonSpecs newButtonSpecs () {
// return new InputSpecs();
// }
//
// @Override
// public Button newButton (final ButtonSpecs button_specs) {
// return new RedButton(button_specs, this.master);
// }

}
