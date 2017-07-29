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

package org.jbox2d.d.collision;

import org.jbox2d.d.collision.Distance.SimplexCache;
import org.jbox2d.d.collision.Manifold.ManifoldType;
import org.jbox2d.d.collision.shapes.CircleShape;
import org.jbox2d.d.collision.shapes.EdgeShape;
import org.jbox2d.d.collision.shapes.PolygonShape;
import org.jbox2d.d.collision.shapes.Shape;
import org.jbox2d.d.common.MathUtils;
import org.jbox2d.d.common.Rot;
import org.jbox2d.d.common.Settings;
import org.jbox2d.d.common.Transform;
import org.jbox2d.d.common.Vector2;
import org.jbox2d.d.pooling.IWorldPool;

import com.jfixby.scarabei.api.err.Err;

/** Functions used for computing contact points, distance queries, and TOI queries. Collision methods are non-static for pooling
 * speed, retrieve a collision object from the {@link SingletonPool}. Should not be finalructed.
 *
 * @author Daniel Murphy */
public class Collision {
	public static final int NULL_FEATURE = Integer.MAX_VALUE;

	private final IWorldPool pool;

	public Collision (final IWorldPool argPool) {
		this.incidentEdge[0] = new ClipVertex();
		this.incidentEdge[1] = new ClipVertex();
		this.clipPoints1[0] = new ClipVertex();
		this.clipPoints1[1] = new ClipVertex();
		this.clipPoints2[0] = new ClipVertex();
		this.clipPoints2[1] = new ClipVertex();
		this.pool = argPool;
	}

	private final DistanceInput input = new DistanceInput();
	private final SimplexCache cache = new SimplexCache();
	private final DistanceOutput output = new DistanceOutput();

	/** Determine if two generic shapes overlap.
	 *
	 * @param shapeA
	 * @param shapeB
	 * @param xfA
	 * @param xfB
	 * @return */
	public final boolean testOverlap (final Shape shapeA, final int indexA, final Shape shapeB, final int indexB,
		final Transform xfA, final Transform xfB) {
		this.input.proxyA.set(shapeA, indexA);
		this.input.proxyB.set(shapeB, indexB);
		this.input.transformA.set(xfA);
		this.input.transformB.set(xfB);
		this.input.useRadii = true;

		this.cache.count = 0;

		this.pool.getDistance().distance(this.output, this.cache, this.input);
		// djm note: anything significant about 10.0f?
		return this.output.distance < 10.0f * Settings.EPSILON;
	}

	/** Compute the point states given two manifolds. The states pertain to the transition from manifold1 to manifold2. So state1
	 * is either persist or remove while state2 is either add or persist.
	 *
	 * @param state1
	 * @param state2
	 * @param manifold1
	 * @param manifold2 */
	public static final void getPointStates (final PointState[] state1, final PointState[] state2, final Manifold manifold1,
		final Manifold manifold2) {

		for (int i = 0; i < Settings.maxManifoldPoints; i++) {
			state1[i] = PointState.NULL_STATE;
			state2[i] = PointState.NULL_STATE;
		}

		// Detect persists and removes.
		for (int i = 0; i < manifold1.pointCount; i++) {
			final ContactID id = manifold1.points[i].id;

			state1[i] = PointState.REMOVE_STATE;

			for (int j = 0; j < manifold2.pointCount; j++) {
				if (manifold2.points[j].id.isEqual(id)) {
					state1[i] = PointState.PERSIST_STATE;
					break;
				}
			}
		}

		// Detect persists and adds
		for (int i = 0; i < manifold2.pointCount; i++) {
			final ContactID id = manifold2.points[i].id;

			state2[i] = PointState.ADD_STATE;

			for (int j = 0; j < manifold1.pointCount; j++) {
				if (manifold1.points[j].id.isEqual(id)) {
					state2[i] = PointState.PERSIST_STATE;
					break;
				}
			}
		}
	}

	/** Clipping for contact manifolds. Sutherland-Hodgman clipping.
	 *
	 * @param vOut
	 * @param vIn
	 * @param normal
	 * @param offset
	 * @return */
	public static final int clipSegmentToLine (final ClipVertex[] vOut, final ClipVertex[] vIn, final Vector2 normal,
		final double offset, final int vertexIndexA) {

		// Start with no output points
		int numOut = 0;
		final ClipVertex vIn0 = vIn[0];
		final ClipVertex vIn1 = vIn[1];
		final Vector2 vIn0v = vIn0.v;
		final Vector2 vIn1v = vIn1.v;

		// Calculate the distance of end points to the line
		final double distance0 = Vector2.dot(normal, vIn0v) - offset;
		final double distance1 = Vector2.dot(normal, vIn1v) - offset;

		// If the points are behind the plane
		if (distance0 <= 0.0f) {
			vOut[numOut++].set(vIn0);
		}
		if (distance1 <= 0.0f) {
			vOut[numOut++].set(vIn1);
		}

		// If the points are on different sides of the plane
		if (distance0 * distance1 < 0.0f) {
			// Find intersection point of edge and plane
			final double interp = distance0 / (distance0 - distance1);

			final ClipVertex vOutNO = vOut[numOut];
			// vOut[numOut].v = vIn[0].v + interp * (vIn[1].v - vIn[0].v);
			vOutNO.v.x = vIn0v.x + interp * (vIn1v.x - vIn0v.x);
			vOutNO.v.y = vIn0v.y + interp * (vIn1v.y - vIn0v.y);

			// VertexA is hitting edgeB.
			vOutNO.id.indexA = (byte)vertexIndexA;
			vOutNO.id.indexB = vIn0.id.indexB;
			vOutNO.id.typeA = (byte)ContactID.Type.VERTEX.ordinal();
			vOutNO.id.typeB = (byte)ContactID.Type.FACE.ordinal();
			++numOut;
		}

		return numOut;
	}

	// #### COLLISION STUFF (not from collision.h or collision.cpp) ####

	// djm pooling
	private static Vector2 d = new Vector2();

