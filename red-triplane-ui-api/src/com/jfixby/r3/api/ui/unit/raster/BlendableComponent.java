package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.r3.api.render.BLEND_MODE;

public interface BlendableComponent {

	public BLEND_MODE getBlendMode();

	public BLEND_MODE setBlendMode(BLEND_MODE mode);

}
