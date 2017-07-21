
package com.jfixby.r3.api.activity.scene;

public interface SceneFactory {

	public Scene2DSpawningConfig newSceneSpecs ();

	public Scene2DComponent newScene (Scene2DSpawningConfig specs);

}
