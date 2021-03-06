/*
 * Copyright (C) 2008 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.badlogic.gdx.backends.android.surfaceview;

import java.io.Writer;
import java.lang.ref.WeakReference;
import java.util.ArrayList;

import javax.microedition.khronos.egl.EGL10;
import javax.microedition.khronos.egl.EGL11;
import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.egl.EGLContext;
import javax.microedition.khronos.egl.EGLDisplay;
import javax.microedition.khronos.egl.EGLSurface;
import javax.microedition.khronos.opengles.GL;
import javax.microedition.khronos.opengles.GL10;

import android.content.Context;
//import android.content.pm.ConfigurationInfo;
import android.graphics.PixelFormat;
import android.opengl.GLDebugHelper;
import android.opengl.GLSurfaceView.EGLConfigChooser;
import android.opengl.GLSurfaceView.Renderer;
//import android.os.SystemProperties;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

/** <b>This class is a slightly modified copy of android.opengl.GLSurfaceView from Android 4.3 source code (API 18). It is
 * intended to be used on Android 2.x if you need proper support for onAttachedToWindow and onDetachedFromWindow methods.<b>
 * <p>
 *
 * It's an implementation of SurfaceView that uses the dedicated surface for displaying OpenGL rendering.
 * <p>
 * A GLSurfaceView provides the following features:
 * <p>
 * <ul>
 * <li>Manages a surface, which is a special piece of memory that can be composited into the Android view system.
 * <li>Manages an EGL display, which enables OpenGL to render into a surface.
 * <li>Accepts a user-provided Renderer object that does the actual rendering.
 * <li>Renders on a dedicated thread to decouple rendering performance from the UI thread.
 * <li>Supports both on-demand and continuous rendering.
 * <li>Optionally wraps, traces, and/or error-checks the renderer's OpenGL calls.
 * </ul>
 *
 * <div class="special reference">
 * <h3>Developer Guides</h3>
 * <p>
 * For more information about how to use OpenGL, read the <a href="{@docRoot}guide/topics/graphics/opengl.html">OpenGL</a>
 * developer guide.
 * </p>
 * </div>
 *
 * <h3>Using GLSurfaceView</h3>
 * <p>
 * Typically you use GLSurfaceView by subclassing it and overriding one or more of the View system input event methods. If your
 * application does not need to override event methods then GLSurfaceView can be used as-is. For the most part GLSurfaceView
 * behavior is customized by calling "set" methods rather than by subclassing. For example, unlike a regular View, drawing is
 * delegated to a separate Renderer object which is registered with the GLSurfaceView using the {@link #setRenderer(Renderer)}
 * call.
 * <p>
 * <h3>Initializing GLSurfaceView</h3> All you have to do to initialize a GLSurfaceView is call {@link #setRenderer(Renderer)}.
 * However, if desired, you can modify the default behavior of GLSurfaceView by calling one or more of these methods before
 * calling setRenderer:
 * <ul>
 * <li>{@link #setDebugFlags(int)}
 * <li>{@link #setEGLConfigChooser(boolean)}
 * <li>{@link #setEGLConfigChooser(EGLConfigChooser)}
 * <li>{@link #setEGLConfigChooser(int, int, int, int, int, int)}
 * <li>{@link #setGLWrapper(GLWrapper)}
 * </ul>
 * <p>
 * <h4>Specifying the android.view.Surface</h4> By default GLSurfaceView will create a PixelFormat.RGB_888 format surface. If a
 * translucent surface is required, call getHolder().setFormat(PixelFormat.TRANSLUCENT). The exact format of a TRANSLUCENT surface
 * is device dependent, but it will be a 32-bit-per-pixel surface with 8 bits per component.
 * <p>
 * <h4>Choosing an EGL Configuration</h4> A given Android device may support multiple EGLConfig rendering configurations. The
 * available configurations may differ in how may channels of data are present, as well as how many bits are allocated to each
 * channel. Therefore, the first thing GLSurfaceView has to do when starting to render is choose what EGLConfig to use.
 * <p>
 * By default GLSurfaceView chooses a EGLConfig that has an RGB_888 pixel format, with at least a 16-bit depth buffer and no
 * stencil.
 * <p>
 * If you would prefer a different EGLConfig you can override the default behavior by calling one of the setEGLConfigChooser
 * methods.
 * <p>
 * <h4>Debug Behavior</h4> You can optionally modify the behavior of GLSurfaceView by calling one or more of the debugging methods
 * {@link #setDebugFlags(int)}, and {@link #setGLWrapper}. These methods may be called before and/or after setRenderer, but
 * typically they are called before setRenderer so that they take effect immediately.
 * <p>
 * <h4>Setting a Renderer</h4> Finally, you must call {@link #setRenderer} to register a {@link Renderer}. The renderer is
 * responsible for doing the actual OpenGL rendering.
 * <p>
 * <h3>Rendering Mode</h3> Once the renderer is set, you can control whether the renderer draws continuously or on-demand by
 * calling {@link #setRenderMode}. The default is continuous rendering.
 * <p>
 * <h3>Activity Life-cycle</h3> A GLSurfaceView must be notified when the activity is paused and resumed. GLSurfaceView clients
 * are required to call {@link #onPause()} when the activity pauses and {@link #onResume()} when the activity resumes. These calls
 * allow GLSurfaceView to pause and resume the rendering thread, and also allow GLSurfaceView to release and recreate the OpenGL
 * display.
 * <p>
 * <h3>Handling events</h3>
 * <p>
 * To handle an event you will typically subclass GLSurfaceView and override the appropriate method, just as you would with any
 * other View. However, when handling the event, you may need to communicate with the Renderer object that's running in the
 * rendering thread. You can do this using any standard Java cross-thread communication mechanism. In addition, one relatively
 * easy way to communicate with your renderer is to call {@link #queueEvent(Runnable)}. For example:
 *
 * <pre class="prettyprint">
 * class MyGLSurfaceView extends GLSurfaceView {
 *
 *     private MyRenderer mMyRenderer;
 *
 *     public void start() {
 *         mMyRenderer = ...;
 *         setRenderer(mMyRenderer);
 *     }
 *
 *     public boolean onKeyDown(int keyCode, KeyEvent event) {
 *         if (keyCode == KeyEvent.KEYCODE_DPAD_CENTER) {
 *             queueEvent(new Runnable() {
 *                 // This method will be called on the rendering
 *                 // thread:
 *                 public void run() {
 *                     mMyRenderer.handleDpadCenter();
 *                 }});
 *             return true;
 *         }
 *         return super.onKeyDown(keyCode, event);
 *     }
 * }
 * </pre>
*/
@SuppressWarnings("synthetic-access")
public class RedGLSurfaceViewAPI18 extends SurfaceView implements SurfaceHolder.Callback {
	private final static String TAG = "GLSurfaceViewAPI18";
	private final static boolean LOG_ATTACH_DETACH = false;
	private final static boolean LOG_THREADS = false;
	private final static boolean LOG_PAUSE_RESUME = false;
	private final static boolean LOG_SURFACE = false;
	private final static boolean LOG_RENDERER = false;
	private final static boolean LOG_RENDERER_DRAW_FRAME = false;
	private final static boolean LOG_EGL = false;
	/** The renderer only renders when the surface is created, or when {@link #requestRender} is called.
	 *
	 * @see #getRenderMode()
	 * @see #setRenderMode(int)
	 * @see #requestRender() */
	public final static int RENDERMODE_WHEN_DIRTY = 0;
	/** The renderer is called continuously to re-render the scene.
	 *
	 * @see #getRenderMode()
	 * @see #setRenderMode(int) */
	public final static int RENDERMODE_CONTINUOUSLY = 1;

	/** Check glError() after every GL call and throw an exception if glError indicates that an error has occurred. This can be
	 * used to help track down which OpenGL ES call is causing an error.
	 *
	 * @see #getDebugFlags
	 * @see #setDebugFlags */
	public final static int DEBUG_CHECK_GL_ERROR = 1;

	/** Log GL calls to the system log at "verbose" level with tag "GLSurfaceView".
	 *
	 * @see #getDebugFlags
	 * @see #setDebugFlags */
	public final static int DEBUG_LOG_GL_CALLS = 2;

	/** Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a renderer. */
	public RedGLSurfaceViewAPI18 (final Context context) {
		super(context);
		this.init();
	}

	/** Standard View constructor. In order to render something, you must call {@link #setRenderer} to register a renderer. */
	public RedGLSurfaceViewAPI18 (final Context context, final AttributeSet attrs) {
		super(context, attrs);
		this.init();
	}

