
package com.jfixby.r3.activity.red.tool;

import com.jfixby.r3.activity.api.ActivityToolkit;
import com.jfixby.r3.activity.api.ScreenShot;
import com.jfixby.r3.activity.api.ScreenShotSpecs;
import com.jfixby.r3.activity.red.RedActivityExecutor;

public class RedActivityTools implements ActivityToolkit {

	private final RedActivityExecutor master;

	public RedActivityTools (final RedActivityExecutor redActivityExecutor) {
		this.master = redActivityExecutor;
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
