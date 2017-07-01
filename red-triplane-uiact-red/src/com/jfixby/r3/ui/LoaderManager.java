
package com.jfixby.r3.ui;

import com.jfixby.r3.uiact.ProgressListener;
import com.jfixby.scarabei.api.err.Err;

public class LoaderManager extends UnitManager {

	public ProgressListener getProgressListener () {
		if (!(this.unit instanceof ProgressListener)) {
			final String error = ("Unit<" + this.unit + "> does not implement ProgressListener");
			Err.reportError(error);
// L.d(error);
// L.e(error);
// Sys.exit();
// Err.reportError(error);
		}
		return (ProgressListener)this.unit;
	}

}
