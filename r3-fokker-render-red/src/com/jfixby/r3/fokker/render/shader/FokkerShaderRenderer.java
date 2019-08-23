
package com.jfixby.r3.fokker.render.shader;

import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.r3.fokker.render.raster.FokkerRasterRenderer;
import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.r3.fokker.shader.api.FokkerShaders;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.names.ID;

public class FokkerShaderRenderer {

	private FokkerRasterRenderer raster_renderer;
	private Rectangle shape;
	private ID fake_asset;

	public void init (final FokkerRasterRenderer raster_renderer) {
		this.raster_renderer = raster_renderer;
		this.shape = Geometry.newRectangle();
		// shape.setPosition(Double.NEGATIVE_INFINITY,
		// Double.POSITIVE_INFINITY);

		this.fake_asset = RenderMachine.DefaultGraphicsAssets().RASTER_IS_MISING();
	}

	public void open (final ID fokkerShader, final ShaderSettings params) {
		final Rectangle shape = params.shape();
		if (shape != null) {
			this.shape.setup(shape);
		} else {
			this.resetShape();
		}
		final FokkerShader shader = FokkerShaders.obtain(fokkerShader);
// shader.applyParameters(params);
		this.raster_renderer.open(null, 1f, shader, params);
	}

	private void resetShape () {
		this.shape.setOriginRelative();
		this.shape.setPosition();
		this.shape.setSize(10000, 10000);
	}

	public void close (final ID fokkerShader) {
		final FokkerShader shader = FokkerShaders.obtain(fokkerShader);
		this.raster_renderer.close(null, shader);

	}

	public void applyShader () {
		this.raster_renderer.drawSprite(this.fake_asset, this.shape);
		// final Texture blend_texture = this.current_shader.getBlendTexture();

		// sprites_renderer.drawSprite(spriteAssetID, shape, current_opacity,
		// blend_texture);
	}

}
