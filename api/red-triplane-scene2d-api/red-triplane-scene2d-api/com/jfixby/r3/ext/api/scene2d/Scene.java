package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.input.InputComponent;
import com.jfixby.r3.api.ui.unit.raster.CanvasComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;

public interface Scene extends CanvasComponent {

	void print();

	public Mapping<AssetID, InputComponent> listInputComponents();

	Camera getCamera();

	public void setOpacity(double alpha);

	public double getOpacity();

	void setDebugRenderFlag(boolean b);

	boolean getDebugRenderFlag();

	public void setDebugColor(Color debug_render_color);

	public Color getDebugColor();

	public ComponentsFactory getComponentsFactory();

	public AssetID getAssetID();

	public Collection<Animation> listAnimations();

	public Collection<Raster> listRaster();

}
