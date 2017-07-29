
package com.jfixby.r3.fokker.render.shader;

import com.jfixby.r3.engine.api.render.DefaultShaders;
import com.jfixby.r3.engine.api.render.Shader;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.r3.fokker.shader.api.ShaderAsset;
import com.jfixby.r3.rana.api.asset.AssetHandler;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.assets.ID;
import com.jfixby.scarabei.api.err.Err;

public class FokkerDefaultShaders implements DefaultShaders, AssetsConsumer {

	private final FokkerRenderMachine fokkerRenderMachine;

	public FokkerDefaultShaders (final FokkerRenderMachine fokkerRenderMachine) {
		this.fokkerRenderMachine = fokkerRenderMachine;
	}

	static Shader TEST = null;

	@Override
	public Shader TEST () {
		TEST = resolve(TEST, this.fokkerRenderMachine.DefaultAssets().SHADER_TEST, this);
		return TEST;
	}

	static Shader NORMAL = null;

	@Override
	public Shader NORMAL () {
		NORMAL = resolve(NORMAL, this.fokkerRenderMachine.DefaultAssets().SHADER_NORMAL, this);
		return NORMAL;
	}

	static Shader MULTIPLY = null;

	@Override
	public Shader MULTIPLY () {
		MULTIPLY = resolve(MULTIPLY, this.fokkerRenderMachine.DefaultAssets().SHADER_MULTIPLY, this);
		return MULTIPLY;
	}

	static Shader GRAYSCALE = null;

	@Override
	public Shader GRAYSCALE () {
		GRAYSCALE = resolve(GRAYSCALE, this.fokkerRenderMachine.DefaultAssets().SHADER_GRAYSCALE, this);
		return GRAYSCALE;
	}

	private static final Shader resolve (final Shader shader, final ID name, final AssetsConsumer consumer) {
		if (shader != null) {
			return shader;
		}
		final AssetHandler asset_handler = LoadedAssets.obtainAsset(name, consumer);
		if (asset_handler == null) {
			Err.reportError("Asset<" + name + "> not found.");
		}
		final ShaderAsset asset = (ShaderAsset)asset_handler.asset();
// return new RedFokkerShader(asset_handler.ID(), asset, consumer);
		Err.throwNotImplementedYet();
		return null;
	}

	static Shader GDX_DEFAULT = null;

	public Shader GDX_DEFAULT () {
		GDX_DEFAULT = resolve(GDX_DEFAULT, this.fokkerRenderMachine.DefaultAssets().SHADER_GDX_DEFAULT, this);
		return GDX_DEFAULT;
	}

}
