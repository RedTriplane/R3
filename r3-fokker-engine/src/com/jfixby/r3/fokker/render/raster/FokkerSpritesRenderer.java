
package com.jfixby.r3.fokker.render.raster;

import static com.badlogic.gdx.graphics.g2d.Batch.C1;
import static com.badlogic.gdx.graphics.g2d.Batch.C2;
import static com.badlogic.gdx.graphics.g2d.Batch.C3;
import static com.badlogic.gdx.graphics.g2d.Batch.C4;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasRegion;
import com.badlogic.gdx.graphics.g2d.TextureAtlas.AtlasSprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.NumberUtils;
import com.jfixby.r3.engine.api.render.FontParameters;
import com.jfixby.r3.engine.api.render.SpritesRenderer;
import com.jfixby.r3.engine.api.screen.Screen;
import com.jfixby.r3.fokker.font.api.FokkerFonts;
import com.jfixby.r3.fokker.font.api.FokkerString;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.texture.api.FokkerTexture;
import com.jfixby.r3.fokker.texture.api.FokkerTextures;
import com.jfixby.r3.rana.api.asset.AssetsConsumer;
import com.jfixby.r3.rana.api.asset.LoadedAssets;
import com.jfixby.scarabei.api.debug.DEBUG_TIMER_MODE;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.DebugTimer;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Rectangle;
import com.jfixby.scarabei.api.geometry.projections.Projection;
import com.jfixby.scarabei.api.math.MathTools;
import com.jfixby.scarabei.api.math.VectorTool;
import com.jfixby.scarabei.api.names.ID;

public class FokkerSpritesRenderer extends SpritesRenderer implements AssetsConsumer {

	private final FokkerRasterRenderer raster_renderer;

	// private OrthographicCamera gdx_screen_Camera;

	public FokkerSpritesRenderer (final FokkerRasterRenderer fokkerRasterRenderer) {
		this.raster_renderer = fokkerRasterRenderer;
		// gdx_screen_Camera = new OrthographicCamera();
	}

	final Rectangle aperture_shape = Geometry.newRectangle();

	final public void drawAperture (final ID spriteAssetID, final double ax, final double ay, final double bx, final double by,
		final double opacity) {

		final double aperture_width = (bx - ax) * 0 + Screen.getScreenWidth();
		final double aperture_height = (by - ay) * 0 + Screen.getScreenHeight();

// final double margin_x = (Screen.getScreenWidth() - aperture_width) * 0.5;
// final double margin_y = (Screen.getScreenHeight() - aperture_height) * 0.5;
		{// top+bottom
			this.aperture_shape.setSize(Screen.getScreenWidth(), ay);

			this.aperture_shape.setOriginRelative(0, 0);
			this.aperture_shape.setPosition(0, 0);
			this.drawSprite(spriteAssetID, this.aperture_shape, opacity, false, this, null);

			this.aperture_shape.setOriginRelative(0, 0);
			this.aperture_shape.setPosition(0, by);
			this.drawSprite(spriteAssetID, this.aperture_shape, opacity, false, this, null);
		}

		{// left+right
			this.aperture_shape.setSize(ax, Screen.getScreenHeight());
			this.aperture_shape.setOriginRelative(0, 0);
			this.aperture_shape.setPosition(0, 0);
			this.drawSprite(spriteAssetID, this.aperture_shape, opacity, false, this, null);

			this.aperture_shape.setOriginRelative(0, 0);
			this.aperture_shape.setPosition(bx, 0);
			this.drawSprite(spriteAssetID, this.aperture_shape, opacity, false, this, null);
		}

	}

	final public void drawSprite (final ID spriteAssetID, final Rectangle shape, final double opacity,
		final Texture blend_texture) {
		this.drawSprite(spriteAssetID, shape, opacity, true, this, blend_texture);
	}

	final Float2 tmpA = Geometry.newFloat2();
	final Float2 tmpB = Geometry.newFloat2();
	final Float2 tmpC = Geometry.newFloat2();
	final Float2 tmpD = Geometry.newFloat2();

	// private static float oldAlpha;

