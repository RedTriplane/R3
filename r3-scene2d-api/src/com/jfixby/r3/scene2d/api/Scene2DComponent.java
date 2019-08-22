
package com.jfixby.r3.scene2d.api;

import com.jfixby.r3.activity.api.ComponentsFactory;

public interface Scene2DComponent {

	public Scene newScene (final ComponentsFactory components_factory, final Scene2DSpawningConfig config);
}
