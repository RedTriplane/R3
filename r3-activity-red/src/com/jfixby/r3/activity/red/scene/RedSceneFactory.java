
package com.jfixby.r3.activity.red.scene;

import com.jfixby.r3.activity.api.scene.Scene2DComponent;
import com.jfixby.r3.activity.api.scene.Scene2DSpawningConfig;
import com.jfixby.r3.activity.api.scene.SceneFactory;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.manager.AssetsManager;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedSceneFactory implements SceneFactory {

	private final RedComponentsFactory components_factory;

	public RedSceneFactory (final RedComponentsFactory redComponentsFactory) {
		this.components_factory = redComponentsFactory;
	}

	@Override
	public Scene2DSpawningConfig newSceneSpecs () {
		return new Scene2DSpawningConfig();
	}

	@Override
	public Scene2DComponent newScene (final Scene2DSpawningConfig config) {
		Debug.checkNull("config", config);
		Debug.checkNull("components_factory", this.components_factory);
// final ID asset_id = config.getStructureID();
		Debug.checkNull("components_factory", this.components_factory);
		Debug.checkNull("structureID", config.structureID);
// final PackageReaderListener listener = config.getPackageListener();
		try {
			AssetsManager.autoResolveAsset(config.structureID).await();
		} catch (final Throwable e) {
			e.printStackTrace();
			Err.reportError(e);
		}

		final AssetHandler handler = LoadedAssets.obtainAsset(config.structureID, this.components_factory);
		if (handler == null) {
			LoadedAssets.printAllLoadedAssets();
			Err.reportError("Asset not found: <" + config.structureID + ">");
		}
		final SceneStructureAsset structure = (SceneStructureAsset)handler.asset();

		final RedScene result = new RedScene();
		final Settings settings = new Settings(structure, result);
		settings.debug_opacity = config.debugOpacity;
		settings.timeStream = config.animationsTimeStream;
		if (settings.timeStream == null) {
			Debug.checkNull("getAnimationsTimeStream()", settings.timeStream);
		}
		settings.locale_name = config.defaultLocaleName;
		settings.render_debug_info = config.renderDebugInfo;
		settings.scene_name = config.structureID + "";

		result.deployScene(this.components_factory, structure, settings);
		LoadedAssets.releaseAsset(handler, this.components_factory);

		return result;
	}

	public void disposeScene (final Scene2DComponent scene) {
		((RedScene)scene).dispose();
	}
}
