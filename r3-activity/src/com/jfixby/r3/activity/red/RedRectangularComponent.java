
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.geometry.RectangularComponent;
import com.jfixby.r3.activity.red.geo.RedRectangleComponent;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.RectangleCorner;
import com.jfixby.scarabei.api.math.Angle;

public abstract class RedRectangularComponent extends RedDrawableComponent implements RectangularComponent {

	public final RedRectangleComponent debug_rectangle;
	public final Rectangle shape;

	public RedRectangularComponent (final RedComponentsFactory master) {

		this.debug_rectangle = (RedRectangleComponent)master.getGeometryDepartment().newRectangle();
		// debug_rectangle.setDebugRenderFlag(true);
		this.shape = this.debug_rectangle.shape();
		this.debug_rectangle.setBorderColor(this.debug_rectangle.getDebugColor());
		// this.setDebugRenderFlag(!true);
	}

	@Override
	public void setDebugColor (final Color debug_render_color) {
		this.debug_rectangle.setDebugColor(debug_render_color);
		this.debug_rectangle.setBorderColor(this.debug_rectangle.getDebugColor());
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
		super.setDebugRenderFlag(b);
		this.debug_rectangle.setDebugRenderFlag(b);
	}

	@Override
	public Color getDebugColor () {
		return this.debug_rectangle.getDebugColor();
	}

	@Override
	public Rectangle shape () {
		return this.shape;
	}

	@Override
	public double getHeight () {
		return this.shape.getHeight();
	}

	@Override
	public double getWidth () {
		return this.shape.getWidth();
	}

	@Override
	public void setSize (final double width, final double height) {
		this.shape.setSize(width, height);
	}

	@Override
	public void setWidth (final double width) {
		this.shape.setWidth(width);
	}

	@Override
	public void setHeight (final double height) {
		this.shape.setHeight(height);
	}

	@Override
	public void setOriginRelative (final ORIGIN_RELATIVE_HORIZONTAL orX, final ORIGIN_RELATIVE_VERTICAL orY) {
		this.shape.setOriginRelative(orX, orY);
	}

	@Override
	public void setOriginRelativeX (final ORIGIN_RELATIVE_HORIZONTAL orX) {
		this.shape.setOriginRelativeX(orX);
	}

	@Override
	public void setOriginRelativeY (final ORIGIN_RELATIVE_VERTICAL orY) {
		this.shape.setOriginRelativeY(orY);
	}

	@Override
	public void setOriginRelative (final double ORIGIN_POSITION_HORIZONTAL, final double ORIGIN_POSITION_VERTICAL) {
		this.shape.setOriginRelative(ORIGIN_POSITION_HORIZONTAL, ORIGIN_POSITION_VERTICAL);
	}

	@Override
	public void setOriginRelativeX (final double ORIGIN_POSITION_HORIZONTAL) {
		this.shape.setOriginRelativeX(ORIGIN_POSITION_HORIZONTAL);
	}

	@Override
	public void setOriginRelativeY (final double ORIGIN_POSITION_VERTICAL) {
		this.shape.setOriginRelativeY(ORIGIN_POSITION_VERTICAL);
	}

	@Override
	public Angle getRotation () {
		return this.shape.getRotation();
	}

	@Override
	public void setRotation (final Angle rotation) {
		this.shape.setRotation(rotation);
	}

	@Override
	public void setRotation (final double rotation) {
		this.shape.setRotation(rotation);
	}

	@Override
	public void setPosition (final double x, final double y) {
		this.shape.setPosition(x, y);
	}

	@Override
	public void setPositionX (final double x) {
		this.shape.setPositionX(x);
	}

	@Override
	public void setPositionY (final double y) {
		this.shape.setPositionY(y);
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.shape.setPosition(position);
	}

	@Override
	public double getPositionX () {
		return this.shape.getPositionX();
	}

	@Override
	public double getPositionY () {
		return this.shape.getPositionY();
	}

	@Override
	public Rectangle setupShape (final Rectangle other) {
		return this.shape.setup(other);
	}

	@Override
	public RectangleCorner getTopLeftCorner () {
		return this.shape.getTopLeftCorner();
	}

	@Override
	public RectangleCorner getTopRightCorner () {
		return this.shape.getTopRightCorner();
	}

	@Override
	public RectangleCorner getBottomLeftCorner () {
		return this.shape.getBottomLeftCorner();
	}

	@Override
	public RectangleCorner getBottomRightCorner () {
		return this.shape.getBottomRightCorner();
	}

	@Override
	public void setOriginAbsolute (final double origin_x, final double origin_y) {
		this.shape.setOriginAbsolute(origin_x, origin_y);
	}

	@Override
	public void setOriginAbsoluteX (final double origin_x) {
		this.shape.setOriginAbsoluteX(origin_x);
	}

	@Override
	public void setOriginAbsoluteY (final double origin_y) {
		this.shape.setOriginAbsoluteY(origin_y);
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 newPosition) {
		this.shape.setPosition(newPosition);
	}

	@Override
	public CanvasPosition getPosition () {
		return this.shape.getPosition();
	}

	@Override
	public void setOriginAbsolute (final CanvasPosition position) {
		this.setOriginAbsolute(position.getX(), position.getY());
	}

	@Override
	final public void offset (final double x, final double y) {
		this.setPositionX(this.getPositionX() + x);
		this.setPositionY(this.getPositionY() + y);
	}

	@Override
	public Rectangle setup (final Rectangle other) {
		return this.shape().setup(other);
	}

	@Override
	public boolean containsPoint (final double canvas_x, final double canvas_y) {
		return this.shape().containsPoint(canvas_x, canvas_y);
	}

	@Override
	public boolean containsPoint (final ReadOnlyFloat2 canvas_point) {
		return this.shape().containsPoint(canvas_point);
	}

	@Override
	public double getOriginRelativeX () {
		return this.shape().getOriginRelativeX();
	}

	@Override
	public double getOriginRelativeY () {
		return this.shape().getOriginRelativeY();
	}

	@Override
	public void toRelative (final Float2 temp_point) {
		this.shape().toRelative(temp_point);
	}

	@Override
	public void toAbsolute (final Float2 temp_point) {
		this.shape().toAbsolute(temp_point);
	}

	@Override
	public Collection<ReadOnlyFloat2> listVertices () {
		return this.shape().listVertices();
	}

	@Override
	public void reScale (final double scaleX, final double scaleY) {
		this.shape().reScale(scaleX, scaleY);
	}

	@Override
	public void setPosition () {
		this.shape().setPosition();
	}

}
