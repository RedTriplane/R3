
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.animation.AnimationFactory;
import com.jfixby.r3.activity.api.camera.CameraFactory;
import com.jfixby.r3.activity.api.geometry.GeometryComponentsFactory;
import com.jfixby.r3.activity.api.input.UserInputFactory;
import com.jfixby.r3.activity.api.parallax.ParallaxFactory;
import com.jfixby.r3.activity.api.scene.SceneFactory;
import com.jfixby.r3.activity.api.shader.ShaderFactory;
import com.jfixby.r3.activity.api.txt.TextFactory;
import com.jfixby.r3.activity.red.cam.RedCameraFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.activity.red.parallax.RedParallaxFactory;
import com.jfixby.r3.activity.red.scene.RedSceneFactory;
import com.jfixby.r3.activity.red.shader.RedShadersFactory;
import com.jfixby.r3.activity.red.text.RedTextFacory;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class RedComponentsFactory implements ComponentsFactory, AssetsConsumer {
	RedGeometryFactory geo_fac;
	RasterFactory raster_fac;
	RedAnimationFactory anim_factF;
	RedUserInputFactory user_input_factory;
	RedCameraFactory camera_factory;
	// RedPizzaFactory pizza_factory;
	RedTextFacory text_factory;
	RedParallaxFactory parallax_factory;

	RedShadersFactory shaders_factory;
	private final RedActivityExecutor master;
	private final RedSceneFactory scene_factory;

	@Override
	public String toString () {
		return "ComponentsFactory@Activity<" + this.master.getActivityDebugName() + ">";
	}

	public RedComponentsFactory (final RedActivityExecutor master) {
		this.master = master;
		this.geo_fac = new RedGeometryFactory(this);
		this.raster_fac = new RasterFactory(this);
		this.anim_factF = new RedAnimationFactory(this);
		this.user_input_factory = new RedUserInputFactory(this);
		this.camera_factory = new RedCameraFactory(this);
		this.text_factory = new RedTextFacory(this);
		this.shaders_factory = new RedShadersFactory(this);
		this.parallax_factory = new RedParallaxFactory(this);
		this.scene_factory = new RedSceneFactory(this);

		// this.pizza_factory = new RedPizzaFactory(this);

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

	@Override
	public SceneFactory getSceneFactory () {
		return this.scene_factory;
	}

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

	public AssetHandler obtainAsset (final ID newAssetID, final boolean allowMissingAsset, final ID missingAsset,
		final boolean reportFail) {
		AssetHandler asset_handler = LoadedAssets.obtainAsset(newAssetID, this);
		if (asset_handler == null) {
// final boolean allowMissingRaster = SystemSettings.getFlag(Settings.AllowMissingRaster);
// final boolean allowMissingRaster = SystemSettings.getFlag(Settings.AllowMissingRaster);
			if (allowMissingAsset) {
// asset_handler = AssetsManager.obtainAsset(FOKKER_SYSTEM_ASSETS.RASTER_IS_MISING, this);
				asset_handler = LoadedAssets.obtainAsset(missingAsset, this);

// if (SystemSettings.getFlag(Settings.PrintLogMessageOnMissingSprite)) {
				if (reportFail) {
					L.d("Asset<" + newAssetID + "> is missing");
// AssetsManager.printAllLoadedAssets();
				}

			} else {
// L.d("Asset<" + newAssetID + "> is missing");
// L.d("Missing asset is not allowed in the RedTriplane settings.");
// L.d("Load missing raster or enable the flag with RedTriplane.setFlag(RedTriplaneFlags.AllowMissingRaster, true)");
				PackagesManager.printAllIndexes();
				Err.reportError("Asset<" + newAssetID + "> not found.");
			}
		}
		return asset_handler;
	}

}
