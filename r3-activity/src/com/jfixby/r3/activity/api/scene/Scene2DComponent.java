
package com.jfixby.r3.activity.api.scene;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.animation.Animation;
import com.jfixby.r3.activity.api.camera.CanvasCamera;
import com.jfixby.r3.activity.api.input.InputComponent;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.NamedComponent;
import com.jfixby.r3.activity.api.locale.LocalizedComponent;
import com.jfixby.r3.activity.api.parallax.Parallax;
import com.jfixby.r3.activity.api.raster.Raster;
import com.jfixby.r3.activity.api.txt.TextBar;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Mapping;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public interface Scene2DComponent extends NamedComponent {

	public void print ();

	public Mapping<String, InputComponent> listInputComponents ();

	public CanvasCamera getCamera ();

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
