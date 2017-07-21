
package com.jfixby.r3.activity.red.shader;

import com.jfixby.r3.activity.api.ComponentsFactory;
import com.jfixby.r3.activity.api.shader.Shader;
import com.jfixby.r3.activity.api.shader.ShaderFactory;
import com.jfixby.r3.activity.api.shader.ShaderSpecs;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.scarabei.api.err.Err;

public class RedShadersFactory implements ShaderFactory {

	private final RedComponentsFactory parent;

	public RedShadersFactory (final RedComponentsFactory redComponentsFactory) {
		this.parent = redComponentsFactory;
	}

	@Override
	public ComponentsFactory parent () {
		return this.parent;
	}

	@Override
	public ShaderSpecs newShaderSpecs () {
		return new ShaderSpecs();
	}

	@Override
	public Shader newShader (final ShaderSpecs specs) {
		Err.throwNotImplementedYet();
		return null;
	}

}
