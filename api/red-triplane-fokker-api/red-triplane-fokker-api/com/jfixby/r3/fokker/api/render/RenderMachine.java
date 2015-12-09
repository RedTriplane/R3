package com.jfixby.r3.fokker.api.render;

import com.jfixby.cmns.api.ComponentInstaller;
import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.txt.RasterizedFont;

public class RenderMachine {

	static private ComponentInstaller<RenderMachineComponent> componentInstaller = new ComponentInstaller<RenderMachineComponent>(
			"RenderMachine");

	public static final void installComponent(
			RenderMachineComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static final RenderMachineComponent invoke() {
		return componentInstaller.invokeComponent();
	}

	public static final RenderMachineComponent component() {
		return componentInstaller.getComponent();
	}

	public static void beginFrame() {
		invoke().beginFrame();
	}

	public static void endFrame() {
		invoke().endFrame();
	}

	public static void clearScreen() {
		invoke().clearScreen();
	}

	public static void beginDrawComponent(FokkerDrawable fokkerDrawable) {
		invoke().beginDrawComponent(fokkerDrawable);
	}

	public static void beginMode(RENDER_MACHINE_STATE mode) {
		invoke().beginMode(mode);
	}

	public static void endMode(RENDER_MACHINE_STATE mode) {
		invoke().endMode(mode);
	}

	// public static void setOffset(Dot offset) {
	// invoke().setOffset(offset);
	// }

	public static void drawLine(Color color, FixedFloat2 a, FixedFloat2 b) {
		invoke().drawLine(color, a, b);
	}

	public static void drawTriangle(Color color, FixedFloat2 a, FixedFloat2 b,
			FixedFloat2 c) {
		invoke().drawTriangle(color, a, b, c);
	}

	public static void endDrawComponent(FokkerDrawable fokkerDrawable) {
		invoke().endDrawComponent(fokkerDrawable);
	}

	public static void init() {
		invoke().init();
	}

	public static void setProjection(CameraProjection projection) {
		invoke().setProjection(projection);
	}

	public static void drawRaster(AssetID spriteAssetID, Rectangle shape,
			double opacity) {
		invoke().drawRaster(spriteAssetID, shape, opacity);
	}

	public static void drawAperture(double ax, double ay, double bx, double by,
			AssetID spriteAssetID, double opacity) {
		invoke().drawAperture(ax, ay, bx, by, spriteAssetID, opacity);
	}

	public static void drawCircle(Color color, double center_x,
			double center_y, double radius) {
		invoke().drawCircle(color, center_x, center_y, radius);
	}

	public static void drawString(String string_value, RasterizedFont font,
			CanvasPosition position) {
		invoke().drawString(string_value, font, position);
	}

}
