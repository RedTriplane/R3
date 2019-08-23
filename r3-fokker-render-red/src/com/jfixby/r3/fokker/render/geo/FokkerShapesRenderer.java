
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

	@Override
	public void init (final FokkerRenderMachine fokkerRenderMachine) {
		super.init(fokkerRenderMachine);

		this.lineRenderer = new LineRenderer(this);
		this.triangleRenderer = new TriangleRenderer(this);
		this.ellipseRenderer = new EllipseRenderer(this);
		this.customRenderer = new CustomRenderer(this);
		GdxRender.activateShapesRenderer();
	}

	@Override
	public void doClose () {
		GdxRender.closeShapesRenderer();

	}

	public void setGdxProjectionMatrix (final Matrix4 combined) {
		GdxRender.setShapeRendererProjectionMatrix(combined);
	}

	public void drawLine (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b) {
		this.lineRenderer.drawLine(color, a, b);
	}

	public void drawTriangle (final Color color, final ReadOnlyFloat2 a, final ReadOnlyFloat2 b, final ReadOnlyFloat2 c) {
		this.triangleRenderer.drawTriangle(color, a, b, c);
	}

	void setColor (final Color color) {
		this.R = color.red();
		this.G = color.green();
		this.B = color.blue();
		this.A = color.alpha();
	}

	private float A;
	private float B;
	private float G;
	private float R;

	public void setGdxColor () {
		GdxRender.setShapeRendererColor(this.R, this.G, this.B, this.A);

	}

	@Override
	public void doOpen () {
		GdxRender.openShapesRenderer(this.getGdxCamera().combined);
	}

	public void drawCircle (final Color color, final double center_x, final double center_y, final double radius) {
		this.ellipseRenderer.drawCircle(color, center_x, center_y, radius);
	}

	@Override
	public void onFrameBegin () {
	}

	@Override
	public void onFrameEnd () {
	}

}
