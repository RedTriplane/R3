
package com.jfixby.red.triplane.fokker.ios.run;

import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.assets.Names;
import com.jfixby.scarabei.ios.api.iOSAppVersion;

public class RedAndroidAppVersion implements iOSAppVersion {

	public String build;
	public String name;
	public String package_name;

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getBuild () {
		return this.build;
	}

	@Override
	public ID getPackageName () {
		return Names.newID(this.package_name);
	}

}
