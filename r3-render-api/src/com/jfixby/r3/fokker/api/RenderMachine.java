
package com.jfixby.r3.fokker.api;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;

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

// public static void init () {
// invoke().init();
// }
// -------------------------------------

	public static void beginFrame () {
		invoke().beginFrame();
	}

	public static void clearScreen () {
		invoke().clearScreen();
	}

	public static void setCameraProjection (final Projection projection) {
		invoke().setCameraProjection(projection);
	}

	public static void beginRasterMode (final TEXTURE_BLEND_MODE blend_mode, final double opacity) {
		invoke().beginRasterMode(blend_mode, opacity);
	}

	public static void endRasterMode (final TEXTURE_BLEND_MODE blend_mode) {
		invoke().endRasterMode(blend_mode);
	}

	public static void beginDrawComponent (final Drawable fokkerDrawable) {
		invoke().beginDrawComponent(fokkerDrawable);
	}

	public static void beginShapesMode () {
		invoke().beginShapesMode();
	}

	public static void endShapesMode () {
		invoke().endShapesMode();
	}

	public static void endDrawComponent (final Drawable fokkerDrawable) {
		invoke().endDrawComponent(fokkerDrawable);
	}

	public static void drawAperture (final double ax, final double ay, final double bx, final double by, final ID spriteAssetID) {
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

	public static void drawString (final ID fontID, final FontParameters fontParams, final String stringValue,
		final CanvasPosition position) {
		invoke().drawString(fontID, fontParams, stringValue, position);
	}

	public static void beginShaderMode (final ID fokkerShader, final ShaderParameters params) {
		invoke().beginShaderMode(fokkerShader, params);
	}

	public static void endShaderMode (final ID fokkerShader) {
		invoke().endShaderMode(fokkerShader);
	}

	public static void applyShader () {
		invoke().applyShader();
	}

	public static DefaultAssets DefaultAssets () {
		return invoke().DefaultAssets();
	}

}
