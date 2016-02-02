package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface Raster extends ProjectableComponent {
	public AssetID getAssetID();

	public void setOpacity(double alpha);

	public double getOpacity();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();

	public ComponentsFactory getComponentsFactory();



}
