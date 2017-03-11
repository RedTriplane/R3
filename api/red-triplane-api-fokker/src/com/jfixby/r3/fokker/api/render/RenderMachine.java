
package com.jfixby.r3.fokker.api.render;

import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;
import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;

public class RenderMachine {

	public static final String PRIMARY_BUFFER_TYPE = "FokkerRenderMachine.PRIMARY_BUFFER_TYPE";

	static private ComponentInstaller<RenderMachineComponent> componentInstaller = new ComponentInstaller<RenderMachineComponent>(
		"RenderMachine");

	public static final void installComponent (final RenderMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RenderMachineComponent invoke () {
		return componentInstaller.getComponent();
	}

	public static final RenderMachineComponent component () {
		return componentInstaller.getComponent();
	}

	public static void init () {
		invoke().init();
	}
	// -------------------------------------

	public static void beginFrame () {
		invoke().beginFrame();
	}

	public static void clearScreen () {
		invoke().clearScreen();
	}

	public static void setCameraProjection (final CameraProjection projection) {
		invoke().setCameraProjection(projection);
	}

	public static void beginRasterMode (final BLEND_MODE blend_mode, final double opacity) {
		invoke().beginRasterMode(blend_mode, opacity);
	}

	public static void endRasterMode (final BLEND_MODE blend_mode) {
		invoke().endRasterMode(blend_mode);
	}

	public static void beginDrawComponent (final FokkerDrawable fokkerDrawable) {
		invoke().beginDrawComponent(fokkerDrawable);
	}

	public static void beginShapesMode () {
		invoke().beginShapesMode();
	}

	public static void endShapesMode () {
		invoke().endShapesMode();
	}

	public static void endDrawComponent (final FokkerDrawable fokkerDrawable) {
		invoke().endDrawComponent(fokkerDrawable);
	}

	public static void drawAperture (final double ax, final double ay, final double bx, final double by,
		final ID spriteAssetID) {
		invoke().drawAperture(ax, ay, bx, by, spriteAssetID);
	}

	public static void endFrame () {
		invoke().endFrame();
	}

	public static void drawLine (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b) {
		invoke().drawLine(color, a, b);
	}

	public static void drawTriangle (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b, final ReadOnlyFloat2 c) {
		invoke().drawTriangle(color, a, b, c);
	}

	// public static void setShader(final FokkerShader shader_handler) {
	// invoke().setShader(shader_handler);
	// }

	public static void drawRaster (final ID spriteAssetID, final Rectangle shape) {
		invoke().drawRaster(spriteAssetID, shape);
	}

	public static void drawCircle (final Color color, final double center_x, final double center_y, final double radius) {
		invoke().drawCircle(color, center_x, center_y, radius);
	}

	public static void drawString (final FokkerString string_value, final CanvasPosition position) {
		invoke().drawString(string_value, position);
	}

	public static void beginShaderMode (final FokkerShader fokkerShader) {
		invoke().beginShaderMode(fokkerShader);
	}

	public static void endShaderMode (final FokkerShader fokkerShader) {
		invoke().endShaderMode(fokkerShader);
	}

	public static void applyShader () {
		invoke().applyShader();
	}

}
