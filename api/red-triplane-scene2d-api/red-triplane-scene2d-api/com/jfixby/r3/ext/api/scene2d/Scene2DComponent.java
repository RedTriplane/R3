package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.ext.api.scene2d.srlz.SceneStructure;
import com.jfixby.rana.api.pkg.PackageReader;

public interface Scene2DComponent {

	Scene2DSpawningConfig newSceneSpawningConfig();

	Scene spawnScene(ComponentsFactory components_factory,
			Scene2DSpawningConfig config);

	Scene spawnScene(ComponentsFactory components_factory,
			SceneStructure structure);

	public PackageReader getPackageReader();

	Scene spawnScene(ComponentsFactory components_factory, AssetID name);

}
