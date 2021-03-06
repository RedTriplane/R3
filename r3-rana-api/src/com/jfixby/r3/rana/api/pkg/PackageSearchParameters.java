
package com.jfixby.r3.rana.api.pkg;

import com.jfixby.r3.rana.api.format.PackageFormat;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;

public class PackageSearchParameters {

	public final List<PackageFormat> acceptablePackageFormats = Collections.newList();
	public final List<PACKAGE_STATUS> acceptablePackageStatuses = Collections.newList(PACKAGE_STATUS.INSTALLED,
		PACKAGE_STATUS.NOT_INSTALLED);

	public ID asset_to_find;
	public boolean getAllFlag = false;

	public void print () {
		L.d(this.toString());
	}

	@Override
	public String toString () {
		return "PackageSearchParameters: acceptablePackageFormats=" + this.acceptablePackageFormats + ", acceptablePackageStatuses="
			+ this.acceptablePackageStatuses + ", asset_to_find=<" + this.asset_to_find + ">";
	}

}
