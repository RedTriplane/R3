
package com.jfixby.red.triplane.fokker.android.run;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.badlogic.gdx.backends.android.RedAndroidApplication;
import com.jfixby.scarabei.android.api.AndroidComponent;
import com.jfixby.scarabei.api.sys.Sys;

import android.app.ActivityManager;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;

public abstract class RedTriplaneAndroidApplication extends RedAndroidApplication {

	private ApplicationListener gdxListener;
	private AndroidApplicationConfiguration androidConfig;

	public static int orientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE;
	final RedAndroidComponent redAndroidComponent = new RedAndroidComponent(this);

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
				this.redAndroidComponent.prepareCamera();
			}
			deployed = true;
		}
		this.initialize(this.gdxListener, this.androidConfig);
		if (useCamera && deploying) {
			this.redAndroidComponent.activateCamera();
		}
	}

	public abstract RedTriplaneAndroidApplicationConfig doGdxDeploy (RedTriplaneAndroidApplication redTriplaneAndroidApplication);

	public void printMemoryClass () {
		final ActivityManager am = (ActivityManager)this.getSystemService(ACTIVITY_SERVICE);
		final int memoryClass = am.getMemoryClass();
		Log.d("", "memoryClass:" + Integer.toString(memoryClass));
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

	public AndroidComponent getAndroidComponent () {
		return this.redAndroidComponent;
	}

}
