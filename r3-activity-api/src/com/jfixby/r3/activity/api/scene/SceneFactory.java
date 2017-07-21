
package com.jfixby.r3.activity.api.scene;

public interface SceneFactory {

	public Scene2DSpawningConfig newSceneSpecs ();

	public Scene2DComponent newScene (Scene2DSpawningConfig specs);

}
