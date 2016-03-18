package com.jfixby.r3.fokker.api.render;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFont;

public interface RenderMachineComponent {

    void beginFrame();

    void endFrame();

    void clearScreen();

    void beginDrawComponent(FokkerDrawable fokkerDrawable);

    // void setOffset(Dot offset);

    void drawLine(Color color, FixedFloat2 a, FixedFloat2 b);

    void drawTriangle(Color color, FixedFloat2 a, FixedFloat2 b, FixedFloat2 c);

    void endDrawComponent(FokkerDrawable fokkerDrawable);

    void init();

    void setProjection(final CameraProjection projection);

    // void drawEllipse(Color color, double positionX, double positionY,
    // double width, double height, double rotation, boolean filled);
    //
    // void drawCircle(Color color, double positionX, double positionY,
    // double radius);

    // void drawDisk(Color color, double positionX, double positionY, double
    // radius);

    void drawRaster(AssetID spriteAssetID, Rectangle shape);

    void beginShapesMode();

    void endShapesMode();

    void drawAperture(double ax, double ay, double bx, double by, AssetID spriteAssetID);

    void drawCircle(Color color, double center_x, double center_y, double radius);

    void drawString(String string_value, RasterizedFont font, CanvasPosition position);

    void beginRasterMode(final BLEND_MODE blend_mode, double opacity);

    void endRasterMode(final BLEND_MODE blend_mode);

    void beginShaderMode(FokkerShader fokkerShader);

    void endShaderMode(FokkerShader fokkerShader);

    // void setShader(final FokkerShader shader_handler);

}
