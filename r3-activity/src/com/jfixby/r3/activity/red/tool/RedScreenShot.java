
package com.jfixby.r3.activity.red.tool;

import com.jfixby.r3.activity.api.ScreenShot;
import com.jfixby.r3.activity.api.ScreenShotSpecs;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.r3.api.render.RenderMachine;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.image.ColorMap;

public class RedScreenShot implements ScreenShot {

	private final com.jfixby.r3.api.render.ScreenShot sh;

	public RedScreenShot (final ScreenShotSpecs sh_spec) {
		if (sh_spec.camera == null) {

		} else {
			final RedCamera redcam = (RedCamera)sh_spec.camera;
			sh_spec.areaWidth = redcam.getScreenAperturedWidth();
			sh_spec.areaHeight = redcam.getScreenAperturedHeight();
			sh_spec.areaX = redcam.getScreenAperturedX();
			sh_spec.areaY = redcam.getScreenAperturedY();
		}
		this.sh = RenderMachine.makeScreenShot(sh_spec.areaWidth, sh_spec.areaHeight, sh_spec.areaX, sh_spec.areaY);

	}

	@Override
	public ColorMap toColorMap () {
		return this.sh.toColorMap();
	}

	@Override
	public void saveToFile (final File screenSHotFile) {
		this.sh.saveToFile(screenSHotFile);
	}

}
