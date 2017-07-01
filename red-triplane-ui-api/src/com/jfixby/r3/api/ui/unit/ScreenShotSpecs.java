package com.jfixby.r3.api.ui.unit;

import com.jfixby.r3.api.ui.unit.camera.Camera;

public interface ScreenShotSpecs {

	int getAreaWidth();

	int getAreaHeight();

	int getAreaY();

	int getAreaX();

	void setAreaX(int x);

	void setAreaY(int y);

	void setAreaWidth(int w);

	void setAreaHeight(int h);

	void setCamera(Camera camera);

	Camera getCamera();
}