	@Override
	protected void finalize () throws Throwable {
		try {
			if (this.mGLThread != null) {
				// GLThread may still be running if this view was never
				// attached to a window.
				this.mGLThread.requestExitAndWait();
			}
		} finally {
			super.finalize();
		}
	}

	private void init () {
		// Install a SurfaceHolder.Callback so we get notified when the
		// underlying surface is created and destroyed
		final SurfaceHolder holder = this.getHolder();
		holder.addCallback(this);
		final int sdkVersion = android.os.Build.VERSION.SDK_INT;
		// setFormat is done by SurfaceView in SDK 2.3 and newer.
		if (sdkVersion <= 8) { // SDK 2.2 or older
			holder.setFormat(PixelFormat.RGB_565);
		}
		// setType is not needed for SDK 2.0 or newer. Uncomment this
		// statement if back-porting this code to older SDKs.
		// holder.setType(SurfaceHolder.SURFACE_TYPE_GPU);
	}

	/** Set the glWrapper. If the glWrapper is not null, its {@link GLWrapper#wrap(GL)} method is called whenever a surface is
	 * created. A GLWrapper can be used to wrap the GL object that's passed to the renderer. Wrapping a GL object enables examining
	 * and modifying the behavior of the GL calls made by the renderer.
	 * <p>
	 * Wrapping is typically used for debugging purposes.
	 * <p>
	 * The default value is null.
	 * @param glWrapper the new GLWrapper */
	public void setGLWrapper (final GLWrapper glWrapper) {
		this.mGLWrapper = glWrapper;
	}

	/** Set the debug flags to a new value. The value is constructed by OR-together zero or more of the DEBUG_CHECK_* constants.
	 * The debug flags take effect whenever a surface is created. The default value is zero.
	 * @param debugFlags the new debug flags
	 * @see #DEBUG_CHECK_GL_ERROR
	 * @see #DEBUG_LOG_GL_CALLS */
	public void setDebugFlags (final int debugFlags) {
		this.mDebugFlags = debugFlags;
	}

	/** Get the current value of the debug flags.
	 * @return the current value of the debug flags. */
	public int getDebugFlags () {
		return this.mDebugFlags;
	}

	/** Control whether the EGL context is preserved when the GLSurfaceView is paused and resumed.
	 * <p>
	 * If set to true, then the EGL context may be preserved when the GLSurfaceView is paused. Whether the EGL context is actually
	 * preserved or not depends upon whether the Android device that the program is running on can support an arbitrary number of
	 * EGL contexts or not. Devices that can only support a limited number of EGL contexts must release the EGL context in order to
	 * allow multiple applications to share the GPU.
	 * <p>
	 * If set to false, the EGL context will be released when the GLSurfaceView is paused, and recreated when the GLSurfaceView is
	 * resumed.
	 * <p>
	 *
	 * The default is false.
	 *
	 * @param preserveOnPause preserve the EGL context when paused */
	public void setPreserveEGLContextOnPause (final boolean preserveOnPause) {
		this.mPreserveEGLContextOnPause = preserveOnPause;
	}

	/** @return true if the EGL context will be preserved when paused */
	public boolean getPreserveEGLContextOnPause () {
		return this.mPreserveEGLContextOnPause;
	}

	/** Set the renderer associated with this view. Also starts the thread that will call the renderer, which in turn causes the
	 * rendering to start.
	 * <p>
	 * This method should be called once and only once in the life-cycle of a GLSurfaceView.
	 * <p>
	 * The following GLSurfaceView methods can only be called <em>before</em> setRenderer is called:
	 * <ul>
	 * <li>{@link #setEGLConfigChooser(boolean)}
	 * <li>{@link #setEGLConfigChooser(EGLConfigChooser)}
	 * <li>{@link #setEGLConfigChooser(int, int, int, int, int, int)}
	 * </ul>
	 * <p>
	 * The following GLSurfaceView methods can only be called <em>after</em> setRenderer is called:
	 * <ul>
	 * <li>{@link #getRenderMode()}
	 * <li>{@link #onPause()}
	 * <li>{@link #onResume()}
	 * <li>{@link #queueEvent(Runnable)}
	 * <li>{@link #requestRender()}
	 * <li>{@link #setRenderMode(int)}
	 * </ul>
	 *
	 * @param renderer the renderer to use to perform OpenGL drawing. */
	public void setRenderer (final Renderer renderer) {
		this.checkRenderThreadState();
		if (this.mEGLConfigChooser == null) {
			this.mEGLConfigChooser = new SimpleEGLConfigChooser(true);
		}
		if (this.mEGLContextFactory == null) {
			this.mEGLContextFactory = new DefaultContextFactory();
		}
		if (this.mEGLWindowSurfaceFactory == null) {
			this.mEGLWindowSurfaceFactory = new DefaultWindowSurfaceFactory();
		}
		this.mRenderer = renderer;
		this.mGLThread = new GLThread(this.mThisWeakRef);
		this.mGLThread.start();
	}

	/** Install a custom EGLContextFactory.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * If this method is not called, then by default a context will be created with no shared context and with a null attribute
	 * list. */
	public void setEGLContextFactory (final EGLContextFactory factory) {
		this.checkRenderThreadState();
		this.mEGLContextFactory = factory;
	}

	/** Install a custom EGLWindowSurfaceFactory.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * If this method is not called, then by default a window surface will be created with a null attribute list. */
	public void setEGLWindowSurfaceFactory (final EGLWindowSurfaceFactory factory) {
		this.checkRenderThreadState();
		this.mEGLWindowSurfaceFactory = factory;
	}

	/** Install a custom EGLConfigChooser.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * If no setEGLConfigChooser method is called, then by default the view will choose an EGLConfig that is compatible with the
	 * current android.view.Surface, with a depth buffer depth of at least 16 bits.
	 * @param configChooser */
	public void setEGLConfigChooser (final EGLConfigChooser configChooser) {
		this.checkRenderThreadState();
		this.mEGLConfigChooser = configChooser;
	}

	/** Install a config chooser which will choose a config as close to 16-bit RGB as possible, with or without an optional depth
	 * buffer as close to 16-bits as possible.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * If no setEGLConfigChooser method is called, then by default the view will choose an RGB_888 surface with a depth buffer
	 * depth of at least 16 bits.
	 *
	 * @param needDepth */
	public void setEGLConfigChooser (final boolean needDepth) {
		this.setEGLConfigChooser(new SimpleEGLConfigChooser(needDepth));
	}

	/** Install a config chooser which will choose a config with at least the specified depthSize and stencilSize, and exactly the
	 * specified redSize, greenSize, blueSize and alphaSize.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * If no setEGLConfigChooser method is called, then by default the view will choose an RGB_888 surface with a depth buffer
	 * depth of at least 16 bits. */
	public void setEGLConfigChooser (final int redSize, final int greenSize, final int blueSize, final int alphaSize,
		final int depthSize, final int stencilSize) {
		this.setEGLConfigChooser(new ComponentSizeChooser(redSize, greenSize, blueSize, alphaSize, depthSize, stencilSize));
	}

	/** Inform the default EGLContextFactory and default EGLConfigChooser which EGLContext client version to pick.
	 * <p>
	 * Use this method to create an OpenGL ES 2.0-compatible context. Example:
	 *
	 * <pre class="prettyprint">
	 * public MyView (Context context) {
	 * 	super(context);
	 * 	setEGLContextClientVersion(2); // Pick an OpenGL ES 2.0 context.
	 * 	setRenderer(new MyRenderer());
	 * }
	 * </pre>
	 * <p>
	 * Note: Activities which require OpenGL ES 2.0 should indicate this by setting @lt;uses-feature
	 * android:glEsVersion="0x00020000" in the activity's AndroidManifest.xml file.
	 * <p>
	 * If this method is called, it must be called before {@link #setRenderer(Renderer)} is called.
	 * <p>
	 * This method only affects the behavior of the default EGLContexFactory and the default EGLConfigChooser. If
	 * {@link #setEGLContextFactory(EGLContextFactory)} has been called, then the supplied EGLContextFactory is responsible for
	 * creating an OpenGL ES 2.0-compatible context. If {@link #setEGLConfigChooser(EGLConfigChooser)} has been called, then the
	 * supplied EGLConfigChooser is responsible for choosing an OpenGL ES 2.0-compatible config.
	 * @param version The EGLContext client version to choose. Use 2 for OpenGL ES 2.0 */
	public void setEGLContextClientVersion (final int version) {
		this.checkRenderThreadState();
		this.mEGLContextClientVersion = version;
	}

