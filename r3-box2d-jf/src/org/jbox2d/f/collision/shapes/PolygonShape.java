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

package org.jbox2d.f.collision.shapes;

import org.jbox2d.f.collision.AABB;
import org.jbox2d.f.collision.RayCastInput;
import org.jbox2d.f.collision.RayCastOutput;
import org.jbox2d.f.common.MathUtils;
import org.jbox2d.f.common.Rot;
import org.jbox2d.f.common.Settings;
import org.jbox2d.f.common.Transform;
import org.jbox2d.f.common.Vector2;
import org.jbox2d.f.pooling.arrays.IntArray;
import org.jbox2d.f.pooling.arrays.Vec2Array;

import com.jfixby.scarabei.api.log.L;

/** A convex polygon shape. Polygons have a maximum number of vertices equal to _maxPolygonVertices. In most cases you should not
 * need many vertices for a convex polygon. */
public class PolygonShape extends Shape {
	/** Dump lots of debug information. */
	private final static boolean m_debug = false;

	/** Local position of the shape centroid in parent body frame. */
	public final Vector2 m_centroid = new Vector2();

	/** The vertices of the shape. Note: use getVertexCount(), not m_vertices.length, to get number of active vertices. */
	public final Vector2 m_vertices[];

	/** The normals of the shape. Note: use getVertexCount(), not m_normals.length, to get number of active normals. */
	public final Vector2 m_normals[];

	/** Number of active vertices in the shape. */
	public int m_count;

	// pooling
	private final Vector2 pool1 = new Vector2();
	private final Vector2 pool2 = new Vector2();
	private final Vector2 pool3 = new Vector2();
	private final Vector2 pool4 = new Vector2();
	private final Transform poolt1 = new Transform();

	public PolygonShape () {
		super(ShapeType.POLYGON);

		this.m_count = 0;
		this.m_vertices = new Vector2[Settings.maxPolygonVertices];
		for (int i = 0; i < this.m_vertices.length; i++) {
			this.m_vertices[i] = new Vector2();
		}
		this.m_normals = new Vector2[Settings.maxPolygonVertices];
		for (int i = 0; i < this.m_normals.length; i++) {
			this.m_normals[i] = new Vector2();
		}
		this.setRadius(Settings.polygonRadius);
		this.m_centroid.setZero();
	}

	@Override
	public final Shape clone () {
		final PolygonShape shape = new PolygonShape();
		shape.m_centroid.set(this.m_centroid);
		for (int i = 0; i < shape.m_normals.length; i++) {
			shape.m_normals[i].set(this.m_normals[i]);
			shape.m_vertices[i].set(this.m_vertices[i]);
		}
		shape.setRadius(this.getRadius());
		shape.m_count = this.m_count;
		return shape;
	}

	/** Create a convex hull from the given array of points. The count must be in the range [3, Settings.maxPolygonVertices].
	 *
	 * @warning the points may be re-ordered, even if they form a convex polygon.
	 * @warning collinear points are removed. */
	public final void set (final Vector2[] vertices, final int count) {
		this.set(vertices, count, null, null);
	}

