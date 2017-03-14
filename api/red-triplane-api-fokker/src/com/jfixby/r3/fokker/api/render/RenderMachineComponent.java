
package com.jfixby.r3.fokker.api.render;

import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;
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

	void init ();

	void setCameraProjection (final CameraProjection projection);

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

	void drawString (final FokkerString string_value, final CanvasPosition position);

	void beginRasterMode (final BLEND_MODE blend_mode, double opacity);

	void endRasterMode (final BLEND_MODE blend_mode);

	void beginShaderMode (FokkerShader fokkerShader);

	void endShaderMode (FokkerShader fokkerShader);

	void applyShader ();

	void setProjection (Projection projection);

	// void setShader(final FokkerShader shader_handler);

}
