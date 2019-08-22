
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.scarabei.api.names.ID;

public interface PackagesBank {

	ID getName ();

	void printAllIndexes ();

	PackageSearchResult findPackages (PackageSearchParameters search_params);

// Promise<Void> rebuildIndex () throws IOException;

	AssetsTank getTank (String tankName);

}