	/** Compute the collision manifold between two circles.
	 *
	 * @param manifold
	 * @param circle1
	 * @param xfA
	 * @param circle2
	 * @param xfB */
	public final void collideCircles (final Manifold manifold, final CircleShape circle1, final Transform xfA,
		final CircleShape circle2, final Transform xfB) {
		manifold.pointCount = 0;
		// before inline:
		// Transform.mulToOut(xfA, circle1.m_p, pA);
		// Transform.mulToOut(xfB, circle2.m_p, pB);
		// d.set(pB).subLocal(pA);
		// double distSqr = d.x * d.x + d.y * d.y;

		// after inline:
		final Vector2 circle1p = circle1.m_p;
		final Vector2 circle2p = circle2.m_p;
		final double pAx = (xfA.q.c * circle1p.x - xfA.q.s * circle1p.y) + xfA.p.x;
		final double pAy = (xfA.q.s * circle1p.x + xfA.q.c * circle1p.y) + xfA.p.y;
		final double pBx = (xfB.q.c * circle2p.x - xfB.q.s * circle2p.y) + xfB.p.x;
		final double pBy = (xfB.q.s * circle2p.x + xfB.q.c * circle2p.y) + xfB.p.y;
		final double dx = pBx - pAx;
		final double dy = pBy - pAy;
		final double distSqr = dx * dx + dy * dy;
		// end inline

		final double radius = circle1.getRadius() + circle2.getRadius();
		if (distSqr > radius * radius) {
			return;
		}

		manifold.type = ManifoldType.CIRCLES;
		manifold.localPoint.set(circle1p);
		manifold.localNormal.setZero();
		manifold.pointCount = 1;

		manifold.points[0].localPoint.set(circle2p);
		manifold.points[0].id.zero();
	}

	// djm pooling, and from above

	/** Compute the collision manifold between a polygon and a circle.
	 *
	 * @param manifold
	 * @param polygon
	 * @param xfA
	 * @param circle
	 * @param xfB */
	public final void collidePolygonAndCircle (final Manifold manifold, final PolygonShape polygon, final Transform xfA,
		final CircleShape circle, final Transform xfB) {
		manifold.pointCount = 0;
		// Vec2 v = circle.m_p;

		// Compute circle position in the frame of the polygon.
		// before inline:
		// Transform.mulToOutUnsafe(xfB, circle.m_p, c);
		// Transform.mulTransToOut(xfA, c, cLocal);
		// final double cLocalx = cLocal.x;
		// final double cLocaly = cLocal.y;
		// after inline:
		final Vector2 circlep = circle.m_p;
		final Rot xfBq = xfB.q;
		final Rot xfAq = xfA.q;
		final double cx = (xfBq.c * circlep.x - xfBq.s * circlep.y) + xfB.p.x;
		final double cy = (xfBq.s * circlep.x + xfBq.c * circlep.y) + xfB.p.y;
		final double px = cx - xfA.p.x;
		final double py = cy - xfA.p.y;
		final double cLocalx = (xfAq.c * px + xfAq.s * py);
		final double cLocaly = (-xfAq.s * px + xfAq.c * py);
		// end inline

		// Find the min separating edge.
		int normalIndex = 0;
		double separation = -Double.MAX_VALUE;
		final double radius = polygon.getRadius() + circle.getRadius();
		final int vertexCount = polygon.m_count;
		double s;
		final Vector2[] vertices = polygon.m_vertices;
		final Vector2[] normals = polygon.m_normals;

		for (int i = 0; i < vertexCount; i++) {
			// before inline
			// temp.set(cLocal).subLocal(vertices[i]);
			// double s = Vec2.dot(normals[i], temp);
			// after inline
			final Vector2 vertex = vertices[i];
			final double tempx = cLocalx - vertex.x;
			final double tempy = cLocaly - vertex.y;
			s = normals[i].x * tempx + normals[i].y * tempy;

			if (s > radius) {
				// early out
				return;
			}

			if (s > separation) {
				separation = s;
				normalIndex = i;
			}
		}

		// Vertices that subtend the incident face.
		final int vertIndex1 = normalIndex;
		final int vertIndex2 = vertIndex1 + 1 < vertexCount ? vertIndex1 + 1 : 0;
		final Vector2 v1 = vertices[vertIndex1];
		final Vector2 v2 = vertices[vertIndex2];

		// If the center is inside the polygon ...
		if (separation < Settings.EPSILON) {
			manifold.pointCount = 1;
			manifold.type = ManifoldType.FACE_A;

			// before inline:
			// manifold.localNormal.set(normals[normalIndex]);
			// manifold.localPoint.set(v1).addLocal(v2).mulLocal(.5f);
			// manifold.points[0].localPoint.set(circle.m_p);
			// after inline:
			final Vector2 normal = normals[normalIndex];
			manifold.localNormal.x = normal.x;
			manifold.localNormal.y = normal.y;
			manifold.localPoint.x = (v1.x + v2.x) * .5f;
			manifold.localPoint.y = (v1.y + v2.y) * .5f;
			final ManifoldPoint mpoint = manifold.points[0];
			mpoint.localPoint.x = circlep.x;
			mpoint.localPoint.y = circlep.y;
			mpoint.id.zero();
			// end inline

			return;
		}

		// Compute barycentric coordinates
		// before inline:
		// temp.set(cLocal).subLocal(v1);
		// temp2.set(v2).subLocal(v1);
		// double u1 = Vec2.dot(temp, temp2);
		// temp.set(cLocal).subLocal(v2);
		// temp2.set(v1).subLocal(v2);
		// double u2 = Vec2.dot(temp, temp2);
		// after inline:
		final double tempX = cLocalx - v1.x;
		final double tempY = cLocaly - v1.y;
		final double temp2X = v2.x - v1.x;
		final double temp2Y = v2.y - v1.y;
		final double u1 = tempX * temp2X + tempY * temp2Y;

		final double temp3X = cLocalx - v2.x;
		final double temp3Y = cLocaly - v2.y;
		final double temp4X = v1.x - v2.x;
		final double temp4Y = v1.y - v2.y;
		final double u2 = temp3X * temp4X + temp3Y * temp4Y;
		// end inline

		if (u1 <= 0f) {
			// inlined
			final double dx = cLocalx - v1.x;
			final double dy = cLocaly - v1.y;
			if (dx * dx + dy * dy > radius * radius) {
				return;
			}

			manifold.pointCount = 1;
			manifold.type = ManifoldType.FACE_A;
			// before inline:
			// manifold.localNormal.set(cLocal).subLocal(v1);
			// after inline:
			manifold.localNormal.x = cLocalx - v1.x;
			manifold.localNormal.y = cLocaly - v1.y;
			// end inline
			manifold.localNormal.normalize();
			manifold.localPoint.set(v1);
			manifold.points[0].localPoint.set(circlep);
			manifold.points[0].id.zero();
		} else if (u2 <= 0.0f) {
			// inlined
			final double dx = cLocalx - v2.x;
			final double dy = cLocaly - v2.y;
			if (dx * dx + dy * dy > radius * radius) {
				return;
			}

			manifold.pointCount = 1;
			manifold.type = ManifoldType.FACE_A;
			// before inline:
			// manifold.localNormal.set(cLocal).subLocal(v2);
			// after inline:
			manifold.localNormal.x = cLocalx - v2.x;
			manifold.localNormal.y = cLocaly - v2.y;
			// end inline
			manifold.localNormal.normalize();
			manifold.localPoint.set(v2);
			manifold.points[0].localPoint.set(circlep);
			manifold.points[0].id.zero();
		} else {
			// Vec2 faceCenter = 0.5f * (v1 + v2);
			// (temp is faceCenter)
			// before inline:
			// temp.set(v1).addLocal(v2).mulLocal(.5f);
			//
			// temp2.set(cLocal).subLocal(temp);
			// separation = Vec2.dot(temp2, normals[vertIndex1]);
			// if (separation > radius) {
			// return;
			// }
			// after inline:
			final double fcx = (v1.x + v2.x) * .5f;
			final double fcy = (v1.y + v2.y) * .5f;

			final double tx = cLocalx - fcx;
			final double ty = cLocaly - fcy;
			final Vector2 normal = normals[vertIndex1];
			separation = tx * normal.x + ty * normal.y;
			if (separation > radius) {
				return;
			}
			// end inline

			manifold.pointCount = 1;
			manifold.type = ManifoldType.FACE_A;
			manifold.localNormal.set(normals[vertIndex1]);
			manifold.localPoint.x = fcx; // (faceCenter)
			manifold.localPoint.y = fcy;
			manifold.points[0].localPoint.set(circlep);
			manifold.points[0].id.zero();
		}
	}

