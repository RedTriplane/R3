
package com.jfixby.r3.api.exe;

public interface EngineExecutor {

	void doUpdate (EngineState gdxAdaptor);

	void doRender (EngineState gdxAdaptor);

	void doDeploy ();

	void doPause ();

	void doDispose ();

	void doResume ();

}
