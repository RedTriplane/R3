package com.jfixby.r3.api.activity.animation;

import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.time.TimeStream;

public interface SimplePostitionModifyer extends Animation ,VisibleComponent{
	
	public TimeStream getTimeStream();

}
