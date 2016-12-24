package com.jfixby.r3.api.shader;

import com.jfixby.scarabei.api.collections.Mapping;

public interface Shader {

	void setFloatParameterValue(String name, double value);

	public double getFloatParameterValue(String name);

	Mapping<String, ShaderParameter> listParameters();

	void setIntParameterValue(String parameter_name, long value);

	long getIntParameterValue(String parameter_name);

	

	void setupValues();

}
