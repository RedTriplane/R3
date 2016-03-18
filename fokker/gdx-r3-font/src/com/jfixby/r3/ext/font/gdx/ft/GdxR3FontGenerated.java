package com.jfixby.r3.ext.font.gdx.ft;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.jfixby.cmns.adopted.gdx.fs.ToGdxFileAdaptor;
import com.jfixby.cmns.api.color.Color;
import com.jfixby.cmns.api.file.File;
import com.jfixby.cmns.api.floatn.Float2;
import com.jfixby.cmns.api.geometry.CanvasPosition;
import com.jfixby.cmns.api.geometry.Geometry;
import com.jfixby.r3.api.ui.unit.camera.CameraProjection;
import com.jfixby.r3.api.ui.unit.txt.StringBounds;
import com.jfixby.r3.ext.api.font.BitmapFont;
import com.jfixby.r3.ext.api.font.BitmapFontRenderer;
import com.jfixby.r3.ext.api.font.FontCache;
import com.jfixby.r3.ext.api.font.FontParameters;
import com.jfixby.red.engine.core.unit.text.RedStringBounds;
import com.jfixby.redtriplane.fokker.render.raster.GDXTextureContainer;
import com.jfixby.redtriplane.fokker.render.raster.SpritesRenderer;

public class GdxR3FontGenerated implements BitmapFont {

    com.badlogic.gdx.graphics.g2d.BitmapFont gdx_bitmap_font;
    private File file;
    private FontCache cache;
    private int size;
    private float reScale;
    private float scale;
    private int borderSize;
    private Color borderColor;

    private com.badlogic.gdx.graphics.Color toGDXColor(Color color) {
	com.badlogic.gdx.graphics.Color gdx_color = new com.badlogic.gdx.graphics.Color(color.red(), color.green(),
		color.blue(), color.alpha());
	return gdx_color;
    }

