
package com.jfixby.r3.activity.red.input;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.geometry.RectangleComponent;
import com.jfixby.r3.activity.api.input.MouseExitEvent;
import com.jfixby.r3.activity.api.input.MouseMovedEvent;
import com.jfixby.r3.activity.api.input.TouchArea;
import com.jfixby.r3.activity.api.input.TouchAreaSpecs;
import com.jfixby.r3.activity.api.input.TouchDownEvent;
import com.jfixby.r3.activity.api.input.TouchDraggedEvent;
import com.jfixby.r3.activity.api.input.TouchUpEvent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class RedTouchArea implements TouchArea, LayerBasedComponent {
	RedTouchArea me = this;

	MouseInputEventListener u_listener = new MouseInputEventListener() {

		@Override
		public boolean onMouseMoved (final MouseMovedEvent event) {

			if (!RedTouchArea.this.shape.containsPoint(event.getCanvasPosition())) {
				return false;
			}
			return RedTouchArea.this.me.onMouseMoved(event);
		}

		@Override
		public boolean onTouchDown (final TouchDownEvent event) {
			final ReadOnlyFloat2 point = event.getCanvasPosition();
// L.d(point, RedTouchArea.this.shape.containsPoint(point) + " " + RedTouchArea.this.shape);
			if (!RedTouchArea.this.shape.containsPoint(point)) {
				return false;
			}
			return RedTouchArea.this.me.onTouchDown(event);
		}

		@Override
		public boolean onTouchUp (final TouchUpEvent event) {
			if (!RedTouchArea.this.shape.containsPoint(event.getCanvasPosition())) {
				return false;
			}
			return RedTouchArea.this.me.onTouchUp(event);
		}

		@Override
		public boolean onTouchDragged (final TouchDraggedEvent event) {
			if (!RedTouchArea.this.shape.containsPoint(event.getCanvasPosition())) {
				return false;
			}
			return RedTouchArea.this.me.onTouchDragged(event);
		}

		@Override
		public boolean onMouseExit (final MouseExitEvent event) {
			return RedTouchArea.this.me.onMouseExit(event);
		}

	};

	@Override
	public String toString () {
		return "TouchArea<" + this.root.getName() + ">=" + this.shape + "";
	}

	private final RedLayer root;

	private final RectangleComponent debug_rectangle;

	public RedTouchArea (final TouchAreaSpecs specs, final RedComponentsFactory master) {
		super();
		// id = specs.getID();
		// JUtils.checkNull("id", id);
		this.root = master.newLayer();

		this.root.setName(specs.getName());

		this.debug_rectangle = master.getGeometryDepartment().newRectangle();

		this.shape = this.debug_rectangle.shape();

		this.shape.setPositionX(specs.getPostionX());
		this.shape.setPositionY(specs.getPostionY());
		this.shape.setHeight(specs.getHeight());
		this.shape.setWidth(specs.getWidth());
		this.root.attachComponent(this.u_listener);
		this.root.attachComponent(this.debug_rectangle);
// debug_rectangle.setVisible(false);
		this.debug_rectangle.setFillColor(Colors.GRAY().customize().setAlpha(0.3f));
	}

	protected boolean onTouchDragged (final TouchDraggedEvent event) {
		// L.d(this.getID().toString(), event);
		return this.listener.onTouchDragged(event);
	}

	protected boolean onTouchUp (final TouchUpEvent event) {
		// L.d(this.getID().toString(), event);
		return this.listener.onTouchUp(event);
	}

	protected boolean onTouchDown (final TouchDownEvent event) {
		// L.d(this.getID().toString(), event);
		return this.listener.onTouchDown(event);
	}

	protected boolean onMouseMoved (final MouseMovedEvent event) {
		// L.d(this.getID().toString(), event);
		return this.listener.onMouseMoved(event);
	}

	protected boolean onMouseExit (final MouseExitEvent event) {
		return this.listener.onMouseExit(event);
	}

	private final Rectangle shape;

	MouseInputEventListener listener = MouseInputEventListener.DEBUG;

	// private AssetID id;

	@Override
	public Rectangle shape () {
		return this.shape;
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public MouseInputEventListener getInputListener () {
		return this.listener;
	}

	@Override
	public void setInputListener (final MouseInputEventListener listener) {
		if (this.listener == null) {
// L.d("set listener", listener);
		} else {
// L.d("remove listener", this.listener);
// L.d("set listener", listener);
		}
		this.listener = listener;
		if (this.listener == null) {
			this.listener = MouseInputEventListener.DEBUG;
		}
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
		this.debug_rectangle.setVisible(b);
	}

	@Override
	public boolean gerDebugRenderFlag () {
		return this.debug_rectangle.isVisible();
	}

	@Override
	public double getHeight () {
		return this.shape().getHeight();
	}

	@Override
	public double getWidth () {
		return this.shape().getWidth();
	}

}
