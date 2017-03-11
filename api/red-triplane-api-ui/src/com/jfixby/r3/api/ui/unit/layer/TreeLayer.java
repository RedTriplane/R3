package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.r3.api.ui.unit.LayerBasedComponent;

public interface TreeLayer<T> extends LayerBasedComponent {

	void detatchComponent(Component component);

	void attachComponent(Component component, T component_value);

}
