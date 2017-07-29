
package com.jfixby.red.triplane.fokker.android;

import com.badlogic.gdx.backends.android.AndroidGraphics;
import com.jfixby.red.triplane.fokker.android.run.RedTriplaneAndroidApplication;
import com.jfixby.scarabei.android.api.camera.AndroidCameraSetup;

import android.graphics.PixelFormat;
import android.view.SurfaceView;

public class RedAndroidCameraSetup implements AndroidCameraSetup {

	private int origWidth;
	private int origHeight;

	private final RedTriplaneAndroidApplication activity;
	private AndroidDeviceCameraController cameraControl;

	public RedAndroidCameraSetup (final RedTriplaneAndroidApplication activity) {
		this.activity = activity;
	}

	public void activateCamera () {
// Pixmap.setBlending(Blending.None);
		if (this.graphics().getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics().getView();
			// force alpha channel - I'm not sure we need this as the GL surface is already using alpha channel
			glView.getHolder().setFormat(PixelFormat.RGBA_8888);
		}
		// we don't want the screen to turn off during the long image saving process
		this.graphics().getView().setKeepScreenOn(true);
		// keep the original screen size
		this.origWidth = this.graphics().getWidth();
		this.origHeight = this.graphics().getHeight();

		// ??
		this.cameraControl.prepareCameraAsync(this.origWidth, this.origHeight);
	}

	private AndroidGraphics graphics () {
		return this.getActivity().graphics();
	}

	public void setFixedSize (final int width, final int height) {
		if (this.graphics().getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics().getView();
			glView.getHolder().setFormat(PixelFormat.RGBA_8888);
			glView.getHolder().setFixedSize(width, height);
		}
	}

	public void restoreFixedSize () {
		if (this.graphics().getView() instanceof SurfaceView) {
			final SurfaceView glView = (SurfaceView)this.graphics().getView();
			glView.getHolder().setFormat(PixelFormat.RGBA_8888);
			glView.getHolder().setFixedSize(this.origWidth, this.origHeight);
		}
	}

	public RedTriplaneAndroidApplication getActivity () {
		return this.activity;
	}

	public void post (final Runnable r) {
		this.getActivity().post(r);
	}

	public void prepareCamera () {
		this.cameraControl = new AndroidDeviceCameraController(this);
	}

}