	/** Set the rendering mode. When renderMode is RENDERMODE_CONTINUOUSLY, the renderer is called repeatedly to re-render the
	 * scene. When renderMode is RENDERMODE_WHEN_DIRTY, the renderer only rendered when the surface is created, or when
	 * {@link #requestRender} is called. Defaults to RENDERMODE_CONTINUOUSLY.
	 * <p>
	 * Using RENDERMODE_WHEN_DIRTY can improve battery life and overall system performance by allowing the GPU and CPU to idle when
	 * the view does not need to be updated.
	 * <p>
	 * This method can only be called after {@link #setRenderer(Renderer)}
	 *
	 * @param renderMode one of the RENDERMODE_X constants
	 * @see #RENDERMODE_CONTINUOUSLY
	 * @see #RENDERMODE_WHEN_DIRTY */
	public void setRenderMode (final int renderMode) {
		this.mGLThread.setRenderMode(renderMode);
	}

	/** Get the current rendering mode. May be called from any thread. Must not be called before a renderer has been set.
	 * @return the current rendering mode.
	 * @see #RENDERMODE_CONTINUOUSLY
	 * @see #RENDERMODE_WHEN_DIRTY */
	public int getRenderMode () {
		return this.mGLThread.getRenderMode();
	}

	/** Request that the renderer render a frame. This method is typically used when the render mode has been set to
	 * {@link #RENDERMODE_WHEN_DIRTY}, so that frames are only rendered on demand. May be called from any thread. Must not be
	 * called before a renderer has been set. */
	public void requestRender () {
		this.mGLThread.requestRender();
	}

	/** This method is part of the SurfaceHolder.Callback interface, and is not normally called or subclassed by clients of
	 * GLSurfaceView. */
	@Override
	public void surfaceCreated (final SurfaceHolder holder) {
		this.mGLThread.surfaceCreated();
	}

	/** This method is part of the SurfaceHolder.Callback interface, and is not normally called or subclassed by clients of
	 * GLSurfaceView. */
	@Override
	public void surfaceDestroyed (final SurfaceHolder holder) {
		// Surface will be destroyed when we return
		this.mGLThread.surfaceDestroyed();
	}

	/** This method is part of the SurfaceHolder.Callback interface, and is not normally called or subclassed by clients of
	 * GLSurfaceView. */
	@Override
	public void surfaceChanged (final SurfaceHolder holder, final int format, final int w, final int h) {
		this.mGLThread.onWindowResize(w, h);
	}

	/** Inform the view that the activity is paused. The owner of this view must call this method when the activity is paused.
	 * Calling this method will pause the rendering thread. Must not be called before a renderer has been set. */
	public void onPause () {
		this.mGLThread.onPause();
	}

	/** Inform the view that the activity is resumed. The owner of this view must call this method when the activity is resumed.
	 * Calling this method will recreate the OpenGL display and resume the rendering thread. Must not be called before a renderer
	 * has been set. */
	public void onResume () {
		this.mGLThread.onResume();
	}

	/** Queue a runnable to be run on the GL rendering thread. This can be used to communicate with the Renderer on the rendering
	 * thread. Must not be called before a renderer has been set.
	 * @param r the runnable to be run on the GL rendering thread. */
	public void queueEvent (final Runnable r) {
		this.mGLThread.queueEvent(r);
	}

	/** This method is used as part of the View class and is not normally called or subclassed by clients of GLSurfaceView. */
	@Override
	protected void onAttachedToWindow () {
		super.onAttachedToWindow();
		if (LOG_ATTACH_DETACH) {
			Log.d(TAG, "onAttachedToWindow reattach =" + this.mDetached);
		}
		if (this.mDetached && (this.mRenderer != null)) {
			int renderMode = RENDERMODE_CONTINUOUSLY;
			if (this.mGLThread != null) {
				renderMode = this.mGLThread.getRenderMode();
			}
			this.mGLThread = new GLThread(this.mThisWeakRef);
			if (renderMode != RENDERMODE_CONTINUOUSLY) {
				this.mGLThread.setRenderMode(renderMode);
			}
			this.mGLThread.start();
		}
		this.mDetached = false;
	}

	/** This method is used as part of the View class and is not normally called or subclassed by clients of GLSurfaceView. Must
	 * not be called before a renderer has been set. */
	@Override
	protected void onDetachedFromWindow () {
		if (LOG_ATTACH_DETACH) {
			Log.d(TAG, "onDetachedFromWindow");
		}
		if (this.mGLThread != null) {
			this.mGLThread.requestExitAndWait();
		}
		this.mDetached = true;
		super.onDetachedFromWindow();
	}

	// ----------------------------------------------------------------------

	/** An interface used to wrap a GL interface.
	 * <p>
	 * Typically used for implementing debugging and tracing on top of the default GL interface. You would typically use this by
	 * creating your own class that implemented all the GL methods by delegating to another GL instance. Then you could add your
	 * own behavior before or after calling the delegate. All the GLWrapper would do was instantiate and return the wrapper GL
	 * instance:
	 *
	 * <pre class="prettyprint">
	 * class MyGLWrapper implements GLWrapper {
	 *     GL wrap(GL gl) {
	 *         return new MyGLImplementation(gl);
	 *     }
	 *     static class MyGLImplementation implements GL,GL10,GL11,... {
	 *         ...
	 *     }
	 * }
	 * </pre>
	 *
	 * @see #setGLWrapper(GLWrapper) */
	public interface GLWrapper {
		/** Wraps a gl interface in another gl interface.
		 * @param gl a GL interface that is to be wrapped.
		 * @return either the input argument or another GL object that wraps the input argument. */
		GL wrap (GL gl);
	}

// /**
// * A generic renderer interface.
// * <p>
// * The renderer is responsible for making OpenGL calls to render a frame.
// * <p>
// * GLSurfaceView clients typically create their own classes that implement
// * this interface, and then call {@link GLSurfaceView#setRenderer} to
// * register the renderer with the GLSurfaceView.
// * <p>
// *
// * <div class="special reference">
// * <h3>Developer Guides</h3>
// * <p>For more information about how to use OpenGL, read the
// * <a href="{@docRoot}guide/topics/graphics/opengl.html">OpenGL</a> developer guide.</p>
// * </div>
// *
// * <h3>Threading</h3>
// * The renderer will be called on a separate thread, so that rendering
// * performance is decoupled from the UI thread. Clients typically need to
// * communicate with the renderer from the UI thread, because that's where
// * input events are received. Clients can communicate using any of the
// * standard Java techniques for cross-thread communication, or they can
// * use the {@link GLSurfaceView#queueEvent(Runnable)} convenience method.
// * <p>
// * <h3>EGL Context Lost</h3>
// * There are situations where the EGL rendering context will be lost. This
// * typically happens when device wakes up after going to sleep. When
// * the EGL context is lost, all OpenGL resources (such as textures) that are
// * associated with that context will be automatically deleted. In order to
// * keep rendering correctly, a renderer must recreate any lost resources
// * that it still needs. The {@link #onSurfaceCreated(GL10, EGLConfig)} method
// * is a convenient place to do this.
// *
// *
// * @see #setRenderer(Renderer)
// */
// public interface Renderer {
// /**
// * Called when the surface is created or recreated.
// * <p>
// * Called when the rendering thread
// * starts and whenever the EGL context is lost. The EGL context will typically
// * be lost when the Android device awakes after going to sleep.
// * <p>
// * Since this method is called at the beginning of rendering, as well as
// * every time the EGL context is lost, this method is a convenient place to put
// * code to create resources that need to be created when the rendering
// * starts, and that need to be recreated when the EGL context is lost.
// * Textures are an example of a resource that you might want to create
// * here.
// * <p>
// * Note that when the EGL context is lost, all OpenGL resources associated
// * with that context will be automatically deleted. You do not need to call
// * the corresponding "glDelete" methods such as glDeleteTextures to
// * manually delete these lost resources.
// * <p>
// * @param gl the GL interface. Use <code>instanceof</code> to
// * test if the interface supports GL11 or higher interfaces.
// * @param config the EGLConfig of the created surface. Can be used
// * to create matching pbuffers.
// */
// void onSurfaceCreated(GL10 gl, EGLConfig config);
//
// /**
// * Called when the surface changed size.
// * <p>
// * Called after the surface is created and whenever
// * the OpenGL ES surface size changes.
// * <p>
// * Typically you will set your viewport here. If your camera
// * is fixed then you could also set your projection matrix here:
// * <pre class="prettyprint">
// * void onSurfaceChanged(GL10 gl, int width, int height) {
// * gl.glViewport(0, 0, width, height);
// * // for a fixed camera, set the projection too
// * float ratio = (float) width / height;
// * gl.glMatrixMode(GL10.GL_PROJECTION);
// * gl.glLoadIdentity();
// * gl.glFrustumf(-ratio, ratio, -1, 1, 1, 10);
// * }
// * </pre>
// * @param gl the GL interface. Use <code>instanceof</code> to
// * test if the interface supports GL11 or higher interfaces.
// * @param width
// * @param height
// */
// void onSurfaceChanged(GL10 gl, int width, int height);
//
// /**
// * Called to draw the current frame.
// * <p>
// * This method is responsible for drawing the current frame.
// * <p>
// * The implementation of this method typically looks like this:
// * <pre class="prettyprint">
// * void onDrawFrame(GL10 gl) {
// * gl.glClear(GL10.GL_COLOR_BUFFER_BIT | GL10.GL_DEPTH_BUFFER_BIT);
// * //... other gl calls to render the scene ...
// * }
// * </pre>
// * @param gl the GL interface. Use <code>instanceof</code> to
// * test if the interface supports GL11 or higher interfaces.
// */
// void onDrawFrame(GL10 gl);
// }

