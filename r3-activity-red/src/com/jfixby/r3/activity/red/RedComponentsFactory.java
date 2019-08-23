
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.UIFactory;
import com.jfixby.r3.activity.api.animation.AnimationFactory;
import com.jfixby.r3.activity.api.audio.SoundFactory;
import com.jfixby.r3.activity.api.camera.CameraFactory;
import com.jfixby.r3.activity.api.geometry.GeometryComponentsFactory;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.api.parallax.ParallaxFactory;
import com.jfixby.r3.activity.api.shader.ShaderFactory;
import com.jfixby.r3.activity.api.txt.TextFactory;
import com.jfixby.r3.activity.red.cam.RedCameraFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.activity.red.parallax.RedParallaxFactory;
import com.jfixby.r3.activity.red.shader.RedShadersFactory;
import com.jfixby.r3.activity.red.sound.RedSoundFactory;
import com.jfixby.r3.activity.red.text.RedTextFacory;
import com.jfixby.r3.activity.red.ui.RedUIFactory;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;

public class RedComponentsFactory implements ComponentsFactory, AssetsConsumer {
	RedGeometryFactory geo_fac;
	RasterFactory raster_fac;
	RedAnimationFactory anim_factF;
	RedUserInputFactory user_input_factory;
	RedCameraFactory camera_factory;
	// RedPizzaFactory pizza_factory;
	RedTextFacory text_factory;
	RedParallaxFactory parallax_factory;
	RedSoundFactory red_sound_factory;

	RedShadersFactory shaders_factory;
	private final RedActivityManager master;
// private final RedSceneFactory scene_factory;
	private final RedUIFactory ui_factory;

	@Override
	public String toString () {
		return "ComponentsFactory@Activity<" + this.master.getActivityDebugName() + ">";
	}

	public RedComponentsFactory (final RedActivityManager master) {
		this.master = master;
		this.geo_fac = new RedGeometryFactory(this);
		this.raster_fac = new RasterFactory(this);
		this.anim_factF = new RedAnimationFactory(this);
		this.user_input_factory = new RedUserInputFactory(this);
		this.camera_factory = new RedCameraFactory(this);
		this.text_factory = new RedTextFacory(this);
		this.shaders_factory = new RedShadersFactory(this);
		this.parallax_factory = new RedParallaxFactory(this);
// this.scene_factory = new RedSceneFactory(this);
		this.ui_factory = new RedUIFactory(this);
		this.red_sound_factory = new RedSoundFactory(this);

	}

	@Override
	public RedLayer newLayer () {
		return new RedLayer(this);
	}

	@Override
	public GeometryComponentsFactory getGeometryDepartment () {
		return this.geo_fac;
	}

	@Override
	public RasterFactory getRasterDepartment () {
		return this.raster_fac;
	}

	@Override
	public CameraFactory getCameraDepartment () {
		return this.camera_factory;
	}

// @Override
// public SceneFactory getSceneDepartment () {
// return this.scene_factory;
// }

	@Override
	public AnimationFactory getAnimationDepartment () {
		return this.anim_factF;
	}

	@Override
	public UserInputFactory getUserInputDepartment () {
		return this.user_input_factory;
	}

	@Override
	public TextFactory getTextDepartment () {
		return this.text_factory;
	}

	@Override
	public ShaderFactory getShadersDepartment () {
		return this.shaders_factory;
	}

	@Override
	public ParallaxFactory getParallaxDepartment () {
		return this.parallax_factory;
	}

	@Override
	public UIFactory getUIDepartment () {
		return this.ui_factory;
	}

	@Override
	public SoundFactory getSoundFactory () {
		return this.red_sound_factory;
	}

}
