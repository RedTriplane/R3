
package com.jfixby.r3.activity.red.act;

import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.taskman.ProgressListener;

public class LoaderManager extends ActivityManager {

	public ProgressListener getProgressListener () {
		if (!(this.unit instanceof ProgressListener)) {
			final String error = ("Activity<" + this.unit + "> does not implement ProgressListener");
			Err.reportError(error);
// L.d(error);
// L.e(error);
// Sys.exit();
// Err.reportError(error);
		}
		return (ProgressListener)this.unit;
	}

}