	// djm pooling, and from above
	private final Vector2 temp = new Vector2();
	private final Transform xf = new Transform();
	private final Vector2 n = new Vector2();
	private final Vector2 v1 = new Vector2();

	/** Find the max separation between poly1 and poly2 using edge normals from poly1.
	 *
	 * @param edgeIndex
	 * @param poly1
	 * @param xf1
	 * @param poly2
	 * @param xf2
	 * @return */
	public final void findMaxSeparation (final EdgeResults results, final PolygonShape poly1, final Transform xf1,
		final PolygonShape poly2, final Transform xf2) {
		final int count1 = poly1.m_count;
		final int count2 = poly2.m_count;
		final Vector2[] n1s = poly1.m_normals;
		final Vector2[] v1s = poly1.m_vertices;
		final Vector2[] v2s = poly2.m_vertices;

		Transform.mulTransToOutUnsafe(xf2, xf1, this.xf);
		final Rot xfq = this.xf.q;

		int bestIndex = 0;
		double maxSeparation = -Double.MAX_VALUE;
		for (int i = 0; i < count1; i++) {
			// Get poly1 normal in frame2.
			Rot.mulToOutUnsafe(xfq, n1s[i], this.n);
			Transform.mulToOutUnsafe(this.xf, v1s[i], this.v1);

			// Find deepest point for normal i.
			double si = Double.MAX_VALUE;
			for (int j = 0; j < count2; ++j) {
				final Vector2 v2sj = v2s[j];
				final double sij = this.n.x * (v2sj.x - this.v1.x) + this.n.y * (v2sj.y - this.v1.y);
				if (sij < si) {
					si = sij;
				}
			}

			if (si > maxSeparation) {
				maxSeparation = si;
				bestIndex = i;
			}
		}

		results.edgeIndex = bestIndex;
		results.separation = maxSeparation;
	}

	public final void findIncidentEdge (final ClipVertex[] c, final PolygonShape poly1, final Transform xf1, final int edge1,
		final PolygonShape poly2, final Transform xf2) {
		final int count1 = poly1.m_count;
		final Vector2[] normals1 = poly1.m_normals;

		final int count2 = poly2.m_count;
		final Vector2[] vertices2 = poly2.m_vertices;
		final Vector2[] normals2 = poly2.m_normals;

		assert (0 <= edge1 && edge1 < count1);

		final ClipVertex c0 = c[0];
		final ClipVertex c1 = c[1];
		final Rot xf1q = xf1.q;
		final Rot xf2q = xf2.q;

		// Get the normal of the reference edge in poly2's frame.
		// Vec2 normal1 = MulT(xf2.R, Mul(xf1.R, normals1[edge1]));
		// before inline:
		// Rot.mulToOutUnsafe(xf1.q, normals1[edge1], normal1); // temporary
		// Rot.mulTrans(xf2.q, normal1, normal1);
		// after inline:
		final Vector2 v = normals1[edge1];
		final double tempx = xf1q.c * v.x - xf1q.s * v.y;
		final double tempy = xf1q.s * v.x + xf1q.c * v.y;
		final double normal1x = xf2q.c * tempx + xf2q.s * tempy;
		final double normal1y = -xf2q.s * tempx + xf2q.c * tempy;

		// end inline

		// Find the incident edge on poly2.
		int index = 0;
		double minDot = Double.MAX_VALUE;
		for (int i = 0; i < count2; ++i) {
			final Vector2 b = normals2[i];
			final double dot = normal1x * b.x + normal1y * b.y;
			if (dot < minDot) {
				minDot = dot;
				index = i;
			}
		}

		// Build the clip vertices for the incident edge.
		final int i1 = index;
		final int i2 = i1 + 1 < count2 ? i1 + 1 : 0;

		// c0.v = Mul(xf2, vertices2[i1]);
		final Vector2 v1 = vertices2[i1];
		final Vector2 out = c0.v;
		out.x = (xf2q.c * v1.x - xf2q.s * v1.y) + xf2.p.x;
		out.y = (xf2q.s * v1.x + xf2q.c * v1.y) + xf2.p.y;
		c0.id.indexA = (byte)edge1;
		c0.id.indexB = (byte)i1;
		c0.id.typeA = (byte)ContactID.Type.FACE.ordinal();
		c0.id.typeB = (byte)ContactID.Type.VERTEX.ordinal();

		// c1.v = Mul(xf2, vertices2[i2]);
		final Vector2 v2 = vertices2[i2];
		final Vector2 out1 = c1.v;
		out1.x = (xf2q.c * v2.x - xf2q.s * v2.y) + xf2.p.x;
		out1.y = (xf2q.s * v2.x + xf2q.c * v2.y) + xf2.p.y;
		c1.id.indexA = (byte)edge1;
		c1.id.indexB = (byte)i2;
		c1.id.typeA = (byte)ContactID.Type.FACE.ordinal();
		c1.id.typeB = (byte)ContactID.Type.VERTEX.ordinal();
	}

