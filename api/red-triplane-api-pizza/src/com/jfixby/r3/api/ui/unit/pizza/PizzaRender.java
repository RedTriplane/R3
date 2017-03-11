package com.jfixby.r3.api.ui.unit.pizza;

import com.jfixby.scarabei.api.ComponentInstaller;

public class PizzaRender {

	static private ComponentInstaller<PizzaRenderComponent> componentInstaller = new ComponentInstaller<PizzaRenderComponent>(
			"PizzaRender");

	public static final void installComponent(
			PizzaRenderComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final PizzaRenderComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final PizzaRenderComponent component() {
		return componentInstaller.getComponent();
	}

}
