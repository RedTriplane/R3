
package com.jfixby.r3.activity.api.txt;

import com.jfixby.r3.activity.api.CanvasPositionable;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.locale.LocalizedComponent;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	void setText (String text);

	String getText ();

	void setDebugRenderFlag (boolean b);

	boolean getDebugRenderFlag ();

}
