
package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;
import com.jfixby.r3.api.ui.unit.raster.BlendableComponent;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_HORIZONTAL;
import com.jfixby.scarabei.api.geometry.ORIGIN_RELATIVE_VERTICAL;
import com.jfixby.scarabei.api.math.Angle;

public interface RasterizedString extends VisibleComponent, BlendableComponent {

	void setPositionXY (double canvas_x, double canvas_y);

	public double getPositionX ();

	public double getPositionY ();

	public void setRotation (Angle rotation);

	public void setRotation (double rotation);

	public Angle getRotation ();

	void setPosition (CanvasPosition position);

	void setPosition (ReadOnlyFloat2 position);

	void setDebugRenderFlag (boolean b);

	boolean getDebugRenderFlag ();

	void setOriginRelativeX (ORIGIN_RELATIVE_HORIZONTAL center);

	void setOriginRelativeY (ORIGIN_RELATIVE_VERTICAL center);

	void setChars (String string);

	String getChars ();

	void setPositionX (double x);

	void setPositionY (double d);

}
