package com.jfixby.r3.api.ui.unit.shader;

public interface ShaderFactory {
    public ShaderSpecs newShaderSpecs();

    public ShaderComponent newShader(ShaderSpecs specs);

}
