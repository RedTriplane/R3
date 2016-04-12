/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package com.badlogic.gdx.backends.android;

import java.lang.reflect.Method;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.android.surfaceview.FillResolutionStrategy;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.GdxNativesLoader;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.SnapshotArray;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;

/** An implementation of the {@link Application} interface for Android. Create an {@link Activity} that derives from this class.
 * In the {@link Activity#onCreate(Bundle)} method call the {@link #initialize(ApplicationListener)} method specifying the
 * configuration for the GLSurfaceView.
 *
 * @author mzechner */
public class RedAndroidApplication extends Activity implements AndroidApplicationBase {
	static {
		GdxNativesLoader.load();
	}

	protected RedAndroidGraphics graphics;
	protected AndroidInput input;
	protected AndroidAudio audio;
	protected AndroidFiles files;
	protected AndroidNet net;
	protected ApplicationListener listener;
	public Handler handler;
	protected boolean firstResume = true;
	protected final Array<Runnable> runnables = new Array<Runnable>();
	protected final Array<Runnable> executedRunnables = new Array<Runnable>();
	protected final SnapshotArray<LifecycleListener> lifecycleListeners = new SnapshotArray<LifecycleListener>(
		LifecycleListener.class);
	private final Array<AndroidEventListener> androidEventListeners = new Array<AndroidEventListener>();
	protected int logLevel = LOG_INFO;
	protected boolean useImmersiveMode = false;
	protected boolean hideStatusBar = false;
	private int wasFocusChanged = -1;
	private boolean isWaitingForAudio = false;

	/** This method has to be called in the {@link Activity#onCreate(Bundle)} method. It sets up all the things necessary to get
	 * input, render via OpenGL and so on. Uses a default {@link AndroidApplicationConfiguration}.
	 *
	 * @param listener the {@link ApplicationListener} implementing the program logic **/
	public void initialize (final ApplicationListener listener) {
		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		this.initialize(listener, config);
	}

	/** This method has to be called in the {@link Activity#onCreate(Bundle)} method. It sets up all the things necessary to get
	 * input, render via OpenGL and so on. You can configure other aspects of the application with the rest of the fields in the
	 * {@link AndroidApplicationConfiguration} instance.
	 *
	 * @param listener the {@link ApplicationListener} implementing the program logic
	 * @param config the {@link AndroidApplicationConfiguration}, defining various settings of the application (use accelerometer,
	 *           etc.). */
	public void initialize (final ApplicationListener listener, final AndroidApplicationConfiguration config) {
		this.init(listener, config, false);
	}

	/** This method has to be called in the {@link Activity#onCreate(Bundle)} method. It sets up all the things necessary to get
	 * input, render via OpenGL and so on. Uses a default {@link AndroidApplicationConfiguration}.
	 * <p>
	 * Note: you have to add the returned view to your layout!
	 *
	 * @param listener the {@link ApplicationListener} implementing the program logic
	 * @return the GLSurfaceView of the application */
	public View initializeForView (final ApplicationListener listener) {
		final AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
		return this.initializeForView(listener, config);
	}

	/** This method has to be called in the {@link Activity#onCreate(Bundle)} method. It sets up all the things necessary to get
	 * input, render via OpenGL and so on. You can configure other aspects of the application with the rest of the fields in the
	 * {@link AndroidApplicationConfiguration} instance.
	 * <p>
	 * Note: you have to add the returned view to your layout!
	 *
	 * @param listener the {@link ApplicationListener} implementing the program logic
	 * @param config the {@link AndroidApplicationConfiguration}, defining various settings of the application (use accelerometer,
	 *           etc.).
	 * @return the GLSurfaceView of the application */
	public View initializeForView (final ApplicationListener listener, final AndroidApplicationConfiguration config) {
		this.init(listener, config, true);
		return this.graphics.getView();
	}