	/** Create a convex hull from the given array of points. The count must be in the range [3, Settings.maxPolygonVertices]. This
	 * method takes an arraypool for pooling.
	 *
	 * @warning the points may be re-ordered, even if they form a convex polygon.
	 * @warning collinear points are removed. */
	public final void set (final Vector2[] verts, final int num, final Vec2Array vecPool, final IntArray intPool) {
		assert (3 <= num && num <= Settings.maxPolygonVertices);
		if (num < 3) {
			this.setAsBox(1.0f, 1.0f);
			return;
		}

		int n = MathUtils.min(num, Settings.maxPolygonVertices);

		// Perform welding and copy vertices into local buffer.
		final Vector2[] ps = (vecPool != null) ? vecPool.get(Settings.maxPolygonVertices)
			: new Vector2[Settings.maxPolygonVertices];
		int tempCount = 0;
		for (int i = 0; i < n; ++i) {
			final Vector2 v = verts[i];
			boolean unique = true;
			for (int j = 0; j < tempCount; ++j) {
				if (MathUtils.distanceSquared(v, ps[j]) < 0.5f * Settings.linearSlop) {
					unique = false;
					break;
				}
			}

			if (unique) {
				ps[tempCount++] = v;
			}
		}

		n = tempCount;
		if (n < 3) {
			// Polygon is degenerate.
			assert (false);
			this.setAsBox(1.0f, 1.0f);
			return;
		}

		// Create the convex hull using the Gift wrapping algorithm
		// http://en.wikipedia.org/wiki/Gift_wrapping_algorithm

		// Find the right most point on the hull
		int i0 = 0;
		float x0 = ps[0].x;
		for (int i = 1; i < n; ++i) {
			final float x = ps[i].x;
			if (x > x0 || (x == x0 && ps[i].y < ps[i0].y)) {
				i0 = i;
				x0 = x;
			}
		}

		final int[] hull = (intPool != null) ? intPool.get(Settings.maxPolygonVertices) : new int[Settings.maxPolygonVertices];
		int m = 0;
		int ih = i0;

		while (true) {
			hull[m] = ih;

			int ie = 0;
			for (int j = 1; j < n; ++j) {
				if (ie == ih) {
					ie = j;
					continue;
				}

				final Vector2 r = this.pool1.set(ps[ie]).subLocal(ps[hull[m]]);
				final Vector2 v = this.pool2.set(ps[j]).subLocal(ps[hull[m]]);
				final float c = Vector2.cross(r, v);
				if (c < 0.0f) {
					ie = j;
				}

				// Collinearity check
				if (c == 0.0f && v.lengthSquared() > r.lengthSquared()) {
					ie = j;
				}
			}

			++m;
			ih = ie;

			if (ie == i0) {
				break;
			}
		}

		this.m_count = m;

		// Copy vertices.
		for (int i = 0; i < this.m_count; ++i) {
			if (this.m_vertices[i] == null) {
				this.m_vertices[i] = new Vector2();
			}
			this.m_vertices[i].set(ps[hull[i]]);
		}

		final Vector2 edge = this.pool1;

		// Compute normals. Ensure the edges have non-zero length.
		for (int i = 0; i < this.m_count; ++i) {
			final int i1 = i;
			final int i2 = i + 1 < this.m_count ? i + 1 : 0;
			edge.set(this.m_vertices[i2]).subLocal(this.m_vertices[i1]);

			assert (edge.lengthSquared() > Settings.EPSILON * Settings.EPSILON);
			Vector2.crossToOutUnsafe(edge, 1f, this.m_normals[i]);
			this.m_normals[i].normalize();
		}

		// Compute the polygon centroid.
		this.computeCentroidToOut(this.m_vertices, this.m_count, this.m_centroid);
	}

	/** Build vertices to represent an axis-aligned box.
	 *
	 * @param hx the half-width.
	 * @param hy the half-height. */
	public final void setAsBox (final float hx, final float hy) {
		this.m_count = 4;
		this.m_vertices[0].set(-hx, -hy);
		this.m_vertices[1].set(hx, -hy);
		this.m_vertices[2].set(hx, hy);
		this.m_vertices[3].set(-hx, hy);
		this.m_normals[0].set(0.0f, -1.0f);
		this.m_normals[1].set(1.0f, 0.0f);
		this.m_normals[2].set(0.0f, 1.0f);
		this.m_normals[3].set(-1.0f, 0.0f);
		this.m_centroid.setZero();
	}

	/** Build vertices to represent an oriented box.
	 *
	 * @param hx the half-width.
	 * @param hy the half-height.
	 * @param center the center of the box in local coordinates.
	 * @param angle the rotation of the box in local coordinates. */
	public final void setAsBox (final float hx, final float hy, final Vector2 center, final float angle) {
		this.m_count = 4;
		this.m_vertices[0].set(-hx, -hy);
		this.m_vertices[1].set(hx, -hy);
		this.m_vertices[2].set(hx, hy);
		this.m_vertices[3].set(-hx, hy);
		this.m_normals[0].set(0.0f, -1.0f);
		this.m_normals[1].set(1.0f, 0.0f);
		this.m_normals[2].set(0.0f, 1.0f);
		this.m_normals[3].set(-1.0f, 0.0f);
		this.m_centroid.set(center);

		final Transform xf = this.poolt1;
		xf.p.set(center);
		xf.q.set(angle);

		// Transform vertices and normals.
		for (int i = 0; i < this.m_count; ++i) {
			Transform.mulToOut(xf, this.m_vertices[i], this.m_vertices[i]);
			Rot.mulToOut(xf.q, this.m_normals[i], this.m_normals[i]);
		}
	}

	@Override
	public int getChildCount () {
		return 1;
	}

