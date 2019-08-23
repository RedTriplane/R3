
package com.jfixby.r3.engine.api.sound;

import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.geometry.projections.ProjectionsStack;
import com.jfixby.scarabei.api.names.ID;

public interface SoundMachineComponent {

	public void deploy ();

	public void destroy ();

	public void beginFrame ();

	public void endFrame ();

	public void setProjection (ProjectionsStack projections_stack);

	public void setCameraProjection (Projection projection);

	DefaultAudioAssets DefaultAssets ();

	void beginDrawComponent (Vocalizable fokkerDrawable);

	void endDrawComponent (Vocalizable fokkerDrawable);

	public void VocalizeEvent (ID asset_id);

}
