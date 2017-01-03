
package com.jfixby.red.triplane.fokker.android;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Pixmap;
import com.jfixby.red.triplane.fokker.android.run.RedTriplaneAndroidApplication;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.ImageFormat;
import android.hardware.Camera;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;

public class AndroidDeviceCameraController implements DeviceCameraControl, Camera.PictureCallback, Camera.AutoFocusCallback {

	private static final int ONE_SECOND_IN_MILI = 1000;
	private final RedAndroidCameraSetup camSetup;
	private CameraSurface cameraSurface;
	private byte[] pictureData;

	public AndroidDeviceCameraController (final RedAndroidCameraSetup camSetup) {
		this.camSetup = camSetup;
	}

	@Override
	public synchronized void prepareCamera (final int screenWidth, final int screenHeight) {
		this.camSetup.setFixedSize(screenWidth, screenHeight);
		if (this.cameraSurface == null) {
			this.cameraSurface = new CameraSurface(this.camSetup.getActivity());
		}
		this.activity().addContentView(this.cameraSurface, new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
	}

	private RedTriplaneAndroidApplication activity () {
		return this.camSetup.getActivity();
	}

	@Override
	public synchronized void startPreview () {
		// ...and start previewing. From now on, the camera keeps pushing preview
		// images to the surface.
		if (this.cameraSurface != null && this.cameraSurface.getCamera() != null) {
			this.cameraSurface.getCamera().startPreview();
		}
	}

	@Override
	public synchronized void stopPreview () {
		// stop previewing.
		if (this.cameraSurface != null) {
			final ViewParent parentView = this.cameraSurface.getParent();
			if (parentView instanceof ViewGroup) {
				final ViewGroup viewGroup = (ViewGroup)parentView;
				viewGroup.removeView(this.cameraSurface);
			}
			if (this.cameraSurface.getCamera() != null) {
				this.cameraSurface.getCamera().stopPreview();
			}
		}
		this.camSetup.restoreFixedSize();
	}

	public void setCameraParametersForPicture (final Camera camera) {
		final Camera.Parameters p = camera.getParameters();
		final Camera.Size previewSize = p.getSupportedPreviewSizes().get(0);
		p.setPreviewSize(previewSize.width, previewSize.height);
		p.setPictureFormat(ImageFormat.JPEG);
		camera.setParameters(p);
	}

	@Override
	public synchronized void takePicture () {
		// the user request to take a picture - start the process by requesting focus
		this.setCameraParametersForPicture(this.cameraSurface.getCamera());
		this.cameraSurface.getCamera().autoFocus(this);
	}

	@Override
	public synchronized void onAutoFocus (final boolean success, final Camera camera) {
		// Focus process finished, we now have focus (or not)
		if (success) {
			if (camera != null) {
				camera.stopPreview();
				// We now have focus take the actual picture
				camera.takePicture(null, null, null, this);
			}
		}
	}

	@Override
	public synchronized void onPictureTaken (final byte[] pictureData, final Camera camera) {
		// We got the picture data - keep it
		this.pictureData = pictureData;
	}

	@Override
	public synchronized byte[] getPictureData () {
		// Give to picture data to whom ever requested it
		return this.pictureData;
	}

	@Override
	public void prepareCameraAsync (final int screenWidth, final int screenHeight) {
		final Runnable r = new Runnable() {
			@Override
			public void run () {
				AndroidDeviceCameraController.this.prepareCamera(screenWidth, screenHeight);
			}
		};
		this.camSetup.post(r);
	}

	@Override
	public synchronized void startPreviewAsync () {
		final Runnable r = new Runnable() {
			@Override
			public void run () {
				AndroidDeviceCameraController.this.startPreview();
			}
		};
		this.camSetup.post(r);
	}

	@Override
	public synchronized void stopPreviewAsync () {
		final Runnable r = new Runnable() {
			@Override
			public void run () {
				AndroidDeviceCameraController.this.stopPreview();
			}
		};
		this.camSetup.post(r);
	}

	@Override
	public synchronized byte[] takePictureAsync (long timeout) {
		timeout *= ONE_SECOND_IN_MILI;
		this.pictureData = null;
		final Runnable r = new Runnable() {
			@Override
			public void run () {
				AndroidDeviceCameraController.this.takePicture();
			}
		};
		this.camSetup.post(r);
		while (this.pictureData == null && timeout > 0) {
			try {
				Thread.sleep(ONE_SECOND_IN_MILI);
				timeout -= ONE_SECOND_IN_MILI;
			} catch (final InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (this.pictureData == null) {
			this.cameraSurface.getCamera().cancelAutoFocus();
		}
		return this.pictureData;
	}

	@Override
	public void saveAsJpeg (final FileHandle jpgfile, final Pixmap pixmap) {
		FileOutputStream fos;
		int x = 0, y = 0;
		int xl = 0, yl = 0;
		try {
			final Bitmap bmp = Bitmap.createBitmap(pixmap.getWidth(), pixmap.getHeight(), Bitmap.Config.ARGB_8888);
			// we need to switch between LibGDX RGBA format to Android ARGB format
			for (x = 0, xl = pixmap.getWidth(); x < xl; x++) {
				for (y = 0, yl = pixmap.getHeight(); y < yl; y++) {
					final int color = pixmap.getPixel(x, y);
					// RGBA => ARGB
					final int RGB = color >> 8;
					final int A = (color & 0x000000ff) << 24;
					final int ARGB = A | RGB;
					bmp.setPixel(x, y, ARGB);
				}
			}
			fos = new FileOutputStream(jpgfile.file());
			bmp.compress(CompressFormat.JPEG, 90, fos);
			fos.close();
		} catch (final FileNotFoundException e) {
			e.printStackTrace();
		} catch (final IOException e) {
			e.printStackTrace();
		} catch (final IllegalArgumentException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean isReady () {
		if (this.cameraSurface != null && this.cameraSurface.getCamera() != null) {
			return true;
		}
		return false;
	}
}
