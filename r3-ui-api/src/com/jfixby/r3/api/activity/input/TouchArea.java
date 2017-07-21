
package com.jfixby.r3.api.activity.input;

import com.jfixby.r3.api.activity.layer.Component;
import com.jfixby.scarabei.api.geometry.Rectangle;

public interface TouchArea extends Component, InputComponent {

	public Rectangle shape ();

	public void setDebugRenderFlag (boolean b);

	public boolean gerDebugRenderFlag ();

	public double getHeight ();

	public double getWidth ();

}
