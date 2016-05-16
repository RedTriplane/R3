package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.geometry.Circle;
import com.jfixby.r3.api.ui.unit.layer.DrawableComponent;

public interface EllipseComponent extends DrawableComponent {

	void setBorderColor(Color border_color);

	Color getBorderColor();

	void setFillColor(Color fill_color);

	Color getFillColor();

	Color getDebugColor();

	Circle shape();

	public void setDebugColor(Color debug_render_color);
	
	public void setOpacity(double alpha);

	public double getOpacity();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();



}
