
package com.jfixby.r3.activity.red.mdesign;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.red.ContainerOwner;
import com.jfixby.r3.activity.red.OnMouseInputEventListenerContainer;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.material.api.btn.Button;
import com.jfixby.r3.material.api.btn.ButtonSpecs;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;

public class RedButton implements Button, LayerBasedComponent, ContainerOwner {
	static enum MODE {
		BUTTON, SWITCH, CUSTOM
	}

	@Override
	public String toString () {
		return "Button[" + this.name + "]";
	}

	private final RedLayer root;

	List<TouchArea> touch_areas = Collections.newList();

	// private U_OnMouseInputEventListener listener;

	private final String name;

	private VisibleComponent on_pressed;

	private final VisibleComponent on_released;

	MODE mode = MODE.BUTTON;

	public RedButton (final ButtonSpecs button_specs, final RedComponentsFactory master) {
		this.root = master.newLayer();
		final Collection<TouchAreaSpecs> areas = button_specs.listTouchAreas();
		this.name = button_specs.getName();

		this.on_pressed = button_specs.getOnPressedRaster();
		this.on_released = button_specs.getOnReleasedRaster();

		if (this.on_pressed == null) {
			this.on_pressed = this.on_released;
		} else {
			this.on_pressed.hide();
		}

		if (this.on_released != null) {
			this.mode = MODE.BUTTON;
			final Layer wrap_layer = this.root.getComponentsFactory().newLayer();
			wrap_layer.attachComponent(this.on_pressed);
			this.root.attachComponent(wrap_layer);
			this.root.attachComponent(this.on_released);
		} else {
			this.mode = MODE.SWITCH;
			final Collection<Raster> options = button_specs.listOptions();
			for (int i = 0; i < options.size(); i++) {
				final VisibleComponent option_i = options.getElementAt(i);
				this.root.attachComponent(option_i);
			}
		}
		for (int i = 0; i < areas.size(); i++) {
			final TouchAreaSpecs area_specs = areas.getElementAt(i);
			// area_specs.setID(id);

			final TouchArea area = master.getUserInputDepartment().newTouchArea(area_specs);
// area.setDebugRenderFlag(!false);
			this.touch_areas.add(area);
		}
		this.root.attachComponents(this.touch_areas);

		for (int i = 0; i < this.touch_areas.size(); i++) {
			this.touch_areas.getElementAt(i).setInputListener(this.container);
		}

	}

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	private final OnMouseInputEventListenerContainer container = new OnMouseInputEventListenerContainer(this);

	@Override
	public MouseInputEventListener getInputListener () {
		return this.container.getListener();
	}

	@Override
	public void setInputListener (final MouseInputEventListener listener) {
		this.container.setListener(listener);

	}

	@Override
	public boolean doPressButton (final boolean onTouchDown) {
		if (this.mode == MODE.BUTTON) {
			this.on_pressed.show();
			this.on_released.hide();
		}
		if (this.mode == MODE.SWITCH) {
		}
		return onTouchDown;
	}

	@Override
	public boolean doReleaseButton (final boolean onTouchUp) {
		if (this.mode == MODE.BUTTON) {
			this.on_pressed.hide();
			this.on_released.show();
		}
		if (this.mode == MODE.SWITCH) {
		}
		return onTouchUp;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
		for (int i = 0; i < this.touch_areas.size(); i++) {
			this.touch_areas.getElementAt(i).setDebugRenderFlag(b);
		}
	}

	@Override
	public Collection<TouchArea> getTouchAreas () {
		return this.touch_areas;
	}

}
