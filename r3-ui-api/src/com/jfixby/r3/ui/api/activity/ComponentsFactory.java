
package com.jfixby.r3.ui.api.activity;

import com.jfixby.r3.ui.api.activity.animation.AnimationFactory;
import com.jfixby.r3.ui.api.activity.camera.CameraFactory;
import com.jfixby.r3.ui.api.activity.geometry.GeometryComponentsFactory;
import com.jfixby.r3.ui.api.activity.input.UserInputFactory;
import com.jfixby.r3.ui.api.activity.layer.Layer;
import com.jfixby.r3.ui.api.activity.parallax.ParallaxFactory;
import com.jfixby.r3.ui.api.activity.raster.RasterComponentsFactory;
import com.jfixby.r3.ui.api.activity.scene.SceneFactory;
import com.jfixby.r3.ui.api.activity.shader.ShaderFactory;
import com.jfixby.r3.ui.api.activity.txt.TextFactory;

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

	SceneFactory getSceneFactory ();

}
