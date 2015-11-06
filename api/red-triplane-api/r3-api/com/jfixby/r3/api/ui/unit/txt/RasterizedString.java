package com.jfixby.r3.api.ui.unit.txt;

import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.math.Angle;
import com.jfixby.r3.api.ui.unit.layer.VisibleComponent;

public interface RasterizedString extends VisibleComponent {

	void setPositionXY(double canvas_x, double canvas_y);

	public double getPositionX();

	public double getPositionY();

	public void setRotation(Angle rotation);

	public void setRotation(double rotation);

	public Angle getRotation();

	void dispose();

	void setPosition(CanvasPosition position);

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	StringBounds getStringBounds();

}
