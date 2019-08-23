
package com.jfixby.r3.activity.red.raster;

import java.util.ArrayList;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.TilesComposition;
import com.jfixby.r3.activity.api.raster.UI_BLEND_MODE;
import com.jfixby.r3.activity.red.BlendModeCasting;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.RedRectangularComponent;
import com.jfixby.r3.activity.red.geo.RedRectangleComponent;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.io.texture.slicer.SliceInfo;
import com.jfixby.r3.io.texture.slicer.SlicesCompositionInfo;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.debug.DEBUG_TIMER_MODE;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.RectangleCorner;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.names.ID;

public class RedTilesComposition extends RedRectangularComponent implements TilesComposition, Drawable {
	private final ID composition_asset_id;
	private float opacity = 1f;
	private final SlicesCompositionInfo composition;
	private final Color tile_grid_color;
	private final RedComponentsFactory master;

	private final ArrayList<RedTileInfo> tiles = new ArrayList<>();

	private final RedRectangleComponent debug_rectangle;
	private RedRectangleComponent tile_shape_rectangle;

	public RedTilesComposition (final RedComponentsFactory master, final TileSet struct_container) {
		super(master);
		this.master = master;
		this.composition = Debug.checkNull("composition", struct_container).getStructure();
		this.composition_asset_id = struct_container.getAssetID();

		this.debug_rectangle = (RedRectangleComponent)master.getGeometryDepartment().newRectangle(this.shape);
		this.debug_rectangle.setDebugRenderFlag(true);
		this.debug_rectangle.setBorderColor(this.debug_rectangle.getDebugColor());

		this.tile_grid_color = Colors.BLUE();
		this.shape.setSize(this.composition.width, this.composition.height);
		this.shape.setOriginRelative(0, 0);

		Debug.checkTrue(this.composition.width >= 0);
		Debug.checkTrue(this.composition.height >= 0);

		for (int i = 0; i < this.composition.tiles.size(); i++) {
			final SliceInfo slice = this.composition.tiles.get(i);

			if (slice.is_empty) {
				continue;
			}
			final ID child_asset_id = this.composition_asset_id.child(slice.postfix);
// AssetsManager.autoResolveAsset(child_asset_id);
			final Raster child = master.getRasterDepartment().newRaster(child_asset_id);

			final RedTileInfo tile = new RedTileInfo();
			this.tiles.add(tile);

			tile.raster = child;
			tile.top_left_x = (1d * slice.index_x * this.composition.cell_width - 1d * this.composition.cell_margin)
				/ this.composition.width;
			tile.top_left_y = (1d * slice.index_y * this.composition.cell_height - 1d * this.composition.cell_margin)
				/ this.composition.height;
			tile.bottom_right_x = tile.top_left_x + (slice.tile_width) / this.composition.width;
			tile.bottom_right_y = tile.top_left_y + (slice.tile_height) / this.composition.height;
		}
		final double width = this.getWidth();
		final double height = this.getHeight();

	}

	final Float2 tmpA = Geometry.newFloat2();
	final Float2 tmpB = Geometry.newFloat2();
	final Float2 tmpC = Geometry.newFloat2();
	final Float2 tmpD = Geometry.newFloat2();
	private UI_BLEND_MODE mode = UI_BLEND_MODE.Normal;
	private final boolean render_tiles = !false;

	// final Rectangle
	final DebugTimer debuger = Debug.newTimer(DEBUG_TIMER_MODE.NANOSECONDS);

