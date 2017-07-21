package com.jfixby.r3.activity.api.layer;

import java.util.Comparator;

import com.jfixby.r3.activity.api.ComponentsFactory;

public interface LayerUtilsComponent {

	<T> TreeLayer<T> newTree(ComponentsFactory factory,
			Comparator<T> comparator);

}
