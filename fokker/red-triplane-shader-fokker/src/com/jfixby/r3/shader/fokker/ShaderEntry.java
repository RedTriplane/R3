package com.jfixby.r3.shader.fokker;

import java.io.IOException;

import com.jfixby.cmns.api.assets.AssetID;
import com.jfixby.cmns.api.file.File;
import com.jfixby.r3.api.shader.FragmentProgram;
import com.jfixby.r3.api.shader.R3_SHADER_SETTINGS;
import com.jfixby.r3.api.shader.ShaderAsset;
import com.jfixby.r3.api.shader.ShaderInfo;
import com.jfixby.r3.api.shader.ShadersContainer;
import com.jfixby.r3.api.shader.VertexProgram;
import com.jfixby.rana.api.asset.Asset;

public class ShaderEntry implements Asset, ShaderAsset {
	AssetID asset_id;
	ShaderInfo shader;
	File shader_folder;
	ShadersContainer container;
	private File frag_file;
	private File vertex_file;
	private RedShaderProgram vertex_program;
	private RedShaderProgram fragment_program;

	public ShaderEntry(AssetID asset_id, ShaderInfo shader, File shader_folder, ShadersContainer container)
			throws IOException {
		super();
		this.asset_id = asset_id;
		this.shader = shader;
		this.shader_folder = shader_folder;
		this.container = container;

		frag_file = shader_folder.child(R3_SHADER_SETTINGS.FRAG_FILE_NAME);
		vertex_file = shader_folder.child(R3_SHADER_SETTINGS.VERT_FILE_NAME);

		vertex_program = new RedShaderProgram(vertex_file);
		fragment_program = new RedShaderProgram(frag_file);
	}

	@Override
	public AssetID getAssetID() {
		return asset_id;
	}

	@Override
	public VertexProgram getVertexProgram() {
		return vertex_program;
	}

	@Override
	public FragmentProgram getFragmentProgram() {
		return fragment_program;
	}

}
