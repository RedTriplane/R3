
package com.jfixby.r3.activity.api.mtdesign;

public interface MaterialDesignDepartment {

	DrawerSpecs newDrawerSpecs ();

	ButtonListSpecs newButtonListSpecs ();

	Drawer newDrawer (DrawerSpecs mtds);

	ButtonList newButtonList (ButtonListSpecs mtds);

}
