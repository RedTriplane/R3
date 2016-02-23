package com.jfixby.r3.api.ui.unit.shader;

public interface Shader {

	public void hide();

	public void show();

	public boolean isVisible();

	public void setName(String name);

	public String getName();

	void setVisible(boolean b);

	void setParameter(String name, String value);

	public String getParameter(String name);

}
