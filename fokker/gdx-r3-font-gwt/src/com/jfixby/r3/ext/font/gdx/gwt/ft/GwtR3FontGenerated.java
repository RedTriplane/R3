package com.jfixby.r3.ext.font.gdx.gwt.ft;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.jfixby.cmns.adopted.gdx.fs.ToGdxFileAdaptor;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.raster.BLEND_MODE;
import com.jfixby.r3.api.ui.unit.txt.StringBounds;
import com.jfixby.r3.ext.api.font.BitmapFont;
import com.jfixby.r3.ext.api.font.BitmapFontRenderer;
import com.jfixby.r3.ext.api.font.FontCache;
import com.jfixby.r3.ext.api.font.FontParameters;
import com.jfixby.red.engine.core.unit.text.RedStringBounds;
import com.jfixby.redtriplane.fokker.render.raster.GDXTextureContainer;

public class GwtR3FontGenerated implements BitmapFont {
	final static private int spriteVertex(int sprite_number, int vertice_name) {
		return vertice_name + sprite_number * SPRITE_SIZE;
	}

	static final int VERTEX_SIZE = 2 + 1 + 2;
	static final int SPRITE_SIZE = 4 * VERTEX_SIZE;

	final static int A_x = 0;
	final static int A_y = 1;
	final static int B_x = 15;
	final static int B_y = 16;
	final static int C_x = 10;
	final static int C_y = 11;
	final static int D_x = 5;
	final static int D_y = 6;

	private File file;
	private FontCache cache;
	private int size;
	private float reScale;
	private float scale;

	private com.badlogic.gdx.graphics.Color toGDXColor(Color color) {
		com.badlogic.gdx.graphics.Color gdx_color = new com.badlogic.gdx.graphics.Color(color.red(), color.green(),
				color.blue(), color.alpha());
		return gdx_color;
	}

	public GwtR3FontGenerated(File file, FontParameters params) {
		this.file = file;

		this.size = params.getSize();
		this.color = params.getColor();
		this.reScale = params.getReScaleValue();
		this.scale = params.getScaleValue();

		this.cache = new GwtR3FontCache(this);

		ToGdxFileAdaptor gdx_font_file = new ToGdxFileAdaptor(file);

	}

	@Override
	public FontCache getFontCache() {
		return cache;
	}

	@Override
	public void dispose() {
	}

	int MAX = 128;
	private Color color;

	private Texture texture;

	public float getReScaleValue() {
		return reScale;
	}

	public float getScaleValue() {
		return this.scale;
	}


	final GDXTextureContainer container = new GDXTextureContainer() {
		@Override
		public Texture getTexture() {
			return texture;
		}
	};

	public StringBounds getStringBounds(String string_value) {
		RedStringBounds result = new RedStringBounds();
		return result;
	}

	@Override
	public void renderBitmapFont(BitmapFontRenderer bitmapFontRenderer, CanvasPosition position, String string_value,
			CameraProjection projection, BLEND_MODE mode, Object blend_texture) {
	}

	
	
	

}