	/** An interface for customizing the eglCreateContext and eglDestroyContext calls.
	 * <p>
	 * This interface must be implemented by clients wishing to call
	 * {@link RedGLSurfaceViewAPI18#setEGLContextFactory(EGLContextFactory)} */
	public interface EGLContextFactory {
		EGLContext createContext (EGL10 egl, EGLDisplay display, EGLConfig eglConfig);

		void destroyContext (EGL10 egl, EGLDisplay display, EGLContext context);
	}

	private class DefaultContextFactory implements EGLContextFactory {
		private final int EGL_CONTEXT_CLIENT_VERSION = 0x3098;

		@Override
		public EGLContext createContext (final EGL10 egl, final EGLDisplay display, final EGLConfig config) {
			final int[] attrib_list = {this.EGL_CONTEXT_CLIENT_VERSION, RedGLSurfaceViewAPI18.this.mEGLContextClientVersion,
				EGL10.EGL_NONE};

			return egl.eglCreateContext(display, config, EGL10.EGL_NO_CONTEXT,
				RedGLSurfaceViewAPI18.this.mEGLContextClientVersion != 0 ? attrib_list : null);
		}

		@Override
		public void destroyContext (final EGL10 egl, final EGLDisplay display, final EGLContext context) {
			if (!egl.eglDestroyContext(display, context)) {
				Log.e("DefaultContextFactory", "display:" + display + " context: " + context);
				if (LOG_THREADS) {
					Log.i("DefaultContextFactory", "tid=" + Thread.currentThread().getId());
				}
				EglHelper.throwEglException("eglDestroyContex", egl.eglGetError());
			}
		}
	}

	/** An interface for customizing the eglCreateWindowSurface and eglDestroySurface calls.
	 * <p>
	 * This interface must be implemented by clients wishing to call
	 * {@link RedGLSurfaceViewAPI18#setEGLWindowSurfaceFactory(EGLWindowSurfaceFactory)} */
	public interface EGLWindowSurfaceFactory {
		/** @return null if the surface cannot be constructed. */
		EGLSurface createWindowSurface (EGL10 egl, EGLDisplay display, EGLConfig config, Object nativeWindow);

		void destroySurface (EGL10 egl, EGLDisplay display, EGLSurface surface);
	}

	private static class DefaultWindowSurfaceFactory implements EGLWindowSurfaceFactory {

		@Override
		public EGLSurface createWindowSurface (final EGL10 egl, final EGLDisplay display, final EGLConfig config,
			final Object nativeWindow) {
			EGLSurface result = null;
			try {
				result = egl.eglCreateWindowSurface(display, config, nativeWindow, null);
			} catch (final IllegalArgumentException e) {
				// This exception indicates that the surface flinger surface
				// is not valid. This can happen if the surface flinger surface has
				// been torn down, but the application has not yet been
				// notified via SurfaceHolder.Callback.surfaceDestroyed.
				// In theory the application should be notified first,
				// but in practice sometimes it is not. See b/4588890
				Log.e(TAG, "eglCreateWindowSurface", e);
			}
			return result;
		}

		@Override
		public void destroySurface (final EGL10 egl, final EGLDisplay display, final EGLSurface surface) {
			egl.eglDestroySurface(display, surface);
		}
	}

// /**
// * An interface for choosing an EGLConfig configuration from a list of
// * potential configurations.
// * <p>
// * This interface must be implemented by clients wishing to call
// * {@link GLSurfaceView#setEGLConfigChooser(EGLConfigChooser)}
// */
// public interface EGLConfigChooser {
// /**
// * Choose a configuration from the list. Implementors typically
// * implement this method by calling
// * {@link EGL10#eglChooseConfig} and iterating through the results. Please consult the
// * EGL specification available from The Khronos Group to learn how to call eglChooseConfig.
// * @param egl the EGL10 for the current display.
// * @param display the current display.
// * @return the chosen configuration.
// */
// EGLConfig chooseConfig(EGL10 egl, EGLDisplay display);
// }

	private abstract class BaseConfigChooser implements EGLConfigChooser {
		public BaseConfigChooser (final int[] configSpec) {
			this.mConfigSpec = this.filterConfigSpec(configSpec);
		}

		@Override
		public EGLConfig chooseConfig (final EGL10 egl, final EGLDisplay display) {
			final int[] num_config = new int[1];
			if (!egl.eglChooseConfig(display, this.mConfigSpec, null, 0, num_config)) {
				throw new IllegalArgumentException("eglChooseConfig failed");
			}

			final int numConfigs = num_config[0];

			if (numConfigs <= 0) {
				throw new IllegalArgumentException("No configs match configSpec");
			}

			final EGLConfig[] configs = new EGLConfig[numConfigs];
			if (!egl.eglChooseConfig(display, this.mConfigSpec, configs, numConfigs, num_config)) {
				throw new IllegalArgumentException("eglChooseConfig#2 failed");
			}
			final EGLConfig config = this.chooseConfig(egl, display, configs);
			if (config == null) {
				throw new IllegalArgumentException("No config chosen");
			}
			return config;
		}

		abstract EGLConfig chooseConfig (EGL10 egl, EGLDisplay display, EGLConfig[] configs);

		protected int[] mConfigSpec;

		private int[] filterConfigSpec (final int[] configSpec) {
			if (RedGLSurfaceViewAPI18.this.mEGLContextClientVersion != 2) {
				return configSpec;
			}
			/*
			 * We know none of the subclasses define EGL_RENDERABLE_TYPE. And we know the configSpec is well formed.
			 */
			final int len = configSpec.length;
			final int[] newConfigSpec = new int[len + 2];
			System.arraycopy(configSpec, 0, newConfigSpec, 0, len - 1);
			newConfigSpec[len - 1] = EGL10.EGL_RENDERABLE_TYPE;
			newConfigSpec[len] = 4; /* EGL_OPENGL_ES2_BIT */
			newConfigSpec[len + 1] = EGL10.EGL_NONE;
			return newConfigSpec;
		}
	}

	/** Choose a configuration with exactly the specified r,g,b,a sizes, and at least the specified depth and stencil sizes. */
	private class ComponentSizeChooser extends BaseConfigChooser {
		public ComponentSizeChooser (final int redSize, final int greenSize, final int blueSize, final int alphaSize,
			final int depthSize, final int stencilSize) {
			super(new int[] {EGL10.EGL_RED_SIZE, redSize, EGL10.EGL_GREEN_SIZE, greenSize, EGL10.EGL_BLUE_SIZE, blueSize,
				EGL10.EGL_ALPHA_SIZE, alphaSize, EGL10.EGL_DEPTH_SIZE, depthSize, EGL10.EGL_STENCIL_SIZE, stencilSize,
				EGL10.EGL_NONE});
			this.mValue = new int[1];
			this.mRedSize = redSize;
			this.mGreenSize = greenSize;
			this.mBlueSize = blueSize;
			this.mAlphaSize = alphaSize;
			this.mDepthSize = depthSize;
			this.mStencilSize = stencilSize;
		}

		@Override
		public EGLConfig chooseConfig (final EGL10 egl, final EGLDisplay display, final EGLConfig[] configs) {
			for (final EGLConfig config : configs) {
				final int d = this.findConfigAttrib(egl, display, config, EGL10.EGL_DEPTH_SIZE, 0);
				final int s = this.findConfigAttrib(egl, display, config, EGL10.EGL_STENCIL_SIZE, 0);
				if ((d >= this.mDepthSize) && (s >= this.mStencilSize)) {
					final int r = this.findConfigAttrib(egl, display, config, EGL10.EGL_RED_SIZE, 0);
					final int g = this.findConfigAttrib(egl, display, config, EGL10.EGL_GREEN_SIZE, 0);
					final int b = this.findConfigAttrib(egl, display, config, EGL10.EGL_BLUE_SIZE, 0);
					final int a = this.findConfigAttrib(egl, display, config, EGL10.EGL_ALPHA_SIZE, 0);
					if ((r == this.mRedSize) && (g == this.mGreenSize) && (b == this.mBlueSize) && (a == this.mAlphaSize)) {
						return config;
					}
				}
			}
			return null;
		}

