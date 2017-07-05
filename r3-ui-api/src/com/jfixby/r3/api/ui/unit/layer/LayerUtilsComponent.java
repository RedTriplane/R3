package com.jfixby.r3.api.ui.unit.layer;

import java.util.Comparator;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface LayerUtilsComponent {

	<T> TreeLayer<T> newTree(ComponentsFactory factory,
			Comparator<T> comparator);

}
