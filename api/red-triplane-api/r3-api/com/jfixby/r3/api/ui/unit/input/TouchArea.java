
package com.jfixby.r3.api.ui.unit.input;

import com.jfixby.cmns.api.geometry.Rectangle;
import com.jfixby.r3.api.ui.unit.layer.Component;

public interface TouchArea extends Component, InputComponent {

	public Rectangle shape ();

	public void setDebugRenderFlag (boolean b);

	public boolean gerDebugRenderFlag ();

	public double getHeight ();

	public double getWidth ();

}