	Rectangle original_texture_shape = Geometry.newRectangle();
	Rectangle packed_texture_shape = Geometry.newRectangle();
	private AtlasSprite atlas_sprite;
	private AtlasRegion region;
	private Texture texture;
	final DebugTimer timer = Debug.newTimer(DEBUG_TIMER_MODE.NANOSECONDS);

	final public void drawSprite (final ID spriteAssetID, final Rectangle raster_shape, final double opacity,
		final boolean project, final FokkerSpritesRenderer renderer, final Texture blend_texture) {

		if (raster_shape.getWidth() == 0 || raster_shape.getHeight() == 0) {
			return;
		}

		final FokkerTexture raster = FokkerTextures.obtain(spriteAssetID);
		if (raster == null) {
			LoadedAssets.printAllLoadedAssets();
			Err.reportError("Asset<" + spriteAssetID + "> not found");
		}

		final com.badlogic.gdx.graphics.g2d.Sprite gdx_sprite = raster.getGdxSprite();
		final Texture alpha_texture = null;
		// Debug.checkNull("asset(" + spriteAssetID + ")", gdx_sprite);

		// tmpA.setXY(0, 0);
		// tmpB.setXY(1, 0);
		// tmpC.setXY(1, 1);
		// tmpD.setXY(0, 1);

		this.tmpD.setXY(0, 0);
		this.tmpC.setXY(1, 0);
		this.tmpB.setXY(1, 1);
		this.tmpA.setXY(0, 1);

		this.atlas_sprite = null;
		this.region = null;

		if (gdx_sprite instanceof AtlasSprite) {
			this.atlas_sprite = (AtlasSprite)gdx_sprite;
			this.region = this.atlas_sprite.getAtlasRegion();

			this.original_texture_shape.setSize(this.region.originalWidth, this.region.originalHeight);
			this.packed_texture_shape.setSize(this.region.packedWidth, this.region.packedHeight);
			// packed_texture_shape.setPosition(region.offsetX, region.offsetY);
			// this.packed_texture_shape.setPositionX(
			// this.region.originalWidth - this.region.offsetX -
			// this.region.getRotatedPackedWidth());
			this.packed_texture_shape.setPositionX(this.region.offsetX);
			this.packed_texture_shape
				.setPositionY(this.region.originalHeight - this.region.offsetY - this.region.getRotatedPackedHeight());

			this.packed_texture_shape.toAbsolute(this.tmpA);
			this.packed_texture_shape.toAbsolute(this.tmpB);
			this.packed_texture_shape.toAbsolute(this.tmpC);
			this.packed_texture_shape.toAbsolute(this.tmpD);

			this.original_texture_shape.toRelative(this.tmpA);
			this.original_texture_shape.toRelative(this.tmpB);
			this.original_texture_shape.toRelative(this.tmpC);
			this.original_texture_shape.toRelative(this.tmpD);

		}

		raster_shape.toAbsolute(this.tmpA);
		raster_shape.toAbsolute(this.tmpB);
		raster_shape.toAbsolute(this.tmpC);
		raster_shape.toAbsolute(this.tmpD);

		this.texture = gdx_sprite.getTexture();

		final float[] spriteVertices = gdx_sprite.getVertices();

		final Projection camProjection = renderer.raster_renderer.machine.camera_projection;
		final Projection layerProjection = renderer.raster_renderer.machine.layer_projection;

		setAlpha(0, opacity, spriteVertices);

// timer.reset();
		setupVertices(0, camProjection, layerProjection, project, spriteVertices, this.tmpA, this.tmpB, this.tmpC, this.tmpD,
			opacity);
// timer.printTime("setupVertices");
		GdxRender.rasterDraw(this.texture, spriteVertices, 0, SPRITE_SIZE, blend_texture, alpha_texture);
		setAlpha(0, 1, spriteVertices);

		this.texture = null;

	}

