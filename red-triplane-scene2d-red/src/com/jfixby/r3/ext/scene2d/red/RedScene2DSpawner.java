
package com.jfixby.r3.ext.scene2d.red;

import java.io.IOException;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.ext.scene2d.api.Scene2DComponent;
import com.jfixby.r3.ext.scene2d.api.Scene2DSpawnerComponent;
import com.jfixby.r3.ext.scene2d.api.Scene2DSpawningConfig;
import com.jfixby.rana.api.asset.AssetHandler;
import com.jfixby.rana.api.asset.AssetsConsumer;
import com.jfixby.rana.api.asset.AssetsManager;
import com.jfixby.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;

public class RedScene2DSpawner implements Scene2DSpawnerComponent {

	public RedScene2DSpawner () {
		super();
	}

	@Override
	public Scene2DComponent spawnScene (final ComponentsFactory components_factory, final Scene2DSpawningConfig config) {
		Debug.checkNull("config", config);
		Debug.checkNull("components_factory", components_factory);
// final ID asset_id = config.getStructureID();
		Debug.checkNull("components_factory", components_factory);
		Debug.checkNull("structureID", config.structureID);
// final PackageReaderListener listener = config.getPackageListener();
		try {
			AssetsManager.autoResolveAsset(config.structureID);
		} catch (final IOException e) {
			e.printStackTrace();
			Err.reportError(e);
		}

		final AssetHandler handler = LoadedAssets.obtainAsset(config.structureID, (AssetsConsumer)components_factory);
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

		result.deployScene(components_factory, structure, settings);
		LoadedAssets.releaseAsset(handler, (AssetsConsumer)components_factory);

		return result;
	}

	@Override
	public void disposeScene (final Scene2DComponent scene) {
		((RedScene)scene).dispose();
	}


}
