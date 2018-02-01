
package com.jfixby.r3.fokker.shader.red;

import com.jfixby.r3.engine.api.render.ShaderParameter;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderParameterInfo;

public class RedShaderParameter implements ShaderParameter {

	private final String name;
	private final String type;
	private final Object defaultValue;

	public RedShaderParameter (final ShaderParameterInfo param) {
		this.name = param.name;
		this.type = param.type;
		this.defaultValue = param.defaultValue;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public String getType () {
		return this.type;
	}

	@Override
	public String TYPE_FLOAT () {
		return ShaderParameterInfo.TYPE_FLOAT;
	}

	@Override
	public String TYPE_INT () {
		return ShaderParameterInfo.TYPE_INT;
	}

	@Override
	public <T> T getDefaultValue () {
		return (T)this.defaultValue;
	}

}
