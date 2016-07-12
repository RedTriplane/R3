
package com.jfixby.r3.ext.api.scene2d;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.cmns.api.floatn.FixedFloat2;
import com.jfixby.r3.api.locale.LocalizedComponent;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.input.InputComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.layer.NamedComponent;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.txt.TextBar;

public interface Scene extends NamedComponent {

	void print ();

	public Mapping<String, InputComponent> listInputComponents ();

	Camera getCamera ();

	public ComponentsFactory getComponentsFactory ();

	public AssetID getAssetID ();

	public Collection<Animation> listAnimations ();

	public Collection<Raster> listRaster ();

	public Collection<ShaderComponent> listShaders ();

	public Collection<LocalizedComponent> listLocalizedComponents ();

	public Collection<TextBar> listTextBars ();

	Layer getRoot ();

	Collection<Layer> findLayer (String layerName);

	FixedFloat2 getOriginalDimentions ();

	void show ();

	void hide ();

}
