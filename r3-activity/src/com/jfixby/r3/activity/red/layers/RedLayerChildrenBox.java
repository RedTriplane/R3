
package com.jfixby.r3.activity.red.layers;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.r3.activity.api.user.KeyboardInputEventListener;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.api.user.ScreenChangeListener;
import com.jfixby.r3.api.render.Drawable;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.CollectionScanner;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedLayerChildrenBox {

	final private FastList<Component> all_components = new FastList<Component>();;
	final private FastList<Object> keyboard_listeners = new FastList<Object>();;
	final private FastList<Object> mouse_listeners = new FastList<Object>();;
	final private FastList<Object> update_listeners = new FastList<Object>();;
	final private FastList<Raster> raster = new FastList<Raster>();;
	final private FastList<Layer> child_layers = new FastList<Layer>();
	final private FastList<VisibleComponent> visible_components = new FastList<VisibleComponent>();
	final private FastList<Object> renderable = new FastList<Object>();;
	final private Map<Object, ScreenDimentionsUpdate> viewport_listeners = Collections.newMap();

	public void detatchComponents (final Collection<? extends Component> attachments) {
		this.all_components.removeAll(attachments);
		this.keyboard_listeners.removeAll(attachments);
		this.mouse_listeners.removeAll(attachments);
		this.update_listeners.removeAll(attachments);
		this.renderable.removeAll(attachments);
		this.raster.removeAll(attachments);
		this.viewport_listeners.removeAll(attachments);
		this.child_layers.removeAll(attachments);
		this.visible_components.removeAll(attachments);
	}

	public void attachComponents (final Collection<? extends Component> attachments) {
		Collections.scanCollection(attachments, this.attachments_scanner);
	}

	public boolean containsComponent (final Component component) {
		return this.all_components.contains(component);
	}

	public void detatchAllComponents () {
		this.all_components.clear();
		this.keyboard_listeners.clear();
		this.mouse_listeners.clear();
		this.update_listeners.clear();
		this.renderable.clear();
		this.raster.clear();
		this.viewport_listeners.clear();
		this.child_layers.clear();
		this.visible_components.clear();
	}

	public void detatchComponent (Component attachment) {
		this.all_components.remove(attachment);

		if (attachment instanceof LayerBasedComponent) {
			final LayerBasedComponent lbc = (LayerBasedComponent)attachment;
			final RedLayer root = (RedLayer)lbc.getRoot();
			attachment = root;
		}

		this.keyboard_listeners.remove(attachment);
		this.mouse_listeners.remove(attachment);
		this.update_listeners.remove(attachment);
		this.renderable.remove(attachment);
		this.raster.remove(attachment);
		this.child_layers.remove(attachment);
		this.visible_components.remove(attachment);
		this.viewport_listeners.remove(attachment);

	}

	final public void attachComponent (final Component attachment) {
		Debug.checkNull("attachment", attachment);
		if (this.all_components.contains(attachment)) {
			Err.reportError("Component is already attached: " + attachment);
		}
		this.all_components.add(attachment);

		if (attachment instanceof LayerBasedComponent) {
			final LayerBasedComponent lbc = (LayerBasedComponent)attachment;
			final RedLayer root = (RedLayer)lbc.getRoot();
			Debug.checkNull(attachment + ".getRoot()", root);

			this.keyboard_listeners.add(root);
			this.mouse_listeners.add(root);
			this.update_listeners.add(root);
			this.renderable.add(root);

			this.child_layers.add(root);
			this.visible_components.add(root);
			this.viewport_listeners.put(root, root.viewport_wrapper);
		}

		if (attachment instanceof RedLayer) {
			this.keyboard_listeners.add(attachment);
			this.mouse_listeners.add(attachment);
			this.update_listeners.add(attachment);
			this.renderable.add(attachment);
			this.child_layers.add((RedLayer)attachment);
			this.visible_components.add((RedLayer)attachment);
			final RedLayer layer = (RedLayer)attachment;
			this.viewport_listeners.put(attachment, layer.viewport_wrapper);
		}
		if (attachment instanceof KeyboardInputEventListener) {
			if (attachment instanceof LayerBasedComponent) {
				Err.reportError("Attachment: <" + attachment
					+ "> can't implement both U_OnKeyboardInputEventListener and LayerBasedComponent interfaces");
			}
			this.keyboard_listeners.add(attachment);
		}
		if (attachment instanceof MouseInputEventListener) {
			if (attachment instanceof LayerBasedComponent) {
				Err.reportError("Attachment: <" + attachment
					+ "> can't implement both U_OnMouseInputEventListener and LayerBasedComponent interfaces");
			}
			this.mouse_listeners.add(attachment);
		}

		if (attachment instanceof VisibleComponent) {
			this.visible_components.add((VisibleComponent)attachment);
		}

		if (attachment instanceof OnUpdateListener) {
			this.update_listeners.add(attachment);
		}

		if (attachment instanceof Drawable) {
			this.renderable.add(attachment);
		}

		if (attachment instanceof Raster) {
// this.renderable.add(attachment);
			this.raster.add((Raster)attachment);
		}

		if (attachment instanceof ScreenChangeListener) {
			final ScreenChangeListener listener = (ScreenChangeListener)attachment;
			final ScreenDimentionsUpdate wrapper = new ScreenDimentionsUpdate(listener);
			this.viewport_listeners.put(attachment, wrapper);
		}

	}

	final public FastList<Object> listActivityKeyboardListeners () {
		return this.keyboard_listeners;
	}

	final private CollectionScanner<Component> attachments_scanner = new CollectionScanner<Component>() {
		@Override
		final public void scanElement (final Component element, final long index) {
			RedLayerChildrenBox.this.attachComponent(element);
		}
	};

	final public FastList<Object> listActivityMouseListeners () {
		return this.mouse_listeners;
	}

	final public FastList<Layer> listChildLayers () {
		return this.child_layers;
	}

	final public Map<Object, ScreenDimentionsUpdate> listViewportListeners () {
		return this.viewport_listeners;
	}

	final public FastList<Object> listUpdateListeners () {
		return this.update_listeners;
	}

	public final FastList<Object> listRenderableComponents () {
		return this.renderable;
	}

	final public FastList<Component> listAll () {
		return this.all_components;
	}

	final public FastList<VisibleComponent> listVisibleComponents () {
		return this.visible_components;
	}

	public FastList<Raster> listRaster () {
		return this.raster;
	}

	public Component getElementAt (final int i) {
		return this.all_components.getElementAt(i);
	}

	public int size () {
		return this.all_components.size();
	}

	public void print (final String tag) {
		this.all_components.print(tag);
	}

}
