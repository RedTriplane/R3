package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.components.ComponentInstaller;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;
import com.jfixby.rana.api.pkg.PackageReader;

public class Scene2D {

	static private ComponentInstaller<Scene2DComponent> componentInstaller = new ComponentInstaller<Scene2DComponent>(
			"Scene2D");

	public static final void installComponent(
			Scene2DComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final Scene2DComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final Scene2DComponent component() {
		return componentInstaller.getComponent();
	}

	public static Scene2DSpawningConfig newSceneSpawningConfig() {
		return invoke().newSceneSpawningConfig();
	}

	public static Scene spawnScene(ComponentsFactory components_factory,
			Scene2DSpawningConfig config) {
		return invoke().spawnScene(components_factory, config);
	}

	public static Scene spawnScene(ComponentsFactory components_factory,
			SceneStructure structure) {
		return invoke().spawnScene(components_factory, structure);
	}

	public static PackageReader getPackageReader() {
		return invoke().getPackageReader();
	}

	public static Scene spawnScene(ComponentsFactory components_factory,
			AssetID name) {
		return invoke().spawnScene(components_factory, name);
	}

}
