package com.jfixby.r3.activity.api.layer;

import com.jfixby.r3.activity.api.LayerBasedComponent;

public interface TreeLayer<T> extends LayerBasedComponent {

	void detatchComponent(Component component);

	void attachComponent(Component component, T component_value);

}
