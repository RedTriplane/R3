package com.jfixby.r3.api.ui.unit.layer;

import java.util.Comparator;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.scarabei.api.ComponentInstaller;

public class LayerUtils {

	static private ComponentInstaller<LayerUtilsComponent> componentInstaller = new ComponentInstaller<LayerUtilsComponent>(
			"LayerUtils");

	public static final void installComponent(
			LayerUtilsComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final LayerUtilsComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final LayerUtilsComponent component() {
		return componentInstaller.getComponent();
	}

	public static final <T> TreeLayer<T> newTree(ComponentsFactory factory,
			Comparator<T> comparator) {
		return invoke().newTree(factory, comparator);
	}

}
