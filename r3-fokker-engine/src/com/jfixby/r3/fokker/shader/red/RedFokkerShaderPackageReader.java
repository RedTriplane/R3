
package com.jfixby.r3.fokker.shader.red;

import com.jfixby.r3.fokker.api.FokkerThread;
import com.jfixby.r3.fokker.shader.api.FokkerShaderPackageReader;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;

public class RedFokkerShaderPackageReader implements PackageLoader, FokkerShaderPackageReader {

	final List<PackageFormat> acceptablePackageFormats;
	private final RedFokkerShaders redFokkerShaders;

	public RedFokkerShaderPackageReader (final RedFokkerShaders redFokkerShaders) {
		this.redFokkerShaders = redFokkerShaders;
		this.acceptablePackageFormats = Collections.newList(new PackageFormat(FokkerShaderPackageReader.PACKAGE_FORMAT));
//
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
				final ShadersGroup group = new ShadersGroup();
				group.read(input, RedFokkerShaderPackageReader.this.redFokkerShaders);
				return input.assetsContainer;
			}
		};

		final Promise<AssetsContainer> promiseToLoad = FokkerThread
			.executeInMainThread("RedFokkerShaderPackageReader.doReadPackage(" + input.packageInfo.packageName + ")", futre);
		return promiseToLoad;
	}

	@Override
	public PackageLoader reader () {
		return this;
	}

}
