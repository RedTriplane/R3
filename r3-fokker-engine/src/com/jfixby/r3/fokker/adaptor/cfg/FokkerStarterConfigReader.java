
package com.jfixby.r3.fokker.adaptor.cfg;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageInfo;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.json.Json;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class FokkerStarterConfigReader implements PackageLoader {

	private final List<PackageFormat> acceptablePackageFormats;

	public FokkerStarterConfigReader () {
		super();
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(FokkerStarterConfig.PACKAGE_FORMAT);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public Promise<AssetsContainer> doReadPackage (final PackageReaderInput input) {
		final Future<Void, AssetsContainer> future = new Future<Void, AssetsContainer>() {
			@Override
			public AssetsContainer deliver (final Void v) throws Throwable {
				final String str = input.packageRootFile.readToString();
				final FokkerStarterConfig configData = Json.deserializeFromString(FokkerStarterConfig.class, str);
				final PackageInfo info = input.packageInfo;
				final String name = info.packageName;
				final ID assetID = Names.newID(name);
				final FokkerStarterConfigAsset asset = new FokkerStarterConfigAsset(assetID, configData);
				input.assetsContainer.addAsset(assetID, asset);
				return input.assetsContainer;
			}
		};
		return TaskManager.executeAsynchronously("FokkerStarterConfigReader.doReadPackage", future);
	}

}
