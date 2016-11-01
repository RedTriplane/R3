
package com.jfixby.red.triplane.fokker.android;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.backends.android.RedAndroidApplication;
import com.jfixby.android.api.AndroidComponent;
import com.jfixby.android.api.camera.AndroidCameraSetup;
import com.jfixby.cmns.api.sys.Sys;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
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

}
