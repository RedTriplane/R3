
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;

public interface RenderMachineComponent {

	void beginFrame ();

	void endFrame ();

	void clearScreen ();

	void beginDrawComponent (FokkerDrawable fokkerDrawable);

	// void setOffset(Dot offset);

	void drawLine (Color color, ReadOnlyFloat2 a, ReadOnlyFloat2 b);

	void drawTriangle (Color color, ReadOnlyFloat2 a, ReadOnlyFloat2 b, ReadOnlyFloat2 c);

	void endDrawComponent (FokkerDrawable fokkerDrawable);

// void init ();

	void setCameraProjection (final Projection projection);

	// void drawEllipse(Color color, double positionX, double positionY,
	// double width, double height, double rotation, boolean filled);
	//
	// void drawCircle(Color color, double positionX, double positionY,
	// double radius);

	// void drawDisk(Color color, double positionX, double positionY, double
	// radius);

	void drawRaster (ID spriteAssetID, Rectangle shape);

	void beginShapesMode ();

	void endShapesMode ();

	void drawAperture (double ax, double ay, double bx, double by, ID spriteAssetID);

	void drawCircle (Color color, double center_x, double center_y, double radius);

	void drawString (final StringHandler string_value, final CanvasPosition position);

	void beginRasterMode (final TEXTURE_BLEND_MODE blend_mode, double opacity);

	void endRasterMode (final TEXTURE_BLEND_MODE blend_mode);

	void beginShaderMode (ID fokkerShader, final ShaderParameters params);

	void endShaderMode (ID fokkerShader);

	void applyShader ();

	void setProjection (Projection projection);

	DefaultAssets DefaultAssets ();

	// void setShader(final FokkerShader shader_handler);

}
