
package com.jfixby.r3.ui.api.activity.txt;

import com.jfixby.r3.ui.api.activity.CanvasPositionable;
import com.jfixby.r3.ui.api.activity.layer.VisibleComponent;
import com.jfixby.r3.ui.api.activity.locale.LocalizedComponent;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	void setText (String text);

	String getText ();

}
