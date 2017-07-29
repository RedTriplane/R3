
package com.jfixby.r3.fokker.render.geo;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.render.raster.FokkerSpritesRenderer;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class TriangleRenderer {

	static private Float2 tmpA;
	static private Float2 tmpB;
	static private Float2 tmpC;

	private final FokkerShapesRenderer renderer;

	public TriangleRenderer (final FokkerShapesRenderer fokkerShapesRenderer) {
		this.renderer = fokkerShapesRenderer;
		tmpA = Geometry.newFloat2();
		tmpB = Geometry.newFloat2();
		tmpC = Geometry.newFloat2();
	}

	public void drawTriangle (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b, final ReadOnlyFloat2 c) {
		this.renderer.setColor(color);

		tmpA.set(a);
		// tmpA.shift(renderer.machine.offset);

		tmpB.set(b);
		// tmpB.shift(renderer.machine.offset);

		tmpC.set(c);
		// tmpC.shift(renderer.machine.offset);

		this.renderer.machine.layer_projection.project(tmpA);
		this.renderer.machine.layer_projection.project(tmpB);
		this.renderer.machine.layer_projection.project(tmpC);

		this.renderer.machine.camera_projection.project(tmpA);
		this.renderer.machine.camera_projection.project(tmpB);
		this.renderer.machine.camera_projection.project(tmpC);

		this.drawTriangle(tmpA, tmpB, tmpC);
	}

	private void drawTriangle (final Float2 a, final Float2 b, final Float2 c) {
		final float ax = FokkerSpritesRenderer.round(a.getX());
		final float ay = FokkerSpritesRenderer.round(a.getY());

		final float bx = FokkerSpritesRenderer.round(b.getX());
		final float by = FokkerSpritesRenderer.round(b.getY());

		final float cx = FokkerSpritesRenderer.round(c.getX());
		final float cy = FokkerSpritesRenderer.round(c.getY());

		this.drawGdxTriangle(ax, ay, bx, by, cx, cy);
	}

	private void drawGdxTriangle (final float ax, final float ay, final float bx, final float by, final float cx, final float cy) {
		// renderer.gdx_shape_renderer.begin(ShapeType.Filled);
		GdxRender.shapeRenderBegin(ShapeType.Filled);
		this.renderer.setGdxColor();
		GdxRender.shapeRenderTriangle(ax, ay, bx, by, cx, cy);
		GdxRender.shapeRenderEnd();
	}

}
