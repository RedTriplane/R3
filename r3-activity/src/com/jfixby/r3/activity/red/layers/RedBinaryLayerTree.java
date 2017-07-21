
package com.jfixby.r3.activity.red.layers;

import java.util.Comparator;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.TreeLayer;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;

public class RedBinaryLayerTree<T> implements TreeLayer<T> {

	private final Map<Component, Node> adress = Collections.newMap();

	// ++++++++++++++++++++++++++

	private ComponentsFactory components_factory;
	private Comparator<T> comparator;
	private Layer root_layer;

	public RedBinaryLayerTree (ComponentsFactory factory, Comparator<T> comparator) {
		Debug.checkNull("factory", factory);
		Debug.checkNull("comparator", comparator);
		this.components_factory = factory;
		this.comparator = comparator;
		this.root_layer = factory.newLayer();

		root = new Node();
		root_layer.attachComponent(root.base);
	}

	final Node root;

	@Override
	public Layer getRoot () {
		return root_layer;
	}

	public void attachComponent (Component Component, T canvas_position) {
		Debug.checkNull("Component", Component);
		Debug.checkNull("canvas_position", canvas_position);
		root.push(Component, canvas_position);

	}

	public void detatchComponent (Component Component) {
		Node node = adress.get(Component);
		node.unSetComponent(Component);
	}

	class Node {
		final Layer base = components_factory.newLayer();
		final Layer left_mount = components_factory.newLayer();
		final Layer center_mount = components_factory.newLayer();
		final Layer right_mount = components_factory.newLayer();
		private Component Component;
		private T canvas_position;

		Node left = null;
		Node right = null;

		public Node () {
			base.closeInputValve();
			base.attachComponent(left_mount);
			base.attachComponent(center_mount);
			base.attachComponent(right_mount);
		}

		public void unSetComponent (Component Component) {
			this.Component = null;
			// this.canvas_position = null;
			this.center_mount.detatchAllComponents();
			adress.remove(Component);
		}

		public void push (Component Component, T canvas_position) {
			if (this.isEmpty()) {
				this.setComponent(Component, canvas_position);
				return;
			}
			// double new_y = canvas_position.getY();
			// double current_y = this.canvas_position.getY();

			// if (current_y > new_y) {
			if (comparator.compare(this.canvas_position, canvas_position) > 0) {
				pushLeft(Component, canvas_position);
			} else {
				pushRight(Component, canvas_position);
			}
		}

		private void pushRight (Component Component, T canvas_position) {
			if (right == null) {
				right = new Node();
				this.right_mount.attachComponent(right.base);
			}
			right.push(Component, canvas_position);
		}

		private void pushLeft (Component Component, T canvas_position) {
			if (left == null) {
				left = new Node();
				this.left_mount.attachComponent(left.base);
			}
			left.push(Component, canvas_position);
		}

		public void setComponent (Component Component, T canvas_position) {
			this.Component = Component;
			this.canvas_position = canvas_position;
			this.center_mount.attachComponent(Component);
			adress.put(Component, this);
		}

		public boolean isEmpty () {
			return canvas_position == null;
		}

	}
}
