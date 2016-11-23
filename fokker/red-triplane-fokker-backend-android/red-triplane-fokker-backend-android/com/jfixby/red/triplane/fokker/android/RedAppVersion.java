
package com.jfixby.red.triplane.fokker.android;

import com.jfixby.android.api.AppVersion;

public class RedAppVersion implements AppVersion {

	public String code;
	public String name;
	public String package_name;

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getCode () {
		return this.code;
	}

	@Override
	public String getPackageName () {
		return this.package_name;
	}

}
