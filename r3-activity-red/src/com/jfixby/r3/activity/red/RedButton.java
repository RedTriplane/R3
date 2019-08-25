
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.input.Button;
import com.jfixby.r3.activity.api.input.ButtonSpecs;
import com.jfixby.r3.activity.api.input.MouseAwayEvent;
import com.jfixby.r3.activity.api.input.MouseExitEvent;
import com.jfixby.r3.activity.api.input.MouseMovedEvent;
import com.jfixby.r3.activity.api.input.MouseScrolledEvent;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.input.TouchDownEvent;
import com.jfixby.r3.activity.api.input.TouchDraggedEvent;
import com.jfixby.r3.activity.api.input.TouchUpEvent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.log.L;

public class RedButton implements Button, LayerBasedComponent {

	private final Layer root;
	private final OffsetProjection parallaxPosition;
	private final MouseInputEventListener listener = new MouseInputEventListener() {
		@Override
		public boolean onMouseMoved (final MouseMovedEvent event) {
			RedButton.this.onHover();
			return true;
		}

		@Override
		public boolean onTouchDown (final TouchDownEvent event) {
			RedButton.this.onPress();
			return true;
		}

		@Override
		public boolean onTouchUp (final TouchUpEvent event) {
			RedButton.this.onPressed();
			return true;
		}

		@Override
		public boolean onTouchDragged (final TouchDraggedEvent event) {
// onPressed();
			return true;
		}

		@Override
		public boolean onMouseExit (final MouseExitEvent event) {
			RedButton.this.onReleased();
			return true;
		}

		@Override
		public boolean onMouseAway (final MouseAwayEvent event) {
			RedButton.this.onReleased();
			return true;
		}

		@Override
		public boolean onMouseScrolled (final MouseScrolledEvent event) {
// L.d("" + this, event);
			return true;
		}
	};
	private final VisibleComponent onReleased;
	private final VisibleComponent onPressed;
	private final VisibleComponent onHover;
	private final VisibleComponent onPress;

	public RedButton (final RedComponentsFactory master, final ButtonSpecs specs) {
		this.root = master.newLayer();
		this.parallaxPosition = Geometry.getProjectionFactory().newOffset();
		this.root.setProjection(this.parallaxPosition);

		for (final TouchAreaSpecs ts : specs.touchAreas) {
			final TouchArea ta = master.getUserInputDepartment().newTouchArea(ts);
			this.root.attachComponent(ta);

			ta.setInputListener(this.listener);

		}

		this.onReleased = this.addIfNotNull(this.root, specs.onReleased, null);
		Debug.checkNull("onReleased", this.onReleased);
		this.onPressed = this.addIfNotNull(this.root, specs.onPressed, this.onReleased);
		this.onHover = this.addIfNotNull(this.root, specs.onHover, this.onReleased);
		this.onPress = this.addIfNotNull(this.root, specs.onPress, this.onPressed);

		this.onReleased();

	}

	private void onHover () {
		this.onPress.setVisible(false);
		this.onPressed.setVisible(false);
		this.onReleased.setVisible(false);
		this.onHover.setVisible(true);
		L.d("onHover");
	}

	private void onPress () {
		this.onHover.setVisible(false);
		this.onPressed.setVisible(false);
		this.onReleased.setVisible(false);
		this.onPress.setVisible(true);
		L.d("onPress");
	}

	private void onPressed () {
		this.onHover.setVisible(false);
		this.onPress.setVisible(false);
		this.onReleased.setVisible(false);
		this.onPressed.setVisible(true);
		L.d("onPressed");
	}

	private void onReleased () {
		this.onHover.setVisible(false);
		this.onPress.setVisible(false);
		this.onPressed.setVisible(false);
		this.onReleased.setVisible(true);
		L.d("onReleased");
	}

	private VisibleComponent addIfNotNull (final Layer root, final VisibleComponent raster, final VisibleComponent defaultValue) {
		if (raster != null) {
			root.attachComponent(raster);
			return raster;
		}
		return defaultValue;
	}

	@Override
	public Layer getRoot () {
		return this.root;
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
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
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
	public void setDebugRenderFlag (final boolean b) {
	}

	@Override
	public boolean getDebugRenderFlag () {
		return false;
	}

	@Override
	public void setPositionX (final double x) {
		this.parallaxPosition.setOffsetX(x);
	}

	@Override
	public void setPositionY (final double y) {
		this.parallaxPosition.setOffsetY(y);
	}

}
