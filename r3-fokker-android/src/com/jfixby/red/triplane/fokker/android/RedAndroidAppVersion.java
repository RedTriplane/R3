
package com.jfixby.red.triplane.fokker.android;

import com.jfixby.scarabei.android.api.AndroidAppVersion;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.names.Names;

public class RedAndroidAppVersion implements AndroidAppVersion {

	public int code = 0;
	public String name;
	public String package_name;

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public int getCode () {
		return this.code;
	}

	@Override
	public ID getPackageName () {
		return Names.newID(this.package_name);
	}

}