	private final EdgeResults results1 = new EdgeResults();
	private final EdgeResults results2 = new EdgeResults();
	private final ClipVertex[] incidentEdge = new ClipVertex[2];
	private final Vector2 localTangent = new Vector2();
	private final Vector2 localNormal = new Vector2();
	private final Vector2 planePoint = new Vector2();
	private final Vector2 tangent = new Vector2();
	private final Vector2 v11 = new Vector2();
	private final Vector2 v12 = new Vector2();
	private final ClipVertex[] clipPoints1 = new ClipVertex[2];
	private final ClipVertex[] clipPoints2 = new ClipVertex[2];

	/** Compute the collision manifold between two polygons.
	 *
	 * @param manifold
	 * @param polygon1
	 * @param xf1
	 * @param polygon2
	 * @param xf2 */
	public final void collidePolygons (final Manifold manifold, final PolygonShape polyA, final Transform xfA,
		final PolygonShape polyB, final Transform xfB) {
		// Find edge normal of max separation on A - return if separating axis
		// is found
		// Find edge normal of max separation on B - return if separation axis
		// is found
		// Choose reference edge as min(minA, minB)
		// Find incident edge
		// Clip

		// The normal points from 1 to 2

		manifold.pointCount = 0;
		final double totalRadius = polyA.getRadius() + polyB.getRadius();

		this.findMaxSeparation(this.results1, polyA, xfA, polyB, xfB);
		if (this.results1.separation > totalRadius) {
			return;
		}

		this.findMaxSeparation(this.results2, polyB, xfB, polyA, xfA);
		if (this.results2.separation > totalRadius) {
			return;
		}

		final PolygonShape poly1; // reference polygon
		final PolygonShape poly2; // incident polygon
		Transform xf1, xf2;
		int edge1; // reference edge
		boolean flip;
		final double k_tol = 0.1f * Settings.linearSlop_collision;

		if (this.results2.separation > this.results1.separation + k_tol) {
			poly1 = polyB;
			poly2 = polyA;
			xf1 = xfB;
			xf2 = xfA;
			edge1 = this.results2.edgeIndex;
			manifold.type = ManifoldType.FACE_B;
			flip = true;
		} else {
			poly1 = polyA;
			poly2 = polyB;
			xf1 = xfA;
			xf2 = xfB;
			edge1 = this.results1.edgeIndex;
			manifold.type = ManifoldType.FACE_A;
			flip = false;
		}
		final Rot xf1q = xf1.q;

		this.findIncidentEdge(this.incidentEdge, poly1, xf1, edge1, poly2, xf2);

		final int count1 = poly1.m_count;
		final Vector2[] vertices1 = poly1.m_vertices;

		final int iv1 = edge1;
		final int iv2 = edge1 + 1 < count1 ? edge1 + 1 : 0;
		this.v11.set(vertices1[iv1]);
		this.v12.set(vertices1[iv2]);
		this.localTangent.x = this.v12.x - this.v11.x;
		this.localTangent.y = this.v12.y - this.v11.y;
		this.localTangent.normalize();

		// Vec2 localNormal = Vec2.cross(dv, 1.0f);
		this.localNormal.x = +1 * this.localTangent.y;
		this.localNormal.y = -1 * this.localTangent.x;

		// Vec2 planePoint = 0.5f * (v11+ v12);
		this.planePoint.x = (this.v11.x + this.v12.x) * .5f;
		this.planePoint.y = (this.v11.y + this.v12.y) * .5f;

		// Rot.mulToOutUnsafe(xf1.q, localTangent, tangent);
		this.tangent.x = xf1q.c * this.localTangent.x - xf1q.s * this.localTangent.y;
		this.tangent.y = xf1q.s * this.localTangent.x + xf1q.c * this.localTangent.y;

		// Vec2.crossToOutUnsafe(tangent, 1f, normal);
		final double normalx = 1f * this.tangent.y;
		final double normaly = -1f * this.tangent.x;

		Transform.mulToOut(xf1, this.v11, this.v11);
		Transform.mulToOut(xf1, this.v12, this.v12);
		// v11 = Mul(xf1, v11);
		// v12 = Mul(xf1, v12);

		// Face offset
		// double frontOffset = Vec2.dot(normal, v11);
		final double frontOffset = normalx * this.v11.x + normaly * this.v11.y;

		// Side offsets, extended by polytope skin thickness.
		// double sideOffset1 = -Vec2.dot(tangent, v11) + totalRadius;
		// double sideOffset2 = Vec2.dot(tangent, v12) + totalRadius;
		final double sideOffset1 = -(this.tangent.x * this.v11.x + this.tangent.y * this.v11.y) + totalRadius;
		final double sideOffset2 = this.tangent.x * this.v12.x + this.tangent.y * this.v12.y + totalRadius;

		// Clip incident edge against extruded edge1 side edges.
		// ClipVertex clipPoints1[2];
		// ClipVertex clipPoints2[2];
		int np;

		// Clip to box side 1
		// np = ClipSegmentToLine(clipPoints1, incidentEdge, -sideNormal,
		// sideOffset1);
		this.tangent.negateLocal();
		np = clipSegmentToLine(this.clipPoints1, this.incidentEdge, this.tangent, sideOffset1, iv1);
		this.tangent.negateLocal();

		if (np < 2) {
			return;
		}

		// Clip to negative box side 1
		np = clipSegmentToLine(this.clipPoints2, this.clipPoints1, this.tangent, sideOffset2, iv2);

		if (np < 2) {
			return;
		}

		// Now clipPoints2 contains the clipped points.
		manifold.localNormal.set(this.localNormal);
		manifold.localPoint.set(this.planePoint);

		int pointCount = 0;
		for (int i = 0; i < Settings.maxManifoldPoints; ++i) {
			// double separation = Vec2.dot(normal, clipPoints2[i].v) -
			// frontOffset;
			final double separation = normalx * this.clipPoints2[i].v.x + normaly * this.clipPoints2[i].v.y - frontOffset;

			if (separation <= totalRadius) {
				final ManifoldPoint cp = manifold.points[pointCount];
				// cp.m_localPoint = MulT(xf2, clipPoints2[i].v);
				final Vector2 out = cp.localPoint;
				final double px = this.clipPoints2[i].v.x - xf2.p.x;
				final double py = this.clipPoints2[i].v.y - xf2.p.y;
				out.x = (xf2.q.c * px + xf2.q.s * py);
				out.y = (-xf2.q.s * px + xf2.q.c * py);
				cp.id.set(this.clipPoints2[i].id);
				if (flip) {
					// Swap features
					cp.id.flip();
				}
				++pointCount;
			}
		}

		manifold.pointCount = pointCount;
	}

