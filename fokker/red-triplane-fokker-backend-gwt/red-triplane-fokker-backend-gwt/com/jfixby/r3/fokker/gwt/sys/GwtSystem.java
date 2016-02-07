package com.jfixby.r3.fokker.gwt.sys;

import com.jfixby.cmns.api.log.L;
import com.jfixby.cmns.api.sys.SystemComponent;
import com.jfixby.cmns.api.time.TimeStream;
import com.jfixby.red.sys.RedSystem;

public class GwtSystem extends RedSystem implements SystemComponent {



	@Override
	public void exit() {
		L.d("EXIT");
		L.d("not allowed to EXIT");
		System.exit(0);

	}

	@Override
	public boolean sleep(long period) {
		L.d("not allowed to sleep", period);
		return false;
	}

}
