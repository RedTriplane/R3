
package com.jfixby.r3.fokker.shader.red;

import java.io.IOException;

import com.jfixby.r3.engine.api.render.Shader;
import com.jfixby.r3.engine.api.render.ShaderProperties;
import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.r3.fokker.assets.api.shader.io.R3_SHADER_SETTINGS;
import com.jfixby.r3.fokker.assets.api.shader.io.ShaderInfo;
import com.jfixby.r3.fokker.assets.api.shader.io.ShadersContainer;
import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.r3.rana.api.Asset;
import com.jfixby.r3.rana.api.AssetsGroup;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.java.LegacyFloat;
import com.jfixby.scarabei.api.java.LegacyInt;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.names.ID;

public class ShaderEntry implements Asset, FokkerShader, Shader {
	ID asset_id;
	ShaderInfo shaderInfo;
	File shader_folder;
	ShadersContainer container;
	private final File frag_file;
	private final File vertex_file;
	private final RedShaderProgram vertex_program;
	private final RedShaderProgram fragment_program;
	private final FokkerShaderProperties properties = new FokkerShaderProperties(this);

	private final com.badlogic.gdx.graphics.glutils.ShaderProgram gdx_shader_program;
	private final ShadersGroup shadersGroup;

	public ShaderEntry (final ID asset_id, final ShaderInfo shaderInfo, final File shader_folder, final ShadersContainer container,
		final ShadersGroup shadersGroup) throws IOException {
		super();
		this.asset_id = Debug.checkNull("asset_id", asset_id);
		this.shaderInfo = shaderInfo;
		this.shadersGroup = shadersGroup;
		this.shader_folder = shader_folder;
		this.container = container;
		this.properties.set(shaderInfo);

		this.frag_file = shader_folder.child(R3_SHADER_SETTINGS.FRAG_FILE_NAME);
		this.vertex_file = shader_folder.child(R3_SHADER_SETTINGS.VERT_FILE_NAME);

		com.badlogic.gdx.graphics.glutils.ShaderProgram.pedantic = false;

		this.vertex_program = new RedShaderProgram(this.vertex_file);
		this.fragment_program = new RedShaderProgram(this.frag_file);

		this.gdx_shader_program = new com.badlogic.gdx.graphics.glutils.ShaderProgram(this.vertex_program.getSourceCode(),
			this.fragment_program.getSourceCode());
		if (!this.gdx_shader_program.isCompiled()) {
			throw new IllegalArgumentException("Error compiling shader: " + this.gdx_shader_program.getLog());
		}
	}

	@Override
	public ID getAssetID () {
		return this.asset_id;
	}

	@Override
	public AssetsGroup getGroup () {
		return this.shadersGroup;
	}

	@Override
	public boolean isOverlay () {
		return this.shaderInfo.isOverlay;
	}

	final private void setFloatParam (final String param_name, final float value) {
		try {
			// L.d("set F " + param_name, value);
			this.gdx_shader_program.setUniformf(param_name, value);
		} catch (final Throwable e) {
			L.e(this.fragment_program.getSourceCode());
			L.e(this.vertex_program.getSourceCode());
			e.printStackTrace();
			Err.reportError("Failed to set parameter " + param_name + "=" + value);

		}
	}

	final private void setIntParam (final String param_name, final int value) {
		try {
			// L.d("set I " + param_name, value);
			this.gdx_shader_program.setUniformi(param_name, value);
		} catch (final Throwable e) {
			L.e(this.fragment_program.getSourceCode());
			L.e(this.vertex_program.getSourceCode());
			e.printStackTrace();
			Err.reportError("Failed to set parameter " + param_name + "=" + value);

		}
	}

	public void dispose () {
		this.gdx_shader_program.dispose();
	}

	@Override
	public com.badlogic.gdx.graphics.glutils.ShaderProgram getGdxShaderProgram () {
		return this.gdx_shader_program;
	}

	@Override
	public void applySettings (final ShaderSettings params) {
		if (this.gdx_shader_program == null) {
			return;
		}
		if (params.hasParams()) {

			this.gdx_shader_program.begin();
			// float_param_values.print("float_param_values");
			for (int i = 0; i < params.floatParams().size(); i++) {
				final LegacyFloat value = params.floatParams().getValueAt(i);
				final String param_name = params.floatParams().getKeyAt(i);
				this.setFloatParam(param_name, value.value);
			}

			for (int i = 0; i < params.intParams().size(); i++) {
				final LegacyInt value = params.intParams().getValueAt(i);
				final String param_name = params.intParams().getKeyAt(i);
				this.setIntParam(param_name, value.value);
			}
			this.gdx_shader_program.end();
		}
	}

	@Override
	public void setOpacity (final double opacity) {
		Err.throwNotImplementedYet();
	}

	@Override
	public ShaderProperties properties () {
		return this.properties;
	}

	@Override
	public ID id () {
		return this.asset_id;
	}

}
