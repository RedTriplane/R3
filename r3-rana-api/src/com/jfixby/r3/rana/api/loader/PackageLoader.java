
package com.jfixby.r3.rana.api.loader;

import java.io.IOException;

import com.jfixby.r3.rana.api.AssetsContainer;
import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.promise.Promise;

public interface PackageLoader {
	Collection<PackageFormat> listAcceptablePackageFormats ();

	Promise<AssetsContainer> doReadPackage (PackageReaderInput input) throws IOException;

}
