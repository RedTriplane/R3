package com.jfixby.r3.ext.api.scene2d.srlz;

public class ShaderParameter {

	public ShaderParameter(String name, String value, ShaderParameterType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public ShaderParameter() {
	}

	public String name;
	public String value;
	public ShaderParameterType type;

}
