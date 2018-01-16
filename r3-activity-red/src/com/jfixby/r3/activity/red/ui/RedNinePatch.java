
package com.jfixby.r3.activity.red.ui;

import com.jfixby.r3.activity.api.raster.UI_BLEND_MODE;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatch;
import com.jfixby.r3.activity.api.ui.ninepatch.NinePatchSettings;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.RectangleCorner;
import com.jfixby.scarabei.api.math.Angle;

public class RedNinePatch implements NinePatch {

	RedNinePatch (final NinePatchSettings settings) {
		Err.throwNotImplementedYet();
	}

	@Override
	public UI_BLEND_MODE getBlendMode () {
		return null;
	}

	@Override
	public UI_BLEND_MODE setBlendMode (final UI_BLEND_MODE mode) {
		return null;
	}

	@Override
	public Rectangle shape () {
		return null;
	}

	@Override
	public Rectangle setupShape (final Rectangle other) {
		return null;
	}

	@Override
	public void setOriginAbsolute (final double origin_x, final double origin_y) {
	}

	@Override
	public void setOriginAbsolute (final CanvasPosition position) {
	}

	@Override
	public void setOriginAbsoluteX (final double origin_x) {
	}

	@Override
	public void setOriginAbsoluteY (final double origin_y) {
	}

	@Override
	public void setOpacity (final float alpha) {
	}

	@Override
	public float getOpacity () {
		return 0;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
	}

	@Override
	public boolean getDebugRenderFlag () {
		return false;
	}

	@Override
	public void setDebugColor (final Color debug_render_color) {
	}

	@Override
	public Color getDebugColor () {
		return null;
	}

	@Override
	public void hide () {
	}

	@Override
	public void show () {
	}

	@Override
	public boolean isVisible () {
		return false;
	}

	@Override
	public void setVisible (final boolean b) {
	}

	@Override
	public void setName (final String name) {
	}

	@Override
	public String getName () {
		return null;
	}

	@Override
	public void setPosition (final CanvasPosition position) {
	}

	@Override
	public void setPosition (final ReadOnlyFloat2 position) {
	}

	@Override
	public void setPosition (final double position_x, final double position_y) {
	}

	@Override
	public void offset (final double x, final double y) {
	}

	@Override
	public double getPositionX () {
		return 0;
	}

	@Override
	public double getPositionY () {
		return 0;
	}

	@Override
	public Angle getRotation () {
		return null;
	}

	@Override
	public void setRotation (final Angle rotation) {
	}

	@Override
	public void setRotation (final double rotation) {
	}

	@Override
	public void setPositionX (final double x) {
	}

	@Override
	public void setPositionY (final double y) {
	}

	@Override
	public double getWidth () {
		return 0;
	}

	@Override
	public double getHeight () {
		return 0;
	}

	@Override
	public void setSize (final double width, final double height) {
	}

	@Override
	public void setWidth (final double width) {
	}

	@Override
	public void setHeight (final double height) {
	}

	@Override
	public void setOriginRelative (final ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL,
		final ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL) {
	}

	@Override
	public void setOriginRelative () {
	}

	@Override
	public void setOriginRelativeX (final ORIGIN_RELATIVE_HORIZONTAL ORIGIN_RELATIVE_HORIZONTAL) {
	}

	@Override
	public void setOriginRelativeY (final ORIGIN_RELATIVE_VERTICAL ORIGIN_RELATIVE_VERTICAL) {
	}

	@Override
	public void setOriginRelative (final double ORIGIN_RELATIVE_HORIZONTAL, final double ORIGIN_POSITION_VERTICAL) {
	}

	@Override
	public void setOriginRelativeX (final double ORIGIN_RELATIVE_HORIZONTAL) {
	}

	@Override
	public void setOriginRelativeY (final double ORIGIN_POSITION_VERTICAL) {
	}

	@Override
	public Rectangle setup (final Rectangle other) {
		return null;
	}

	@Override
	public RectangleCorner getTopLeftCorner () {
		return null;
	}

	@Override
	public RectangleCorner getTopRightCorner () {
		return null;
	}

	@Override
	public RectangleCorner getBottomLeftCorner () {
		return null;
	}

	@Override
	public RectangleCorner getBottomRightCorner () {
		return null;
	}

	@Override
	public CanvasPosition getPosition () {
		return null;
	}

	@Override
	public boolean containsPoint (final double canvas_x, final double canvas_y) {
		return false;
	}

	@Override
	public boolean containsPoint (final ReadOnlyFloat2 canvas_point) {
		return false;
	}

	@Override
	public double getOriginRelativeX () {
		return 0;
	}

	@Override
	public double getOriginRelativeY () {
		return 0;
	}

	@Override
	public void toRelative (final Float2 temp_point) {
	}

	@Override
	public void toAbsolute (final Float2 temp_point) {
	}

	@Override
	public void setOriginAbsolute (final ReadOnlyFloat2 tmp) {
	}

	@Override
	public Collection<ReadOnlyFloat2> listVertices () {
		return null;
	}

	@Override
	public void reScale (final double scaleX, final double scaleY) {
	}

	@Override
	public void setPosition () {
	}

	@Override
	public void setSize (final Rectangle rectangle) {
	}

}
