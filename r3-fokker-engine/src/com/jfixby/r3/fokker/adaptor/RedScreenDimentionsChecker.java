
package com.jfixby.r3.fokker.adaptor;

import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.engine.api.screen.ScreenDimentionsChecker;

public class RedScreenDimentionsChecker implements ScreenDimentionsChecker {

	long previous_check = -1;

	@Override
	public String toString () {
		return "RedScreenDimentionsChecker [previous_check=" + this.previous_check + "]";
	}

	@Override
	public boolean screenDimentionsHaveChanged () {
		return Screen.getLastUpdateCycleNumber() != this.previous_check;
	}

	@Override
	public void okGotIt () {
		this.previous_check = Screen.getLastUpdateCycleNumber();
	}

}