	@Override
	public final boolean testPoint (final Transform xf, final Vector2 p) {
		float tempx, tempy;
		final Rot xfq = xf.q;

		tempx = p.x - xf.p.x;
		tempy = p.y - xf.p.y;
		final float pLocalx = xfq.c * tempx + xfq.s * tempy;
		final float pLocaly = -xfq.s * tempx + xfq.c * tempy;

		if (m_debug) {
			System.out.println("--testPoint debug--");
			System.out.println("Vertices: ");
			for (int i = 0; i < this.m_count; ++i) {
				System.out.println(this.m_vertices[i]);
			}
			System.out.println("pLocal: " + pLocalx + ", " + pLocaly);
		}

		for (int i = 0; i < this.m_count; ++i) {
			final Vector2 vertex = this.m_vertices[i];
			final Vector2 normal = this.m_normals[i];
			tempx = pLocalx - vertex.x;
			tempy = pLocaly - vertex.y;
			final float dot = normal.x * tempx + normal.y * tempy;
			if (dot > 0.0f) {
				return false;
			}
		}

		return true;
	}

	@Override
	public final void computeAABB (final AABB aabb, final Transform xf, final int childIndex) {
		final Vector2 lower = aabb.lowerBound;
		final Vector2 upper = aabb.upperBound;
		final Vector2 v1 = this.m_vertices[0];
		final float xfqc = xf.q.c;
		final float xfqs = xf.q.s;
		final float xfpx = xf.p.x;
		final float xfpy = xf.p.y;
		lower.x = (xfqc * v1.x - xfqs * v1.y) + xfpx;
		lower.y = (xfqs * v1.x + xfqc * v1.y) + xfpy;
		upper.x = lower.x;
		upper.y = lower.y;

		for (int i = 1; i < this.m_count; ++i) {
			final Vector2 v2 = this.m_vertices[i];
			// Vec2 v = Mul(xf, m_vertices[i]);
			final float vx = (xfqc * v2.x - xfqs * v2.y) + xfpx;
			final float vy = (xfqs * v2.x + xfqc * v2.y) + xfpy;
			lower.x = lower.x < vx ? lower.x : vx;
			lower.y = lower.y < vy ? lower.y : vy;
			upper.x = upper.x > vx ? upper.x : vx;
			upper.y = upper.y > vy ? upper.y : vy;
		}

		lower.x -= this.m_radius;
		lower.y -= this.m_radius;
		upper.x += this.m_radius;
		upper.y += this.m_radius;
	}

	/** Get the vertex count.
	 *
	 * @return */
	public final int getVertexCount () {
		return this.m_count;
	}

	/** Get a vertex by index.
	 *
	 * @param index
	 * @return */
	public final Vector2 getVertex (final int index) {
		assert (0 <= index && index < this.m_count);
		return this.m_vertices[index];
	}

	@Override
	public float computeDistanceToOut (final Transform xf, final Vector2 p, final int childIndex, final Vector2 normalOut) {
		final float xfqc = xf.q.c;
		final float xfqs = xf.q.s;
		float tx = p.x - xf.p.x;
		float ty = p.y - xf.p.y;
		final float pLocalx = xfqc * tx + xfqs * ty;
		final float pLocaly = -xfqs * tx + xfqc * ty;

		float maxDistance = -Float.MAX_VALUE;
		float normalForMaxDistanceX = pLocalx;
		float normalForMaxDistanceY = pLocaly;

		for (int i = 0; i < this.m_count; ++i) {
			final Vector2 vertex = this.m_vertices[i];
			final Vector2 normal = this.m_normals[i];
			tx = pLocalx - vertex.x;
			ty = pLocaly - vertex.y;
			final float dot = normal.x * tx + normal.y * ty;
			if (dot > maxDistance) {
				maxDistance = dot;
				normalForMaxDistanceX = normal.x;
				normalForMaxDistanceY = normal.y;
			}
		}

		float distance;
		if (maxDistance > 0) {
			float minDistanceX = normalForMaxDistanceX;
			float minDistanceY = normalForMaxDistanceY;
			float minDistance2 = maxDistance * maxDistance;
			for (int i = 0; i < this.m_count; ++i) {
				final Vector2 vertex = this.m_vertices[i];
				final float distanceVecX = pLocalx - vertex.x;
				final float distanceVecY = pLocaly - vertex.y;
				final float distance2 = (distanceVecX * distanceVecX + distanceVecY * distanceVecY);
				if (minDistance2 > distance2) {
					minDistanceX = distanceVecX;
					minDistanceY = distanceVecY;
					minDistance2 = distance2;
				}
			}
			distance = MathUtils.sqrt(minDistance2);
			normalOut.x = xfqc * minDistanceX - xfqs * minDistanceY;
			normalOut.y = xfqs * minDistanceX + xfqc * minDistanceY;
			normalOut.normalize();
		} else {
			distance = maxDistance;
			normalOut.x = xfqc * normalForMaxDistanceX - xfqs * normalForMaxDistanceY;
			normalOut.y = xfqs * normalForMaxDistanceX + xfqc * normalForMaxDistanceY;
		}

		return distance;
	}