	private void init (final ApplicationListener listener, final AndroidApplicationConfiguration config, final boolean isForView) {
		if (this.getVersion() < MINIMUM_SDK) {
			throw new GdxRuntimeException("LibGDX requires Android API Level " + MINIMUM_SDK + " or later.");
		}

		this.graphics = new RedAndroidGraphics(this, config,
			config.resolutionStrategy == null ? new FillResolutionStrategy() : config.resolutionStrategy);
		this.input = AndroidInputFactory.newAndroidInput(this, this, this.graphics.view, config);
		this.audio = new AndroidAudio(this, config);
		this.getFilesDir(); // workaround for Android bug #10515463
		this.files = new AndroidFiles(this.getAssets(), this.getFilesDir().getAbsolutePath());
		this.net = new AndroidNet(this);
		this.listener = listener;
		this.handler = new Handler();
		this.useImmersiveMode = config.useImmersiveMode;
		this.hideStatusBar = config.hideStatusBar;

		// Add a specialized audio lifecycle listener
		this.addLifecycleListener(new LifecycleListener() {

			@Override
			public void resume () {
				// No need to resume audio here
			}

			@Override
			public void pause () {
				RedAndroidApplication.this.audio.pause();
			}

			@Override
			public void dispose () {
				RedAndroidApplication.this.audio.dispose();
			}
		});

		Gdx.app = this;
		Gdx.input = this.getInput();
		Gdx.audio = this.getAudio();
		Gdx.files = this.getFiles();
		Gdx.graphics = this.getGraphics();
		Gdx.net = this.getNet();

		if (!isForView) {
			try {
				this.requestWindowFeature(Window.FEATURE_NO_TITLE);
			} catch (final Exception ex) {
				this.log("AndroidApplication", "Content already displayed, cannot request FEATURE_NO_TITLE", ex);
			}
			this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
			this.setContentView(this.graphics.getView(), this.createLayoutParams());
		}

		this.createWakeLock(config.useWakelock);
		this.hideStatusBar(this.hideStatusBar);
		this.useImmersiveMode(this.useImmersiveMode);
		if (this.useImmersiveMode && this.getVersion() >= Build.VERSION_CODES.KITKAT) {
			try {
				final Class<?> vlistener = AndroidVisibilityListener.class;
				final Object o = vlistener.newInstance();
				final Method method = vlistener.getDeclaredMethod("createListener", AndroidApplicationBase.class);
				method.invoke(o, this);
			} catch (final Exception e) {
				this.log("AndroidApplication", "Failed to create AndroidVisibilityListener", e);
			}
		}
	}

	protected FrameLayout.LayoutParams createLayoutParams () {
		final FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(android.view.ViewGroup.LayoutParams.MATCH_PARENT,
			android.view.ViewGroup.LayoutParams.MATCH_PARENT);
		layoutParams.gravity = Gravity.CENTER;
		return layoutParams;
	}

	protected void createWakeLock (final boolean use) {
		if (use) {
			this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		}
	}

	protected void hideStatusBar (final boolean hide) {
		if (!hide || this.getVersion() < 11) {
			return;
		}

		final View rootView = this.getWindow().getDecorView();

		try {
			final Method m = View.class.getMethod("setSystemUiVisibility", int.class);
			if (this.getVersion() <= 13) {
				m.invoke(rootView, 0x0);
			}
			m.invoke(rootView, 0x1);
		} catch (final Exception e) {
			this.log("AndroidApplication", "Can't hide status bar", e);
		}
	}

	@Override
	public void onWindowFocusChanged (final boolean hasFocus) {
		super.onWindowFocusChanged(hasFocus);
		this.useImmersiveMode(this.useImmersiveMode);
		this.hideStatusBar(this.hideStatusBar);
		if (hasFocus) {
			this.wasFocusChanged = 1;
			if (this.isWaitingForAudio) {
				this.audio.resume();
				this.isWaitingForAudio = false;
			}
		} else {
			this.wasFocusChanged = 0;
		}
	}

	@TargetApi(19)
	@Override
	public void useImmersiveMode (final boolean use) {
		if (!use || this.getVersion() < Build.VERSION_CODES.KITKAT) {
			return;
		}

		final View view = this.getWindow().getDecorView();
		try {
			final Method m = View.class.getMethod("setSystemUiVisibility", int.class);
			final int code = View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				| View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_FULLSCREEN
				| View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
			m.invoke(view, code);
		} catch (final Exception e) {
			this.log("AndroidApplication", "Can't set immersive mode", e);
		}
	}

	@Override
	protected void onPause () {
		final boolean isContinuous = this.graphics.isContinuousRendering();
		final boolean isContinuousEnforced = RedAndroidGraphics.enforceContinuousRendering;

		// from here we don't want non continuous rendering
		RedAndroidGraphics.enforceContinuousRendering = true;
		this.graphics.setContinuousRendering(true);
		// calls to setContinuousRendering(false) from other thread (ex: GLThread)
		// will be ignored at this point...
		this.graphics.pause();

		this.input.onPause();

		if (this.isFinishing()) {
			this.graphics.clearManagedCaches();
			this.graphics.destroy();
		}

		RedAndroidGraphics.enforceContinuousRendering = isContinuousEnforced;
		this.graphics.setContinuousRendering(isContinuous);

		this.graphics.onPauseGLSurfaceView();

		super.onPause();
	}

	@Override
	protected void onResume () {
		Gdx.app = this;
		Gdx.input = this.getInput();
		Gdx.audio = this.getAudio();
		Gdx.files = this.getFiles();
		Gdx.graphics = this.getGraphics();
		Gdx.net = this.getNet();

		this.input.onResume();

		if (this.graphics != null) {
			this.graphics.onResumeGLSurfaceView();
		}

		if (!this.firstResume) {
			this.graphics.resume();
		} else {
			this.firstResume = false;
		}

		this.isWaitingForAudio = true;
		if (this.wasFocusChanged == 1 || this.wasFocusChanged == -1) {
			this.audio.resume();
			this.isWaitingForAudio = false;
		}
		super.onResume();
	}