		private int findConfigAttrib (final EGL10 egl, final EGLDisplay display, final EGLConfig config, final int attribute,
			final int defaultValue) {

			if (egl.eglGetConfigAttrib(display, config, attribute, this.mValue)) {
				return this.mValue[0];
			}
			return defaultValue;
		}

		private final int[] mValue;
		// Subclasses can adjust these values:
		protected int mRedSize;
		protected int mGreenSize;
		protected int mBlueSize;
		protected int mAlphaSize;
		protected int mDepthSize;
		protected int mStencilSize;
	}

	/** This class will choose a RGB_888 surface with or without a depth buffer. */
	private class SimpleEGLConfigChooser extends ComponentSizeChooser {
		public SimpleEGLConfigChooser (final boolean withDepthBuffer) {
			super(8, 8, 8, 0, withDepthBuffer ? 16 : 0, 0);
		}
	}

	/** An EGL helper class. */

	private static class EglHelper {
		public EglHelper (final WeakReference<RedGLSurfaceViewAPI18> glSurfaceViewWeakRef) {
			this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
		}

		/** Initialize EGL for a given configuration spec. */
		public void start () {
			if (LOG_EGL) {
				Log.w("EglHelper", "start() tid=" + Thread.currentThread().getId());
			}
			/*
			 * Get an EGL instance
			 */
			this.mEgl = (EGL10)EGLContext.getEGL();

			/*
			 * Get to the default display.
			 */
			this.mEglDisplay = this.mEgl.eglGetDisplay(EGL10.EGL_DEFAULT_DISPLAY);

			if (this.mEglDisplay == EGL10.EGL_NO_DISPLAY) {
				throw new RuntimeException("eglGetDisplay failed");
			}

			/*
			 * We can now initialize EGL for that display
			 */
			final int[] version = new int[2];
			if (!this.mEgl.eglInitialize(this.mEglDisplay, version)) {
				throw new RuntimeException("eglInitialize failed");
			}
			final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
			if (view == null) {
				this.mEglConfig = null;
				this.mEglContext = null;
			} else {
				this.mEglConfig = view.mEGLConfigChooser.chooseConfig(this.mEgl, this.mEglDisplay);

				/*
				 * Create an EGL context. We want to do this as rarely as we can, because an EGL context is a somewhat heavy object.
				 */
				this.mEglContext = view.mEGLContextFactory.createContext(this.mEgl, this.mEglDisplay, this.mEglConfig);
			}
			if (this.mEglContext == null || this.mEglContext == EGL10.EGL_NO_CONTEXT) {
				this.mEglContext = null;
				this.throwEglException("createContext");
			}
			if (LOG_EGL) {
				Log.w("EglHelper", "createContext " + this.mEglContext + " tid=" + Thread.currentThread().getId());
			}

			this.mEglSurface = null;
		}

		/** Create an egl surface for the current SurfaceHolder surface. If a surface already exists, destroy it before creating the
		 * new surface.
		 *
		 * @return true if the surface was created successfully. */
		public boolean createSurface () {
			if (LOG_EGL) {
				Log.w("EglHelper", "createSurface()  tid=" + Thread.currentThread().getId());
			}
			/*
			 * Check preconditions.
			 */
			if (this.mEgl == null) {
				throw new RuntimeException("egl not initialized");
			}
			if (this.mEglDisplay == null) {
				throw new RuntimeException("eglDisplay not initialized");
			}
			if (this.mEglConfig == null) {
				throw new RuntimeException("mEglConfig not initialized");
			}

			/*
			 * The window size has changed, so we need to create a new surface.
			 */
			this.destroySurfaceImp();

			/*
			 * Create an EGL surface we can render into.
			 */
			final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
			if (view != null) {
				this.mEglSurface = view.mEGLWindowSurfaceFactory.createWindowSurface(this.mEgl, this.mEglDisplay, this.mEglConfig,
					view.getHolder());
			} else {
				this.mEglSurface = null;
			}

			if (this.mEglSurface == null || this.mEglSurface == EGL10.EGL_NO_SURFACE) {
				final int error = this.mEgl.eglGetError();
				if (error == EGL10.EGL_BAD_NATIVE_WINDOW) {
					Log.e("EglHelper", "createWindowSurface returned EGL_BAD_NATIVE_WINDOW.");
				}
				return false;
			}

			/*
			 * Before we can issue GL commands, we need to make sure the context is current and bound to a surface.
			 */
			if (!this.mEgl.eglMakeCurrent(this.mEglDisplay, this.mEglSurface, this.mEglSurface, this.mEglContext)) {
				/*
				 * Could not make the context current, probably because the underlying SurfaceView surface has been destroyed.
				 */
				logEglErrorAsWarning("EGLHelper", "eglMakeCurrent", this.mEgl.eglGetError());
				return false;
			}

			return true;
		}

		/** Create a GL object for the current EGL context. */
		GL createGL () {

			GL gl = this.mEglContext.getGL();
			final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
			if (view != null) {
				if (view.mGLWrapper != null) {
					gl = view.mGLWrapper.wrap(gl);
				}

				if ((view.mDebugFlags & (DEBUG_CHECK_GL_ERROR | DEBUG_LOG_GL_CALLS)) != 0) {
					int configFlags = 0;
					Writer log = null;
					if ((view.mDebugFlags & DEBUG_CHECK_GL_ERROR) != 0) {
						configFlags |= GLDebugHelper.CONFIG_CHECK_GL_ERROR;
					}
					if ((view.mDebugFlags & DEBUG_LOG_GL_CALLS) != 0) {
						log = new LogWriter();
					}
					gl = GLDebugHelper.wrap(gl, configFlags, log);
				}
			}
			return gl;
		}

		/** Display the current render surface.
		 * @return the EGL error code from eglSwapBuffers. */
		public final int swap () {
			if (!this.mEgl.eglSwapBuffers(this.mEglDisplay, this.mEglSurface)) {
				return this.mEgl.eglGetError();
			}
			return EGL10.EGL_SUCCESS;
		}

		public void destroySurface () {
			if (LOG_EGL) {
				Log.w("EglHelper", "destroySurface()  tid=" + Thread.currentThread().getId());
			}
			this.destroySurfaceImp();
		}

		private void destroySurfaceImp () {
			if (this.mEglSurface != null && this.mEglSurface != EGL10.EGL_NO_SURFACE) {
				this.mEgl.eglMakeCurrent(this.mEglDisplay, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_SURFACE, EGL10.EGL_NO_CONTEXT);
				final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
				if (view != null) {
					view.mEGLWindowSurfaceFactory.destroySurface(this.mEgl, this.mEglDisplay, this.mEglSurface);
				}
				this.mEglSurface = null;
			}
		}

		public void finish () {
			if (LOG_EGL) {
				Log.w("EglHelper", "finish() tid=" + Thread.currentThread().getId());
			}
			if (this.mEglContext != null) {
				final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
				if (view != null) {
					view.mEGLContextFactory.destroyContext(this.mEgl, this.mEglDisplay, this.mEglContext);
				}
				this.mEglContext = null;
			}
			if (this.mEglDisplay != null) {
				this.mEgl.eglTerminate(this.mEglDisplay);
				this.mEglDisplay = null;
			}
		}

		private void throwEglException (final String function) {
			throwEglException(function, this.mEgl.eglGetError());
		}

		public static void throwEglException (final String function, final int error) {
			final String message = formatEglError(function, error);
			if (LOG_THREADS) {
				Log.e("EglHelper", "throwEglException tid=" + Thread.currentThread().getId() + " " + message);
			}
			throw new RuntimeException(message);
		}

		public static void logEglErrorAsWarning (final String tag, final String function, final int error) {
			Log.w(tag, formatEglError(function, error));
		}

