
package com.jfixby.r3.activity.api.input;

import com.jfixby.r3.activity.api.layer.NamedComponent;
import com.jfixby.r3.activity.api.layer.VisibleComponent;

public interface Button extends VisibleComponent, InputComponent, NamedComponent {
	public void setDebugRenderFlag (boolean b);

	public boolean getDebugRenderFlag ();

	void setPositionX (double x);

	void setPositionY (double y);

}
