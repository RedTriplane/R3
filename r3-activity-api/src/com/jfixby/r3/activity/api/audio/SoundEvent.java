
package com.jfixby.r3.activity.api.audio;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.scarabei.api.names.ID;

public interface SoundEvent extends CanvasComponent {

	public ComponentsFactory getComponentsFactory ();

	public ID getAssetID ();

	SoundEvent copy ();

}
