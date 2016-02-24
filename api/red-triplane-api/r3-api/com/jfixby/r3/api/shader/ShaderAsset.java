package com.jfixby.r3.api.shader;

import com.jfixby.cmns.api.collections.Mapping;
import com.jfixby.rana.api.asset.Asset;

public interface ShaderAsset extends Asset {

	public VertexProgram getVertexProgram();

	public FragmentProgram getFragmentProgram();

	public Mapping<String, ShaderParameter> listParameters();

}
