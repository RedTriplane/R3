package com.jfixby.r3.api.activity.layer;

import java.util.Comparator;

import com.jfixby.r3.api.activity.ComponentsFactory;

public interface LayerUtilsComponent {

	<T> TreeLayer<T> newTree(ComponentsFactory factory,
			Comparator<T> comparator);

}
