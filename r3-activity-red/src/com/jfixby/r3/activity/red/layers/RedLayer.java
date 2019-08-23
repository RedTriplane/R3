
package com.jfixby.r3.activity.red.layers;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.RootLayer;
import com.jfixby.r3.activity.api.animation.EventsSequence;
import com.jfixby.r3.activity.api.animation.LayersAnimation;
import com.jfixby.r3.activity.api.animation.PositionsSequence;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.NamedComponent;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.util.Utils;
import com.jfixby.scarabei.api.util.path.RelativePath;

public class RedLayer implements Layer, RootLayer {
	private final boolean listening_for_updates = true;
	private boolean input_valve_is_open = true;
	private boolean visible = true;

	private final RedLayerChildrenBox layer_children;

	private RedCamera camera;
	// private RedShader shader;
	public ScreenDimentionsUpdate viewport_wrapper = new ScreenDimentionsUpdate(this);
	private String name = super.toString();
	private final ComponentsFactory factory;
	private Projection projection;

	public RedLayer (final ComponentsFactory factory) {
		this.factory = factory;
		this.layer_children = new RedLayerChildrenBox();

	}

	@Override
	public void hide () {
		this.visible = false;
	}

	@Override
	public void show () {
		this.visible = true;
	}

	@Override
	public boolean isVisible () {
		return this.visible;
	}

	@Override
	public void closeInputValve () {
		this.input_valve_is_open = false;
	}

	@Override
	public boolean inputValveIsOpen () {
		return this.input_valve_is_open;
	}

	@Override
	public void openInputValve () {
		this.input_valve_is_open = true;
	}

	public boolean isListeningForUpdates () {
		return this.listening_for_updates;
	}

	@Override
	public void setCamera (final CanvasCamera camera) {
		if (this.camera != null) {
			// ScreenChangeListener listener = this.camera
			// .getCameraViewportListener();
			// if (listener != null) {
			// this.layer_children.detatchComponent(listener);
			// }
		}
		this.camera = (RedCamera)camera;
		if (this.camera != null) {
			// ScreenChangeListener listener = this.camera
			// .getCameraViewportListener();
			// if (listener != null) {
			// this.layer_children.attachComponent(listener);
			// }
		}
	}

	@Override
	public void setProjection (final Projection projection) {
		this.projection = projection;
	}

	@Override
	public Projection getProjection () {
		return this.projection;
	}

	@Override
	public RedCamera getCamera () {
		return this.camera;
	}

	// @Override
	// public RedShader getShader() {
	// return this.shader;
	// }

	@Override
	public void attachComponent (final Component attachment) {
		this.layer_children.attachComponent(attachment);
	}

	@Override
	public void detatchComponent (final Component attachment) {
		this.layer_children.detatchComponent(attachment);
	}

	@Override
	public void detatchAllComponents () {
		this.layer_children.detatchAllComponents();
	}

	@Override
	public boolean containsComponent (final Component component) {
		return this.layer_children.containsComponent(component);
	}

	@Override
	public void attachComponents (final Collection<? extends Component> attachments) {
		this.layer_children.attachComponents(attachments);
	}

	@Override
	public void detatchComponents (final Collection<? extends Component> attachments) {
		this.layer_children.detatchComponents(attachments);
	}

	public FastList<Object> listActivityKeyboardListeners () {
		return this.layer_children.listActivityKeyboardListeners();
	}

	public FastList<Object> listActivityMouseListeners () {
		return this.layer_children.listActivityMouseListeners();
	}

	public Map<Object, ScreenDimentionsUpdate> listViewportListeners () {
		return this.layer_children.listViewportListeners();
	}

	public FastList<Object> listUpdateListeners () {
		return this.layer_children.listUpdateListeners();
	}

	final public FastList<Object> listRenderableComponents () {
		return this.layer_children.listRenderableComponents();
	}

	final public FastList<Object> listVocalizableComponents () {
		return this.layer_children.listVocalizableComponents();
	}

