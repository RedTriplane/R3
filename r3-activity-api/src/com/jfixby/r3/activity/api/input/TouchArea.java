
package com.jfixby.r3.activity.api.input;

import com.jfixby.r3.activity.api.layer.Component;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface TouchArea extends Component, InputComponent {

	public Rectangle shape ();

	public void setDebugRenderFlag (boolean b);

	public boolean getDebugRenderFlag ();

	public double getHeight ();

	public double getWidth ();

	public MouseInputEventListener getInputListener ();

	public void setInputListener (MouseInputEventListener listener);
}
