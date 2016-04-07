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

package com.badlogic.gdx.backends.lwjgl;

import java.awt.Canvas;
import java.io.File;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Audio;
import com.badlogic.gdx.Files;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.LifecycleListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.backends.lwjgl.audio.OpenALAudio;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Clipboard;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ObjectMap;
import com.badlogic.gdx.utils.SnapshotArray;

/** An OpenGL surface fullscreen or in a lightweight window. */
public class FokkerLwjglApplication implements Application {
	protected final LwjglGraphics graphics;
	protected OpenALAudio audio;
	protected final LwjglFiles files;
	protected final LwjglInput input;
	protected final LwjglNet net;
	protected final ApplicationListener listener;
	protected Thread mainLoopThread;
	protected boolean running = true;
	protected final Array<Runnable> runnables = new Array<Runnable>();
	protected final Array<Runnable> executedRunnables = new Array<Runnable>();
	protected final SnapshotArray<LifecycleListener> lifecycleListeners = new SnapshotArray<LifecycleListener>(
		LifecycleListener.class);
	protected int logLevel = LOG_INFO;
	protected String preferencesdir;
	protected Files.FileType preferencesFileType;
	final ObjectMap<String, Preferences> preferences = new ObjectMap<String, Preferences>();

	public FokkerLwjglApplication (ApplicationListener listener, String title, int width, int height) {
		this(listener, createConfig(title, width, height));
	}

	public FokkerLwjglApplication (ApplicationListener listener) {
		this(listener, null, 640, 480);
	}

	public FokkerLwjglApplication (ApplicationListener listener, FokkerLwjglApplicationConfiguration config) {
		this(listener, config, new LwjglGraphics(config));
	}

	public FokkerLwjglApplication (ApplicationListener listener, Canvas canvas) {
		this(listener, new FokkerLwjglApplicationConfiguration(), new LwjglGraphics(canvas));
	}

	public FokkerLwjglApplication (ApplicationListener listener, FokkerLwjglApplicationConfiguration config, Canvas canvas) {
		this(listener, config, new LwjglGraphics(canvas, config));
	}

	public FokkerLwjglApplication (ApplicationListener listener, FokkerLwjglApplicationConfiguration config,
		LwjglGraphics graphics) {
		LwjglNativesLoader.load();

		if (config.title == null) config.title = listener.getClass().getSimpleName();

		this.graphics = graphics;
		if (!FokkerLwjglApplicationConfiguration.disableAudio) {
			try {
				audio = new OpenALAudio(config.audioDeviceSimultaneousSources, config.audioDeviceBufferCount,
					config.audioDeviceBufferSize);
			} catch (Throwable t) {
				log("LwjglApplication", "Couldn't initialize audio, disabling audio", t);
				FokkerLwjglApplicationConfiguration.disableAudio = true;
			}
		}
		files = new LwjglFiles();
		input = new LwjglInput();
		net = new LwjglNet();
		this.listener = listener;
		this.preferencesdir = config.preferencesDirectory;
		this.preferencesFileType = config.preferencesFileType;

		Gdx.app = this;
		Gdx.graphics = graphics;
		Gdx.audio = audio;
		Gdx.files = files;
		Gdx.input = input;
		Gdx.net = net;
		initialize();
	}

	private static FokkerLwjglApplicationConfiguration createConfig (String title, int width, int height) {
		FokkerLwjglApplicationConfiguration config = new FokkerLwjglApplicationConfiguration();
		config.title = title;
		config.width = width;
		config.height = height;
		config.vSyncEnabled = true;
		return config;
	}

	final private void initialize () {
		mainLoopThread = new Thread("LWJGL Application") {
			@Override
			final public void run () {
				graphics.setVSync(graphics.config.vSyncEnabled);
				try {
					FokkerLwjglApplication.this.mainLoop();
				} catch (Throwable t) {
					if (audio != null) audio.dispose();
					Gdx.input.setCursorCatched(false);
					if (t instanceof RuntimeException)
						throw (RuntimeException)t;
					else
						throw new GdxRuntimeException(t);
				}
			}
		};
		mainLoopThread.start();
	}

