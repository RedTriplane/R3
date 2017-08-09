
package com.jfixby.r3.rana.api.manager;

import java.io.IOException;

import com.jfixby.scarabei.api.ComponentInstaller;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.promise.Promise;

public class AssetsManager {

	public static final String AutoresolveDependencies = "AutoresolveDependencies";

	static private ComponentInstaller<AssetsManagerComponent> componentInstaller = new ComponentInstaller<>("AssetsManager");

	public static final void installComponent (final AssetsManagerComponent component_to_install) {
		componentInstaller.installComponent(component_to_install);
	}

	public static void installComponent (final String className) {
		componentInstaller.installComponent(className);
	}

	public static final AssetsManagerComponent invoke () {
		return componentInstaller.invokeComponent();
	}

	public static final AssetsManagerComponent component () {
		return componentInstaller.getComponent();
	}

	public static void autoResolveAssetAsync (final ID dependency) throws IOException {
		invoke().autoResolveAssetAsync(dependency);
	}

	public static void autoResolveAssetsAsync (final Collection<ID> dependencies) throws IOException {
		invoke().autoResolveAssetsAsync(dependencies);
	}

	public static AssetsPurgeResult purge () {
		return invoke().purge();
	}

	public static Promise<Void> autoResolveAsset (final ID dependency) {
		return invoke().autoResolveAsset(dependency);
	}

	public static Promise<Void> autoResolveAssets (final Collection<ID> dependencies) {
		return invoke().autoResolveAssets(dependencies);
	}

}
