
package com.jfixby.r3.api.logic;

import com.jfixby.cmns.api.util.Progress;

public interface TaskProgress extends Progress {

	public int getTotalSteps ();

	public int getProcessedSteps ();

}
