package com.jfixby.r3.api.ui.unit.animation;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.time.TimeStream;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface LayersAnimation extends Animation, VisibleComponent {

	Collection<VisibleComponent> listFrames();

	public TimeStream getTimeStream();

}
