
package com.jfixby.r3.fokker.texture.red;

import com.jfixby.r3.fokker.api.FokkerThread;
import com.jfixby.r3.fokker.texture.api.FokkerTexturePackageReader;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;

public final class FokkerTextureLoader implements FokkerTexturePackageReader, PackageLoader {
	public static final PackageFormat TEXTURE = new PackageFormat(FokkerTexturePackageReader.PACKAGE_FORMAT_TEXTURE);
	public static final PackageFormat ATLAS = new PackageFormat(FokkerTexturePackageReader.PACKAGE_FORMAT_ATLAS);

	final List<PackageFormat> acceptablePackageFormats = Collections.newList();
	private final RedFokkerTextures registry;

	public FokkerTextureLoader (final RedFokkerTextures registry) {
		this.acceptablePackageFormats.add(TEXTURE);
		this.acceptablePackageFormats.add(ATLAS);
		this.registry = registry;
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public Promise<AssetsContainer> doReadPackage (final PackageReaderInput input) {

		final Future<Void, AssetsContainer> futre = new Future<Void, AssetsContainer>() {
			@Override
			public AssetsContainer deliver (final Void v) throws Throwable {
				final RedFokkerRasterDataGroup group = new RedFokkerRasterDataGroup();
				group.read(input, FokkerTextureLoader.this, FokkerTextureLoader.this.registry);
				return input.assetsContainer;
			}
		};

		final Promise<AssetsContainer> promiseToLoad = FokkerThread
			.executeInMainThread("FokkerTextureLoader.doReadPackage(" + input.packageInfo.packageName + ")", futre);
		return promiseToLoad;

	}

	@Override
	public PackageLoader reader () {
		return this;
	}

}
