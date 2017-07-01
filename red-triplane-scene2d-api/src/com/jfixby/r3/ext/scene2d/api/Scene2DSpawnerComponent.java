
package com.jfixby.r3.ext.scene2d.api;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface Scene2DSpawnerComponent {

	Scene2DComponent spawnScene (ComponentsFactory components_factory, Scene2DSpawningConfig config);

	void disposeScene (Scene2DComponent loader_scene);

}
