
package com.jfixby.r3.activity.red.geo;

import com.jfixby.r3.activity.api.geometry.RectangleComponent;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.color.CustomColor;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.RectangleCorner;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.math.FloatMath;

public class RedRectangleComponent implements RectangleComponent, VisibleComponent, Drawable {

	final CustomColor border_color = Colors.GREEN().customize();
	final CustomColor fill_color = Colors.NO().customize();
	private final Rectangle shape;

	public RedRectangleComponent (final RedComponentsFactory master, final Rectangle shape) {
		this.shape = shape;
	}

	@Override
	public void setBorderColor (final Color border_color) {
		this.border_color.setValue(border_color);
	}

	@Override
	public Color getBorderColor () {
		return this.border_color;
	}

	@Override
	public void setFillColor (final Color fill_color) {
		this.fill_color.setValue(fill_color);
	}

	@Override
	public void setSize (final Rectangle rectangle) {
		this.setSize(rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public Color getFillColor () {
		return this.fill_color;
	}

	@Override
	public Rectangle shape () {
		return this.shape;
	}

	final CustomColor color = Colors.newColor();

	@Override
	public String toString () {
		return "RedRectangleComponent [name=" + this.name + "]";
	}

	@Override
	public void doDraw () {
		RenderMachine.beginDrawComponent(this);
		RenderMachine.beginShapesMode();
		// RenderMachine.setOffset(this.offset);

		final Rectangle shape = this.shape();

		final ReadOnlyFloat2 a = shape.getTopRightCorner().transformed();
		final ReadOnlyFloat2 b = shape.getTopLeftCorner().transformed();
		final ReadOnlyFloat2 c = shape.getBottomLeftCorner().transformed();
		final ReadOnlyFloat2 d = shape.getBottomRightCorner().transformed();

		this.color.setValue(this.getFillColor());
		this.color.setAlpha(this.getOpacity() * this.color.alpha());

		RenderMachine.drawTriangle(this.color, a, b, c);
		RenderMachine.drawTriangle(this.color, c, d, a);

		this.color.setValue(this.getBorderColor());
		this.color.setAlpha(this.getOpacity() * this.color.alpha());

		RenderMachine.drawLine(this.color, a, b);
		RenderMachine.drawLine(this.color, c, b);
		RenderMachine.drawLine(this.color, c, d);
		RenderMachine.drawLine(this.color, a, d);

		if (this.getDebugRenderFlag()) {
			this.debug_render();
		}

		RenderMachine.endShapesMode();
		RenderMachine.endDrawComponent(this);
	}

	Float2 tmpA = Geometry.newFloat2();
	Float2 tmpB = Geometry.newFloat2();
	private boolean visible = true;
	private boolean debugRender;
	private float opacity = 1;
	private String name;

	private void debug_render () {
		final Color color = this.getDebugColor();
		final Rectangle shape = this.shape();
		final double radius = FloatMath.min(shape.getHeight(), shape.getWidth()) / 15f;

		final double delta = radius * 2;

		{
			this.tmpA.setXY(shape.getPositionX(), shape.getPositionY());
			this.tmpA.addX(+delta);
			this.tmpB.setXY(shape.getPositionX(), shape.getPositionY());
			this.tmpB.addX(-delta);
			RenderMachine.drawLine(color, this.tmpB, this.tmpA);
		}
		{
			this.tmpA.setXY(shape.getPositionX(), shape.getPositionY());
			this.tmpA.addY(+delta);
			this.tmpB.setXY(shape.getPositionX(), shape.getPositionY());
			this.tmpB.addY(-delta);
			RenderMachine.drawLine(color, this.tmpB, this.tmpA);

		}

		// RenderMachine.drawEllipse(color, this.offset.getX(),
		// this.offset.getY(), radius, radius, 0, false);

		// RenderMachine.drawCircle(color, shape.getPositionX(),
		// shape.getPositionY(), radius);

		// Err.reportError();

	}

	@Override
	public Angle getRotation () {
		return this.shape().getRotation();
	}

	@Override
	public void setRotation (final Angle rotation) {
		this.shape().setRotation(rotation);
	}

	@Override
	public void setRotation (final double rotation) {
		this.shape().setRotation(rotation);
	}

	@Override
	public void setPosition (final double x, final double y) {
		this.shape().setPosition(x, y);
	}

	@Override
	public void setPositionX (final double x) {
		this.shape().setPositionX(x);
	}

	@Override
	public void setPositionY (final double y) {
		this.shape().setPositionY(y);
	}

	@Override
	public double getPositionX () {
		return this.shape().getPositionX();
	}

	@Override
	public double getPositionY () {
		return this.shape().getPositionY();
	}

	@Override
	public void setPosition (final CanvasPosition position) {
		this.shape().setPosition(position);
	}

	@Override
	public void setOriginAbsolute (final double origin_x, final double origin_y) {
		this.shape().setOriginAbsolute(origin_x, origin_y);
	}

	@Override
	public void setOriginAbsoluteX (final double origin_x) {
		this.shape().setOriginAbsoluteX(origin_x);
	}

	@Override
	public void setOriginAbsoluteY (final double origin_y) {
		this.shape().setOriginAbsoluteX(origin_y);
	}

	@Override
	public void setSize (final double width, final double height) {
		this.shape().setSize(width, height);
	}

	@Override
	public void setOriginRelative (final double or_relative_x, final double or_relative_y) {
		this.shape().setOriginRelative(or_relative_x, or_relative_y);
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 position_xy) {
		this.setPosition(position_xy.getX(), position_xy.getY());
	}

	@Override
	public void setOriginAbsolute (final CanvasPosition position) {
		this.setOriginAbsolute(position.getX(), position.getY());
	}

	@Override
	public void setOriginRelative (final ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL,
		final ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL) {
		this.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL, ORIGIN_RELATIVE_VERTICAL);
	}

	@Override
	public void setOriginAbsolute (final ReadOnlyFloat2 point) {
		this.shape.setOriginAbsolute(point);
	}

	@Override
	public double getWidth () {
		return this.shape.getWidth();
	}

	@Override
	public double getHeight () {
		return this.shape.getHeight();
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
	public void setOriginRelativeX (final ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL) {
		this.shape.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL);
	}

	@Override
	public void setOriginRelativeY (final ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL) {
		this.shape.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL);
	}

	@Override
	public void setOriginRelativeX (final double ORIGIN_RELATIVE_HORIZONTAL) {
		this.shape.setOriginRelativeX(ORIGIN_RELATIVE_HORIZONTAL);
	}

	@Override
	public void setOriginRelativeY (final double ORIGIN_RELATIVE_VERTICAL) {
		this.shape.setOriginRelativeY(ORIGIN_RELATIVE_VERTICAL);
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
	public CanvasPosition getPosition () {
		return this.shape.getPosition();
	}

	@Override
	public void setOpacity (final float alpha) {
		this.opacity = alpha;
	}

	@Override
	public float getOpacity () {
		return this.opacity;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
		this.debugRender = b;
	}

	@Override
	public boolean getDebugRenderFlag () {
		return this.debugRender;
	}

	@Override
	public void setDebugColor (final Color debug_render_color) {
		this.border_color.setValue(debug_render_color);
	}

	@Override
	public Color getDebugColor () {
		return this.border_color;
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
	public void setVisible (final boolean b) {
		this.visible = b;
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
	public void offset (final double x, final double y) {
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

	@Override
	public void setOriginRelative () {
		this.shape.setOriginRelative();
	}

}
