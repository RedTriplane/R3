
package com.jfixby.r3.activity.red.cam;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.camera.CanvasCameraSpecs;
import com.jfixby.r3.activity.api.camera.ScreenDimentions;
import com.jfixby.r3.activity.api.camera.Shadow;
import com.jfixby.r3.activity.api.camera.ShadowSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.user.CanvasCameraManager;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.math.FloatMath;

public class RedShadow implements Shadow, LayerBasedComponent, CanvasCameraManager {
	private Layer root;
	private CanvasCamera camera;

	@Override
	public void onScreenUpdate (final ScreenDimentions viewport_update, final CanvasCamera your_camera) {
		// your_camera.setDebugFlag(!true);
		your_camera.setSize(Screen.getScreenWidth(), Screen.getScreenHeight());
		your_camera.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.shadow_sprite.setWidth(your_camera.getWidth() * 1.91);
		this.shadow_sprite.setHeight(your_camera.getHeight() * 1.91);
		this.shadow_sprite.setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
		this.shadow_sprite.setPosition();
		your_camera.setPosition();
		// L.d("onScreenUpdate");

	}

	private Raster shadow_sprite;

	public RedShadow (final ShadowSpecs shadow_specs, final ComponentsFactory components_factory) {
		this.setup(components_factory);
		this.reset();
		this.root.closeInputValve();
	}

	public void setup (final ComponentsFactory components_factory) {
		this.root = components_factory.newLayer();
		this.root.setName("ShadowLayer");
		final CanvasCameraSpecs cam_specs = components_factory.getCameraDepartment().newCameraSpecs();
		cam_specs.setCameraManager(this);
// cam_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);

		this.camera = components_factory.getCameraDepartment().newCamera(cam_specs);
		this.camera.setDebugName("ShadowCamera");
		// camera.setApertureOpacity(ABSOLUTE_CLEAR);
		this.root.setCamera(this.camera);

		this.shadow_sprite = components_factory.getRasterDepartment().newRaster(RenderMachine.DefaultGraphicsAssets().BLACK());
		this.shadow_sprite.setOpacity(1f);
		this.root.attachComponent(this.shadow_sprite);

// this.shadow_sprite.setHeight(1.1);
// this.shadow_sprite.setWidth(1.1);
// this.shadow_sprite.shape().setOriginRelative(ORIGIN_RELATIVE_HORIZONTAL.CENTER, ORIGIN_RELATIVE_VERTICAL.CENTER);
// this.shadow_sprite.setPosition(0, 0);
// this.shadow_sprite.setDebugRenderFlag(false);

		this.onScreenUpdate(null, this.camera);

	}

	public Layer getRootLayer () {
		return this.root;
	}

	@Override
	public void setValue (float f) {
		f = FloatMath.limit(ABSOLUTE_CLEAR, f, ABSOLUTE_DARKNESS);
		this.shadow_sprite.setOpacity(f);
	}

	public void reset () {
		this.setValue(ABSOLUTE_DARKNESS);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public float getValue () {
		return this.shadow_sprite.getOpacity();
	}
}
