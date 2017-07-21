package com.jfixby.r3.io.scene2d;

public class ShaderParameterValue implements java.io.Serializable{

	public ShaderParameterValue(String name, String value, ShaderParameterType type) {
		this.name = name;
		this.value = value;
		this.type = type;
	}

	public ShaderParameterValue() {
	}

	public String name;
	public String value;
	public ShaderParameterType type;

}
