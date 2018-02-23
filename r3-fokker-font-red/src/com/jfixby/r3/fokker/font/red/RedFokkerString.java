
package com.jfixby.r3.fokker.font.red;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
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
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.font.RasterStringSettings;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.math.FloatMath;

public class RedFokkerString implements FokkerString {

	private final BitmapFont bmpFont;
	private final BitmapFontCache cache;
	final VerticesCache verticesCache = new VerticesCache();
	final Rectangle shape = Geometry.newRectangle();

	@Override
	public void dispose () {
		this.bmpFont.dispose();
	}

	public RedFokkerString (final RedTTFFont redTTFFont, final RasterStringSettings rasterStringSettings) {

		final File fontFile = redTTFFont.getFontFile();
		final FileHandle gdxFile = new ToGdxFileAdaptor(fontFile);
		final FreeTypeFontGenerator generator = new FreeTypeFontGenerator(gdxFile);

		final FreeTypeFontParameter parameter = new FreeTypeFontParameter();
		parameter.size = (int)rasterStringSettings.fontSize;
		parameter.characters = rasterStringSettings.string;
		parameter.flip = true;
		this.bmpFont = generator.generateFont(parameter);
		this.cache = this.bmpFont.getCache();
		this.cache.clear();
		this.cache.addText(rasterStringSettings.string, 0, 0);
		final int n = this.getRegions().size;
		this.verticesCache.updateNumberOfRegions(n);

		generator.dispose();
		this.updateShape();
// this.bmpFont.draw(null, 0, 0);
	}

	final Float2 tmpA = Geometry.newFloat2();
	final Float2 tmpB = Geometry.newFloat2();
	final Float2 tmpC = Geometry.newFloat2();
	final Float2 tmpD = Geometry.newFloat2();

	final Float2 TL = Geometry.newFloat2();
	final Float2 BR = Geometry.newFloat2();

	private void updateShape () {

		final FokkerString redString = this;
		boolean firstVertex = true;
		final Array<TextureRegion> regions = redString.getRegions();
		final int n = regions.size;
		this.shape.setSize(0, 0);
		this.shape.setPosition();

		for (int region = 0; region < n; region++) {
			final int spriteSize = redString.getVertexCount(region);
			if (spriteSize > 0) { // ignore if this texture has no glyphs

				final Texture texture = regions.get(region).getTexture();
				final float[] spriteVertices = redString.getVertices(region);

				final int number_of_sprites = redString.getNumberOfSprites(region);
				for (int k = 0; k < number_of_sprites; k++) {
					this.tmpA.setX(spriteVertices[spriteVertex(k, A_x)]);
					this.tmpA.setY(spriteVertices[spriteVertex(k, A_y)]);

					if (firstVertex) {
						firstVertex = false;
						this.TL.set(this.tmpA);
						this.BR.set(this.tmpA);
					} else {
						this.includeValue(this.tmpA);
					}

					this.tmpB.setX(spriteVertices[spriteVertex(k, B_x)]);
					this.tmpB.setY(spriteVertices[spriteVertex(k, B_y)]);
					this.includeValue(this.tmpB);

					this.tmpC.setX(spriteVertices[spriteVertex(k, C_x)]);
					this.tmpC.setY(spriteVertices[spriteVertex(k, C_y)]);
					this.includeValue(this.tmpC);

					this.tmpD.setX(spriteVertices[spriteVertex(k, D_x)]);
					this.tmpD.setY(spriteVertices[spriteVertex(k, D_y)]);
					this.includeValue(this.tmpD);

					//
					// SpritesRenderer.offset(tmpA, position, rescale, scale);
					// SpritesRenderer.setAlpha(k, opacity, spriteVertices);
				}

			}

		}

		this.shape.setHeight(this.BR.getY() - this.TL.getY());
		this.shape.setWidth(this.BR.getX() - this.TL.getX());
		this.shape.setOriginRelative();
		this.shape.setPosition(this.TL);
		this.shape.setOriginAbsolute(0, 0);
// L.d("shape", this.shape);
	}

	final private void includeValue (final Float2 tmp) {
		this.TL.setX(FloatMath.min(tmp.getX(), this.TL.getX()));
		this.TL.setY(FloatMath.min(tmp.getY(), this.TL.getY()));
		//
		this.BR.setX(FloatMath.max(tmp.getX(), this.BR.getX()));
		this.BR.setY(FloatMath.max(tmp.getY(), this.BR.getY()));
	}

	final static public int spriteVertex (final int sprite_number, final int vertice_name) {
		return vertice_name + sprite_number * SPRITE_SIZE;
	}

	public static final int VERTEX_SIZE = 2 + 1 + 2;
	public static final int SPRITE_SIZE = 4 * VERTEX_SIZE;

	public final static int A_x = 0;
	public final static int A_y = 1;
	public final static int B_x = 15;
	public final static int B_y = 16;
	public final static int C_x = 10;
	public final static int C_y = 11;
	public final static int D_x = 5;
	public final static int D_y = 6;

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
		this.verticesCache.updateRegion(region, this.cache.getVertices(region), this.shape);
		return this.verticesCache.getVertices(region);
	}

	@Override
	public int getNumberOfSprites (final int region) {
		return this.verticesCache.getNumberOfSprites(region);
	}

	@Override
	public Rectangle shape () {
		return this.shape;
	}

}
