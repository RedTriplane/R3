
package com.jfixby.r3.ext.scene2d.api;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.animation.Animation;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.input.InputComponent;
import com.jfixby.r3.api.ui.unit.layer.Layer;
import com.jfixby.r3.api.ui.unit.layer.NamedComponent;
import com.jfixby.r3.api.ui.unit.locale.LocalizedComponent;
import com.jfixby.r3.api.ui.unit.parallax.Parallax;
import com.jfixby.r3.api.ui.unit.raster.Raster;
import com.jfixby.r3.api.ui.unit.shader.ShaderComponent;
import com.jfixby.r3.api.ui.unit.txt.TextBar;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public interface Scene2DComponent extends NamedComponent {

	public void print ();

	public Mapping<String, InputComponent> listInputComponents ();

	public Camera getCamera ();

	public ComponentsFactory getComponentsFactory ();

	public ID getAssetID ();

	public Collection<Animation> listAnimations ();

	public Collection<Raster> listRaster ();

	public Collection<ShaderComponent> listShaders ();

	public Collection<LocalizedComponent> listLocalizedComponents ();

	public Collection<TextBar> listTextBars ();

	public Collection<Parallax> listParallaxes ();

	public Layer getRoot ();

	public Collection<Layer> findLayer (String layerName);

	public ReadOnlyFloat2 getOriginalDimentions ();

	public void show ();

	public void hide ();

	public void startAllAnimations ();

}
