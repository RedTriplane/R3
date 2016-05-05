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

    public CameraSurface( Context context ) {
        super( context );
        // We're implementing the Callback interface and want to get notified
        // about certain surface events.
        getHolder().addCallback( this );
        // We're changing the surface to a PUSH surface, meaning we're receiving
        // all buffer data from another component - the camera, in this case.
        getHolder().setType( SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS );
    }

    public void surfaceCreated( SurfaceHolder holder ) {
        // Once the surface is created, simply open a handle to the camera hardware.
//        camera = Camera.open();
        camera = openFrontFacingCameraGingerbread();
    }

    public void surfaceChanged( SurfaceHolder holder, int format, int width, int height ) {
        // This method is called when the surface changes, e.g. when it's size is set.
        // We use the opportunity to initialize the camera preview display dimensions.
        Camera.Parameters parameters = camera.getParameters();
        List<Camera.Size> previewSizes = parameters.getSupportedPreviewSizes();

        // You need to choose the most appropriate previewSize for your app
        Camera.Size previewSize = previewSizes.get(0);
        float aspectRatio = width / height;
        float closestMatch = Math.abs((float)previewSize.width / previewSize.height - aspectRatio);

        for (Camera.Size size : previewSizes)
        {
            float match = Math.abs((float)size.width / size.height - aspectRatio);
            if (match < closestMatch)
            {
                previewSize = size;
                closestMatch = match;
            }
        }

        parameters.setPreviewSize(previewSize.width, previewSize.height);
        camera.setParameters(parameters);
        camera.startPreview();

        // We also assign the preview display to this surface...
        try {
            camera.setPreviewDisplay( holder );
        } catch( IOException e ) {
            e.printStackTrace();
        }
    }

    public void surfaceDestroyed( SurfaceHolder holder ) {
        // Once the surface gets destroyed, we stop the preview mode and release
        // the whole camera since we no longer need it.
        camera.stopPreview();
        camera.release();
        camera = null;
    }

    public Camera getCamera() {
        return camera;
    }

    private Camera openFrontFacingCameraGingerbread() {
        int cameraCount = 0;
        Camera cam = null;
        Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
        cameraCount = Camera.getNumberOfCameras();
        for (int camIdx = 0; camIdx < cameraCount; camIdx++) {
            Camera.getCameraInfo(camIdx, cameraInfo);
            if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_FRONT) {
                try {
                    cam = Camera.open(camIdx);
                } catch (RuntimeException e) {
                    Log.e("camera", "Camera failed to open: " + e.getLocalizedMessage());
                }
            }
        }

        return cam;
    }
}


