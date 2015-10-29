package com.jfixby.r3.api.ui.unit;

import com.jfixby.r3.api.assets.AssetsConsumer;
import com.jfixby.r3.api.ui.unit.animation.AnimationFactory;
import com.jfixby.r3.api.ui.unit.geometry.GeometryComponentsFactory;
import com.jfixby.r3.api.ui.unit.input.UserInputFactory;
import com.jfixby.r3.api.ui.unit.layer.CameraComponentsFactory;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.layer.PhysicsFactory;
import com.jfixby.r3.api.ui.unit.raster.RasterComponentsFactory;
import com.jfixby.r3.api.ui.unit.txt.TextFactory;

public interface ComponentsFactory extends AssetsConsumer {

	public static final String Pangram_EN = "The quick brown fox jumps over the lazy dog.";
	public static final String Pangram_RU = "Съешь же ещё этих мягких французских булок, да выпей чаю.";
	public static final String Pangram_IT = "Quel vituperabile xenofobo zelante assaggia il whisky ed esclama: alleluja!";

	Layer newLayer();

	CameraComponentsFactory getCameraDepartment();

	GeometryComponentsFactory getGeometryDepartment();

	RasterComponentsFactory getRasterDepartment();

	AnimationFactory getAnimationDepartment();

	UserInputFactory getUserInputDepartment();

	PhysicsFactory getPhysicsDepartment();

	TextFactory getTextDepartment();

}
