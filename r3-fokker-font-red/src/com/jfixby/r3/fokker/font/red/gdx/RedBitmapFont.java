
package com.jfixby.r3.fokker.font.red.gdx;

import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.jfixby.r3.fokker.io.ToGdxFileAdaptor;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.file.File;

public class RedBitmapFont {

	final com.badlogic.gdx.graphics.g2d.BitmapFont gdx_bitmap_font;
	private final File file;
	private final GdxR3FontCache cache;
	private final int size;
// private final float reScale;
// private final float scale;
	private final int borderSize;
	private Color borderColor;
	private boolean isDisposed = false;

	private com.badlogic.gdx.graphics.Color toGDXColor (final Color color) {
		final com.badlogic.gdx.graphics.Color gdx_color = new com.badlogic.gdx.graphics.Color(color.red(), color.green(),
			color.blue(), color.alpha());
		return gdx_color;
	}

	public RedBitmapFont (final File file, final GdxR3FontParameters params) {
		this.file = file;

		this.size = params.getSize();
		this.color = params.getColor();
		this.borderSize = params.getBorderSize();
		this.borderColor = params.getBorderColor();
		if (this.borderColor == null) {
			this.borderColor = this.color;
		}
// this.reScale = params.getReScaleValue();
// this.scale = params.getScaleValue();

		final ToGdxFileAdaptor gdx_font_file = new ToGdxFileAdaptor(file);
		final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(gdx_font_file);
		FreeTypeFontGenerator.setMaxTextureSize(1024);

		final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = params.getSize();
		parameter.flip = true;
		parameter.magFilter = TextureFilter.Linear;
		parameter.minFilter = TextureFilter.Linear;
		parameter.color = this.toGDXColor(this.color);
		parameter.shadowColor = this.toGDXColor(this.color);
		if (this.borderSize > 0) {
			parameter.borderColor = this.toGDXColor(this.borderColor);
		}
		parameter.borderWidth = this.borderSize;
		// parameter.incremental = true;
		parameter.characters = params.getCharacters();// FreeTypeFontGenerator.DEFAULT_CHARS;
		if (parameter.characters == null || parameter.characters.equals("")) {
			parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
		}
		// + CHARS_LIST.cyrillic;
		this.gdx_bitmap_font = generator.generateFont(parameter); // font
		Debug.checkNull("gdx_bitmap_font", this.gdx_bitmap_font);
		// size
		// 12
		// pixels
		generator.dispose(); // don't forget to dispose to avoid memory
		// leaks!

		this.cache = new GdxR3FontCache(this);

	}

// @Override
// public FontCache getFontCache () {
// return this.cache;
// }

	public void dispose () {
		this.checkDisposed();
		this.isDisposed = true;
		this.cache.dispose();
		this.gdx_bitmap_font.dispose();

	}

	final private void checkDisposed () {
		if (this.isDisposed) {
			Err.reportError("Font is disposed " + this);
		}
	}

	int MAX = 128;
	private final Color color;

// private Texture texture;

// public float getReScaleValue () {
// this.checkDisposed();
// return this.reScale;
// }
//
// public float getScaleValue () {
// this.checkDisposed();
// return this.scale;
// }

// final GDXTextureContainer container = new GDXTextureContainer() {
// @Override
// public Texture getTexture () {
// RedBitmapFont.this.checkDisposed();
// return RedBitmapFont.this.texture;
// }
//
// };

// public StringBounds getStringBounds (final String string_value) {
//
// final com.badlogic.gdx.graphics.g2d.BitmapFont gdx_bitmap_font = this.getGdxBitmapFont();
// final com.badlogic.gdx.graphics.g2d.BitmapFontCache gdx_font_cache = gdx_bitmap_font.getCache();
// gdx_font_cache.clear();
// gdx_font_cache.addText(string_value, 0, 0);
// final Array<TextureRegion> regions = gdx_bitmap_font.getRegions();
// final int n = regions.size;
// final RedStringBounds result = new RedStringBounds();
// for (int region = 0; region < n; region++) {
// final int spriteSize = gdx_font_cache.getVertexCount(region);
// if (spriteSize > 0) {
// final float[] spriteVertices = gdx_font_cache.getVertices(region);
// final int number_of_sprites = spriteVertices.length / SpritesRenderer.SPRITE_SIZE;
// for (int k = 0; k < number_of_sprites; k++) {
// result.addVertex(this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_x)],
// this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_y)]);
// result.addVertex(this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_x)],
// this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_y)]);
// result.addVertex(this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_x)],
// this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_y)]);
// result.addVertex(this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_x)],
// this.reScale * this.scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_y)]);
// }
// }
// }
//
// return result;
//
// }

	public com.badlogic.gdx.graphics.g2d.BitmapFont getGdxFont () {
		this.checkDisposed();
		return this.gdx_bitmap_font;
	}

	public GdxR3FontCache getCache () {
		return this.cache;
	}

}
