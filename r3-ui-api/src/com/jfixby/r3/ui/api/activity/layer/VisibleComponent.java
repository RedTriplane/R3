package com.jfixby.r3.ui.api.activity.layer;

public interface VisibleComponent extends NamedComponent  {
	// DrawableComponent + Layers
	public void hide();

	public void show();

	public boolean isVisible();

	

	void setVisible(boolean b);

}
