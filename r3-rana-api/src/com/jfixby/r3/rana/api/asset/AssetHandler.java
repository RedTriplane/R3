
package com.jfixby.r3.rana.api.asset;

import com.jfixby.r3.rana.api.Asset;
import com.jfixby.scarabei.api.names.ID;

public interface AssetHandler {

	<T extends Asset> T asset ();

	ID ID ();

	void printAll ();

// long readPackageTimeStamp ();

}