	@Override
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.factory;
	}

	@Override
	final public void print () {
		print(0, this);

	}

	@Override
	final public String toString () {
		return "Layer[" + this.getName() + "] " + isHidden(!this.isVisible());
	}

	private static void print (final int indent, final Component component) {
		final int order = 0;
		if (component instanceof Layer) {
			final Layer group = (Layer)component;
			final boolean is_visible = group.isVisible();

			L.d(prefix(indent) + group.toString());
			for (final Component child : group.listChildren()) {
				print(indent + 1, child);
			}
		} else if (component instanceof Raster) {
			final Raster raster = (Raster)component;
			final boolean is_visible = raster.isVisible();
			L.d(prefix(indent).append("Raster[").append(raster.getName()).append("]").toString(), "[" + raster.getAssetID() + "] "
				+ isHidden(!is_visible) + " (" + raster.getPositionX() + ", " + raster.getPositionY() + ")");
		} else if (component instanceof LayersAnimation) {
			final LayersAnimation animation = (LayersAnimation)component;
			L.d(prefix(indent) + animation.toString());
			for (final VisibleComponent child : animation.listFrames()) {
				print(indent + 1, child);
			}
		} else if (component instanceof PositionsSequence) {
			final PositionsSequence animation = (PositionsSequence)component;
			L.d(prefix(indent) + animation.toString());
		} else if (component instanceof EventsSequence) {
			final EventsSequence animation = (EventsSequence)component;
			L.d(prefix(indent) + animation.toString());
// } else if (component instanceof TextBar) {
// final TextBar type = (TextBar)component;
// L.d(prefix(indent) + type.toString());
		} else if (component instanceof LayerBasedComponent) {
			final LayerBasedComponent lb = (LayerBasedComponent)component;
			final Layer group = lb.getRoot();
			final boolean is_visible = group.isVisible();

			// L.d(prefix(indent) + component.toString() + " : "
			// + group.listChildren().size());
			L.d(prefix(indent).append(lb).append(isHidden(!is_visible)));
			print(indent + 1, group);

			// RedScene child = (RedScene) component;
			// boolean is_visible = child.isVisible();
			// L.d(prefix(indent) + "Raster[" + child.getName() + "]",
			// "[" + child.getName() + "] " + isHidden(!is_visible) + " ("
			// + child.getPositionX() + ", "
			// + child.getPositionY() + ")");
			// print(indent + 1, child.getRoot());
		} else {
			L.d(prefix(indent) + "" + component + "");
		}

	}

	private static StringBuilder prefix (final int prefix) {
		final StringBuilder result = new StringBuilder();

		for (int i = 0; i < prefix; i++) {
			result.append(" ");
		}

		return result;
	}

	private static String isHidden (final boolean isHidden) {
		if (isHidden) {
			return "(hidden)";
		} else {
			return "";
		}

	}

	@Override
	public void setVisible (final boolean b) {
		this.show();
		if (!b) {
			this.hide();
		}
	}

	@Override
	public <Q extends NamedComponent> Q findComponent (final String element_name) {
		for (int i = 0; i < this.layer_children.size(); i++) {
			final Component component = this.layer_children.getElementAt(i);
			if (component instanceof NamedComponent) {
				final Q q = (Q)component;
				if (Utils.equalObjects(q.getName(), element_name)) {
					return q;
				}
			}
		}
		return null;
	}

	@Override
	public <Q extends NamedComponent> Collection<Q> findComponents (final String element_name) {
		final List<Q> result = Collections.newList();
		for (int i = 0; i < this.layer_children.size(); i++) {
			final Component component = this.layer_children.getElementAt(i);
			if (component instanceof NamedComponent) {
				final Q q = (Q)component;
				if (Utils.equalObjects(q.getName(), element_name)) {
					result.add(q);
				}
			}
		}
		return result;
	}

	@Override
	public Collection<Component> listChildren () {
		final List<Component> result = Collections.newList();
		for (int i = 0; i < this.layer_children.size(); i++) {
			result.add(this.layer_children.getElementAt(i));
		}
		return result;
	}

	@Override
	public <Q extends Component> Q findComponent () {
		return (Q)this.layer_children.getElementAt(0);
	}

	@Override
	public void printChildrenList (final String tag) {
		L.d(tag, this.layer_children);
	}

	@Override
	public <Q extends NamedComponent> Collection<Q> findComponents (final ID element_name) {
		return this.findComponents(element_name.toString());
	}

	@Override
	public <Q extends NamedComponent> Q findComponent (final ID element_name) {
		return this.findComponent(element_name.toString());
	}

	@Override
	public <Q extends NamedComponent> Q findComponent (final RelativePath relative) {
		Debug.checkNull("relative", relative);
		NamedComponent pointer = this;
		for (int i = 0; i < relative.size(); i++) {
			final String stepName = relative.getStep(i);
			Layer layer = null;
			if (!(pointer instanceof Layer)) {
				return null;
			}
			layer = (Layer)pointer;
			pointer = layer.findComponent(stepName);
			if (pointer == null) {
				return null;
			}
		}
		return (Q)pointer;
	}

}
