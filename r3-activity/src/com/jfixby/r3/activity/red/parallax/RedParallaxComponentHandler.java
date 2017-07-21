
package com.jfixby.r3.activity.red.parallax;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.OffsetProjection;
import com.jfixby.scarabei.api.geometry.projections.ProjectionFactory;

public class RedParallaxComponentHandler {

	private final RedParallax master;
	private final Component component;
	private final Layer root;
	private float multiplier_x;
	private float multiplier_y;
	private float multiplier_z;
// private final Camera camera;
	private final OffsetProjection offset;

	public RedParallaxComponentHandler (final RedParallax redParallax, final Component component) {
		this.master = redParallax;
		final ComponentsFactory factory = this.master.getRoot().getComponentsFactory();
		this.root = factory.newLayer();
		this.component = component;
		this.root.attachComponent(component);

		final ProjectionFactory projectFactory = Geometry.getProjectionFactory();

		this.offset = projectFactory.newOffset();

// final CameraSpecs camera_specs = factory.getCameraDepartment().newCameraSpecs();
// camera_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.EXPAND_CAMERA_VIEWPORT_ON_SCREEN_RESIZE);
// camera_specs.setSimpleCameraPolicy(SIMPLE_CAMERA_POLICY.KEEP_ASPECT_RATIO_ON_SCREEN_RESIZE);
// camera_specs.setProjectionMode(PROJECTION_MODE.STACK);

// this.camera = factory.getCameraDepartment().newCamera(camera_specs);
// this.camera.setZoom(0.25);
// this.root.setCamera(this.camera);

// this.camera.setPositionX(this.master.x);
// this.camera.setPositionY(this.master.y);
		this.root.setProjection(this.offset);
	}

	public Component getRoot () {
		return this.root;
	}

	public void setMultiplierX (final float multiplier_x) {
		this.multiplier_x = multiplier_x;
	}

	public void setMultiplierY (final float multiplier_y) {
		this.multiplier_y = multiplier_y;
	}

	public void setMultiplierZ (final float multiplier_z) {
		this.multiplier_z = multiplier_z;
	}

	public void setParallaxOffset (final Float2 offset) {
		this.offset.setOffsetX(-offset.getX() * this.multiplier_x * this.master.getWidth());
		this.offset.setOffsetY(-offset.getY() * this.multiplier_y * this.master.getHeight());
	}

}
