
package com.jfixby.r3.activity.red.mdesign;

import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.material.api.ButtonList;
import com.jfixby.r3.material.api.ButtonListSpecs;
import com.jfixby.r3.material.api.Drawer;
import com.jfixby.r3.material.api.DrawerSpecs;
import com.jfixby.r3.material.api.MaterialDesignDepartment;

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
		return new RedDrawer(this.master, mtds);
	}

	@Override
	public ButtonList newButtonList (final ButtonListSpecs mtds) {
		return new RedButtonList(this.master, mtds);
	}

}
