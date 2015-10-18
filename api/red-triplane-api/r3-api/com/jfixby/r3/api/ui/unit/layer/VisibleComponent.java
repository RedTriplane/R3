package com.jfixby.r3.api.ui.unit.layer;

public interface VisibleComponent extends Component {
	// DrawableComponent + Layers
	public void hide();

	public void show();

	public boolean isVisible();

	public void setName(String name);

	public String getName();

	void setVisible(boolean b);

}
