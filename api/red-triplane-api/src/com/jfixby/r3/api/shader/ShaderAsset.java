
package com.jfixby.r3.api.shader;

import com.jfixby.rana.api.asset.Asset;
import com.jfixby.scarabei.api.collections.Mapping;

public interface ShaderAsset extends Asset {

	public VertexProgram getVertexProgram ();

	public FragmentProgram getFragmentProgram ();

	public Mapping<String, ShaderParameter> listParameters ();

	public boolean isOverlay ();

}
