
package com.jfixby.r3.fokker.render.geo;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.render.raster.FokkerSpritesRenderer;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class LineRenderer {
	static private Float2 tmpA;
	static private Float2 tmpB;
	private final FokkerShapesRenderer shapes_renderer;

	public LineRenderer (final FokkerShapesRenderer fokkerShapesRenderer) {
		this.shapes_renderer = fokkerShapesRenderer;
		tmpA = Geometry.newFloat2();
		tmpB = Geometry.newFloat2();
	}

	public void drawLine (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b) {
		this.shapes_renderer.setColor(color);

		tmpA.set(a);
		// tmpA.shift(shapes_renderer.machine.offset);

		tmpB.set(b);
		// tmpB.shift(shapes_renderer.machine.offset);

		this.shapes_renderer.machine.layer_projection.project(tmpA);
		this.shapes_renderer.machine.layer_projection.project(tmpB);

		this.shapes_renderer.machine.camera_projection.project(tmpA);
		this.shapes_renderer.machine.camera_projection.project(tmpB);

		this.drawLine(tmpA, tmpB);
	}

	private void drawLine (final ReadOnlyFloat2 screen_point_a, final ReadOnlyFloat2 screen_point_b) {
		final float f_Ax = FokkerSpritesRenderer.round(screen_point_a.getX());
		final float f_Ay = FokkerSpritesRenderer.round(screen_point_a.getY());
		final float f_Bx = FokkerSpritesRenderer.round(screen_point_b.getX());
		final float f_By = FokkerSpritesRenderer.round(screen_point_b.getY());
		this.drawGDxLine(f_Ax, f_Ay, f_Bx, f_By);
	}

	private void drawGDxLine (final float f_Ax, final float f_Ay, final float f_Bx, final float f_By) {

		GdxRender.shapeRenderBegin(ShapeType.Line);
		this.shapes_renderer.setGdxColor();
		// geometry_renderer.setColor(Color.GREEN);
		GdxRender.shapeRenderLine(f_Ax, f_Ay, f_Bx, f_By);
		GdxRender.shapeRenderEnd();
	}

}
