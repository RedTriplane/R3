
package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.camera.Camera;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.util.path.RelativePath;

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

	public <Q extends NamedComponent> Q findComponent (RelativePath relative);

	public <Q extends NamedComponent> Collection<Q> findComponents (ID element_name);

	public <Q extends NamedComponent> Q findComponent (ID element_name);

	public <Q extends Component> Q findComponent ();

	public Collection<Component> listChildren ();

	public void printChildrenList (String tag);

}
