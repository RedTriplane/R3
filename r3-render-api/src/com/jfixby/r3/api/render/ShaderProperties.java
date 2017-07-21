
package com.jfixby.r3.api.render;

import com.jfixby.scarabei.api.collections.Mapping;

public interface ShaderProperties {

	Mapping<String, ShaderParameter> listParameters ();

	ShaderSettings newShaderSettings ();

}
