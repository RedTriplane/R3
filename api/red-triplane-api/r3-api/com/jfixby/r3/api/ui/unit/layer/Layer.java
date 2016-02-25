package com.jfixby.r3.api.ui.unit.layer;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.r3.api.ui.unit.ComponentsFactory;
import com.jfixby.r3.api.ui.unit.camera.Camera;

public interface Layer extends VisibleComponent {
	public void closeInputValve();

	public boolean inputValveIsOpen();

	public void openInputValve();

	void setCamera(Camera camera);

	Camera getCamera();

	// void setShader(Shader shader);

	// Shader getShader();

	public void attachComponent(Component attachment);

	public void detatchComponent(Component attachment);

	public void detatchAllComponents();

	public boolean containsComponent(Component component);

	public void attachComponents(Collection<? extends Component> attachments);

	public void detatchComponents(Collection<? extends Component> attachments);

	public ComponentsFactory getComponentsFactory();

	public void print();

	Collection<Component> listChildren();

	public ComponentsList<Layer> listChildLayers();

	public ComponentsList<VisibleComponent> listVisibleComponents();

}
