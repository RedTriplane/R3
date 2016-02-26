package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;

public interface BitmapFontRenderer {

	void offset(Float2 tmpA, CanvasPosition position, float rescale, float scale);

	float round(double x);

	void rasterDraw(TextureContainer texture, float[] spriteVertices, int i, int spriteSize, final BLEND_MODE mode,
			Object blend_texture);

}
