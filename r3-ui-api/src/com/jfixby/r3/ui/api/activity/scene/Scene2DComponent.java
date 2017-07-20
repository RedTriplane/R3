
package com.jfixby.r3.ui.api.activity.scene;

import com.jfixby.r3.ui.api.activity.ComponentsFactory;
import com.jfixby.r3.ui.api.activity.animation.Animation;
import com.jfixby.r3.ui.api.activity.camera.Camera;
import com.jfixby.r3.ui.api.activity.input.InputComponent;
import com.jfixby.r3.ui.api.activity.layer.Layer;
import com.jfixby.r3.ui.api.activity.layer.NamedComponent;
import com.jfixby.r3.ui.api.activity.locale.LocalizedComponent;
import com.jfixby.r3.ui.api.activity.parallax.Parallax;
import com.jfixby.r3.ui.api.activity.raster.Raster;
import com.jfixby.r3.ui.api.activity.txt.TextBar;
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

	public Collection<LocalizedComponent> listLocalizedComponents ();

	public Collection<TextBar> listTextBars ();

	public Collection<Parallax> listParallaxes ();

	public Layer getRoot ();

	public Collection<Layer> findLayer (String layerName);

	public ReadOnlyFloat2 getOriginalDimentions ();

	public void show ();

	public void hide ();

	public void startAllAnimations ();

	public void dispose ();

}