	private final Vector2 Q = new Vector2();
	private final Vector2 e = new Vector2();
	private final ContactID cf = new ContactID();
	private final Vector2 e1 = new Vector2();
	private final Vector2 P = new Vector2();

	// Compute contact points for edge versus circle.
	// This accounts for edge connectivity.
	public void collideEdgeAndCircle (final Manifold manifold, final EdgeShape edgeA, final Transform xfA,
		final CircleShape circleB, final Transform xfB) {
		manifold.pointCount = 0;

		// Compute circle in frame of edge
		// Vec2 Q = MulT(xfA, Mul(xfB, circleB.m_p));
		Transform.mulToOutUnsafe(xfB, circleB.m_p, this.temp);
		Transform.mulTransToOutUnsafe(xfA, this.temp, this.Q);

		final Vector2 A = edgeA.m_vertex1;
		final Vector2 B = edgeA.m_vertex2;
		this.e.set(B).subLocal(A);

		// Barycentric coordinates
		final double u = Vector2.dot(this.e, this.temp.set(B).subLocal(this.Q));
		final double v = Vector2.dot(this.e, this.temp.set(this.Q).subLocal(A));

		final double radius = edgeA.getRadius() + circleB.getRadius();

		// ContactFeature cf;
		this.cf.indexB = 0;
		this.cf.typeB = (byte)ContactID.Type.VERTEX.ordinal();

		// Region A
		if (v <= 0.0f) {
			final Vector2 P = A;
			d.set(this.Q).subLocal(P);
			final double dd = Vector2.dot(d, d);
			if (dd > radius * radius) {
				return;
			}

			// Is there an edge connected to A?
			if (edgeA.m_hasVertex0) {
				final Vector2 A1 = edgeA.m_vertex0;
				final Vector2 B1 = A;
				this.e1.set(B1).subLocal(A1);
				final double u1 = Vector2.dot(this.e1, this.temp.set(B1).subLocal(this.Q));

				// Is the circle in Region AB of the previous edge?
				if (u1 > 0.0f) {
					return;
				}
			}

			this.cf.indexA = 0;
			this.cf.typeA = (byte)ContactID.Type.VERTEX.ordinal();
			manifold.pointCount = 1;
			manifold.type = Manifold.ManifoldType.CIRCLES;
			manifold.localNormal.setZero();
			manifold.localPoint.set(P);
			// manifold.points[0].id.key = 0;
			manifold.points[0].id.set(this.cf);
			manifold.points[0].localPoint.set(circleB.m_p);
			return;
		}

		// Region B
		if (u <= 0.0f) {
			final Vector2 P = B;
			d.set(this.Q).subLocal(P);
			final double dd = Vector2.dot(d, d);
			if (dd > radius * radius) {
				return;
			}

			// Is there an edge connected to B?
			if (edgeA.m_hasVertex3) {
				final Vector2 B2 = edgeA.m_vertex3;
				final Vector2 A2 = B;
				final Vector2 e2 = this.e1;
				e2.set(B2).subLocal(A2);
				final double v2 = Vector2.dot(e2, this.temp.set(this.Q).subLocal(A2));

				// Is the circle in Region AB of the next edge?
				if (v2 > 0.0f) {
					return;
				}
			}

			this.cf.indexA = 1;
			this.cf.typeA = (byte)ContactID.Type.VERTEX.ordinal();
			manifold.pointCount = 1;
			manifold.type = Manifold.ManifoldType.CIRCLES;
			manifold.localNormal.setZero();
			manifold.localPoint.set(P);
			// manifold.points[0].id.key = 0;
			manifold.points[0].id.set(this.cf);
			manifold.points[0].localPoint.set(circleB.m_p);
			return;
		}

		// Region AB
		final double den = Vector2.dot(this.e, this.e);
		assert (den > 0.0f);

		// Vec2 P = (1.0f / den) * (u * A + v * B);
		this.P.set(A).mulLocal(u).addLocal(this.temp.set(B).mulLocal(v));
		this.P.mulLocal(1.0f / den);
		d.set(this.Q).subLocal(this.P);
		final double dd = Vector2.dot(d, d);
		if (dd > radius * radius) {
			return;
		}

		this.n.x = -this.e.y;
		this.n.y = this.e.x;
		if (Vector2.dot(this.n, this.temp.set(this.Q).subLocal(A)) < 0.0f) {
			this.n.set(-this.n.x, -this.n.y);
		}
		this.n.normalize();

		this.cf.indexA = 0;
		this.cf.typeA = (byte)ContactID.Type.FACE.ordinal();
		manifold.pointCount = 1;
		manifold.type = Manifold.ManifoldType.FACE_A;
		manifold.localNormal.set(this.n);
		manifold.localPoint.set(A);
		// manifold.points[0].id.key = 0;
		manifold.points[0].id.set(this.cf);
		manifold.points[0].localPoint.set(circleB.m_p);
	}

	private final EPCollider collider = new EPCollider();

	public void collideEdgeAndPolygon (final Manifold manifold, final EdgeShape edgeA, final Transform xfA,
		final PolygonShape polygonB, final Transform xfB) {
		this.collider.collide(manifold, edgeA, xfA, polygonB, xfB);
	}

	/** Java-specific class for returning edge results */
	private static class EdgeResults {
		public double separation;
		public int edgeIndex;
	}

	/** Used for computing contact manifolds. */
	public static class ClipVertex {
		public final Vector2 v;
		public final ContactID id;

		public ClipVertex () {
			this.v = new Vector2();
			this.id = new ContactID();
		}

		public void set (final ClipVertex cv) {
			final Vector2 v1 = cv.v;
			this.v.x = v1.x;
			this.v.y = v1.y;
			final ContactID c = cv.id;
			this.id.indexA = c.indexA;
			this.id.indexB = c.indexB;
			this.id.typeA = c.typeA;
			this.id.typeB = c.typeB;
		}
	}

	/** This is used for determining the state of contact points.
	 *
	 * @author Daniel Murphy */
	public static enum PointState {
		/** point does not exist */
		NULL_STATE,
		/** point was added in the update */
		ADD_STATE,
		/** point persisted across the update */
		PERSIST_STATE,
		/** point was removed in the update */
		REMOVE_STATE
	}

	/** This structure is used to keep track of the best separating axis. */
	static class EPAxis {
		enum Type {
			UNKNOWN, EDGE_A, EDGE_B
		}

		Type type;
		int index;
		double separation;
	}

	/** This holds polygon B expressed in frame A. */
	static class TempPolygon {
		final Vector2[] vertices = new Vector2[Settings.maxPolygonVertices];
		final Vector2[] normals = new Vector2[Settings.maxPolygonVertices];
		int count;

