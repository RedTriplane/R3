
package com.jfixby.r3.api.ui.unit;

import com.jfixby.r3.api.ui.unit.animation.AnimationFactory;
import com.jfixby.r3.api.ui.unit.geometry.GeometryComponentsFactory;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.CameraComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.raster.RasterComponentsFactory;
import com.jfixby.r3.api.ui.unit.shader.ShaderFactory;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;
import com.jfixby.rana.api.asset.AssetsConsumer;

public interface ComponentsFactory extends AssetsConsumer {

	Layer newLayer ();

	CameraComponentsFactory getCameraDepartment ();

	GeometryComponentsFactory getGeometryDepartment ();

	RasterComponentsFactory getRasterDepartment ();

	AnimationFactory getAnimationDepartment ();

	UserInputFactory getUserInputDepartment ();

	TextFactory getTextDepartment ();

	ShaderFactory getShadersDepartment ();

}