	final public static void setupVertices (final int k, final Projection cam, final Projection layer, final boolean project,
		final float[] spriteVertices, final Float2 tmpA, final Float2 tmpB, final Float2 tmpC, final Float2 tmpD,
		final double opacity) {
		if (project) {
			layer.project(tmpA);
			layer.project(tmpB);
			layer.project(tmpC);
			layer.project(tmpD);
		}
		if (project) {
			cam.project(tmpA);
			cam.project(tmpB);
			cam.project(tmpC);
			cam.project(tmpD);
		}
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, A_x)] = round(tmpA.getX());//
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, A_y)] = round(tmpA.getY());

		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, B_x)] = round(tmpB.getX());
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, B_y)] = round(tmpB.getY());

		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C_x)] = round(tmpC.getX());
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C_y)] = round(tmpC.getY());

		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, D_x)] = round(tmpD.getX());
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, D_y)] = round(tmpD.getY());

	}

	static float color;
	static int intBits;
	static float a;
	static int alphaBits;

	final private static void setAlpha (final int k, final double opacity, final float[] spriteVertices) {
		a = (float)(opacity);
		intBits = NumberUtils.floatToIntColor(spriteVertices[C1]);
		alphaBits = (int)(255 * a) << 24;
		// clear alpha on original color
		intBits = intBits & 0x00FFFFFF;
		// write new alpha
		intBits = intBits | alphaBits;
		color = NumberUtils.intToFloatColor(intBits);
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C1)] = color;
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C2)] = color;
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C3)] = color;
		spriteVertices[FokkerSpritesRenderer.spriteVertex(k, C4)] = color;

	}

	final public void drawString (final ID fontID, final FontParameters fontParams, final String stringValue,
		final CanvasPosition position, final double opacity, final Texture blend_texture) {

		final FokkerString redString = FokkerFonts.component().obtainString(fontID, fontParams, stringValue);

		final Projection camProjection = this.raster_renderer.machine.camera_projection;
		final Projection layProjection = this.raster_renderer.machine.layer_projection;

		final Array<TextureRegion> regions = redString.getRegions();
		final int n = regions.size;

		for (int region = 0; region < n; region++) {
			final int spriteSize = redString.getVertexCount(region);
			if (spriteSize > 0) { // ignore if this texture has no glyphs

				final Texture texture = regions.get(region).getTexture();
				final float[] spriteVertices = redString.getVertices(region);

				final int number_of_sprites = redString.getNumberOfSprites(region);
				for (int k = 0; k < number_of_sprites; k++) {
					this.tmpA.setX(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.A_x)]);
					this.tmpA.setY(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.A_y)]);

					this.tmpB.setX(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.B_x)]);
					this.tmpB.setY(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.B_y)]);

					this.tmpC.setX(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.C_x)]);
					this.tmpC.setY(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.C_y)]);

					this.tmpD.setX(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.D_x)]);
					this.tmpD.setY(spriteVertices[FokkerSpritesRenderer.spriteVertex(k, FokkerSpritesRenderer.D_y)]);

					//
					// SpritesRenderer.offset(tmpA, position, rescale, scale);
					FokkerSpritesRenderer.offset(this.tmpA, position);
					FokkerSpritesRenderer.offset(this.tmpB, position);
					FokkerSpritesRenderer.offset(this.tmpC, position);
					FokkerSpritesRenderer.offset(this.tmpD, position);
					// SpritesRenderer.setAlpha(k, opacity, spriteVertices);
					FokkerSpritesRenderer.setupVertices(k, camProjection, layProjection, true, spriteVertices, this.tmpA, this.tmpB,
						this.tmpC, this.tmpD, opacity);
				}

				this.texture = texture;
				GdxRender.rasterDraw(this.texture, spriteVertices, 0, spriteSize, blend_texture, null);

				this.texture = null;
				return;
			}
		}
// }
	}

	static final VectorTool tool = MathTools.newVectorTool();

	final public static void offset (final Float2 tmp, final CanvasPosition position) {
		final double offset_x = position.getX();
		final double offset_y = position.getY();

		tool.X = tmp.getX();
		tool.Y = tmp.getY();
		tool.XYtoAR();
		tool.A = tool.A + position.getRotation().toRadians();
		tool.R = tool.R;
		tool.ARtoXY();
		tmp.setX(tool.X + offset_x);
		tmp.setY(tool.Y + offset_y);

	}

}
