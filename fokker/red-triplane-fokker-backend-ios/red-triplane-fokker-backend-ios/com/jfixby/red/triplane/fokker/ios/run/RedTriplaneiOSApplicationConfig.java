
package com.jfixby.red.triplane.fokker.ios.run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.iosrobovm.IOSApplicationConfiguration;

public class RedTriplaneiOSApplicationConfig {

	private ApplicationListener gdx_listener;
	private IOSApplicationConfiguration app_config;

	public void setGdxListener (final ApplicationListener gdx_listener) {
		this.gdx_listener = gdx_listener;
	}

	public void setApplicationConfig (final IOSApplicationConfiguration app_config) {
		this.app_config = app_config;
	}

	public ApplicationListener getGdxListener () {
		return this.gdx_listener;
	}

	public IOSApplicationConfiguration getIOSApplicationConfiguration () {
		return this.app_config;
	}

}
