
package com.jfixby.r3.engine.api.render;

import com.jfixby.scarabei.api.collections.Mapping;

public interface ShaderProperties {

	Mapping<String, ShaderParameter> listParameters ();

	ShaderSettings newShaderSettings ();

}
