/*******************************************************************************
 * Copyright (c) 2013, Daniel Murphy
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted provided that the following conditions are met:
 * 	* Redistributions of source code must retain the above copyright notice,
 * 	  this list of conditions and the following disclaimer.
 * 	* Redistributions in binary form must reproduce the above copyright notice,
 * 	  this list of conditions and the following disclaimer in the documentation
 * 	  and/or other materials provided with the distribution.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT,
 * INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT
 * NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY,
 * WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package org.jbox2d.d.collision.shapes;

import org.jbox2d.d.collision.AABB;
import org.jbox2d.d.collision.RayCastInput;
import org.jbox2d.d.collision.RayCastOutput;
import org.jbox2d.d.common.MathUtils;
import org.jbox2d.d.common.Rot;
import org.jbox2d.d.common.Settings;
import org.jbox2d.d.common.Transform;
import org.jbox2d.d.common.Vector2;

/**
 * A line segment (edge) shape. These can be connected in chains or loops to
 * other edge shapes. The connectivity information is used to ensure correct
 * contact normals.
 * 
 * @author Daniel
 */
public class EdgeShape extends Shape {

	/**
	 * edge vertex 1
	 */
	public final Vector2 m_vertex1 = new Vector2();
	/**
	 * edge vertex 2
	 */
	public final Vector2 m_vertex2 = new Vector2();

	/**
	 * optional adjacent vertex 1. Used for smooth collision
	 */
	public final Vector2 m_vertex0 = new Vector2();
	/**
	 * optional adjacent vertex 2. Used for smooth collision
	 */
	public final Vector2 m_vertex3 = new Vector2();
	public boolean m_hasVertex0 = false, m_hasVertex3 = false;

	public EdgeShape() {
		super(ShapeType.EDGE);
		setRadius(Settings.polygonRadius_legacy);
	}

	@Override
	public int getChildCount() {
		return 1;
	}

	public void set(Vector2 v1, Vector2 v2) {
		m_vertex1.set(v1);
		m_vertex2.set(v2);
		m_hasVertex0 = m_hasVertex3 = false;
	}

	@Override
	public boolean testPoint(Transform xf, Vector2 p) {
		return false;
	}

	// for pooling
	private final Vector2 normal = new Vector2();

	@Override
	public double computeDistanceToOut(Transform xf, Vector2 p, int childIndex,
			Vector2 normalOut) {
		double xfqc = xf.q.c;
		double xfqs = xf.q.s;
		double xfpx = xf.p.x;
		double xfpy = xf.p.y;
		double v1x = (xfqc * m_vertex1.x - xfqs * m_vertex1.y) + xfpx;
		double v1y = (xfqs * m_vertex1.x + xfqc * m_vertex1.y) + xfpy;
		double v2x = (xfqc * m_vertex2.x - xfqs * m_vertex2.y) + xfpx;
		double v2y = (xfqs * m_vertex2.x + xfqc * m_vertex2.y) + xfpy;

		double dx = p.x - v1x;
		double dy = p.y - v1y;
		double sx = v2x - v1x;
		double sy = v2y - v1y;
		double ds = dx * sx + dy * sy;
		if (ds > 0) {
			double s2 = sx * sx + sy * sy;
			if (ds > s2) {
				dx = p.x - v2x;
				dy = p.y - v2y;
			} else {
				dx -= ds / s2 * sx;
				dy -= ds / s2 * sy;
			}
		}

		double d1 = MathUtils.sqrt(dx * dx + dy * dy);
		if (d1 > 0) {
			normalOut.x = 1 / d1 * dx;
			normalOut.y = 1 / d1 * dy;
		} else {
			normalOut.x = 0;
			normalOut.y = 0;
		}
		return d1;
	}

