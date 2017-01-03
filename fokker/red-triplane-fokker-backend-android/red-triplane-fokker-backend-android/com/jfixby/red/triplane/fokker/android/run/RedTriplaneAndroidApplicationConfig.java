
package com.jfixby.red.triplane.fokker.android.run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.iOSApplicationConfiguration;

public class RedTriplaneAndroidApplicationConfig {

	private ApplicationListener gdx_listener;
	private iOSApplicationConfiguration android_config;

	public void setGdxListener (ApplicationListener gdx_listener) {
		this.gdx_listener = gdx_listener;
	}

	public void setAndroidApplicationConfig (iOSApplicationConfiguration android_config) {
		this.android_config = android_config;
	}

	public ApplicationListener getGdxListener () {
		return gdx_listener;
	}

	public iOSApplicationConfiguration getAndroidApplicationConfig () {
		return android_config;
	}

}