		// Method taken from class android.opengl.EGLLogWrapper which is package-private
		private static String getErrorString (final int error) {
			switch (error) {
			case EGL10.EGL_SUCCESS:
				return "EGL_SUCCESS";
			case EGL10.EGL_NOT_INITIALIZED:
				return "EGL_NOT_INITIALIZED";
			case EGL10.EGL_BAD_ACCESS:
				return "EGL_BAD_ACCESS";
			case EGL10.EGL_BAD_ALLOC:
				return "EGL_BAD_ALLOC";
			case EGL10.EGL_BAD_ATTRIBUTE:
				return "EGL_BAD_ATTRIBUTE";
			case EGL10.EGL_BAD_CONFIG:
				return "EGL_BAD_CONFIG";
			case EGL10.EGL_BAD_CONTEXT:
				return "EGL_BAD_CONTEXT";
			case EGL10.EGL_BAD_CURRENT_SURFACE:
				return "EGL_BAD_CURRENT_SURFACE";
			case EGL10.EGL_BAD_DISPLAY:
				return "EGL_BAD_DISPLAY";
			case EGL10.EGL_BAD_MATCH:
				return "EGL_BAD_MATCH";
			case EGL10.EGL_BAD_NATIVE_PIXMAP:
				return "EGL_BAD_NATIVE_PIXMAP";
			case EGL10.EGL_BAD_NATIVE_WINDOW:
				return "EGL_BAD_NATIVE_WINDOW";
			case EGL10.EGL_BAD_PARAMETER:
				return "EGL_BAD_PARAMETER";
			case EGL10.EGL_BAD_SURFACE:
				return "EGL_BAD_SURFACE";
			case EGL11.EGL_CONTEXT_LOST:
				return "EGL_CONTEXT_LOST";
			default:
				return "0x" + Integer.toHexString(error);
			}
		}

		public static String formatEglError (final String function, final int error) {
			return function + " failed: " + getErrorString(error);
		}

		private final WeakReference<RedGLSurfaceViewAPI18> mGLSurfaceViewWeakRef;
		EGL10 mEgl;
		EGLDisplay mEglDisplay;
		EGLSurface mEglSurface;
		EGLConfig mEglConfig;
		EGLContext mEglContext;

	}

	/** A generic GL Thread. Takes care of initializing EGL and GL. Delegates to a Renderer instance to do the actual drawing. Can
	 * be configured to render continuously or on request.
	 *
	 * All potentially blocking synchronization is done through the sGLThreadManager object. This avoids multiple-lock ordering
	 * issues. */
	static class GLThread extends Thread {
		GLThread (final WeakReference<RedGLSurfaceViewAPI18> glSurfaceViewWeakRef) {
			super();
			this.mWidth = 0;
			this.mHeight = 0;
			this.mRequestRender = true;
			this.mRenderMode = RENDERMODE_CONTINUOUSLY;
			this.mGLSurfaceViewWeakRef = glSurfaceViewWeakRef;
		}

		@Override
		public void run () {
			this.setName("GLThread " + this.getId());
			if (LOG_THREADS) {
				Log.i("GLThread", "starting tid=" + this.getId());
			}

			try {
				this.guardedRun();
			} catch (final InterruptedException e) {
				// fall thru and exit normally
			} finally {
				sGLThreadManager.threadExiting(this);
			}
		}

		/*
		 * This private method should only be called inside a synchronized(sGLThreadManager) block.
		 */
		private void stopEglSurfaceLocked () {
			if (this.mHaveEglSurface) {
				this.mHaveEglSurface = false;
				this.mEglHelper.destroySurface();
			}
		}

		/*
		 * This private method should only be called inside a synchronized(sGLThreadManager) block.
		 */
		private void stopEglContextLocked () {
			if (this.mHaveEglContext) {
				this.mEglHelper.finish();
				this.mHaveEglContext = false;
				sGLThreadManager.releaseEglContextLocked(this);
			}
		}

		GL10 gl_guardedRun = null;
		boolean createEglContext_guardedRun = false;
		boolean createEglSurface_guardedRun = false;
		boolean createGlInterface_guardedRun = false;
		boolean lostEglContext_guardedRun = false;
		boolean sizeChanged_guardedRun = false;
		boolean wantRenderNotification_guardedRun = false;
		boolean doRenderNotification_guardedRun = false;
		boolean askedToReleaseEglContext_guardedRun = false;
		int w_guardedRun = 0;
		int h_guardedRun = 0;
		Runnable event_guardedRun = null;
		boolean pausing_guardedRun = false;
		boolean preserveEglContextOnPause_guardedRun;
		int swapError_guardedRun;