	@Override
	protected void onDestroy () {
		super.onDestroy();
		Gdx.app = null;
		Gdx.input = null;
		Gdx.audio = null;
		Gdx.files = null;
		Gdx.graphics = null;
		Gdx.net = null;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return this.listener;
	}

	@Override
	public Audio getAudio () {
		return this.audio;
	}

	@Override
	public Files getFiles () {
		return this.files;
	}

	@Override
	public Graphics getGraphics () {
		return this.graphics;
	}

	@Override
	public AndroidInput getInput () {
		return this.input;
	}

	@Override
	public Net getNet () {
		return this.net;
	}

	@Override
	public ApplicationType getType () {
		return ApplicationType.Android;
	}

	@Override
	public int getVersion () {
		return android.os.Build.VERSION.SDK_INT;
	}

	@Override
	public long getJavaHeap () {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	@Override
	public long getNativeHeap () {
		return Debug.getNativeHeapAllocatedSize();
	}

	@Override
	public Preferences getPreferences (final String name) {
		return new AndroidPreferences(this.getSharedPreferences(name, Context.MODE_PRIVATE));
	}

	AndroidClipboard clipboard;

	@Override
	public Clipboard getClipboard () {
		if (this.clipboard == null) {
			this.clipboard = new AndroidClipboard(this);
		}
		return this.clipboard;
	}

	@Override
	public void postRunnable (final Runnable runnable) {
		synchronized (this.runnables) {
			this.runnables.add(runnable);
			Gdx.graphics.requestRendering();
		}
	}

	@Override
	public void onConfigurationChanged (final Configuration config) {
		super.onConfigurationChanged(config);
		boolean keyboardAvailable = false;
		if (config.hardKeyboardHidden == Configuration.HARDKEYBOARDHIDDEN_NO) {
			keyboardAvailable = true;
		}
		this.input.keyboardAvailable = keyboardAvailable;
	}

	@Override
	public void exit () {
		this.handler.post(new Runnable() {
			@Override
			public void run () {
				RedAndroidApplication.this.finish();
			}
		});
	}

	@Override
	public void debug (final String tag, final String message) {
		if (this.logLevel >= LOG_DEBUG) {
			Log.d(tag, message);
		}
	}

	@Override
	public void debug (final String tag, final String message, final Throwable exception) {
		if (this.logLevel >= LOG_DEBUG) {
			Log.d(tag, message, exception);
		}
	}

	@Override
	public void log (final String tag, final String message) {
		if (this.logLevel >= LOG_INFO) {
			Log.i(tag, message);
		}
	}

	@Override
	public void log (final String tag, final String message, final Throwable exception) {
		if (this.logLevel >= LOG_INFO) {
			Log.i(tag, message, exception);
		}
	}

	@Override
	public void error (final String tag, final String message) {
		if (this.logLevel >= LOG_ERROR) {
			Log.e(tag, message);
		}
	}

	@Override
	public void error (final String tag, final String message, final Throwable exception) {
		if (this.logLevel >= LOG_ERROR) {
			Log.e(tag, message, exception);
		}
	}

	@Override
	public void setLogLevel (final int logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public int getLogLevel () {
		return this.logLevel;
	}

	@Override
	public void addLifecycleListener (final LifecycleListener listener) {
		synchronized (this.lifecycleListeners) {
			this.lifecycleListeners.add(listener);
		}
	}

	@Override
	public void removeLifecycleListener (final LifecycleListener listener) {
		synchronized (this.lifecycleListeners) {
			this.lifecycleListeners.removeValue(listener, true);
		}
	}

	@Override
	protected void onActivityResult (final int requestCode, final int resultCode, final Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		// forward events to our listeners if there are any installed
		synchronized (this.androidEventListeners) {
			for (int i = 0; i < this.androidEventListeners.size; i++) {
				this.androidEventListeners.get(i).onActivityResult(requestCode, resultCode, data);
			}
		}
	}

	/** Adds an event listener for Android specific event such as onActivityResult(...). */
	public void addAndroidEventListener (final AndroidEventListener listener) {
		synchronized (this.androidEventListeners) {
			this.androidEventListeners.add(listener);
		}
	}

	/** Removes an event listener for Android specific event such as onActivityResult(...). */
	public void removeAndroidEventListener (final AndroidEventListener listener) {
		synchronized (this.androidEventListeners) {
			this.androidEventListeners.removeValue(listener, true);
		}
	}

	@Override
	public Context getContext () {
		return this;
	}

	@Override
	public Array<Runnable> getRunnables () {
		return this.runnables;
	}

	@Override
	public Array<Runnable> getExecutedRunnables () {
		return this.executedRunnables;
	}

	@Override
	public SnapshotArray<LifecycleListener> getLifecycleListeners () {
		return this.lifecycleListeners;
	}

	@Override
	public Window getApplicationWindow () {
		return this.getWindow();
	}

	@Override
	public Handler getHandler () {
		return this.handler;
	}
}
