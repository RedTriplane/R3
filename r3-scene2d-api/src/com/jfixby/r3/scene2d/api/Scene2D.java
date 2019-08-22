
package com.jfixby.r3.scene2d.api;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.scarabei.api.ComponentInstaller;

public class Scene2D {
	static private ComponentInstaller<Scene2DComponent> componentInstaller = new ComponentInstaller<>("Scene2D");

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final void installComponent (final Scene2DComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final Scene2DComponent replaceComponent (final Scene2DComponent component_to_install) {
		return componentInstaller.replaceComponent(component_to_install);
	}

	public static Scene2DComponent deInstallCurrentComponent () {
		return componentInstaller.deInstallCurrentComponent();
	}

	public static final Scene2DComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final Scene2DComponent component () {
		return componentInstaller.getComponent();
	}

	public static Scene newScene (final ComponentsFactory components_factory, final Scene2DSpawningConfig config) {
		return invoke().newScene(components_factory, config);
	}

}
