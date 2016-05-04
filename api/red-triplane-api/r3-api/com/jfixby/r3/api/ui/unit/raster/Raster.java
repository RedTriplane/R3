
package com.jfixby.r3.api.ui.unit.raster;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;

public interface Raster extends ProjectableComponent, BlendableComponent {
	public AssetID getAssetID ();

	@Override
	public void setOpacity (double alpha);

	@Override
	public double getOpacity ();

	@Override
	void setDebugRenderFlag (boolean b);

	@Override
	boolean getDebugRenderFlag ();

	@Override
	public void setDebugColor (Color debug_render_color);

	@Override
	public Color getDebugColor ();

	public ComponentsFactory getComponentsFactory ();

}
