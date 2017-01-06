
package com.jfixby.red.triplane.fokker.ios.run;

import com.badlogic.gdx.backends.iosrobovm.IOSApplication;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.sys.SystemInfo;
import com.jfixby.scarabei.ios.api.DisplayMetrics;
import com.jfixby.scarabei.ios.api.iOSAppVersion;
import com.jfixby.scarabei.ios.api.iOSComponent;
import com.jfixby.scarabei.ios.api.camera.iOSCameraSetup;

public class RedTriplaneiOSApplication implements iOSComponent {

	private final IOSApplication.Delegate iosLauncher;
	private final iOSLauncher launcher;

	public RedTriplaneiOSApplication (final iOSLauncher launcher) {
		if (launcher == null) {
			throw new Error("launcher is null");
		}
		this.iosLauncher = launcher.getDelegate();
		this.launcher = launcher;
	}

	@Override
	public String getApplicationPrivateDirPathString () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public iOSCameraSetup getCameraSetup () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public File getPrivateFolder () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public File getCacheFolder () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public DisplayMetrics getDisplayMetrics () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getBrand () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getModel () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getHost () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getVersionRelease () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public iOSAppVersion getAppVersion () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public SystemInfo getSystemInfo () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getSerial () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getFingerPrint () {
		Err.throwNotImplementedYet();
		return null;
	}

	@Override
	public String getManufacturer () {
		Err.throwNotImplementedYet();
		return null;
	}

}
