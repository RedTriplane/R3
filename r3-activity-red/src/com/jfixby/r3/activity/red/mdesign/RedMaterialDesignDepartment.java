
package com.jfixby.r3.activity.red.mdesign;

import com.jfixby.r3.activity.api.mtdesign.ButtonList;
import com.jfixby.r3.activity.api.mtdesign.ButtonListSpecs;
import com.jfixby.r3.activity.api.mtdesign.Drawer;
import com.jfixby.r3.activity.api.mtdesign.DrawerSpecs;
import com.jfixby.r3.activity.api.mtdesign.MaterialDesignDepartment;
import com.jfixby.r3.activity.red.RedComponentsFactory;

public class RedMaterialDesignDepartment implements MaterialDesignDepartment {

	private final RedComponentsFactory master;

	public RedMaterialDesignDepartment (final RedComponentsFactory redComponentsFactory) {
		this.master = redComponentsFactory;
	}

	@Override
	public DrawerSpecs newDrawerSpecs () {
		return new DrawerSpecs();
	}

	@Override
	public ButtonListSpecs newButtonListSpecs () {
		return new ButtonListSpecs();
	}

	@Override
	public Drawer newDrawer (final DrawerSpecs mtds) {
		return new RedDrawer(this.master);
	}

	@Override
	public ButtonList newButtonList (final ButtonListSpecs mtds) {
		return new RedButtonList(this.master);
	}

}