		private final void guardedRun () throws InterruptedException {
			this.mEglHelper = new EglHelper(this.mGLSurfaceViewWeakRef);
			this.mHaveEglContext = false;
			this.mHaveEglSurface = false;
			try {
				this.gl_guardedRun = null;
				this.createEglContext_guardedRun = false;
				this.createEglSurface_guardedRun = false;
				this.createGlInterface_guardedRun = false;
				this.lostEglContext_guardedRun = false;
				this.sizeChanged_guardedRun = false;
				this.wantRenderNotification_guardedRun = false;
				this.doRenderNotification_guardedRun = false;
				this.askedToReleaseEglContext_guardedRun = false;
				this.w_guardedRun = 0;
				this.h_guardedRun = 0;
				this.event_guardedRun = null;
				this.pausing_guardedRun = false;

				while (true) {
					synchronized (sGLThreadManager) {
						while (true) {
							if (this.mShouldExit) {
								return;
							}

							if (!this.mEventQueue.isEmpty()) {
								this.event_guardedRun = this.mEventQueue.remove(0);
								break;
							}

							// Update the pause state.
							this.pausing_guardedRun = false;
							if (this.mPaused != this.mRequestPaused) {
								this.pausing_guardedRun = this.mRequestPaused;
								this.mPaused = this.mRequestPaused;
								sGLThreadManager.notifyAll();
								if (LOG_PAUSE_RESUME) {
									Log.i("GLThread", "mPaused is now " + this.mPaused + " tid=" + this.getId());
								}
							}

							// Do we need to give up the EGL context?
							if (this.mShouldReleaseEglContext) {
								if (LOG_SURFACE) {
									Log.i("GLThread", "releasing EGL context because asked to tid=" + this.getId());
								}
								this.stopEglSurfaceLocked();
								this.stopEglContextLocked();
								this.mShouldReleaseEglContext = false;
								this.askedToReleaseEglContext_guardedRun = true;
							}

							// Have we lost the EGL context?
							if (this.lostEglContext_guardedRun) {
								this.stopEglSurfaceLocked();
								this.stopEglContextLocked();
								this.lostEglContext_guardedRun = false;
							}

							// When pausing, release the EGL surface:
							if (this.pausing_guardedRun && this.mHaveEglSurface) {
								if (LOG_SURFACE) {
									Log.i("GLThread", "releasing EGL surface because paused tid=" + this.getId());
								}
								this.stopEglSurfaceLocked();
							}

							// When pausing, optionally release the EGL Context:
							if (this.pausing_guardedRun && this.mHaveEglContext) {
								final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
								this.preserveEglContextOnPause_guardedRun = view == null ? false : view.mPreserveEGLContextOnPause;
								if (!this.preserveEglContextOnPause_guardedRun || sGLThreadManager.shouldReleaseEGLContextWhenPausing()) {
									this.stopEglContextLocked();
									if (LOG_SURFACE) {
										Log.i("GLThread", "releasing EGL context because paused tid=" + this.getId());
									}
								}
							}

							// When pausing, optionally terminate EGL:
							if (this.pausing_guardedRun) {
								if (sGLThreadManager.shouldTerminateEGLWhenPausing()) {
									this.mEglHelper.finish();
									if (LOG_SURFACE) {
										Log.i("GLThread", "terminating EGL because paused tid=" + this.getId());
									}
								}
							}

							// Have we lost the SurfaceView surface?
							if ((!this.mHasSurface) && (!this.mWaitingForSurface)) {
								if (LOG_SURFACE) {
									Log.i("GLThread", "noticed surfaceView surface lost tid=" + this.getId());
								}
								if (this.mHaveEglSurface) {
									this.stopEglSurfaceLocked();
								}
								this.mWaitingForSurface = true;
								this.mSurfaceIsBad = false;
								sGLThreadManager.notifyAll();
							}

							// Have we acquired the surface view surface?
							if (this.mHasSurface && this.mWaitingForSurface) {
								if (LOG_SURFACE) {
									Log.i("GLThread", "noticed surfaceView surface acquired tid=" + this.getId());
								}
								this.mWaitingForSurface = false;
								sGLThreadManager.notifyAll();
							}

							if (this.doRenderNotification_guardedRun) {
								if (LOG_SURFACE) {
									Log.i("GLThread", "sending render notification tid=" + this.getId());
								}
								this.wantRenderNotification_guardedRun = false;
								this.doRenderNotification_guardedRun = false;
								this.mRenderComplete = true;
								sGLThreadManager.notifyAll();
							}

							// Ready to draw?
							if (this.readyToDraw()) {

								// If we don't have an EGL context, try to acquire one.
								if (!this.mHaveEglContext) {
									if (this.askedToReleaseEglContext_guardedRun) {
										this.askedToReleaseEglContext_guardedRun = false;
									} else if (sGLThreadManager.tryAcquireEglContextLocked(this)) {
										try {
											this.mEglHelper.start();
										} catch (final RuntimeException t) {
											sGLThreadManager.releaseEglContextLocked(this);
											throw t;
										}
										this.mHaveEglContext = true;
										this.createEglContext_guardedRun = true;

										sGLThreadManager.notifyAll();
									}
								}

								if (this.mHaveEglContext && !this.mHaveEglSurface) {
									this.mHaveEglSurface = true;
									this.createEglSurface_guardedRun = true;
									this.createGlInterface_guardedRun = true;
									this.sizeChanged_guardedRun = true;
								}

								if (this.mHaveEglSurface) {
									if (this.mSizeChanged) {
										this.sizeChanged_guardedRun = true;
										this.w_guardedRun = this.mWidth;
										this.h_guardedRun = this.mHeight;
										this.wantRenderNotification_guardedRun = true;
										if (LOG_SURFACE) {
											Log.i("GLThread", "noticing that we want render notification tid=" + this.getId());
										}

										// Destroy and recreate the EGL surface.
										this.createEglSurface_guardedRun = true;

										this.mSizeChanged = false;
									}
									this.mRequestRender = false;
									sGLThreadManager.notifyAll();
									break;
								}
							}

							// By design, this is the only place in a GLThread thread where we wait().
							if (LOG_THREADS) {
								Log.i("GLThread",
									"waiting tid=" + this.getId() + " mHaveEglContext: " + this.mHaveEglContext + " mHaveEglSurface: "
										+ this.mHaveEglSurface + " mFinishedCreatingEglSurface: " + this.mFinishedCreatingEglSurface
										+ " mPaused: " + this.mPaused + " mHasSurface: " + this.mHasSurface + " mSurfaceIsBad: "
										+ this.mSurfaceIsBad + " mWaitingForSurface: " + this.mWaitingForSurface + " mWidth: " + this.mWidth
										+ " mHeight: " + this.mHeight + " mRequestRender: " + this.mRequestRender + " mRenderMode: "
										+ this.mRenderMode);
							}
							sGLThreadManager.wait();
						}
					} // end of synchronized(sGLThreadManager)

					if (this.event_guardedRun != null) {
						this.event_guardedRun.run();
						this.event_guardedRun = null;
						continue;
					}

					if (this.createEglSurface_guardedRun) {
						if (LOG_SURFACE) {
							Log.w("GLThread", "egl createSurface");
						}
						if (this.mEglHelper.createSurface()) {
							synchronized (sGLThreadManager) {
								this.mFinishedCreatingEglSurface = true;
								sGLThreadManager.notifyAll();
							}
						} else {
							synchronized (sGLThreadManager) {
								this.mFinishedCreatingEglSurface = true;
								this.mSurfaceIsBad = true;
								sGLThreadManager.notifyAll();
							}
							continue;
						}
						this.createEglSurface_guardedRun = false;
					}

					if (this.createGlInterface_guardedRun) {
						this.gl_guardedRun = (GL10)this.mEglHelper.createGL();

						sGLThreadManager.checkGLDriver(this.gl_guardedRun);
						this.createGlInterface_guardedRun = false;
					}

					if (this.createEglContext_guardedRun) {
						if (LOG_RENDERER) {
							Log.w("GLThread", "onSurfaceCreated");
						}
						final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
						if (view != null) {
							view.mRenderer.onSurfaceCreated(this.gl_guardedRun, this.mEglHelper.mEglConfig);
						}
						this.createEglContext_guardedRun = false;
					}

					if (this.sizeChanged_guardedRun) {
						if (LOG_RENDERER) {
							Log.w("GLThread", "onSurfaceChanged(" + this.w_guardedRun + ", " + this.h_guardedRun + ")");
						}
						final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
						if (view != null) {
							view.mRenderer.onSurfaceChanged(this.gl_guardedRun, this.w_guardedRun, this.h_guardedRun);
						}
						this.sizeChanged_guardedRun = false;
					}

					if (LOG_RENDERER_DRAW_FRAME) {
						Log.w("GLThread", "onDrawFrame tid=" + this.getId());
					}
					{
						final RedGLSurfaceViewAPI18 view = this.mGLSurfaceViewWeakRef.get();
						if (view != null) {
							view.mRenderer.onDrawFrame(null);
						}
					}
					this.swapError_guardedRun = this.mEglHelper.swap();
					{
						switch (this.swapError_guardedRun) {
						case EGL10.EGL_SUCCESS:
							break;
						case EGL11.EGL_CONTEXT_LOST:
							if (LOG_SURFACE) {
								Log.i("GLThread", "egl context lost tid=" + this.getId());
							}
							this.lostEglContext_guardedRun = true;
							break;
						default:
							// Other errors typically mean that the current surface is bad,
							// probably because the SurfaceView surface has been destroyed,
							// but we haven't been notified yet.
							// Log the error to help developers understand why rendering stopped.
							EglHelper.logEglErrorAsWarning("GLThread", "eglSwapBuffers", this.swapError_guardedRun);

							synchronized (sGLThreadManager) {
								this.mSurfaceIsBad = true;
								sGLThreadManager.notifyAll();
							}
							break;
						}
					}

					if (this.wantRenderNotification_guardedRun) {
						this.doRenderNotification_guardedRun = true;
					}
				}

			} finally {
				/*
				 * clean-up everything...
				 */
				synchronized (sGLThreadManager) {
					this.stopEglSurfaceLocked();
					this.stopEglContextLocked();
				}
			}
		}

		final public boolean ableToDraw () {
			return this.mHaveEglContext && this.mHaveEglSurface && this.readyToDraw();
		}

		private final boolean readyToDraw () {
			return (!this.mPaused) && this.mHasSurface && (!this.mSurfaceIsBad) && (this.mWidth > 0) && (this.mHeight > 0)
				&& (this.mRequestRender || (this.mRenderMode == RENDERMODE_CONTINUOUSLY));
		}

		public void setRenderMode (final int renderMode) {
			if (!((RENDERMODE_WHEN_DIRTY <= renderMode) && (renderMode <= RENDERMODE_CONTINUOUSLY))) {
				throw new IllegalArgumentException("renderMode");
			}
			synchronized (sGLThreadManager) {
				this.mRenderMode = renderMode;
				sGLThreadManager.notifyAll();
			}
		}

		public int getRenderMode () {
			synchronized (sGLThreadManager) {
				return this.mRenderMode;
			}
		}

		public void requestRender () {
			synchronized (sGLThreadManager) {
				this.mRequestRender = true;
				sGLThreadManager.notifyAll();
			}
		}

