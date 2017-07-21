
package com.jfixby.r3.activity.red.cam;

import com.jfixby.r3.activity.api.camera.CameraProjection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.projections.Projection;

public class RedCameraProjection implements CameraProjection, Projection {

	private final RedCamera master;

	public RedCameraProjection (final RedCamera redCamera) {
		this.master = redCamera;

	}

	Float2 tmp = Geometry.newFloat2();

	@Override
	public void project (final Float2 temp_point) {
// L.d("master_camera", master);
		this.master.project(temp_point);
	}

	@Override
	public void unProject (final Float2 point) {
		this.master.unProject(point);
	}

	@Override
	final public Projection projection () {
		return this;
	}

}