	static boolean shouldRender = false;
	static int frameRate;
	int lastWidth;
	int lastHeight;

	final void mainLoop () {
		final SnapshotArray<LifecycleListener> lifecycleListeners = this.lifecycleListeners;

		try {
			graphics.setupDisplay();
		} catch (LWJGLException e) {
			throw new GdxRuntimeException(e);
		}

		this.listener.create();
		this.graphics.resize = true;

		this.lastWidth = graphics.getWidth();
		this.lastHeight = graphics.getHeight();

		this.graphics.lastTime = System.nanoTime();
		boolean wasActive = true;
		while (this.running) {
			Display.processMessages();
			if (Display.isCloseRequested()) {
				exit();
			}

			boolean isActive = Display.isActive();
			if (wasActive && !isActive) { // if it's just recently minimized from active state
				wasActive = false;
				synchronized (lifecycleListeners) {
					LifecycleListener[] listeners = lifecycleListeners.begin();
					for (int i = 0, n = lifecycleListeners.size; i < n; ++i) {
						listeners[i].pause();
					}
					lifecycleListeners.end();
				}
				this.listener.pause();
			}
			if (!wasActive && isActive) { // if it's just recently focused from minimized state
				wasActive = true;
				synchronized (lifecycleListeners) {
					LifecycleListener[] listeners = lifecycleListeners.begin();
					for (int i = 0, n = lifecycleListeners.size; i < n; ++i) {
						listeners[i].resume();
					}
					lifecycleListeners.end();
				}
				this.listener.resume();
			}

			shouldRender = false;

			if (this.graphics.canvas != null) {
				int width = this.graphics.canvas.getWidth();
				int height = this.graphics.canvas.getHeight();
				if (this.lastWidth != width || this.lastHeight != height) {
					this.lastWidth = width;
					this.lastHeight = height;
					Gdx.gl.glViewport(0, 0, this.lastWidth, this.lastHeight);
					this.listener.resize(this.lastWidth, this.lastHeight);
					shouldRender = true;
				}
			} else {
				this.graphics.config.x = Display.getX();
				this.graphics.config.y = Display.getY();
				if (this.graphics.resize || Display.wasResized()
					|| (int)(Display.getWidth() * Display.getPixelScaleFactor()) != this.graphics.config.width
					|| (int)(Display.getHeight() * Display.getPixelScaleFactor()) != this.graphics.config.height) {
					this.graphics.resize = false;
					this.graphics.config.width = (int)(Display.getWidth() * Display.getPixelScaleFactor());
					this.graphics.config.height = (int)(Display.getHeight() * Display.getPixelScaleFactor());
					Gdx.gl.glViewport(0, 0, this.graphics.config.width, this.graphics.config.height);
					if (this.listener != null) {
						this.listener.resize(this.graphics.config.width, this.graphics.config.height);
					}
					this.graphics.requestRendering();
				}
			}

			if (executeRunnables()) {
				shouldRender = true;
			}

			// If one of the runnables set running to false, for example after an exit().
			if (!this.running) {
				break;
			}

			this.input.update();
			shouldRender |= graphics.shouldRender();
			this.input.processEvents();
			if (this.audio != null) {
				this.audio.update();
			}

			if (!isActive && this.graphics.config.backgroundFPS == -1) {
				shouldRender = false;
			}
			frameRate = isActive ? this.graphics.config.foregroundFPS : this.graphics.config.backgroundFPS;
			if (shouldRender) {
				this.graphics.updateTime();
				this.graphics.frameId++;
				this.listener.render();
				Display.update(false);
			} else {
				// Sleeps to avoid wasting CPU in an empty loop.
				if (frameRate == -1) {
					frameRate = 10;
				}
				if (frameRate == 0) {
					frameRate = this.graphics.config.backgroundFPS;
				}
				if (frameRate == 0) {
					frameRate = 30;
				}
			}
			if (frameRate > 0) {
				Display.sync(frameRate);
			}
		}

		synchronized (lifecycleListeners) {
			LifecycleListener[] listeners = lifecycleListeners.begin();
			for (int i = 0, n = lifecycleListeners.size; i < n; ++i) {
				listeners[i].pause();
				listeners[i].dispose();
			}
			lifecycleListeners.end();
		}
		this.listener.pause();
		this.listener.dispose();
		Display.destroy();
		if (this.audio != null) {
			audio.dispose();
		}
		if (graphics.config.forceExit) {
			System.exit(-1);
		}
	}

