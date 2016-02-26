package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;

public interface BitmapFont {

	FontCache getFontCache();

	void dispose();

	void renderBitmapFont(BitmapFontRenderer bitmapFontRenderer, CanvasPosition position, String string_value,
			CameraProjection projection, final BLEND_MODE mode, final Object blend_texture);

}
