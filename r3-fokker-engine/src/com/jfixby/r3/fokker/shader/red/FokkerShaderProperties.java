
package com.jfixby.r3.fokker.shader.red;

import com.jfixby.r3.engine.api.render.ShaderParameter;
import com.jfixby.r3.engine.api.render.ShaderProperties;
import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderInfo;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderParameterInfo;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Map;
import com.jfixby.scarabei.api.collections.Mapping;

public class FokkerShaderProperties implements ShaderProperties {
	private final Map<String, ShaderParameter> parameters_by_name = Collections.newMap();

	public FokkerShaderProperties (final ShaderEntry redFokkerShader) {
	}

	public void set (final ShaderInfo info) {
		for (final ShaderParameterInfo param : info.parameters_list) {
			final String key = param.name;
			final ShaderParameter value = new RedShaderParameter(param);
			this.parameters_by_name.put(key, value);
		}
	}

	@Override
	public Mapping<String, ShaderParameter> listParameters () {
		return this.parameters_by_name;
	}

	@Override
	public ShaderSettings newShaderSettings () {
		final RedShaderSettings settings = new RedShaderSettings();
		for (final String paramname : this.parameters_by_name.keys()) {
			final ShaderParameter param = this.parameters_by_name.get(paramname);
			if (param.getType().equals(param.TYPE_FLOAT())) {
				final float def = param.getDefaultValue();
				settings.setFloatParameterValue(paramname, def);
			}
			if (param.getType().equals(param.TYPE_INT())) {
				final int def = param.getDefaultValue();
				settings.setIntParameterValue(paramname, def);
			}

		}
		return settings;
	}

}
