
package com.jfixby.r3.fokker.render;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.jfixby.r3.engine.api.render.FokkerShapesRenderDevice;

public class FokkerRenderDevice implements FokkerShapesRenderDevice {

	final private com.badlogic.gdx.graphics.glutils.ShapeRenderer gdx_shape_renderer;

	public FokkerRenderDevice (ShapeRenderer gdx_shape_renderer) {
		this.gdx_shape_renderer = gdx_shape_renderer;
	}

}
