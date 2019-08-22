
package com.jfixby.r3.rana.api.pkg;

import java.io.IOException;

public interface AssetsTank {

	PackageSearchResult findPackages (PackageSearchParameters search_params);

	void rebuildIndex () throws IOException;

	public String getName ();

	void printIndex ();

	String getShortName ();

	void installPackage (PackagePackingArgs pkgSpec) throws IOException;

}