    public GdxR3FontGenerated(File file, FontParameters params) {
	this.file = file;

	this.size = params.getSize();
	this.color = params.getColor();
	this.borderSize = params.getBorderSize();
	this.borderColor = params.getBorderColor();
	this.reScale = params.getReScaleValue();
	this.scale = params.getScaleValue();

	this.cache = new GdxR3FontCache(this);

	ToGdxFileAdaptor gdx_font_file = new ToGdxFileAdaptor(file);
	FreeTypeFontGenerator generator = new FreeTypeFontGenerator(gdx_font_file);
	FreeTypeFontGenerator.setMaxTextureSize(1024);

	FreeTypeFontParameter parameter = new FreeTypeFontParameter();
	parameter.size = params.getSize();
	parameter.flip = true;
	parameter.magFilter = TextureFilter.Linear;
	parameter.minFilter = TextureFilter.Linear;
	parameter.color = toGDXColor(color);
	parameter.shadowColor = toGDXColor(color);
	if (borderSize > 0) {
	    parameter.borderColor = toGDXColor(borderColor);
	}
	parameter.borderWidth = borderSize;
	// parameter.incremental = true;
	parameter.characters = params.getCharacters();// FreeTypeFontGenerator.DEFAULT_CHARS;
	if (parameter.characters == null) {
	    parameter.characters = FreeTypeFontGenerator.DEFAULT_CHARS;
	}
	// + CHARS_LIST.cyrillic;
	gdx_bitmap_font = generator.generateFont(parameter); // font
	// size
	// 12
	// pixels
	generator.dispose(); // don't forget to dispose to avoid memory
			     // leaks!

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

    final Float2 tmpA = Geometry.newFloat2();
    final Float2 tmpB = Geometry.newFloat2();
    final Float2 tmpC = Geometry.newFloat2();
    final Float2 tmpD = Geometry.newFloat2();

    @Override
    public void renderBitmapFont(final BitmapFontRenderer bitmapFontRenderer, final CanvasPosition position,
	    final String string_value, final CameraProjection projection, final Object blend, double opacity) {
	final com.badlogic.gdx.graphics.g2d.BitmapFont gdx_bitmap_font = getGdxBitmapFont();
	final BitmapFontCache gdx_font_cache = gdx_bitmap_font.getCache();
	final float rescale = getReScaleValue();
	final float scale = getScaleValue();

	gdx_font_cache.clear();
	gdx_font_cache.addText(string_value, 0, 0);
	final Array<TextureRegion> regions = gdx_bitmap_font.getRegions();

	final int n = regions.size;
	for (int region = 0; region < n; region++) {
	    final int spriteSize = gdx_font_cache.getVertexCount(region);
	    if (spriteSize > 0) { // ignore if this texture has no glyphs

		final Texture texture = regions.get(region).getTexture();
		final float[] spriteVertices = gdx_font_cache.getVertices(region);

		final int number_of_sprites = spriteVertices.length / SpritesRenderer.SPRITE_SIZE;
		for (int k = 0; k < number_of_sprites; k++) {
		    tmpA.setX(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_x)]);
		    tmpA.setY(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_y)]);

		    tmpB.setX(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_x)]);
		    tmpB.setY(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_y)]);

		    tmpC.setX(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_x)]);
		    tmpC.setY(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_y)]);

		    tmpD.setX(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_x)]);
		    tmpD.setY(spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_y)]);

		    //
		    // SpritesRenderer.offset(tmpA, position, rescale, scale);
		    SpritesRenderer.offset(tmpA, position, rescale, scale);
		    SpritesRenderer.offset(tmpB, position, rescale, scale);
		    SpritesRenderer.offset(tmpC, position, rescale, scale);
		    SpritesRenderer.offset(tmpD, position, rescale, scale);
		    // SpritesRenderer.setAlpha(k, opacity, spriteVertices);
		    SpritesRenderer.setupVertices(k, projection, true, spriteVertices, tmpA, tmpB, tmpC, tmpD, opacity);
		}
		this.texture = texture;
		bitmapFontRenderer.rasterDraw(container, spriteVertices, 0, spriteSize, blend);
		for (int k = 0; k < number_of_sprites; k++) {
		    // SpritesRenderer.setAlpha(k, 1, spriteVertices);
		}

		this.texture = null;
		return;
	    }
	}
    }

    com.badlogic.gdx.graphics.g2d.BitmapFont getGdxBitmapFont() {
	return this.gdx_bitmap_font;
    }

    final GDXTextureContainer container = new GDXTextureContainer() {
	@Override
	public Texture getTexture() {
	    return texture;
	}

    };

    public StringBounds getStringBounds(String string_value) {

	com.badlogic.gdx.graphics.g2d.BitmapFont gdx_bitmap_font = this.getGdxBitmapFont();
	com.badlogic.gdx.graphics.g2d.BitmapFontCache gdx_font_cache = gdx_bitmap_font.getCache();
	gdx_font_cache.clear();
	gdx_font_cache.addText(string_value, 0, 0);
	final Array<TextureRegion> regions = gdx_bitmap_font.getRegions();
	int n = regions.size;
	RedStringBounds result = new RedStringBounds();
	for (int region = 0; region < n; region++) {
	    final int spriteSize = gdx_font_cache.getVertexCount(region);
	    if (spriteSize > 0) {
		final float[] spriteVertices = gdx_font_cache.getVertices(region);
		int number_of_sprites = spriteVertices.length / SpritesRenderer.SPRITE_SIZE;
		for (int k = 0; k < number_of_sprites; k++) {
		    result.addVertex(
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_x)],
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.A_y)]);
		    result.addVertex(
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_x)],
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.B_y)]);
		    result.addVertex(
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_x)],
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.C_y)]);
		    result.addVertex(
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_x)],
			    reScale * scale * spriteVertices[SpritesRenderer.spriteVertex(k, SpritesRenderer.D_y)]);
		}
	    }
	}

	return result;

    }

}
