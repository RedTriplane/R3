
package com.jfixby.r3.activity.red.raster;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.raster.UI_BLEND_MODE;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.RedRectangularComponent;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RasterData;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.engine.api.render.TEXTURE_BLEND_MODE;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.names.ID;

public class RedTile extends RedRectangularComponent implements Raster, Drawable {

	@Override
	public String toString () {
		return "Tile[" + this.asset_id + "] name=" + this.getName() + " shape=" + this.shape;
	}

	// private AssetID spriteAssetID;

	private float opacity = 1f;

	// private AssetHandler obtain_sprite;
	private final ID asset_id;

	private final RedComponentsFactory master;

	private UI_BLEND_MODE mode = UI_BLEND_MODE.Normal;

	public RedTile (final RedComponentsFactory master, final RasterData data) {
		super(master);
		this.master = master;
		this.asset_id = data.getAssetID();
		Debug.checkNull("asset_id", this.asset_id);

	}

	@Override
	public void setSize (final Rectangle rectangle) {
		this.setSize(rectangle.getWidth(), rectangle.getHeight());
	}

	@Override
	public Raster copy () {
		final Raster copy = this.getComponentsFactory().getRasterDepartment().newRaster(this.getAssetID());

		// copy settings...
		copy.setupShape(this.shape());
		copy.setOpacity(this.getOpacity());

		return copy;
	}

	@Override
	public ComponentsFactory getComponentsFactory () {
		return this.master;
	}

	@Override
	public void doDraw () {
		RenderMachine.component().beginDrawComponent(this);
		RenderMachine.component().beginRasterMode(this.TOtxtmODE(this.mode), this.opacity);

		RenderMachine.component().drawRaster(this.asset_id, this.shape());

		RenderMachine.component().endRasterMode(this.TOtxtmODE(this.mode));
		RenderMachine.component().endDrawComponent(this);
		if (this.getDebugRenderFlag()) {
			this.debug_rectangle.doDraw();
		}
	}

	private TEXTURE_BLEND_MODE TOtxtmODE (final UI_BLEND_MODE mode2) {

// Err.throwNotImplementedYet();
		return TEXTURE_BLEND_MODE.Normal;
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
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
	public UI_BLEND_MODE getBlendMode () {
		return this.mode;
	}

	@Override
	public UI_BLEND_MODE setBlendMode (UI_BLEND_MODE mode) {
		if (mode == null) {
			mode = UI_BLEND_MODE.Normal;
		}
		final UI_BLEND_MODE old = this.mode;
		this.mode = mode;
		return old;
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
