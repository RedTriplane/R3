package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;

public interface BitmapFontRenderer {

	void offset(Float2 tmpA, CanvasPosition position, float rescale, float scale);

	float round(double x);

	void rasterDraw(TextureContainer texture, float[] spriteVertices, int i, int spriteSize);

}
