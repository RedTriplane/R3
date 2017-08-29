
package com.jfixby.r3.fokker.render.raster;

import com.badlogic.gdx.graphics.Texture;
import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.r3.engine.api.render.ShaderSettings;
import com.jfixby.r3.engine.api.render.TEXTURE_BLEND_MODE;
import com.jfixby.r3.fokker.render.FokkerDefaultAssets;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.render.RenderBuffer;
import com.jfixby.r3.fokker.render.geo.Renderer;
import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.r3.fokker.shader.api.FokkerShaders;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.StateSwitcher;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.names.ID;

public class FokkerRasterRenderer extends Renderer {

	private FokkerSpritesRenderer sprites_renderer;
	private RenderBuffer primary_buffer;
	private SecondaryRenderBuffer secondary_buffer;
	// private Matrix4 combined;
	private StateSwitcher<TEXTURE_BLEND_MODE> blend_state;

	final CurrentShaderContainer current_shader = new CurrentShaderContainer();
	private TEXTURE_BLEND_MODE mode;
	private double current_opacity = 1f;

	// private static StateSwitcher<RASTER_MODE> raster_mode;

	final public void init (final FokkerRenderMachine fokkerRenderMachine, final RenderBuffer primary_buffer) {
		super.init(fokkerRenderMachine);
		this.sprites_renderer = new FokkerSpritesRenderer(this);
		GdxRender.activateRasterRenderer();
		this.primary_buffer = primary_buffer;

		this.blend_state = Debug.newStateSwitcher(TEXTURE_BLEND_MODE.Normal);
		this.current_shader.init();
	}

	@Override
	public void onFrameBegin () {
		// blend_state.expectState(TEXTURE_BLEND_MODE.NOT_SET);
		this.blend_state.switchState(TEXTURE_BLEND_MODE.Normal);
		GdxRender.setShader(null);
	}

	@Override
	final public void doOpen () {
		GdxRender.openRasterRenderer(this.getGdxCamera().combined);
	}

	boolean shaderIsOverlay = false;

	public void open (final TEXTURE_BLEND_MODE blend_mode, final double opacity, final FokkerShader shader,
		final ShaderSettings params) {
		this.mode = blend_mode;
		this.current_opacity = opacity;
		if (blend_mode == TEXTURE_BLEND_MODE.Normal) {
			GdxRender.setShader(null);
		} else {
			this.shaderIsOverlay = shader.isOverlay();
			if (this.shaderIsOverlay) {
				this.loadOverlayShader(blend_mode, opacity, shader, params);
			} else {
				this.loadTextureShader(blend_mode, opacity, shader, params);
			}
		}
		super.open();
	}

	public void close (final TEXTURE_BLEND_MODE blend_mode, final FokkerShader shader) {
		this.current_opacity = 1;
		if (this.mode != blend_mode) {
			Err.reportError("Unexpected TEXTURE_BLEND_MODE: " + blend_mode);
		}
		super.close();
		if (blend_mode == TEXTURE_BLEND_MODE.Normal) {
			GdxRender.setShader(null);
		} else {
			if (this.shaderIsOverlay) {
				this.unloadOverlayShader(blend_mode);
			} else {
				this.unloadTextureShader(blend_mode);
			}
		}
	}

	@Override
	final public void doClose () {
		// blend_state.doesNotExpectState(TEXTURE_BLEND_MODE.NOT_SET);
		GdxRender.closeRasterRenderer();
	}

	@Override
	public void onFrameEnd () {
		// blend_state.doesNotExpectState(TEXTURE_BLEND_MODE.NOT_SET);
		// blend_state.switchState(TEXTURE_BLEND_MODE.NOT_SET);
		GdxRender.setShader(null);
		this.blend_state.switchState(TEXTURE_BLEND_MODE.Normal);
	}

	// @Override
	// final public void setGdxProjectionMatrix(final Matrix4 combined) {
	// GdxRender.setRasterRendererProjectionMatrix(combined);
	// this.combined = combined;
	// }

