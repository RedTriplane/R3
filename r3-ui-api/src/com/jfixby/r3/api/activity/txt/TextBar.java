
package com.jfixby.r3.api.activity.txt;

import com.jfixby.r3.api.activity.CanvasPositionable;
import com.jfixby.r3.api.activity.layer.VisibleComponent;
import com.jfixby.r3.api.activity.locale.LocalizedComponent;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	void setText (String text);

	String getText ();

}
