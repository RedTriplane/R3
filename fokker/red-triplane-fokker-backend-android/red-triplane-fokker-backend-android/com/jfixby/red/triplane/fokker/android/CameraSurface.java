
package com.jfixby.red.triplane.fokker.android;

import java.io.IOException;
import java.util.List;

import android.content.Context;
import android.hardware.Camera;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class CameraSurface extends SurfaceView implements SurfaceHolder.Callback {
	private Camera camera;

	public CameraSurface (final Context context) {
		super(context);
		// We're implementing the Callback interface and want to get notified
		// about certain surface events.
		this.getHolder().addCallback(this);
		// We're changing the surface to a PUSH surface, meaning we're receiving
		// all buffer data from another component - the camera, in this case.
		this.getHolder().setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
	}

	@Override
	public void surfaceCreated (final SurfaceHolder holder) {
		// Once the surface is created, simply open a handle to the camera hardware.
// camera = Camera.open();
		this.camera = this.openFrontFacingCameraGingerbread();
	}

	@Override
	public void surfaceChanged (final SurfaceHolder holder, final int format, final int width, final int height) {
		// This method is called when the surface changes, e.g. when it's size is set.
		// We use the opportunity to initialize the camera preview display dimensions.
		final Camera.Parameters parameters = this.camera.getParameters();
		final List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

		// You need to choose the most appropriate previewSize for your app
		Camera.Size previewSize = previewSizes.get(0);
		final float aspectRatio = width / height;
		float closestMatch = Math.abs((float)previewSize.width / previewSize.height - aspectRatio);

		for (final Camera.Size size : previewSizes) {
			final float match = Math.abs((float)size.width / size.height - aspectRatio);
			if (match < closestMatch) {
				previewSize = size;
				closestMatch = match;
			}
		}

		parameters.setPreviewSize(previewSize.width, previewSize.height);
		this.camera.setParameters(parameters);
		this.camera.startPreview();

		// We also assign the preview display to this surface...
		try {
			this.camera.setPreviewDisplay(holder);
		} catch (final IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void surfaceDestroyed (final SurfaceHolder holder) {
		// Once the surface gets destroyed, we stop the preview mode and release
		// the whole camera since we no longer need it.
		this.camera.stopPreview();
		this.camera.release();
		this.camera = null;
	}

	public Camera getCamera () {
		return this.camera;
	}

	private Camera openFrontFacingCameraGingerbread () {
		final int cameraCount = 0;
		Camera cam = null;
// final Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
// cameraCount = Camera.getNumberOfCameras();
// for (int camIdx = 0; camIdx < cameraCount; camIdx++)
		{
// Camera.getCameraInfo(camIdx, cameraInfo);
// if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK)
			{
				try {
					cam = Camera.open();
				} catch (final RuntimeException e) {
					e.printStackTrace();
					Log.e("camera", "Camera failed to open: " + e.getLocalizedMessage());
				}
			}
		}

		return cam;
	}
}
