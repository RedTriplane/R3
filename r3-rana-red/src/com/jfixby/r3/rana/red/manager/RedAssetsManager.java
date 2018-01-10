
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
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.sys.settings.ExecutionMode;
import com.jfixby.scarabei.api.sys.settings.SystemSettings;

public class RedAssetsManager implements AssetsManagerComponent {
	final AssetsConsumer stub_consumer = new StubAssetsConsumer();

// @Override
// public void autoResolveAssetAsync (final ID dependency) throws IOException {
//
// }

//
// @Override
// public void autoResolveAssetsAsync (final Collection<ID> dependencies) throws IOException {
// Debug.checkNull("dependencies", dependencies);
//
// final boolean isMain = SysExecutor.isMainThread();
// if (isMain) {
// L.e("AssetsConsumer heavy call in main thread: autoResolveAssets (" + dependencies.toJavaList() + ")");
// }
//
// for (final ID dependency : dependencies) {
//
// final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, RedAssetsManager.this.stub_consumer);
//
// if (asset_entry != null) {
// L.d("already loaded", dependency);
// LoadedAssets.releaseAsset(asset_entry, RedAssetsManager.this.stub_consumer);
// continue;
// }
// // if (!updated) {
// //// ResourcesManager.updateAll();
// // updated = true;
// // }
// // this.resolve(dependency, true, listener);
// try {
//// RedAssetsManager.this.resolve(dependency, true).await();
// final PackageHandler pkg = this.findPackage(dependency, true).deliver(null);
// final PackageHandler deps = this.resolveDependencies().deliver(pkg);
// final PackageReaderInputAndReader install = this.installPackage().deliver(deps);
// this.readPackage().deliver(install);
// } catch (final Throwable e) {
// throw new IOException("Failed to resolve asset[" + dependency + "]", e);
// }
// }
// }
//
// private Promise<AssetsContainer> resolve (final Collection<ID> dependencies, final boolean print_debug_output) {
// final Promise<PackageHandler> promiseToFindPackage = TaskManager.newPromise("findPackage(" + dependency + ")",
// this.findPackage(dependency, print_debug_output));
// final Promise<PackageHandler> promiseToResolve = promiseToFindPackage.then("resolce dependencies for (" + dependency + ")",
// this.resolveDependencies());
// final Promise<PackageReaderInputAndReader> promisePrepareToRead = promiseToResolve
// .then("installAndReadPackage(" + dependency + ")", this.installPackage());
// final Promise<AssetsContainer> readPromise = promisePrepareToRead.then("readPackage", this.readPackage());
// return readPromise;
// }

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

	void registerAssetsContainer (final SealedAssetsContainer container) {
		LoadedAssets.registerAssetsContainer(container);
		RedAssetsManager.this.conrtainersReg.registerContainer(container);
	}

	@Override
	public void autoResolveAsset (final ID dependency) throws IOException {

		final AssetHandler asset_entry = LoadedAssets.obtainAsset(dependency, RedAssetsManager.this.stub_consumer);

		if (asset_entry != null) {
			LoadedAssets.releaseAsset(asset_entry, RedAssetsManager.this.stub_consumer);
			return;
		}
		L.e("Asset[" + dependency + "] delays loading since it is not pre-loaded.");

		final AssetsContainer container = RedAssetsManager.this.resolveAsync(dependency, true);
		Debug.checkNull("container", container);
// return container;

		if (container == null) {
			return;
		}
		RedAssetsManager.this.registerAssetsContainer(container.seal());
		return;
	}

	private AssetsContainer resolveAsync (final ID dependency, final boolean print_debug_output) throws IOException {
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

		final Collection<ID> deps = package_handler.listDependencies();
		RedAssetsManager.this.autoResolveAssets(deps);

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

		L.d("Rana: reading package[" + readArgs.packageInfo.packageFormat + "]", readArgs.packageRootFile.parent());
		L.d("                     ", package_reader);
		final AssetsContainer result_container = package_reader.doReadPackage(readArgs);
		return result_container;

	}

	@Override
	public void autoResolveAssets (final Collection<ID> dependencies) throws IOException {
		for (final ID dependency : dependencies) {
			RedAssetsManager.this.autoResolveAsset(dependency);
		}
	}

}