	public boolean executeRunnables () {
		synchronized (runnables) {
			for (int i = runnables.size - 1; i >= 0; i--)
				executedRunnables.add(runnables.get(i));
			runnables.clear();
		}
		if (executedRunnables.size == 0) return false;
		do
			executedRunnables.pop().run();
		while (executedRunnables.size > 0);
		return true;
	}

	@Override
	public ApplicationListener getApplicationListener () {
		return listener;
	}

	@Override
	public Audio getAudio () {
		return audio;
	}

	@Override
	public Files getFiles () {
		return files;
	}

	@Override
	public LwjglGraphics getGraphics () {
		return graphics;
	}

	@Override
	public Input getInput () {
		return input;
	}

	@Override
	public Net getNet () {
		return net;
	}

	@Override
	public ApplicationType getType () {
		return ApplicationType.Desktop;
	}

	@Override
	public int getVersion () {
		return 0;
	}

	public void stop () {
		running = false;
		try {
			mainLoopThread.join();
		} catch (Exception ex) {
		}
	}

	@Override
	public long getJavaHeap () {
		return Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
	}

	@Override
	public long getNativeHeap () {
		return getJavaHeap();
	}

	@Override
	public Preferences getPreferences (String name) {
		if (preferences.containsKey(name)) {
			return preferences.get(name);
		} else {
			Preferences prefs = new LwjglPreferences(new LwjglFileHandle(new File(preferencesdir, name), preferencesFileType));
			preferences.put(name, prefs);
			return prefs;
		}
	}

	@Override
	public Clipboard getClipboard () {
		return new LwjglClipboard();
	}

	@Override
	public void postRunnable (Runnable runnable) {
		synchronized (runnables) {
			runnables.add(runnable);
			Gdx.graphics.requestRendering();
		}
	}

	@Override
	public void debug (String tag, String message) {
		if (logLevel >= LOG_DEBUG) {
			System.out.println(tag + ": " + message);
		}
	}

	@Override
	public void debug (String tag, String message, Throwable exception) {
		if (logLevel >= LOG_DEBUG) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void log (String tag, String message) {
		if (logLevel >= LOG_INFO) {
			System.out.println(tag + ": " + message);
		}
	}

	@Override
	public void log (String tag, String message, Throwable exception) {
		if (logLevel >= LOG_INFO) {
			System.out.println(tag + ": " + message);
			exception.printStackTrace(System.out);
		}
	}

	@Override
	public void error (String tag, String message) {
		if (logLevel >= LOG_ERROR) {
			System.err.println(tag + ": " + message);
		}
	}

	@Override
	public void error (String tag, String message, Throwable exception) {
		if (logLevel >= LOG_ERROR) {
			System.err.println(tag + ": " + message);
			exception.printStackTrace(System.err);
		}
	}

	@Override
	public void setLogLevel (int logLevel) {
		this.logLevel = logLevel;
	}

	@Override
	public int getLogLevel () {
		return logLevel;
	}

	@Override
	public void exit () {
		postRunnable(new Runnable() {
			@Override
			public void run () {
				running = false;
			}
		});
	}

	@Override
	public void addLifecycleListener (LifecycleListener listener) {
		synchronized (lifecycleListeners) {
			lifecycleListeners.add(listener);
		}
	}

	@Override
	public void removeLifecycleListener (LifecycleListener listener) {
		synchronized (lifecycleListeners) {
			lifecycleListeners.removeValue(listener, true);
		}
	}
}
