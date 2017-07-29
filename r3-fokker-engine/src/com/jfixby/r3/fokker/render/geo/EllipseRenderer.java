
package com.jfixby.r3.fokker.render.geo;

import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.r3.fokker.render.raster.FokkerSpritesRenderer;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Circle;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.math.MathTools;
import com.jfixby.scarabei.api.math.Matrix;

public class EllipseRenderer {

	private static final double pixels_per_curve_segment = 5;
	private final FokkerShapesRenderer shapes_renderer;
	private final Matrix T;
	private final Matrix R;
	static private Float2 tmpA;
	static private Float2 tmpB;
	static private Float2 tmpC;
	static private Float2 tmpD;
	static private Float2 tmpE;

	public EllipseRenderer (final FokkerShapesRenderer fokkerShapesRenderer) {
		this.shapes_renderer = fokkerShapesRenderer;
		tmpA = Geometry.newFloat2();
		tmpB = Geometry.newFloat2();
		tmpC = Geometry.newFloat2();
		tmpD = Geometry.newFloat2();
		tmpE = Geometry.newFloat2();
		this.T = MathTools.newMatrix(2, 2);
		this.R = MathTools.newMatrix(3, 3);
	}

	public void drawEllipse (final Color color, final double positionX, final double positionY, final double width,
		final double height, final double rotation, final boolean filled) {
		this.shapes_renderer.setColor(color);

		tmpA.setXY(positionX, positionY);
		// tmpA.shift(-width / 2, -height / 2);

		tmpB.setXY(width / 2, 0);
		tmpC.setXY(0, height / 2);

		MathTools.setupRotationMatrix(this.R, rotation);
		Geometry.applyTransformation(this.R, tmpB);
		Geometry.applyTransformation(this.R, tmpC);

		tmpC.add(positionX, positionY);
		tmpB.add(positionX, positionY);

		this.shapes_renderer.machine.layer_projection.project(tmpA);
		this.shapes_renderer.machine.layer_projection.project(tmpB);
		this.shapes_renderer.machine.layer_projection.project(tmpC);
		this.shapes_renderer.machine.layer_projection.project(tmpD);

		this.shapes_renderer.machine.camera_projection.project(tmpA);
		this.shapes_renderer.machine.camera_projection.project(tmpB);
		this.shapes_renderer.machine.camera_projection.project(tmpC);
		this.shapes_renderer.machine.camera_projection.project(tmpD);
		// L.d("ellipse", "begin");

		this.drawEllipse(tmpA, tmpB, tmpC, filled);
		// L.d("ellipse", "end");

	}

	private void drawEllipse (final Float2 center, final Float2 Vx, final Float2 Vy, final boolean filled) {

		final double center_x = center.getX();
		final double center_y = center.getY();

		final double VXx = Vx.getX() - center_x;
		final double VXy = Vx.getY() - center_y;

		final double VYx = Vy.getX() - center_x;
		final double VYy = Vy.getY() - center_y;

		this.T.resetToZeroMatrix();
		this.T.setValue(0, 0, VXx);
		this.T.setValue(0, 1, VXy);
		this.T.setValue(1, 0, VYx);
		this.T.setValue(1, 1, VYy);

		final double radius_x = FloatMath.norm(VXx, VXy);
		final double radius_y = FloatMath.norm(VYx, VYy);

		final double diametr = FloatMath.norm(radius_x, radius_y) * 2;
		final double curve_len = diametr * FloatMath.VAL_2PI();

		final int segments = (int)(2 + curve_len / pixels_per_curve_segment);

		tmpD.setXY(FloatMath.cos(0), FloatMath.sin(0));
		Geometry.applyTransformation(this.T, tmpD);
		tmpD.add(center_x, center_y);

		this.beginGdxDrawing(filled);
		double alpha;
		double cos;
		double sin;

		for (int i = 1; i <= segments; i++) {
			alpha = FloatMath.VAL_2PI() * i / segments;
			cos = FloatMath.cos(alpha);
			sin = FloatMath.sin(alpha);
			tmpE.set(tmpD);
			tmpD.setXY(cos, sin);
			Geometry.applyTransformation(this.T, tmpD);
			tmpD.add(center_x, center_y);

			this.drawGdxSegment(center, tmpE, tmpD, filled);

		}
		this.endGdxDrawing();

	}

	private void endGdxDrawing () {
		GdxRender.shapeRenderEnd();

	}

	private void beginGdxDrawing (final boolean filled) {
		if (!filled) {
			GdxRender.shapeRenderBegin(ShapeType.Line);
		} else {
			GdxRender.shapeRenderBegin(ShapeType.Filled);
		}
		this.shapes_renderer.setGdxColor();
	}

	float x1;
	float y1;
	float x2;
	float y2;
	float x3;
	float y3;

	private void drawGdxSegment (final ReadOnlyFloat2 center, final ReadOnlyFloat2 A, final ReadOnlyFloat2 B, final boolean filled) {
		this.x1 = FokkerSpritesRenderer.round(A.getX());
		this.y1 = FokkerSpritesRenderer.round(A.getY());
		this.x2 = FokkerSpritesRenderer.round(B.getX());
		this.y2 = FokkerSpritesRenderer.round(B.getY());
		if (!filled) {
			GdxRender.shapeRenderLine(this.x1, this.y1, this.x2, this.y2);
		} else {
			this.x3 = FokkerSpritesRenderer.round(center.getX());
			this.y3 = FokkerSpritesRenderer.round(center.getY());

			GdxRender.shapeRenderTriangle(this.x1, this.y1, this.x2, this.y2, this.x3, this.y3);
		}
	}

	final Circle tmp_circle = Geometry.newCircle();

	public void drawCircle (final Color color, final double center_x, final double center_y, final double radius) {
		this.drawEllipse(color, center_x, center_y, 2 * radius, 2 * radius, 0, false);

		// tmp_circle.setPosition(center_x, center_y);
		// tmp_circle.setRadius(radius);
		// beginGdxDrawing(false);
		//
		// final double curve_len = radius * 2 * FloatMath.VAL_2PI();
		//
		// final int segments = (int) (2 + curve_len /
		// pixels_per_curve_segment);
		//
		// for (int i = 1; i <= segments; i++) {
		// alpha = FloatMath.VAL_2PI() * i / segments;
		// tmpE.set(tmp_circle.getVertexAt(alpha).world());
		// alpha = FloatMath.VAL_2PI() * (i + 1) / segments;
		// tmpD.set(tmp_circle.getVertexAt(alpha).world());
		// drawGdxSegment(tmp_circle.getCenter().world(), tmpE, tmpD, false);
		// }
		// //
		// endGdxDrawing();

	}

	double alpha;
}
