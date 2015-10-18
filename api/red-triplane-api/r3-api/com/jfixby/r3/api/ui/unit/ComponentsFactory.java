package com.jfixby.r3.api.ui.unit;

import com.jfixby.r3.api.assets.AssetsConsumer;
import com.jfixby.r3.api.ui.unit.animation.AnimationFactory;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.CameraComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.GeometryComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.layer.PhysicsFactory;
import com.jfixby.r3.api.ui.unit.layer.RasterComponentsFactory;

public interface ComponentsFactory extends AssetsConsumer {

	Layer newLayer();

	CameraComponentsFactory getCameraDepartment();

	GeometryComponentsFactory getGeometryDepartment();

	RasterComponentsFactory getRasterDepartment();

	AnimationFactory getAnimationDepartment();

	UserInputFactory getUserInputDepartment();

	PhysicsFactory getPhysicsDepartment();

}
