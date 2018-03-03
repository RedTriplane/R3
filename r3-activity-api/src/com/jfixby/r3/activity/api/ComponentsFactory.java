
package com.jfixby.r3.activity.api;

import com.jfixby.r3.activity.api.animation.AnimationFactory;
import com.jfixby.r3.activity.api.camera.CameraFactory;
import com.jfixby.r3.activity.api.geometry.GeometryComponentsFactory;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.mtdesign.MaterialDesignDepartment;
import com.jfixby.r3.activity.api.parallax.ParallaxFactory;
import com.jfixby.r3.activity.api.raster.RasterComponentsFactory;
import com.jfixby.r3.activity.api.scene.SceneFactory;
import com.jfixby.r3.activity.api.shader.ShaderFactory;
import com.jfixby.r3.activity.api.txt.TextFactory;

public interface ComponentsFactory {

	Layer newLayer ();

	CameraFactory getCameraDepartment ();

	GeometryComponentsFactory getGeometryDepartment ();

	RasterComponentsFactory getRasterDepartment ();

	AnimationFactory getAnimationDepartment ();

	UserInputFactory getUserInputDepartment ();

	TextFactory getTextDepartment ();

	ShaderFactory getShadersDepartment ();

	ParallaxFactory getParallaxDepartment ();

	SceneFactory getSceneDepartment ();

	UIFactory getUIDepartment ();

	MaterialDesignDepartment getMaterialDesignDepartment ();

}