		public TempPolygon () {
			for (int i = 0; i < this.vertices.length; i++) {
				this.vertices[i] = new Vector2();
				this.normals[i] = new Vector2();
			}
		}
	}

	/** Reference face used for clipping */
	static class ReferenceFace {
		int i1, i2;
		final Vector2 v1 = new Vector2();
		final Vector2 v2 = new Vector2();
		final Vector2 normal = new Vector2();

		final Vector2 sideNormal1 = new Vector2();
		double sideOffset1;

		final Vector2 sideNormal2 = new Vector2();
		double sideOffset2;
	}

	/** This class collides and edge and a polygon, taking into account edge adjacency. */
	static class EPCollider {
		enum VertexType {
			ISOLATED, CONCAVE, CONVEX
		}

		final TempPolygon m_polygonB = new TempPolygon();

		final Transform m_xf = new Transform();
		final Vector2 m_centroidB = new Vector2();
		Vector2 m_v0 = new Vector2();
		Vector2 m_v1 = new Vector2();
		Vector2 m_v2 = new Vector2();
		Vector2 m_v3 = new Vector2();
		final Vector2 m_normal0 = new Vector2();
		final Vector2 m_normal1 = new Vector2();
		final Vector2 m_normal2 = new Vector2();
		final Vector2 m_normal = new Vector2();

		VertexType m_type1, m_type2;

		final Vector2 m_lowerLimit = new Vector2();
		final Vector2 m_upperLimit = new Vector2();
		double m_radius;
		boolean m_front;

		public EPCollider () {
			for (int i = 0; i < 2; i++) {
				this.ie[i] = new ClipVertex();
				this.clipPoints1[i] = new ClipVertex();
				this.clipPoints2[i] = new ClipVertex();
			}
		}

		private final Vector2 edge1 = new Vector2();
		private final Vector2 temp = new Vector2();
		private final Vector2 edge0 = new Vector2();
		private final Vector2 edge2 = new Vector2();
		private final ClipVertex[] ie = new ClipVertex[2];
		private final ClipVertex[] clipPoints1 = new ClipVertex[2];
		private final ClipVertex[] clipPoints2 = new ClipVertex[2];
		private final ReferenceFace rf = new ReferenceFace();
		private final EPAxis edgeAxis = new EPAxis();
		private final EPAxis polygonAxis = new EPAxis();

