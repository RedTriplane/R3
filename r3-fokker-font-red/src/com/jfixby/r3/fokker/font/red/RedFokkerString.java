
package com.jfixby.r3.fokker.font.red;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.BitmapFontCache;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.utils.Array;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.font.red.io.RedTTFFont;
import com.jfixby.r3.fokker.io.ToGdxFileAdaptor;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.font.RasterStringSettings;

public class RedFokkerString implements FokkerString {

	private final BitmapFont bmpFont;
	private final BitmapFontCache cache;

	public RedFokkerString (final RedTTFFont redTTFFont, final RasterStringSettings rasterStringSettings) {

		final File fontFile = redTTFFont.getFontFile();
		final FileHandle gdxFile = new ToGdxFileAdaptor(fontFile);
		final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(gdxFile);

		final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int)rasterStringSettings.fontSize;
		parameter.characters = rasterStringSettings.string;

		this.bmpFont = generator.generateFont(parameter);
		this.cache = this.bmpFont.getCache();
		this.cache.clear();
		this.cache.addText(rasterStringSettings.string, 0, 0);

		generator.dispose();

// this.bmpFont.draw(null, 0, 0);
	}

	@Override
	public Array<TextureRegion> getRegions () {
		return this.bmpFont.getRegions();
	}

	@Override
	public int getVertexCount (final int region) {
		return this.cache.getVertexCount(region);
	}

	@Override
	public float[] getVertices (final int region) {
		return this.cache.getVertices(region);
	}

	@Override
	public int getNumberOfSprites (final int region) {
		return this.cache.getLayouts().size;
	}

}