		public void surfaceCreated () {
			synchronized (sGLThreadManager) {
				if (LOG_THREADS) {
					Log.i("GLThread", "surfaceCreated tid=" + this.getId());
				}
				this.mHasSurface = true;
				this.mFinishedCreatingEglSurface = false;
				sGLThreadManager.notifyAll();
				while (this.mWaitingForSurface && !this.mFinishedCreatingEglSurface && !this.mExited) {
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void surfaceDestroyed () {
			synchronized (sGLThreadManager) {
				if (LOG_THREADS) {
					Log.i("GLThread", "surfaceDestroyed tid=" + this.getId());
				}
				this.mHasSurface = false;
				sGLThreadManager.notifyAll();
				while ((!this.mWaitingForSurface) && (!this.mExited)) {
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException e) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void onPause () {
			synchronized (sGLThreadManager) {
				if (LOG_PAUSE_RESUME) {
					Log.i("GLThread", "onPause tid=" + this.getId());
				}
				this.mRequestPaused = true;
				sGLThreadManager.notifyAll();
				while ((!this.mExited) && (!this.mPaused)) {
					if (LOG_PAUSE_RESUME) {
						Log.i("Main thread", "onPause waiting for mPaused.");
					}
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void onResume () {
			synchronized (sGLThreadManager) {
				if (LOG_PAUSE_RESUME) {
					Log.i("GLThread", "onResume tid=" + this.getId());
				}
				this.mRequestPaused = false;
				this.mRequestRender = true;
				this.mRenderComplete = false;
				sGLThreadManager.notifyAll();
				while ((!this.mExited) && this.mPaused && (!this.mRenderComplete)) {
					if (LOG_PAUSE_RESUME) {
						Log.i("Main thread", "onResume waiting for !mPaused.");
					}
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void onWindowResize (final int w, final int h) {
			synchronized (sGLThreadManager) {
				this.mWidth = w;
				this.mHeight = h;
				this.mSizeChanged = true;
				this.mRequestRender = true;
				this.mRenderComplete = false;
				sGLThreadManager.notifyAll();

				// Wait for thread to react to resize and render a frame
				while (!this.mExited && !this.mPaused && !this.mRenderComplete && this.ableToDraw()) {
					if (LOG_SURFACE) {
						Log.i("Main thread", "onWindowResize waiting for render complete from tid=" + this.getId());
					}
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void requestExitAndWait () {
			// don't call this from GLThread thread or it is a guaranteed
			// deadlock!
			synchronized (sGLThreadManager) {
				this.mShouldExit = true;
				sGLThreadManager.notifyAll();
				while (!this.mExited) {
					try {
						sGLThreadManager.wait();
					} catch (final InterruptedException ex) {
						Thread.currentThread().interrupt();
					}
				}
			}
		}

		public void requestReleaseEglContextLocked () {
			this.mShouldReleaseEglContext = true;
			sGLThreadManager.notifyAll();
		}

		/** Queue an "event" to be run on the GL rendering thread.
		 * @param r the runnable to be run on the GL rendering thread. */
		public void queueEvent (final Runnable r) {
			if (r == null) {
				throw new IllegalArgumentException("r must not be null");
			}
			synchronized (sGLThreadManager) {
				this.mEventQueue.add(r);
				sGLThreadManager.notifyAll();
			}
		}

		// Once the thread is started, all accesses to the following member
		// variables are protected by the sGLThreadManager monitor
		private boolean mShouldExit;
		private boolean mExited;
		private boolean mRequestPaused;
		private boolean mPaused;
		private boolean mHasSurface;
		private boolean mSurfaceIsBad;
		private boolean mWaitingForSurface;
		private boolean mHaveEglContext;
		private boolean mHaveEglSurface;
		private boolean mFinishedCreatingEglSurface;
		private boolean mShouldReleaseEglContext;
		private int mWidth;
		private int mHeight;
		private int mRenderMode;
		private boolean mRequestRender;
		private boolean mRenderComplete;
		private final ArrayList<Runnable> mEventQueue = new ArrayList<Runnable>();
		private boolean mSizeChanged = true;

		// End of member variables protected by the sGLThreadManager monitor.

		private EglHelper mEglHelper;

		/** Set once at thread construction time, nulled out when the parent view is garbage called. This weak reference allows the
		 * GLSurfaceView to be garbage collected while the GLThread is still alive. */
		private final WeakReference<RedGLSurfaceViewAPI18> mGLSurfaceViewWeakRef;

	}

	static class LogWriter extends Writer {

		@Override
		public void close () {
			this.flushBuilder();
		}

		@Override
		public void flush () {
			this.flushBuilder();
		}

		@Override
		public void write (final char[] buf, final int offset, final int count) {
			for (int i = 0; i < count; i++) {
				final char c = buf[offset + i];
				if (c == '\n') {
					this.flushBuilder();
				} else {
					this.mBuilder.append(c);
				}
			}
		}

		private void flushBuilder () {
			if (this.mBuilder.length() > 0) {
				Log.v("GLSurfaceView", this.mBuilder.toString());
				this.mBuilder.delete(0, this.mBuilder.length());
			}
		}

		private final StringBuilder mBuilder = new StringBuilder();
	}

	private void checkRenderThreadState () {
		if (this.mGLThread != null) {
			throw new IllegalStateException("setRenderer has already been called for this instance.");
		}
	}

	private static class GLThreadManager {
		private static String TAG = "GLThreadManager";

		public synchronized void threadExiting (final GLThread thread) {
			if (LOG_THREADS) {
				Log.i("GLThread", "exiting tid=" + thread.getId());
			}
			thread.mExited = true;
			if (this.mEglOwner == thread) {
				this.mEglOwner = null;
			}
			this.notifyAll();
		}

		/*
		 * Tries once to acquire the right to use an EGL context. Does not block. Requires that we are already in the
		 * sGLThreadManager monitor when this is called.
		 *
		 * @return true if the right to use an EGL context was acquired.
		 */
		public boolean tryAcquireEglContextLocked (final GLThread thread) {
			if (this.mEglOwner == thread || this.mEglOwner == null) {
				this.mEglOwner = thread;
				this.notifyAll();
				return true;
			}
			this.checkGLESVersion();
			if (this.mMultipleGLESContextsAllowed) {
				return true;
			}
			// Notify the owning thread that it should release the context.
			// TODO: implement a fairness policy. Currently
			// if the owning thread is drawing continuously it will just
			// reacquire the EGL context.
			if (this.mEglOwner != null) {
				this.mEglOwner.requestReleaseEglContextLocked();
			}
			return false;
		}

		/*
		 * Releases the EGL context. Requires that we are already in the sGLThreadManager monitor when this is called.
		 */
		public void releaseEglContextLocked (final GLThread thread) {
			if (this.mEglOwner == thread) {
				this.mEglOwner = null;
			}
			this.notifyAll();
		}

		public synchronized boolean shouldReleaseEGLContextWhenPausing () {
			// Release the EGL context when pausing even if
			// the hardware supports multiple EGL contexts.
			// Otherwise the device could run out of EGL contexts.
			return this.mLimitedGLESContexts;
		}

		public synchronized boolean shouldTerminateEGLWhenPausing () {
			this.checkGLESVersion();
			return !this.mMultipleGLESContextsAllowed;
		}

		public synchronized void checkGLDriver (final GL10 gl) {
			if (!this.mGLESDriverCheckComplete) {
				this.checkGLESVersion();
				final String renderer = gl.glGetString(GL10.GL_RENDERER);
				if (this.mGLESVersion < kGLES_20) {
					this.mMultipleGLESContextsAllowed = !renderer.startsWith(kMSM7K_RENDERER_PREFIX);
					this.notifyAll();
				}
				this.mLimitedGLESContexts = !this.mMultipleGLESContextsAllowed;
				if (LOG_SURFACE) {
					Log.w(TAG, "checkGLDriver renderer = \"" + renderer + "\" multipleContextsAllowed = "
						+ this.mMultipleGLESContextsAllowed + " mLimitedGLESContexts = " + this.mLimitedGLESContexts);
				}
				this.mGLESDriverCheckComplete = true;
			}
		}

		private void checkGLESVersion () {
			if (!this.mGLESVersionCheckComplete) {
// mGLESVersion = SystemProperties.getInt(
// "ro.opengles.version",
// ConfigurationInfo.GL_ES_VERSION_UNDEFINED);
// SystemProperties is a hidden class that is not part of the public SDK
// so we force GL ES version to 2.0
				this.mGLESVersion = kGLES_20;
				if (this.mGLESVersion >= kGLES_20) {
					this.mMultipleGLESContextsAllowed = true;
				}
				if (LOG_SURFACE) {
					Log.w(TAG, "checkGLESVersion mGLESVersion =" + " " + this.mGLESVersion + " mMultipleGLESContextsAllowed = "
						+ this.mMultipleGLESContextsAllowed);
				}
				this.mGLESVersionCheckComplete = true;
			}
		}

		/** This check was required for some pre-Android-3.0 hardware. Android 3.0 provides support for hardware-accelerated views,
		 * therefore multiple EGL contexts are supported on all Android 3.0+ EGL drivers. */
		private boolean mGLESVersionCheckComplete;
		private int mGLESVersion;
		private boolean mGLESDriverCheckComplete;
		private boolean mMultipleGLESContextsAllowed;
		private boolean mLimitedGLESContexts;
		private static final int kGLES_20 = 0x20000;
		private static final String kMSM7K_RENDERER_PREFIX = "Q3Dimension MSM7500 ";
		private GLThread mEglOwner;
	}

	private static final GLThreadManager sGLThreadManager = new GLThreadManager();

	private final WeakReference<RedGLSurfaceViewAPI18> mThisWeakRef = new WeakReference<RedGLSurfaceViewAPI18>(this);
	private GLThread mGLThread;
	private Renderer mRenderer;
	private boolean mDetached;
	private EGLConfigChooser mEGLConfigChooser;
	private EGLContextFactory mEGLContextFactory;
	private EGLWindowSurfaceFactory mEGLWindowSurfaceFactory;
	private GLWrapper mGLWrapper;
	private int mDebugFlags;
	private int mEGLContextClientVersion;
	private boolean mPreserveEGLContextOnPause;
}
