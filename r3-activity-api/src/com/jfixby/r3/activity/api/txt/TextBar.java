
package com.jfixby.r3.activity.api.txt;

import com.jfixby.r3.activity.api.CanvasPositionable;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.api.locale.LocalizedComponent;
import com.jfixby.scarabei.api.names.ID;
import com.jfixby.scarabei.api.strings.Text;

public interface TextBar extends VisibleComponent, LocalizedComponent, CanvasPositionable {

	void setDebugRenderFlag (boolean b);

	boolean getDebugRenderFlag ();

	public void setTextID (final ID text);

	public Text getText ();

	public ID getTextID ();

}