	@Override
	public void doDraw () {

		if (!this.render_tiles) {
			return;
		}

		RenderMachine.component().beginDrawComponent(this);
		RenderMachine.component().beginRasterMode(BlendModeCasting.toRenderMachineBlendMode(this.mode), this.opacity);
// debuger.reset();
		for (int i = 0; i < this.tiles.size(); i++) {
			final RedTileInfo tile = this.tiles.get(i);
			final Raster raster = tile.raster;
			final ID raster_asset_id = raster.getAssetID();
			final Rectangle tile_shape = raster.shape();

			tile.update(this.shape, this.getRotation());

			RenderMachine.component().drawRaster(raster_asset_id, tile_shape);

		}
// debuger.printTimeAbove(0.0001, this.toString());
// debuger.reset();
		RenderMachine.component().endRasterMode(BlendModeCasting.toRenderMachineBlendMode(this.mode));

		if (this.getDebugRenderFlag()) {
			RenderMachine.component().beginShapesMode();
			for (final RedTileInfo tile : this.tiles) {
				final Raster child = tile.raster;
				final ID child_asset_id = child.getAssetID();

				this.tmpA.setXY(tile.top_left_x, tile.top_left_y);
				this.tmpB.setXY(tile.bottom_right_x, tile.top_left_y);
				this.tmpC.setXY(tile.bottom_right_x, tile.bottom_right_y);
				this.tmpD.setXY(tile.top_left_x, tile.bottom_right_y);

				this.shape.toAbsolute(this.tmpA);
				this.shape.toAbsolute(this.tmpB);
				this.shape.toAbsolute(this.tmpC);
				this.shape.toAbsolute(this.tmpD);

				// RenderMachine.drawRaster(child_asset_id, tmpA, tmpB, tmpC,
				// tmpD, child.getOpacity() * opacity);
				RenderMachine.component().drawLine(this.tile_grid_color, this.tmpA, this.tmpB);
				RenderMachine.component().drawLine(this.tile_grid_color, this.tmpC, this.tmpB);
				RenderMachine.component().drawLine(this.tile_grid_color, this.tmpC, this.tmpD);
				RenderMachine.component().drawLine(this.tile_grid_color, this.tmpA, this.tmpD);

			}
			RenderMachine.component().endShapesMode();
			this.debug_rectangle.doDraw();
			// for (RedTileInfo tile : tiles) {
			// final Raster child = tile.raster;
			// final Rectangle tile_shape = child.shape();
			// tile_shape_rectangle = (RedRectangleComponent)
			// master.getGeometryDepartment().newRectangle(tile_shape);
			// tile_shape_rectangle.doDraw();
			// }
		}
		RenderMachine.component().endDrawComponent(this);

	}

	@Override
	public void setDebugColor (final Color debug_render_color) {
		this.debug_rectangle.setDebugColor(debug_render_color);
		this.debug_rectangle.setBorderColor(this.debug_rectangle.getDebugColor());
	}

	@Override
	public Rectangle shape () {
		return this.shape;
	}

	@Override
	public ID getAssetID () {
		return this.composition_asset_id;
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
	public double getHeight () {
		return this.shape.getHeight();
	}

	@Override
	public double getWidth () {
		return this.shape.getWidth();
	}

	@Override
	public void setSize (final double width, final double height) {
		this.shape.setWidth(width);
		this.shape.setHeight(height);
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
	public void setPosition (final CanvasPosition position) {
		super.setPosition(position);
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
	public Color getDebugColor () {
		return this.debug_rectangle.getDebugColor();
	}

	@Override
	public void setOriginAbsolute (final double origin_x, final double origin_y) {
		this.shape.setOriginAbsolute(origin_x, origin_y);
	}

	@Override
	public void setOriginAbsoluteX (final double origin_x) {
		this.setOriginAbsolute(origin_x, this.getPositionY());
	}

	@Override
	public void setOriginAbsoluteY (final double origin_y) {

		this.setOriginAbsolute(this.getPositionX(), origin_y);
	}

	@Override
	public String toString () {
		// return "TilesComposition[" + composition_asset_id + "] " +
		// this.getName();
		return "TilesComposition[" + this.composition_asset_id + "](" + this.tiles.size() + ") name=" + this.getName() + " shape="
			+ this.shape;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.master;
	}

	@Override
	public UI_BLEND_MODE getBlendMode () {
		return this.mode;
	}

	@Override
	public UI_BLEND_MODE setBlendMode (final UI_BLEND_MODE mode) {
		UI_BLEND_MODE old = null;
		for (int i = 0; i < this.tiles.size(); i++) {
			final RedTileInfo tile = this.tiles.get(i);
			old = tile.raster.setBlendMode(mode);
		}
		this.mode = mode;
		return old;
	}

	@Override
	public Raster copy () {
		final Raster copy = this.getComponentsFactory().getRasterDepartment().newRaster(this.getAssetID());
		copy.setupShape(this.shape());
		copy.setOpacity(this.getOpacity());
		return copy;
	}

	@Override
	public void setSize (final Rectangle rectangle) {
		this.setSize(rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public void setOriginAbsolute (final ReadOnlyFloat2 point) {
		this.setOriginAbsoluteX(point.getX());
		this.setOriginAbsoluteY(point.getY());
	}

	@Override
	public void setOriginRelative () {
		this.shape.setOriginRelative();
	}

}
