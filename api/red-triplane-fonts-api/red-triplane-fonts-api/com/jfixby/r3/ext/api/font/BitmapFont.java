package com.jfixby.r3.ext.api.font;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;

public interface BitmapFont {

	FontCache getFontCache();

	void dispose();

	void renderBitmapFont(BitmapFontRenderer bitmapFontRenderer, CanvasPosition position, String string_value,
			CameraProjection projection, final Object blend_texture, double opacity);

}
