package com.jfixby.r3.api.activity.layer;

import com.jfixby.r3.api.activity.LayerBasedComponent;

public interface TreeLayer<T> extends LayerBasedComponent {

	void detatchComponent(Component component);

	void attachComponent(Component component, T component_value);

}
