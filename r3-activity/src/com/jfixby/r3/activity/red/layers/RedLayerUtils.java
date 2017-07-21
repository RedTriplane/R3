
package com.jfixby.r3.activity.red.layers;

import java.util.Comparator;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.LayerUtilsComponent;
import com.jfixby.r3.activity.api.layer.TreeLayer;

public class RedLayerUtils implements LayerUtilsComponent {

	@Override
	public <T> TreeLayer<T> newTree (ComponentsFactory factory, Comparator<T> comparator) {
		return new RedBinaryLayerTree<T>(factory, comparator);
	}

}
