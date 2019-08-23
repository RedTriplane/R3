
package com.jfixby.r3.engine.api.render;

import com.jfixby.scarabei.api.ComponentInstaller;

public class RenderMachine {

	public static final String PRIMARY_BUFFER_TYPE = "FokkerRenderMachine.PRIMARY_BUFFER_TYPE";

	static private ComponentInstaller<RenderMachineComponent> componentInstaller = new ComponentInstaller<>("RenderMachine");

	public static final void installComponent (final RenderMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RenderMachineComponent component () {
		return componentInstaller.getComponent();
	}

	public static final RenderMachineComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static DefaultGraphicsAssets DefaultGraphicsAssets () {
		return component().DefaultGraphicsAssets();
	}

	public static ScreenShot makeScreenShot (final int areaWidth, final int areaHeight, final int areaX, final int areaY) {
		return component().makeScreenShot(areaWidth, areaHeight, areaX, areaY);
	}

	public static void deploy () {
		invoke().deploy();
	}

	public static void destroy () {
		invoke().destroy();
	}

}