	@Override
	public final boolean raycast (final RayCastOutput output, final RayCastInput input, final Transform xf, final int childIndex) {
		final float xfqc = xf.q.c;
		final float xfqs = xf.q.s;
		final Vector2 xfp = xf.p;
		float tempx, tempy;
		// b2Vec2 p1 = b2MulT(xf.q, input.p1 - xf.p);
		// b2Vec2 p2 = b2MulT(xf.q, input.p2 - xf.p);
		tempx = input.p1.x - xfp.x;
		tempy = input.p1.y - xfp.y;
		final float p1x = xfqc * tempx + xfqs * tempy;
		final float p1y = -xfqs * tempx + xfqc * tempy;

		tempx = input.p2.x - xfp.x;
		tempy = input.p2.y - xfp.y;
		final float p2x = xfqc * tempx + xfqs * tempy;
		final float p2y = -xfqs * tempx + xfqc * tempy;

		final float dx = p2x - p1x;
		final float dy = p2y - p1y;

		float lower = 0, upper = input.maxFraction;

		int index = -1;

		for (int i = 0; i < this.m_count; ++i) {
			final Vector2 normal = this.m_normals[i];
			final Vector2 vertex = this.m_vertices[i];
			// p = p1 + a * d
			// dot(normal, p - v) = 0
			// dot(normal, p1 - v) + a * dot(normal, d) = 0
			final float tempxn = vertex.x - p1x;
			final float tempyn = vertex.y - p1y;
			final float numerator = normal.x * tempxn + normal.y * tempyn;
			final float denominator = normal.x * dx + normal.y * dy;

			if (denominator == 0.0f) {
				if (numerator < 0.0f) {
					return false;
				}
			} else {
				// Note: we want this predicate without division:
				// lower < numerator / denominator, where denominator < 0
				// Since denominator < 0, we have to flip the inequality:
				// lower < numerator / denominator <==> denominator * lower >
				// numerator.
				if (denominator < 0.0f && numerator < lower * denominator) {
					// Increase lower.
					// The segment enters this half-space.
					lower = numerator / denominator;
					index = i;
				} else if (denominator > 0.0f && numerator < upper * denominator) {
					// Decrease upper.
					// The segment exits this half-space.
					upper = numerator / denominator;
				}
			}

			if (upper < lower) {
				return false;
			}
		}

		assert (0.0f <= lower && lower <= input.maxFraction);

		if (index >= 0) {
			output.fraction = lower;
			// normal = Mul(xf.R, m_normals[index]);
			final Vector2 normal = this.m_normals[index];
			final Vector2 out = output.normal;
			out.x = xfqc * normal.x - xfqs * normal.y;
			out.y = xfqs * normal.x + xfqc * normal.y;
			return true;
		}
		return false;
	}

	public final void computeCentroidToOut (final Vector2[] vs, final int count, final Vector2 out) {
		assert (count >= 3);

		out.set(0.0f, 0.0f);
		float area = 0.0f;

		// pRef is the reference point for forming triangles.
		// It's location doesn't change the result (except for rounding error).
		final Vector2 pRef = this.pool1;
		pRef.setZero();

		final Vector2 e1 = this.pool2;
		final Vector2 e2 = this.pool3;

		final float inv3 = 1.0f / 3.0f;

		for (int i = 0; i < count; ++i) {
			// Triangle vertices.
			final Vector2 p1 = pRef;
			final Vector2 p2 = vs[i];
			final Vector2 p3 = i + 1 < count ? vs[i + 1] : vs[0];

			e1.set(p2).subLocal(p1);
			e2.set(p3).subLocal(p1);

			final float D = Vector2.cross(e1, e2);

			final float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			e1.set(p1).addLocal(p2).addLocal(p3).mulLocal(triangleArea * inv3);
			out.addLocal(e1);
		}

		// Centroid
		assert (area > Settings.EPSILON);
		out.mulLocal(1.0f / area);
	}

