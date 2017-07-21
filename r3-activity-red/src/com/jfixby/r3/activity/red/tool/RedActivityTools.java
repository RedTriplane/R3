
package com.jfixby.r3.activity.red.tool;

import com.jfixby.r3.activity.api.ActivityToolkit;
import com.jfixby.r3.activity.api.ScreenShot;
import com.jfixby.r3.activity.api.ScreenShotSpecs;

public class RedActivityTools implements ActivityToolkit {

	public RedActivityTools () {
	}

	@Override
	public ScreenShotSpecs newScreenShotSpecs () {
		return new ScreenShotSpecs();
	}

	@Override
	public ScreenShot newScreenShot (final ScreenShotSpecs sh_spec) {
		return new RedScreenShot(sh_spec);
	}

}
