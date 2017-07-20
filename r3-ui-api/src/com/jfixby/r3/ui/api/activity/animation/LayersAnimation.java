
package com.jfixby.r3.ui.api.activity.animation;

import com.jfixby.r3.ui.api.activity.layer.VisibleComponent;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.time.TimeStream;

public interface LayersAnimation extends Animation, VisibleComponent {

	public TimeStream getTimeStream ();

	public Collection<VisibleComponent> listFrames ();

}
