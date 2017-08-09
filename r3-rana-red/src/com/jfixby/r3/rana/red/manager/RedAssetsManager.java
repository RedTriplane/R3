
package com.jfixby.r3.rana.red.manager;

import java.io.IOException;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.SealedAssetsContainer;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.r3.rana.api.loader.PackagesLoader;
import com.jfixby.r3.rana.api.manager.AssetsManagerComponent;
import com.jfixby.r3.rana.api.manager.AssetsPurgeResult;
import com.jfixby.r3.rana.api.pkg.PACKAGE_STATUS;
import com.jfixby.r3.rana.api.pkg.PackageHandler;
import com.jfixby.r3.rana.api.pkg.PackageSearchParameters;
import com.jfixby.r3.rana.api.pkg.PackageSearchResult;
import com.jfixby.r3.rana.api.pkg.PackagesManager;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;
import com.jfixby.scarabei.api.taskman.SysExecutor;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class RedAssetsManager implements AssetsManagerComponent {
	final AssetsConsumer stub_consumer = new StubAssetsConsumer();

	@Override
	public void autoResolveAssetAsync (final ID dependency) throws IOException {
		final boolean isMain = SysExecutor.isMainThread();
		if (isMain) {
			L.e("AssetsConsumer heavy call in main thread: autoResolveAssetAsync (" + dependency + ")");
		}

		final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, RedAssetsManager.this.stub_consumer);

		if (asset_entry != null) {
			LoadedAssets.releaseAsset(asset_entry, RedAssetsManager.this.stub_consumer);
		}
		L.e("Asset[" + dependency + "] delays loading since it is not pre-loaded.");
		try {
			RedAssetsManager.this.resolve(dependency, true);
		} catch (final IOException e) {
			throw new IOException("Failed to resolve asset[" + dependency + "]", e);
		}

	}

	private void resolve (final ID dependency, final boolean print_debug_output) throws IOException {

		// L.d("RESOLVING DEPENDENCY", dependency);
		final PackageSearchParameters search_params = new PackageSearchParameters();
		search_params.asset_to_find = dependency;

		final PackageSearchResult search_result = PackagesManager.findPackages(search_params);
		if (print_debug_output) {
			// search_result.print();

			// this.printAllLoadedAssets();
			// L.d();
		}
		if (search_result.isEmpty()) {
			final String msg = "Asset [" + dependency + "] was not found in any package.";
			// L.e()
			L.d(msg);

			if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
				PackagesManager.printAllPackages();
			}

			if (SystemSettings.executionModeCovers(ExecutionMode.EARLY_DEVELOPMENT)) {
				PackagesManager.printAllIndexes();
			}
// Err.reportError(msg);
			throw new IOException(msg);
			//
		}

		final PackageHandler package_handler = search_result.getBest();
		// package_handler.print();

		final PACKAGE_STATUS package_status = package_handler.getStatus();
		if (PACKAGE_STATUS.NOT_INSTALLED == package_status) {
			package_handler.install();
		}

		final PackageFormat format = package_handler.getFormat();
		final Collection<PackageLoader> package_loaders = PackagesLoader.findPackageReaders(format);
		if (package_loaders.isEmpty()) {
			PackagesLoader.printInstalledPackageReaders();
// L.e("Failed to read package", package_handler);
			throw new IOException("Failed to read package: " + package_handler + " No package reader for " + format);
			//
		}

		final PackageLoader package_reader = package_loaders.getLast();
		// final DebugTimer debigTimer = Debug.newTimer();
		// debigTimer.reset();

		final PackageReaderInput readArgs = new PackageReaderInput();
		readArgs.packageRootFile = package_handler.getRootFile(true);
		final AssetsContainer container = LoadedAssets.newAssetsContainer();
		readArgs.assetsContainer = container;
		readArgs.packageInfo.packageName = package_handler.getPackageName();
		readArgs.packageInfo.packageFormat = package_handler.getFormat();
		readArgs.packageInfo.packedAssets = package_handler.listPackedAssets();
		readArgs.packageInfo.dependencies = package_handler.listDependencies();
		final Collection<ID> deps = package_handler.listDependencies();
		if (deps.size() > 0) {
			this.autoResolveDeps(deps);
		}
		L.d("Rana: reading package", readArgs.packageRootFile.parent());
		package_reader.doReadPackage(readArgs);
		container.printAll();
		LoadedAssets.registerAssetsContainer(container.seal());
		this.conrtainersReg.registerContainer(container.seal());
// L.d("MISSING OWNER!");
// package_handler.doReadPackage(listener, package_reader);
// debigTimer.printTimeAbove(50L, "LOAD-TIME: Asset[" + dependency + "] loaded");

// return true;
	}

	private void autoResolveDeps (final Collection<ID> listDependencies) throws IOException {
		this.autoResolveAssetsAsync(listDependencies);
	}

	@Override
	public void autoResolveAssetsAsync (final Collection<ID> dependencies) throws IOException {
		Debug.checkNull("dependencies", dependencies);

		final boolean isMain = SysExecutor.isMainThread();
		if (isMain) {
			L.e("AssetsConsumer heavy call in main thread: autoResolveAssets (" + dependencies.toJavaList() + ")");
		}

		for (final ID dependency : dependencies) {

			final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, RedAssetsManager.this.stub_consumer);

			if (asset_entry != null) {
				L.d("already loaded", dependency);
				LoadedAssets.releaseAsset(asset_entry, RedAssetsManager.this.stub_consumer);
				continue;
			}
			// if (!updated) {
			//// ResourcesManager.updateAll();
			// updated = true;
			// }
			// this.resolve(dependency, true, listener);
			try {
				RedAssetsManager.this.resolve(dependency, true);
			} catch (final IOException e) {
				throw new IOException("Failed to resolve asset[" + dependency + "]", e);
			}
		}
	}

	final ContainersRegistry conrtainersReg = new ContainersRegistry();

	@Override
	public AssetsPurgeResult purge () {
		final RedAssetsPurgeResult result = new RedAssetsPurgeResult();
		final Collection<SealedAssetsContainer> unused = LoadedAssets.listUnusedContainers();
		for (final SealedAssetsContainer c : unused) {
			final AssetsContainerOwner owner = this.conrtainersReg.getContainerOwner(c);
			Debug.checkNull("Owner of " + c + " not found", owner);
			owner.onAssetsUnload(c);
			this.conrtainersReg.unregisterContainer(c);
			if (!this.conrtainersReg.hasMoreContainers(owner)) {
				this.conrtainersReg.unregister(owner);
				result.addOwner(owner);
			}
		}
		LoadedAssets.unRegisterAssetsContainers(unused);
		return result;
	}

	@Override
	public Promise<Void> autoResolveAsset (final ID dependency) {
		final Future<Void, Void> future = new Future<Void, Void>() {

			@Override
			public Void deliver (final Void input) throws Throwable {
				RedAssetsManager.this.autoResolveAssetAsync(dependency);
				return null;
			}
		};
		return TaskManager.newPromise(future);
	}

	@Override
	public Promise<Void> autoResolveAssets (final Collection<ID> dependencies) {
		final Future<Void, Void> future = new Future<Void, Void>() {

			@Override
			public Void deliver (final Void input) throws Throwable {
				RedAssetsManager.this.autoResolveAssetsAsync(dependencies);
				return null;
			}
		};
		return TaskManager.newPromise(future);
	}

}
