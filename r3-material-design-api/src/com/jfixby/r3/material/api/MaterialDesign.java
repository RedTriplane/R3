
package com.jfixby.r3.material.api;

import com.jfixby.r3.material.api.btn.ButtonList;
import com.jfixby.r3.material.api.btn.ButtonListSpecs;
import com.jfixby.scarabei.api.ComponentInstaller;

public class MaterialDesign {
	static private ComponentInstaller<MaterialDesignComponent> componentInstaller = new ComponentInstaller<>("MaterialDesign");

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final void installComponent (final MaterialDesignComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final MaterialDesignComponent replaceComponent (final MaterialDesignComponent component_to_install) {
		return componentInstaller.replaceComponent(component_to_install);
	}

	public static MaterialDesignComponent deInstallCurrentComponent () {
		return componentInstaller.deInstallCurrentComponent();
	}

	public static final MaterialDesignComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final MaterialDesignComponent component () {
		return componentInstaller.getComponent();
	}

	public static DrawerSpecs newDrawerSpecs () {
		return invoke().newDrawerSpecs();
	}

	public static ButtonListSpecs newButtonListSpecs () {
		return invoke().newButtonListSpecs();
	}

	public static ButtonList newButtonList (final ButtonListSpecs mtds) {
		return invoke().newButtonList(mtds);
	}

}