	// p = p1 + t * d
	// v = v1 + s * e
	// p1 + t * d = v1 + s * e
	// s * e - t * d = p1 - v1
	@Override
	public boolean raycast(RayCastOutput output, RayCastInput input,
			Transform xf, int childIndex) {

		double tempx, tempy;
		final Vector2 v1 = m_vertex1;
		final Vector2 v2 = m_vertex2;
		final Rot xfq = xf.q;
		final Vector2 xfp = xf.p;

		// Put the ray into the edge's frame of reference.
		// b2Vec2 p1 = b2MulT(xf.q, input.p1 - xf.p);
		// b2Vec2 p2 = b2MulT(xf.q, input.p2 - xf.p);
		tempx = input.p1.x - xfp.x;
		tempy = input.p1.y - xfp.y;
		final double p1x = xfq.c * tempx + xfq.s * tempy;
		final double p1y = -xfq.s * tempx + xfq.c * tempy;

		tempx = input.p2.x - xfp.x;
		tempy = input.p2.y - xfp.y;
		final double p2x = xfq.c * tempx + xfq.s * tempy;
		final double p2y = -xfq.s * tempx + xfq.c * tempy;

		final double dx = p2x - p1x;
		final double dy = p2y - p1y;

		// final Vec2 normal = pool2.set(v2).subLocal(v1);
		// normal.set(normal.y, -normal.x);
		normal.x = v2.y - v1.y;
		normal.y = v1.x - v2.x;
		normal.normalize();
		final double normalx = normal.x;
		final double normaly = normal.y;

		// q = p1 + t * d
		// dot(normal, q - v1) = 0
		// dot(normal, p1 - v1) + t * dot(normal, d) = 0
		tempx = v1.x - p1x;
		tempy = v1.y - p1y;
		double numerator = normalx * tempx + normaly * tempy;
		double denominator = normalx * dx + normaly * dy;

		if (denominator == 0.0f) {
			return false;
		}

		double t = numerator / denominator;
		if (t < 0.0f || 1.0f < t) {
			return false;
		}

		// Vec2 q = p1 + t * d;
		final double qx = p1x + t * dx;
		final double qy = p1y + t * dy;

		// q = v1 + s * r
		// s = dot(q - v1, r) / dot(r, r)
		// Vec2 r = v2 - v1;
		final double rx = v2.x - v1.x;
		final double ry = v2.y - v1.y;
		final double rr = rx * rx + ry * ry;
		if (rr == 0.0f) {
			return false;
		}
		tempx = qx - v1.x;
		tempy = qy - v1.y;
		// double s = Vec2.dot(pool5, r) / rr;
		double s = (tempx * rx + tempy * ry) / rr;
		if (s < 0.0f || 1.0f < s) {
			return false;
		}

		output.fraction = t;
		if (numerator > 0.0f) {
			// output.normal = -b2Mul(xf.q, normal);
			output.normal.x = -xfq.c * normal.x + xfq.s * normal.y;
			output.normal.y = -xfq.s * normal.x - xfq.c * normal.y;
		} else {
			// output->normal = b2Mul(xf.q, normal);
			output.normal.x = xfq.c * normal.x - xfq.s * normal.y;
			output.normal.y = xfq.s * normal.x + xfq.c * normal.y;
		}
		return true;
	}

	@Override
	public void computeAABB(AABB aabb, Transform xf, int childIndex) {
		final Vector2 lowerBound = aabb.lowerBound;
		final Vector2 upperBound = aabb.upperBound;
		final Rot xfq = xf.q;

		final double v1x = (xfq.c * m_vertex1.x - xfq.s * m_vertex1.y) + xf.p.x;
		final double v1y = (xfq.s * m_vertex1.x + xfq.c * m_vertex1.y) + xf.p.y;
		final double v2x = (xfq.c * m_vertex2.x - xfq.s * m_vertex2.y) + xf.p.x;
		final double v2y = (xfq.s * m_vertex2.x + xfq.c * m_vertex2.y) + xf.p.y;

		lowerBound.x = v1x < v2x ? v1x : v2x;
		lowerBound.y = v1y < v2y ? v1y : v2y;
		upperBound.x = v1x > v2x ? v1x : v2x;
		upperBound.y = v1y > v2y ? v1y : v2y;

		lowerBound.x -= getRadius();
		lowerBound.y -= getRadius();
		upperBound.x += getRadius();
		upperBound.y += getRadius();
	}

	@Override
	public void computeMass(MassData massData, double density) {
		massData.mass = 0.0f;
		massData.center.set(m_vertex1).addLocal(m_vertex2).mulLocal(0.5f);
		massData.I = 0.0f;
	}

	@Override
	public Shape clone() {
		EdgeShape edge = new EdgeShape();
		edge.setRadius(this.getRadius());
		edge.m_hasVertex0 = this.m_hasVertex0;
		edge.m_hasVertex3 = this.m_hasVertex3;
		edge.m_vertex0.set(this.m_vertex0);
		edge.m_vertex1.set(this.m_vertex1);
		edge.m_vertex2.set(this.m_vertex2);
		edge.m_vertex3.set(this.m_vertex3);
		return edge;
	}
}
