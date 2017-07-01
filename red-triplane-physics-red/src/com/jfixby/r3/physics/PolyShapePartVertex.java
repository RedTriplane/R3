
package com.jfixby.r3.physics;

public class PolyShapePartVertex {

	private double x;
	private double y;

	public PolyShapePartVertex (double x, double y) {
		this.x = x;
		this.y = y;

	}

	public double getX () {
		return this.x;
	}

	public double getY () {
		return this.y;
	}

	@Override
	public String toString () {
		return "Vertex [" + x + "," + y + "]";
	}

}
