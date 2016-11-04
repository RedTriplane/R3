
package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.r3.api.ui.unit.projection.Projection;

public interface Layer extends VisibleComponent {
	public void closeInputValve ();

	public boolean inputValveIsOpen ();

	public void openInputValve ();

	void setCamera (Camera camera);

	Camera getCamera ();

	public void setProjection (Projection projection);

	Projection getProjection ();

	// void setShader(Shader shader);

	// Shader getShader();

	public void attachComponent (Component attachment);

	public void detatchComponent (Component attachment);

	public void detatchAllComponents ();

	public boolean containsComponent (Component component);

	public void attachComponents (Collection<? extends Component> attachments);

	public void detatchComponents (Collection<? extends Component> attachments);

	public ComponentsFactory getComponentsFactory ();

	public void print ();

	public <Q extends NamedComponent> Collection<Q> findComponents (String element_name);

	public <Q extends NamedComponent> Q findComponent (String element_name);

	public <Q extends NamedComponent> Collection<Q> findComponents (AssetID element_name);

	public <Q extends NamedComponent> Q findComponent (AssetID element_name);

	public <Q extends Component> Q findComponent ();

	public Collection<Component> listChildren ();

	public void printChildren (String tag);

}
