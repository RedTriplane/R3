
package com.jfixby.r3.fokker.render.geo;

import com.badlogic.gdx.math.Matrix4;
import com.jfixby.r3.fokker.render.FokkerRenderMachine;
import com.jfixby.r3.fokker.render.GdxRender;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;

public class FokkerShapesRenderer extends Renderer {

	private LineRenderer lineRenderer;
	private TriangleRenderer triangleRenderer;
	private EllipseRenderer ellipseRenderer;
	private CustomRenderer customRenderer;

	public void init (FokkerRenderMachine fokkerRenderMachine) {
		super.init(fokkerRenderMachine);

		lineRenderer = new LineRenderer(this);
		triangleRenderer = new TriangleRenderer(this);
		ellipseRenderer = new EllipseRenderer(this);
		customRenderer = new CustomRenderer(this);
		GdxRender.activateShapesRenderer();
	}

	public void doClose () {
		GdxRender.closeShapesRenderer();

	}

	public void setGdxProjectionMatrix (final Matrix4 combined) {
		GdxRender.setShapeRendererProjectionMatrix(combined);
	}

	public void drawLine (Color color, ReadOnlyFloat2 a, ReadOnlyFloat2 b) {
		this.lineRenderer.drawLine(color, a, b);
	}

	public void drawTriangle (Color color, ReadOnlyFloat2 a, ReadOnlyFloat2 b, ReadOnlyFloat2 c) {
		this.triangleRenderer.drawTriangle(color, a, b, c);
	}

	// public void drawEllipse(Color color, double positionX, double positionY,
	// double width, double height, double rotation, boolean filled) {
	// this.ellipseRenderer.drawEllipse(color, positionX, positionY, width,
	// height, rotation, filled);
	// }

	void setColor (Color color) {
		R = color.red();
		G = color.green();
		B = color.blue();
		A = color.alpha();
	}

	private float A;
	private float B;
	private float G;
	private float R;

	public void setGdxColor () {
		GdxRender.setShapeRendererColor(R, G, B, A);

	}

	// public void drawDisk(Color color, double positionX, double positionY,
	// double radius) {
	// ellipseRenderer.drawEllipse(color, positionX, positionY, radius * 2,
	// radius * 2, 0, true);
	// }

	@Override
	public void doOpen () {
		GdxRender.openShapesRenderer(this.getGdxCamera().combined);
	}

	public void drawCircle (Color color, double center_x, double center_y, double radius) {
		this.ellipseRenderer.drawCircle(color, center_x, center_y, radius);
	}

	@Override
	public void onFrameBegin () {
	}

	@Override
	public void onFrameEnd () {
	}

	// public void drawFokkerRenderable(FokkerShapesRenderable self_renderable)
	// {
	// customRenderer.draw(self_renderable);
	// }
}
