
package com.jfixby.r3.fokker.font.red;

import java.io.IOException;

import com.jfixby.r3.fokker.font.api.FokkerFontPackageReader;
import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.r3.rana.api.loader.PackageLoader;
import com.jfixby.r3.rana.api.loader.PackageReaderInput;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.promise.Future;
import com.jfixby.scarabei.api.promise.Promise;
import com.jfixby.scarabei.api.taskman.TaskManager;

public class TrueTypeFontFontPackageLoader implements PackageLoader, FokkerFontPackageReader {

	final List<PackageFormat> acceptablePackageFormats;
	private final FokkerFontRegister redFokkerFonts;

	public TrueTypeFontFontPackageLoader (final FokkerFontRegister redFokkerFonts) {
		this.redFokkerFonts = redFokkerFonts;
		this.acceptablePackageFormats = Collections.newList();
		final PackageFormat format = new PackageFormat(FokkerFontPackageReader.PACKAGE_FORMAT);
		this.acceptablePackageFormats.add(format);
	}

	@Override
	public Collection<PackageFormat> listAcceptablePackageFormats () {
		return this.acceptablePackageFormats;
	}

	@Override
	public Promise<AssetsContainer> doReadPackage (final PackageReaderInput input) throws IOException {
// final PackageHandler handler = input.getPackageHandler();
		final Future<Void, AssetsContainer> future = new Future<Void, AssetsContainer>() {
			@Override
			public AssetsContainer deliver (final Void v) throws Throwable {
				final RedTTFFontGroup group = new RedTTFFontGroup();
				group.read(input, TrueTypeFontFontPackageLoader.this.redFokkerFonts);
				return input.assetsContainer;
			}
		};
		return TaskManager.newPromise("TrueTypeFontFontPackageLoader.doReadPackage()", future);
	}

	@Override
	public PackageLoader reader () {
		return this;
	}

}
