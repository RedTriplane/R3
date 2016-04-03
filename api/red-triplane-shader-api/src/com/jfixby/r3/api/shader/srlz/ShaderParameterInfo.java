package com.jfixby.r3.api.shader.srlz;

import java.io.Serializable;

public class ShaderParameterInfo implements Serializable {
    public String name;
    public String type;

    public ShaderParameterInfo(String name, String type) {
	this.name = name;
	this.type = type;
    }

    public ShaderParameterInfo() {
    }

}
