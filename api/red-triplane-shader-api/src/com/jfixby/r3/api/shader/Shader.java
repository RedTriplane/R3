package com.jfixby.r3.api.shader;

import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.r3.api.shader.ShaderParameter;

public interface Shader {

	public void hide();

	public void show();

	public boolean isVisible();

	public void setName(String name);

	public String getName();

	void setVisible(boolean b);

	void setFloatParameterValue(String name, double value);

	public double getFloatParameterValue(String name);

	Mapping<String, ShaderParameter> listParameters();

}
