
package com.jfixby.r3.ext.scene2d.api;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.scarabei.api.ComponentInstaller;

public class Scene2DSpawner {

	static private ComponentInstaller<Scene2DSpawnerComponent> componentInstaller = new ComponentInstaller<Scene2DSpawnerComponent>(
		"Scene2D");

	public static final void installComponent (final Scene2DSpawnerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final Scene2DSpawnerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final Scene2DSpawnerComponent component () {
		return componentInstaller.getComponent();
	}

	public static Scene2DComponent spawnScene (final ComponentsFactory components_factory, final Scene2DSpawningConfig config) {
		return invoke().spawnScene(components_factory, config);
	}

	public static void disposeScene (final Scene2DComponent loader_scene) {
		invoke().disposeScene(loader_scene);
	}

}
