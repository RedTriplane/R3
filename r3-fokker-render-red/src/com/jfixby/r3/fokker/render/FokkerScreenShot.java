
package com.jfixby.r3.fokker.render;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.PixmapIO.PNG;
import com.badlogic.gdx.utils.GdxRuntimeException;
import com.badlogic.gdx.utils.ScreenUtils;
import com.jfixby.r3.engine.api.render.ScreenShot;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.file.File;
import com.jfixby.scarabei.api.file.FileOutputStream;
import com.jfixby.scarabei.api.image.ArrayColorMap;
import com.jfixby.scarabei.api.image.ArrayColorMapSpecs;
import com.jfixby.scarabei.api.image.ColorMap;
import com.jfixby.scarabei.api.image.ImageProcessing;
import com.jfixby.scarabei.api.log.L;

public class FokkerScreenShot implements ScreenShot {
	private final int w;
	private final int h;
	private final int x;
	private final int y;

	private final Pixmap gdx_pixmap;

	public FokkerScreenShot (final int areaWidth, final int areaHeight, final int areaX, final int areaY) {
		this.w = areaWidth < 0 ? Gdx.graphics.getWidth() : areaWidth;
		this.h = areaHeight < 0 ? Gdx.graphics.getHeight() : areaHeight;
		this.x = areaX;
		this.y = areaY;
		this.gdx_pixmap = getScreenshot(this.x, this.y, this.w, this.h, true);
	}

	@Override
	public ColorMap toColorMap () {
		final ArrayColorMapSpecs specs = ImageProcessing.newArrayColorMapSpecs();
		specs.setWidth(this.w);
		specs.setHeight(this.h);
		final ArrayColorMap map = ImageProcessing.newArrayColorMap(specs);
		for (int i = 0; i < this.w; i++) {
			for (int j = 0; j < this.h; j++) {
				final Color color_value = toGdxColor(this.gdx_pixmap.getPixel(i, j));
				map.setValue(i, j, color_value);
			}
		}
		return map;
	}

	final static private Color toGdxColor (final int pixel) {
		final com.badlogic.gdx.graphics.Color gdx_color = new com.badlogic.gdx.graphics.Color(pixel);
		final Color color = Colors.newColor(gdx_color.a, gdx_color.r, gdx_color.g, gdx_color.b);
		return color;
	}

	private static Pixmap getScreenshot (final int x, final int y, final int w, final int h, final boolean yDown) {
		final Pixmap pixmap = ScreenUtils.getFrameBufferPixmap(x, y, w, h);

		if (yDown) {
			// Flip the pixmap upside down
			final ByteBuffer pixels = pixmap.getPixels();
			final int numBytes = w * h * 4;
			final byte[] lines = new byte[numBytes];
			final int numBytesPerLine = w * 4;
			for (int i = 0; i < h; i++) {
				pixels.position((h - i - 1) * numBytesPerLine);
				pixels.get(lines, i * numBytesPerLine, numBytesPerLine);
			}
			pixels.clear();
			pixels.put(lines);
			pixels.clear();
		}

		return pixmap;
	}

	@Override
	public void saveToFile (final File screenSHotFile) {
//
// final FileHandle file = new ToGdxFileAdaptor(screenSHotFile);
		L.d("writing", screenSHotFile);
// PixmapIO.writePNG(file, this.gdx_pixmap);

		final FileOutputStream os = screenSHotFile.newOutputStream();
		os.open();
		final Pixmap pixmap = this.gdx_pixmap;
		try {
			final PNG writer = new PNG((int)(pixmap.getWidth() * pixmap.getHeight() * 1.5f)); // Guess at deflated size.
			try {
				writer.setFlipY(false);

				final OutputStream output = os.toJavaOutputStream();
				writer.write(output, pixmap);
			} finally {
				writer.dispose();
			}
		} catch (final IOException ex) {
			throw new GdxRuntimeException("Error writing PNG: " + screenSHotFile, ex);
		}

		os.close();

	}

}