		public void collide (final Manifold manifold, final EdgeShape edgeA, final Transform xfA, final PolygonShape polygonB,
			final Transform xfB) {

			Transform.mulTransToOutUnsafe(xfA, xfB, this.m_xf);
			Transform.mulToOutUnsafe(this.m_xf, polygonB.m_centroid, this.m_centroidB);

			this.m_v0 = edgeA.m_vertex0;
			this.m_v1 = edgeA.m_vertex1;
			this.m_v2 = edgeA.m_vertex2;
			this.m_v3 = edgeA.m_vertex3;

			final boolean hasVertex0 = edgeA.m_hasVertex0;
			final boolean hasVertex3 = edgeA.m_hasVertex3;

			this.edge1.set(this.m_v2).subLocal(this.m_v1);
			this.edge1.normalize();
			this.m_normal1.set(this.edge1.y, -this.edge1.x);
			final double offset1 = Vector2.dot(this.m_normal1, this.temp.set(this.m_centroidB).subLocal(this.m_v1));
			double offset0 = 0.0f, offset2 = 0.0f;
			boolean convex1 = false, convex2 = false;

			// Is there a preceding edge?
			if (hasVertex0) {
				this.edge0.set(this.m_v1).subLocal(this.m_v0);
				this.edge0.normalize();
				this.m_normal0.set(this.edge0.y, -this.edge0.x);
				convex1 = Vector2.cross(this.edge0, this.edge1) >= 0.0f;
				offset0 = Vector2.dot(this.m_normal0, this.temp.set(this.m_centroidB).subLocal(this.m_v0));
			}

			// Is there a following edge?
			if (hasVertex3) {
				this.edge2.set(this.m_v3).subLocal(this.m_v2);
				this.edge2.normalize();
				this.m_normal2.set(this.edge2.y, -this.edge2.x);
				convex2 = Vector2.cross(this.edge1, this.edge2) > 0.0f;
				offset2 = Vector2.dot(this.m_normal2, this.temp.set(this.m_centroidB).subLocal(this.m_v2));
			}

			// Determine front or back collision. Determine collision normal
			// limits.
			if (hasVertex0 && hasVertex3) {
				if (convex1 && convex2) {
					this.m_front = offset0 >= 0.0f || offset1 >= 0.0f || offset2 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal0.x;
						this.m_lowerLimit.y = this.m_normal0.y;
						this.m_upperLimit.x = this.m_normal2.x;
						this.m_upperLimit.y = this.m_normal2.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal1.x;
						this.m_lowerLimit.y = -this.m_normal1.y;
						this.m_upperLimit.x = -this.m_normal1.x;
						this.m_upperLimit.y = -this.m_normal1.y;
					}
				} else if (convex1) {
					this.m_front = offset0 >= 0.0f || (offset1 >= 0.0f && offset2 >= 0.0f);
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal0.x;
						this.m_lowerLimit.y = this.m_normal0.y;
						this.m_upperLimit.x = this.m_normal1.x;
						this.m_upperLimit.y = this.m_normal1.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal2.x;
						this.m_lowerLimit.y = -this.m_normal2.y;
						this.m_upperLimit.x = -this.m_normal1.x;
						this.m_upperLimit.y = -this.m_normal1.y;
					}
				} else if (convex2) {
					this.m_front = offset2 >= 0.0f || (offset0 >= 0.0f && offset1 >= 0.0f);
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal1.x;
						this.m_lowerLimit.y = this.m_normal1.y;
						this.m_upperLimit.x = this.m_normal2.x;
						this.m_upperLimit.y = this.m_normal2.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal1.x;
						this.m_lowerLimit.y = -this.m_normal1.y;
						this.m_upperLimit.x = -this.m_normal0.x;
						this.m_upperLimit.y = -this.m_normal0.y;
					}
				} else {
					this.m_front = offset0 >= 0.0f && offset1 >= 0.0f && offset2 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal1.x;
						this.m_lowerLimit.y = this.m_normal1.y;
						this.m_upperLimit.x = this.m_normal1.x;
						this.m_upperLimit.y = this.m_normal1.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal2.x;
						this.m_lowerLimit.y = -this.m_normal2.y;
						this.m_upperLimit.x = -this.m_normal0.x;
						this.m_upperLimit.y = -this.m_normal0.y;
					}
				}
			} else if (hasVertex0) {
				if (convex1) {
					this.m_front = offset0 >= 0.0f || offset1 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal0.x;
						this.m_lowerLimit.y = this.m_normal0.y;
						this.m_upperLimit.x = -this.m_normal1.x;
						this.m_upperLimit.y = -this.m_normal1.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal1.x;
						this.m_lowerLimit.y = this.m_normal1.y;
						this.m_upperLimit.x = -this.m_normal1.x;
						this.m_upperLimit.y = -this.m_normal1.y;
					}
				} else {
					this.m_front = offset0 >= 0.0f && offset1 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal1.x;
						this.m_lowerLimit.y = this.m_normal1.y;
						this.m_upperLimit.x = -this.m_normal1.x;
						this.m_upperLimit.y = -this.m_normal1.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = this.m_normal1.x;
						this.m_lowerLimit.y = this.m_normal1.y;
						this.m_upperLimit.x = -this.m_normal0.x;
						this.m_upperLimit.y = -this.m_normal0.y;
					}
				}
			} else if (hasVertex3) {
				if (convex2) {
					this.m_front = offset1 >= 0.0f || offset2 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal1.x;
						this.m_lowerLimit.y = -this.m_normal1.y;
						this.m_upperLimit.x = this.m_normal2.x;
						this.m_upperLimit.y = this.m_normal2.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal1.x;
						this.m_lowerLimit.y = -this.m_normal1.y;
						this.m_upperLimit.x = this.m_normal1.x;
						this.m_upperLimit.y = this.m_normal1.y;
					}
				} else {
					this.m_front = offset1 >= 0.0f && offset2 >= 0.0f;
					if (this.m_front) {
						this.m_normal.x = this.m_normal1.x;
						this.m_normal.y = this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal1.x;
						this.m_lowerLimit.y = -this.m_normal1.y;
						this.m_upperLimit.x = this.m_normal1.x;
						this.m_upperLimit.y = this.m_normal1.y;
					} else {
						this.m_normal.x = -this.m_normal1.x;
						this.m_normal.y = -this.m_normal1.y;
						this.m_lowerLimit.x = -this.m_normal2.x;
						this.m_lowerLimit.y = -this.m_normal2.y;
						this.m_upperLimit.x = this.m_normal1.x;
						this.m_upperLimit.y = this.m_normal1.y;
					}
				}
			} else {
				this.m_front = offset1 >= 0.0f;
				if (this.m_front) {
					this.m_normal.x = this.m_normal1.x;
					this.m_normal.y = this.m_normal1.y;
					this.m_lowerLimit.x = -this.m_normal1.x;
					this.m_lowerLimit.y = -this.m_normal1.y;
					this.m_upperLimit.x = -this.m_normal1.x;
					this.m_upperLimit.y = -this.m_normal1.y;
				} else {
					this.m_normal.x = -this.m_normal1.x;
					this.m_normal.y = -this.m_normal1.y;
					this.m_lowerLimit.x = this.m_normal1.x;
					this.m_lowerLimit.y = this.m_normal1.y;
					this.m_upperLimit.x = this.m_normal1.x;
					this.m_upperLimit.y = this.m_normal1.y;
				}
			}

			// Get polygonB in frameA
			this.m_polygonB.count = polygonB.m_count;
			for (int i = 0; i < polygonB.m_count; ++i) {
				Transform.mulToOutUnsafe(this.m_xf, polygonB.m_vertices[i], this.m_polygonB.vertices[i]);
				Rot.mulToOutUnsafe(this.m_xf.q, polygonB.m_normals[i], this.m_polygonB.normals[i]);
			}

			this.m_radius = 2.0f * Settings.linearSlop_legacy;

			manifold.pointCount = 0;

			this.computeEdgeSeparation(this.edgeAxis);

			// If no valid normal can be found than this edge should not
			// collide.
			if (this.edgeAxis.type == EPAxis.Type.UNKNOWN) {
				return;
			}

			if (this.edgeAxis.separation > this.m_radius) {
				return;
			}

			this.computePolygonSeparation(this.polygonAxis);
			if (this.polygonAxis.type != EPAxis.Type.UNKNOWN && this.polygonAxis.separation > this.m_radius) {
				return;
			}

			// Use hysteresis for jitter reduction.
			final double k_relativeTol = 0.98f;
			final double k_absoluteTol = 0.001f;
			if (1 == 1) {
				Err.reportError("fail");
			}

			EPAxis primaryAxis;
			if (this.polygonAxis.type == EPAxis.Type.UNKNOWN) {
				primaryAxis = this.edgeAxis;
			} else if (this.polygonAxis.separation > k_relativeTol * this.edgeAxis.separation + k_absoluteTol) {
				primaryAxis = this.polygonAxis;
			} else {
				primaryAxis = this.edgeAxis;
			}

			final ClipVertex ie0 = this.ie[0];
			final ClipVertex ie1 = this.ie[1];

			if (primaryAxis.type == EPAxis.Type.EDGE_A) {
				manifold.type = Manifold.ManifoldType.FACE_A;

				// Search for the polygon normal that is most anti-parallel to
				// the edge normal.
				int bestIndex = 0;
				double bestValue = Vector2.dot(this.m_normal, this.m_polygonB.normals[0]);
				for (int i = 1; i < this.m_polygonB.count; ++i) {
					final double value = Vector2.dot(this.m_normal, this.m_polygonB.normals[i]);
					if (value < bestValue) {
						bestValue = value;
						bestIndex = i;
					}
				}

				final int i1 = bestIndex;
				final int i2 = i1 + 1 < this.m_polygonB.count ? i1 + 1 : 0;

				ie0.v.set(this.m_polygonB.vertices[i1]);
				ie0.id.indexA = 0;
				ie0.id.indexB = (byte)i1;
				ie0.id.typeA = (byte)ContactID.Type.FACE.ordinal();
				ie0.id.typeB = (byte)ContactID.Type.VERTEX.ordinal();

				ie1.v.set(this.m_polygonB.vertices[i2]);
				ie1.id.indexA = 0;
				ie1.id.indexB = (byte)i2;
				ie1.id.typeA = (byte)ContactID.Type.FACE.ordinal();
				ie1.id.typeB = (byte)ContactID.Type.VERTEX.ordinal();

				if (this.m_front) {
					this.rf.i1 = 0;
					this.rf.i2 = 1;
					this.rf.v1.set(this.m_v1);
					this.rf.v2.set(this.m_v2);
					this.rf.normal.set(this.m_normal1);
				} else {
					this.rf.i1 = 1;
					this.rf.i2 = 0;
					this.rf.v1.set(this.m_v2);
					this.rf.v2.set(this.m_v1);
					this.rf.normal.set(this.m_normal1).negateLocal();
				}
			} else {
				manifold.type = Manifold.ManifoldType.FACE_B;

				ie0.v.set(this.m_v1);
				ie0.id.indexA = 0;
				ie0.id.indexB = (byte)primaryAxis.index;
				ie0.id.typeA = (byte)ContactID.Type.VERTEX.ordinal();
				ie0.id.typeB = (byte)ContactID.Type.FACE.ordinal();

				ie1.v.set(this.m_v2);
				ie1.id.indexA = 0;
				ie1.id.indexB = (byte)primaryAxis.index;
				ie1.id.typeA = (byte)ContactID.Type.VERTEX.ordinal();
				ie1.id.typeB = (byte)ContactID.Type.FACE.ordinal();

				this.rf.i1 = primaryAxis.index;
				this.rf.i2 = this.rf.i1 + 1 < this.m_polygonB.count ? this.rf.i1 + 1 : 0;
				this.rf.v1.set(this.m_polygonB.vertices[this.rf.i1]);
				this.rf.v2.set(this.m_polygonB.vertices[this.rf.i2]);
				this.rf.normal.set(this.m_polygonB.normals[this.rf.i1]);
			}

			this.rf.sideNormal1.set(this.rf.normal.y, -this.rf.normal.x);
			this.rf.sideNormal2.set(this.rf.sideNormal1).negateLocal();
			this.rf.sideOffset1 = Vector2.dot(this.rf.sideNormal1, this.rf.v1);
			this.rf.sideOffset2 = Vector2.dot(this.rf.sideNormal2, this.rf.v2);

			// Clip incident edge against extruded edge1 side edges.
			int np;

			// Clip to box side 1
			np = clipSegmentToLine(this.clipPoints1, this.ie, this.rf.sideNormal1, this.rf.sideOffset1, this.rf.i1);

			if (np < Settings.maxManifoldPoints) {
				return;
			}

			// Clip to negative box side 1
			np = clipSegmentToLine(this.clipPoints2, this.clipPoints1, this.rf.sideNormal2, this.rf.sideOffset2, this.rf.i2);

			if (np < Settings.maxManifoldPoints) {
				return;
			}

			// Now clipPoints2 contains the clipped points.
			if (primaryAxis.type == EPAxis.Type.EDGE_A) {
				manifold.localNormal.set(this.rf.normal);
				manifold.localPoint.set(this.rf.v1);
			} else {
				manifold.localNormal.set(polygonB.m_normals[this.rf.i1]);
				manifold.localPoint.set(polygonB.m_vertices[this.rf.i1]);
			}

			int pointCount = 0;
			for (int i = 0; i < Settings.maxManifoldPoints; ++i) {
				double separation;

				separation = Vector2.dot(this.rf.normal, this.temp.set(this.clipPoints2[i].v).subLocal(this.rf.v1));

				if (separation <= this.m_radius) {
					final ManifoldPoint cp = manifold.points[pointCount];

					if (primaryAxis.type == EPAxis.Type.EDGE_A) {
						// cp.localPoint = MulT(m_xf, clipPoints2[i].v);
						Transform.mulTransToOutUnsafe(this.m_xf, this.clipPoints2[i].v, cp.localPoint);
						cp.id.set(this.clipPoints2[i].id);
					} else {
						cp.localPoint.set(this.clipPoints2[i].v);
						cp.id.typeA = this.clipPoints2[i].id.typeB;
						cp.id.typeB = this.clipPoints2[i].id.typeA;
						cp.id.indexA = this.clipPoints2[i].id.indexB;
						cp.id.indexB = this.clipPoints2[i].id.indexA;
					}

					++pointCount;
				}
			}

			manifold.pointCount = pointCount;
		}

		public void computeEdgeSeparation (final EPAxis axis) {
			axis.type = EPAxis.Type.EDGE_A;
			axis.index = this.m_front ? 0 : 1;
			axis.separation = Double.MAX_VALUE;
			final double nx = this.m_normal.x;
			final double ny = this.m_normal.y;

			for (int i = 0; i < this.m_polygonB.count; ++i) {
				final Vector2 v = this.m_polygonB.vertices[i];
				final double tempx = v.x - this.m_v1.x;
				final double tempy = v.y - this.m_v1.y;
				final double s = nx * tempx + ny * tempy;
				if (s < axis.separation) {
					axis.separation = s;
				}
			}
		}

		private final Vector2 perp = new Vector2();
		private final Vector2 n = new Vector2();

		public void computePolygonSeparation (final EPAxis axis) {
			axis.type = EPAxis.Type.UNKNOWN;
			axis.index = -1;
			axis.separation = -Double.MAX_VALUE;

			this.perp.x = -this.m_normal.y;
			this.perp.y = this.m_normal.x;

			for (int i = 0; i < this.m_polygonB.count; ++i) {
				final Vector2 normalB = this.m_polygonB.normals[i];
				final Vector2 vB = this.m_polygonB.vertices[i];
				this.n.x = -normalB.x;
				this.n.y = -normalB.y;

				// double s1 = Vec2.dot(n, temp.set(vB).subLocal(m_v1));
				// double s2 = Vec2.dot(n, temp.set(vB).subLocal(m_v2));
				double tempx = vB.x - this.m_v1.x;
				double tempy = vB.y - this.m_v1.y;
				final double s1 = this.n.x * tempx + this.n.y * tempy;
				tempx = vB.x - this.m_v2.x;
				tempy = vB.y - this.m_v2.y;
				final double s2 = this.n.x * tempx + this.n.y * tempy;
				final double s = MathUtils.min(s1, s2);

				if (s > this.m_radius) {
					// No collision
					axis.type = EPAxis.Type.EDGE_B;
					axis.index = i;
					axis.separation = s;
					return;
				}

				// Adjacency
				if (this.n.x * this.perp.x + this.n.y * this.perp.y >= 0.0f) {
					if (Vector2.dot(this.temp.set(this.n).subLocal(this.m_upperLimit), this.m_normal) < -Settings.angularSlop) {
						continue;
					}
				} else {
					if (Vector2.dot(this.temp.set(this.n).subLocal(this.m_lowerLimit), this.m_normal) < -Settings.angularSlop) {
						continue;
					}
				}

				if (s > axis.separation) {
					axis.type = EPAxis.Type.EDGE_B;
					axis.index = i;
					axis.separation = s;
				}
			}
		}
	}
}
