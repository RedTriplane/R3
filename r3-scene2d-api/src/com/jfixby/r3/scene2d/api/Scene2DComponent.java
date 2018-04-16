
package com.jfixby.r3.scene2d.api;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.Layer;

public interface Scene2DComponent {

	public Layer newScene (final ComponentsFactory components_factory, final Scene2DSpawningConfig config);
}
