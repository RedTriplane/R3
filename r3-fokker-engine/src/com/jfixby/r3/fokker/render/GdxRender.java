
package com.jfixby.r3.fokker.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Matrix4;
import com.jfixby.r3.fokker.render.raster.FokkerRasterRenderer;
import com.jfixby.r3.fokker.shader.api.FokkerShader;
import com.jfixby.r3.fokker.shader.api.FokkerShaders;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.err.Err;

public class GdxRender {

	static final boolean DRAW_SHAPES = true;
	// static final boolean APPLY_COMPLEX_SHADERS_SHAPES = false;

	static com.badlogic.gdx.graphics.glutils.ShapeRenderer gdx_shape_renderer;
	static FokkerRenderDevice shapes_render_device;
	private static com.badlogic.gdx.graphics.g2d.SpriteBatch gdx_sprite_batch;

	private static FokkerRasterRenderer shadersProvider;

	// private static RenderBuffer frame_buffer;

	public static void init (final FokkerRasterRenderer raster_renderer) {
		shadersProvider = raster_renderer;
		if (gdx_sprite_batch == null) {

			final FokkerShader defaultShader = FokkerShaders.obtain(raster_renderer.DefaultAssets().SHADER_GDX_DEFAULT);
			final ShaderProgram gdxDefaultShader = defaultShader.getGdxShaderProgram();
			gdx_sprite_batch = new com.badlogic.gdx.graphics.g2d.SpriteBatch(1000, gdxDefaultShader);
		}
		// frame_buffer = new RenderBuffer();
		// frame_buffer.updateScreen();
	}

	public static void setShader (final ShaderProgram shader) {

		gdx_sprite_batch.setShader(shader);
	}

	final public static void openRasterRenderer (final Matrix4 combined) {

		Gdx.gl.glEnable(GL20.GL_BLEND);
		gdx_sprite_batch.enableBlending();
		gdx_sprite_batch.setBlendFunction(-1, -1);
		Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
		gdx_sprite_batch.begin();
		gdx_sprite_batch.setProjectionMatrix(combined);

	}

	final public static void openShapesRenderer (final Matrix4 combined) {
		if (DRAW_SHAPES) {
			Gdx.gl.glEnable(GL20.GL_BLEND);
			Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);
			gdx_shape_renderer.setProjectionMatrix(combined);
		} else {
			Err.reportError("");
		}
	}

	public static void primaryBufferTextureFlush (final Texture texture, final int w, final int h) {
		gdx_sprite_batch.begin();
		gdx_sprite_batch.enableBlending();
		gdx_sprite_batch.setBlendFunction(-1, -1);
// Gdx.gl20.glBlendFuncSeparate(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_DST_ALPHA);
		Gdx.gl20.glBlendFuncSeparate(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA, GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);

// gdx_sprite_batch.setBlendFunction(GL20.GL_ONE, GL20.GL_ONE_MINUS_SRC_ALPHA);// PMA
// gdx_sprite_batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);// render sprites
		gdx_sprite_batch.draw(texture, 0, 0, w, h);
		gdx_sprite_batch.end();
	}

	final public static void clearScreen (final Color background_color) {

		CLEAR_R = background_color.red();
		CLEAR_G = background_color.green();
		CLEAR_B = background_color.blue();
		CLEAR_A = background_color.alpha();

		Gdx.graphics.getGL20().glClearColor(CLEAR_R, CLEAR_G, CLEAR_B, CLEAR_A);
		Gdx.graphics.getGL20().glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

// Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	public static float CLEAR_R = 0;
	public static float CLEAR_G = 0;
	public static float CLEAR_B = 0;
	public static float CLEAR_A = 1;

	// private static void log(String string) {
	// // L.d("GdxRender", string);
	// }

	final public static void closeShapesRenderer () {
		if (DRAW_SHAPES) {
			// log("closeShapesRenderer");
			Gdx.gl.glDisable(GL20.GL_BLEND);
		}
	}

	final public static void activateShapesRenderer () {
		if (DRAW_SHAPES) {
			// log("activateShapesRenderer");
			gdx_shape_renderer = new com.badlogic.gdx.graphics.glutils.ShapeRenderer();
			shapes_render_device = new FokkerRenderDevice(gdx_shape_renderer);
		}
	}

	final public static void setShapeRendererProjectionMatrix (final Matrix4 combined) {
		if (DRAW_SHAPES) {
			// log("setShapeRendererProjectionMatrix");
			gdx_shape_renderer.setProjectionMatrix(combined);
		}
	}

	final public static void setShapeRendererColor (final float R, final float G, final float B, final float A) {
		if (DRAW_SHAPES) {
			// log("setShapeRendererColor");
			gdx_shape_renderer.setColor(R, G, B, A);
		}
	}

	final public static void activateRasterRenderer () {
	}

	final public static Matrix4 closeRasterRenderer () {
		final Matrix4 matrix = gdx_sprite_batch.getProjectionMatrix();
		gdx_sprite_batch.end();
		return matrix;
	}

	final public static void setRasterRendererProjectionMatrix (final Matrix4 combined) {
		// log("setRasterRendererProjectionMatrix");
		gdx_sprite_batch.setProjectionMatrix(combined);
	}

	final public static void shapeRenderEnd () {
		if (DRAW_SHAPES) {
			// log("shapeRenderEnd");
			gdx_shape_renderer.end();
		}
	}

	final public static void shapeRenderBegin (final ShapeType type) {
		if (DRAW_SHAPES) {
			// log("shapeRenderBegin");
			gdx_shape_renderer.begin(type);
		}
	}

	final public static void shapeRenderLine (final float x1, final float y1, final float x2, final float y2) {
		if (DRAW_SHAPES) {
			// log("shapeRenderLine");
			gdx_shape_renderer.line(x1, y1, x2, y2);
		}
	}

	final public static void shapeRenderTriangle (final float x1, final float y1, final float x2, final float y2, final float x3,
		final float y3) {
		if (DRAW_SHAPES) {
			// log("shapeRenderTriangle");
			gdx_shape_renderer.triangle(x1, y1, x2, y2, x3, y3);
		}
	}

	final public static void rasterDraw (final Texture texture, final float[] spriteVertices, final int i, final int spriteSize,
		final Texture blend_texture, final Texture alpha) {
		// log("rasterDraw");
		if (texture == null) {
			return;
		}
		if (alpha != null) {
			alpha.bind(2);
		}
		if (blend_texture != null) {
			blend_texture.bind(1);
		}
		{
			texture.bind(0);
			gdx_sprite_batch.draw(texture, spriteVertices, 0, spriteSize);
		}
	}

	public static void secondaryBufferTextureFlush (final Texture texture) {
		gdx_sprite_batch.draw(texture, 0, 0);

	}

	// public static void drawFokkerShapesRenderable(
	// FokkerShapesRenderable self_renderable) {
	// self_renderable.doDraw(shapes_render_device);
	// }

}