	public final void drawSprite (final ID spriteAssetID, final Rectangle shape) {
		final Texture blend_texture = this.current_shader.getBlendTexture();
		this.sprites_renderer.drawSprite(spriteAssetID, shape, this.current_opacity, blend_texture);
	}

	public final void drawAperture (final ID spriteAssetID, final double ax, final double ay, final double bx, final double by) {
		this.sprites_renderer.drawAperture(spriteAssetID, ax, ay, bx, by, this.current_opacity);
	}

	public final void drawString (final ID fontID, final FontParameters fontParams, final String stringValue,
		final CanvasPosition position) {
		// blend_state.doesNotExpectState(TEXTURE_BLEND_MODE.NOT_SET);
		final Texture blend_texture = this.current_shader.getBlendTexture();
		this.sprites_renderer.drawString(fontID, fontParams, stringValue, position, this.current_opacity, blend_texture);
// sprites_renderer.drawString(string_value, position, opacity, blend_texture);
	}

	final void loadOverlayShader (final TEXTURE_BLEND_MODE next_blend_mode, final double opacity, final FokkerShader customShader,
		final ShaderSettings params) {
		if (this.secondary_buffer == null) {
			this.secondary_buffer = new SecondaryRenderBuffer();
		}

		this.primary_buffer.pause();

		//
		this.secondary_buffer.begin(this.getGdxCamera().combined);
		final Texture tmp_texture = this.primary_buffer.getResult();
		this.secondary_buffer.saveTexture(tmp_texture);
		this.secondary_buffer.end();
		this.primary_buffer.resume();

		final FokkerShader shader = this.shaderFor(next_blend_mode, customShader);
		final Texture blend_texture = this.secondary_buffer.getResult();
		this.current_shader.setShader(next_blend_mode, shader, blend_texture);

		// texture.bind(1);

		this.current_shader.activateShader(opacity, params);

	}

	private void unloadOverlayShader (final TEXTURE_BLEND_MODE blend_mode) {
		this.current_shader.deactivateShader();
	}

	final void loadTextureShader (final TEXTURE_BLEND_MODE next_blend_mode, final double opacity, final FokkerShader customShader,
		final ShaderSettings params) {
		final FokkerShader shader = this.shaderFor(next_blend_mode, customShader);
		this.current_shader.setShader(next_blend_mode, shader, null);

		// texture.bind(1);

		this.current_shader.activateShader(opacity, params);
	}

	private void unloadTextureShader (final TEXTURE_BLEND_MODE blend_mode) {
		this.current_shader.deactivateShader();
	}

	private FokkerShader shaderFor (final TEXTURE_BLEND_MODE blend_mode, final FokkerShader customShader) {
		if (blend_mode == null) {
			return customShader;
		}
		if (blend_mode == TEXTURE_BLEND_MODE.Normal) {
			Err.reportError("Wrong TEXTURE_BLEND_MODE = " + blend_mode);
		}
		if (blend_mode == TEXTURE_BLEND_MODE.TEST) {
			return FokkerShaders.obtain(this.machine.DefaultAssets().SHADER_TEST);
		}
		if (blend_mode == TEXTURE_BLEND_MODE.Normal) {
			return FokkerShaders.obtain(this.machine.DefaultAssets().SHADER_NORMAL);
		}
		if (blend_mode == TEXTURE_BLEND_MODE.Multiply) {
			return FokkerShaders.obtain(this.machine.DefaultAssets().SHADER_MULTIPLY);
		}
		if (blend_mode == TEXTURE_BLEND_MODE.Grayscale) {
			return FokkerShaders.obtain(this.machine.DefaultAssets().SHADER_GRAYSCALE);
		}
		Err.reportError("Unknown TEXTURE_BLEND_MODE=" + blend_mode);
		return null;
	}

	public FokkerDefaultAssets DefaultAssets () {
		return this.machine.DefaultAssets();
	}

}
