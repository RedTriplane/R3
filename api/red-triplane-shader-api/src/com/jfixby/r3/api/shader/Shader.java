package com.jfixby.r3.api.shader;

import com.jfixby.cmns.api.collections.Mapping;

public interface Shader {

	void setFloatParameterValue(String name, double value);

	public double getFloatParameterValue(String name);

	Mapping<String, ShaderParameter> listParameters();

	

}
