package com.jfixby.r3.activity.api.animation;

import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public interface SimplePostitionModifyer extends Animation ,VisibleComponent{
	
	public TimeStream getTimeStream();

}
