
package com.jfixby.red.triplane.fokker.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.backends.android.RedAndroidApplication;
import com.jfixby.android.api.AndroidComponent;
import com.jfixby.android.api.AndroidSystemInfoTags;
import com.jfixby.android.api.AndroidAppVersion;
import com.jfixby.android.api.DisplayMetrics;
import com.jfixby.android.api.camera.AndroidCameraSetup;
import com.jfixby.cmns.api.err.Err;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.file.LocalFileSystem;
import com.jfixby.cmns.api.sys.Sys;
import com.jfixby.cmns.api.sys.SystemInfo;
import com.jfixby.cmns.api.sys.SystemInfoTags;
import com.jfixby.red.sys.RedDeviceInfo;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

public abstract class RedTriplaneAndroidApplication extends RedAndroidApplication implements AndroidComponent {

	private ApplicationListener gdxListener;
	private AndroidApplicationConfiguration androidConfig;
	private final RedAndroidCameraSetup cameraSetup = new RedAndroidCameraSetup(this);
	public static int orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;

	static boolean deployed = false;
	public static boolean useCamera = false;

	@Override
	protected void onCreate (final Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Log.d("", "onCreate()");
		this.setRequestedOrientation(orientation);
		boolean deploying = false;
		synchronized (this) {
			if (deployed) {
				return;
			}
			deploying = true;

			final RedTriplaneAndroidApplicationConfig config = this.doGdxDeploy(this);
			this.gdxListener = config.getGdxListener();
			this.androidConfig = config.getAndroidApplicationConfig();
			if (useCamera) {
				this.cameraSetup.prepareCamera();
			}
			deployed = true;
		}
		this.initialize(this.gdxListener, this.androidConfig);
		if (useCamera && deploying) {
			this.cameraSetup.activateCamera();
		}
	}

	public abstract RedTriplaneAndroidApplicationConfig doGdxDeploy (RedTriplaneAndroidApplication redTriplaneAndroidApplication);

	public void printMemoryClass () {
		final ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		final int memoryClass = am.getMemoryClass();
		Log.d("", "memoryClass:" + Integer.toString(memoryClass));
	}

	@Override
	public long getRecommendedHeapSize () {
		final ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		final int memoryClass = am.getMemoryClass();
		return memoryClass;
	}

	@Override
	public long getMaxHeapSize () {
		final Runtime rt = Runtime.getRuntime();
		final long maxMemory = rt.maxMemory() / (1024 * 1024);
		// Log.v("onCreate", "maxMemory:" + Long.toString(maxMemory));
		return maxMemory;
	}

	@Override
	public String getApplicationPrivateDirPathString () {
		final String java_path = this.getApplication().getApplicationContext().getFilesDir().getAbsolutePath();
		return java_path;
	}

	@Override
	public AndroidCameraSetup getCameraSetup () {
		return this.cameraSetup;
	}

	@Override
	protected void onPause () {
		super.onPause();
		Log.d("", "onPause()");
		Sys.exit();
	}

	@Override
	protected void onResume () {
		super.onResume();
		Log.d("", "onResume()");
	}

	@Override
	protected void onDestroy () {
		super.onDestroy();
		Log.d("", "onDestroy()");
	}

	@Override
	protected void onRestart () {
		super.onRestart();
		Log.d("", "onRestart()");
	}

	@Override
	protected void onStart () {
		super.onStart();
		Log.d("", "onStart()");
	}

	@Override
	protected void onStop () {
		super.onStop();
		Log.d("", "onStop()");
	}

	public void post (final Runnable r) {
		this.handler.post(r);
	}

	public AndroidGraphics graphics () {
		return this.graphics;
	}

	@Override
	public File getPrivateFolder () {
		final String path = this.getApplicationPrivateDirPathString();
		final File privateFolder = LocalFileSystem.newFile(path);
		return privateFolder;
	}

	@Override
	public File getCacheFolder () {
		final java.io.File cache = this.getCacheDir();
		return LocalFileSystem.newFile(cache);
	}

	@Override
	public DisplayMetrics getDisplayMetrics () {

		final android.util.DisplayMetrics dm = new android.util.DisplayMetrics();
		try {
			this.getWindowManager().getDefaultDisplay().getMetrics(dm);
		} catch (final Exception e) {
			Err.reportError(e);
		}
		final int height = dm.heightPixels;
		final int width = dm.widthPixels;

		this.displayMetrics.set(width, height);

		return this.displayMetrics;
	}

	final RedDisplayMetrics displayMetrics = new RedDisplayMetrics();

	@Override
	public String getBrand () {
		return Build.BRAND;
	}

	@Override
	public String getSerial () {
		return Build.SERIAL;
	}

	@Override
	public String getModel () {
		return Build.MODEL;
	}

	@Override
	public String getFingerPrint () {
		return Build.FINGERPRINT;
	}

	@Override
	public String getManufacturer () {
		return Build.MANUFACTURER;
	}

	@Override
	public String getHost () {
		return Build.HOST;
	}

	@Override
	public String getVersionRelease () {
		return Build.VERSION.RELEASE;
	}

	@Override
	public AndroidAppVersion getAppVersion () {
		final RedAndroidAppVersion version = new RedAndroidAppVersion();

		try {
			final PackageInfo pInfo = this.getPackageManager().getPackageInfo(this.getPackageName(), 0);
			version.package_name = this.getPackageName();
			version.name = pInfo.versionName;
			version.code = pInfo.versionCode;

		} catch (final NameNotFoundException e) {
			e.printStackTrace();
		}

		return version;
	}

	@Override
	public SystemInfo getSystemInfo () {
		final RedDeviceInfo deviceInfo = new RedDeviceInfo();
		{
			final DisplayMetrics displayMetrics = this.getDisplayMetrics();
			final int height = displayMetrics.getHeight();
			final int width = displayMetrics.getWidth();
			deviceInfo.putValue(AndroidSystemInfoTags.Display.WIDTH, width);
			deviceInfo.putValue(AndroidSystemInfoTags.Display.HEIGHT, height);
		}
		{
			final String brand = this.getBrand();
			deviceInfo.putValue(AndroidSystemInfoTags.Brand, brand);
		}
		{
			final String value = this.getSerial();
			deviceInfo.putValue(AndroidSystemInfoTags.Serial, value);
		}

		{
			final String value = this.getFingerPrint();
			deviceInfo.putValue(AndroidSystemInfoTags.Fingerprint, value);
		}

		{
			final String value = this.getManufacturer();
			deviceInfo.putValue(AndroidSystemInfoTags.Manufacturer, value);
		}

		{
			final String model = this.getModel();
			deviceInfo.putValue(AndroidSystemInfoTags.Model, model);
		}
		{
			final String release = this.getVersionRelease();
			deviceInfo.putValue(AndroidSystemInfoTags.Release, release);
		}

		{
			final AndroidAppVersion version = this.getAppVersion();
			deviceInfo.putValue(AndroidSystemInfoTags.App.Version.Name, version.getName());
			deviceInfo.putValue(AndroidSystemInfoTags.App.Version.Code, version.getCode());
			deviceInfo.putValue(AndroidSystemInfoTags.App.Version.PackageName, version.getPackageName());
		}

		{
			final String host = this.getHost();
			deviceInfo.putValue(AndroidSystemInfoTags.Host, host);
		}

		{
			deviceInfo.putValue(SystemInfoTags.System.OS_NAME, "Android");
		}

		return deviceInfo;
	}

}
