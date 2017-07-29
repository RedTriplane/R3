
package com.jfixby.red.triplane.fokker.android.run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;

public class RedTriplaneAndroidApplicationConfig {

	private ApplicationListener gdx_listener;
	private AndroidApplicationConfiguration android_config;

	public void setGdxListener (final ApplicationListener gdx_listener) {
		this.gdx_listener = gdx_listener;
	}

	public void setAndroidApplicationConfig (final AndroidApplicationConfiguration android_config) {
		this.android_config = android_config;
	}

	public ApplicationListener getGdxListener () {
		return this.gdx_listener;
	}

	public AndroidApplicationConfiguration getAndroidApplicationConfig () {
		return this.android_config;
	}

}
