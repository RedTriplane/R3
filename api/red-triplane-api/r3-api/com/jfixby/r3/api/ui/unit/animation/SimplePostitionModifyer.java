package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.time.TimeStream;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface SimplePostitionModifyer extends Animation ,VisibleComponent{
	
	public TimeStream getTimeStream();

}
