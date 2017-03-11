package com.jfixby.r3.api.ui.unit.geometry;

import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.geometry.ClosedPolygonalChain;

public interface PolyComponent extends CanvasComponent {

	void setBorderColor(Color border_color);

	Color getBorderColor();

	void setFillColor(Color fill_color);

	Color getFillColor();

	ClosedPolygonalChain getShape();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();
}