	@Override
	public void computeMass (final MassData massData, final float density) {
		// Polygon mass, centroid, and inertia.
		// Let rho be the polygon density in mass per unit area.
		// Then:
		// mass = rho * int(dA)
		// centroid.x = (1/mass) * rho * int(x * dA)
		// centroid.y = (1/mass) * rho * int(y * dA)
		// I = rho * int((x*x + y*y) * dA)
		//
		// We can compute these integrals by summing all the integrals
		// for each triangle of the polygon. To evaluate the integral
		// for a single triangle, we make a change of variables to
		// the (u,v) coordinates of the triangle:
		// x = x0 + e1x * u + e2x * v
		// y = y0 + e1y * u + e2y * v
		// where 0 <= u && 0 <= v && u + v <= 1.
		//
		// We integrate u from [0,1-v] and then v from [0,1].
		// We also need to use the Jacobian of the transformation:
		// D = cross(e1, e2)
		//
		// Simplification: triangle centroid = (1/3) * (p1 + p2 + p3)
		//
		// The rest of the derivation is handled by computer algebra.

		assert (this.m_count >= 3);

		final Vector2 center = this.pool1;
		center.setZero();
		float area = 0.0f;
		float I = 0.0f;

		// pRef is the reference point for forming triangles.
		// It's location doesn't change the result (except for rounding error).
		final Vector2 s = this.pool2;
		s.setZero();
		// This code would put the reference point inside the polygon.
		for (int i = 0; i < this.m_count; ++i) {
			s.addLocal(this.m_vertices[i]);
		}
		s.mulLocal(1.0f / this.m_count);

		final float k_inv3 = 1.0f / 3.0f;

		final Vector2 e1 = this.pool3;
		final Vector2 e2 = this.pool4;

		for (int i = 0; i < this.m_count; ++i) {
			// Triangle vertices.
			e1.set(this.m_vertices[i]).subLocal(s);
			e2.set(s).negateLocal().addLocal(i + 1 < this.m_count ? this.m_vertices[i + 1] : this.m_vertices[0]);

			final float D = Vector2.cross(e1, e2);

			final float triangleArea = 0.5f * D;
			area += triangleArea;

			// Area weighted centroid
			center.x += triangleArea * k_inv3 * (e1.x + e2.x);
			center.y += triangleArea * k_inv3 * (e1.y + e2.y);

			final float ex1 = e1.x, ey1 = e1.y;
			final float ex2 = e2.x, ey2 = e2.y;

			final float intx2 = ex1 * ex1 + ex2 * ex1 + ex2 * ex2;
			final float inty2 = ey1 * ey1 + ey2 * ey1 + ey2 * ey2;

			I += (0.25f * k_inv3 * D) * (intx2 + inty2);
		}

		// Total mass
		massData.mass = density * area;

		// Center of mass
		if (!(area > Settings.EPSILON)) {
			L.d("area", area);
			L.d("EPSILON", Settings.EPSILON);
			L.d("Body is too small");
			// Err.reportError("Body is too small");
		}
		assert (area > Settings.EPSILON);
		center.mulLocal(1.0f / area);
		massData.center.set(center).addLocal(s);

		// Inertia tensor relative to the local origin (point s)
		massData.I = I * density;

		// Shift to center of mass then to original body origin.
		massData.I += massData.mass * (Vector2.dot(massData.center, massData.center));
	}

	/** Validate convexity. This is a very time consuming operation.
	 *
	 * @return */
	public boolean validate () {
		for (int i = 0; i < this.m_count; ++i) {
			final int i1 = i;
			final int i2 = i < this.m_count - 1 ? i1 + 1 : 0;
			final Vector2 p = this.m_vertices[i1];
			final Vector2 e = this.pool1.set(this.m_vertices[i2]).subLocal(p);

			for (int j = 0; j < this.m_count; ++j) {
				if (j == i1 || j == i2) {
					continue;
				}

				final Vector2 v = this.pool2.set(this.m_vertices[j]).subLocal(p);
				final float c = Vector2.cross(e, v);
				if (c < 0.0f) {
					return false;
				}
			}
		}

		return true;
	}

	/** Get the vertices in local coordinates. */
	public Vector2[] getVertices () {
		return this.m_vertices;
	}

	/** Get the edge normal vectors. There is one for each vertex. */
	public Vector2[] getNormals () {
		return this.m_normals;
	}

	/** Get the centroid and apply the supplied transform. */
	public Vector2 centroid (final Transform xf) {
		return Transform.mul(xf, this.m_centroid);
	}

	/** Get the centroid and apply the supplied transform. */
	public Vector2 centroidToOut (final Transform xf, final Vector2 out) {
		Transform.mulToOutUnsafe(xf, this.m_centroid, out);
		return out;
	}
}
