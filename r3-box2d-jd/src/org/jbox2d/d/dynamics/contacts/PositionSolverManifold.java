package org.jbox2d.d.dynamics.contacts;

import org.jbox2d.d.common.Rot;
import org.jbox2d.d.common.Transform;
import org.jbox2d.d.common.Vector2;

class PositionSolverManifold {

	public final Vector2 normal = new Vector2();
	public final Vector2 point = new Vector2();
	public double separation;

	public void initialize(ContactPositionConstraint pc, Transform xfA,
			Transform xfB, int index) {
		assert (pc.pointCount > 0);

		final Rot xfAq = xfA.q;
		final Rot xfBq = xfB.q;
		final Vector2 pcLocalPointsI = pc.localPoints[index];
		switch (pc.type) {
		case CIRCLES: {
			// Transform.mulToOutUnsafe(xfA, pc.localPoint, pointA);
			// Transform.mulToOutUnsafe(xfB, pc.localPoints[0], pointB);
			// normal.set(pointB).subLocal(pointA);
			// normal.normalize();
			//
			// point.set(pointA).addLocal(pointB).mulLocal(.5f);
			// temp.set(pointB).subLocal(pointA);
			// separation = Vec2.dot(temp, normal) - pc.radiusA - pc.radiusB;
			final Vector2 plocalPoint = pc.localPoint;
			final Vector2 pLocalPoints0 = pc.localPoints[0];
			final double pointAx = (xfAq.c * plocalPoint.x - xfAq.s
					* plocalPoint.y)
					+ xfA.p.x;
			final double pointAy = (xfAq.s * plocalPoint.x + xfAq.c
					* plocalPoint.y)
					+ xfA.p.y;
			final double pointBx = (xfBq.c * pLocalPoints0.x - xfBq.s
					* pLocalPoints0.y)
					+ xfB.p.x;
			final double pointBy = (xfBq.s * pLocalPoints0.x + xfBq.c
					* pLocalPoints0.y)
					+ xfB.p.y;
			normal.x = pointBx - pointAx;
			normal.y = pointBy - pointAy;
			normal.normalize();

			point.x = (pointAx + pointBx) * .5;
			point.y = (pointAy + pointBy) * .5;
			final double tempx = pointBx - pointAx;
			final double tempy = pointBy - pointAy;
			separation = tempx * normal.x + tempy * normal.y - pc.radiusA
					- pc.radiusB;
			break;
		}

		case FACE_A: {
			// Rot.mulToOutUnsafe(xfAq, pc.localNormal, normal);
			// Transform.mulToOutUnsafe(xfA, pc.localPoint, planePoint);
			//
			// Transform.mulToOutUnsafe(xfB, pc.localPoints[index], clipPoint);
			// temp.set(clipPoint).subLocal(planePoint);
			// separation = Vec2.dot(temp, normal) - pc.radiusA - pc.radiusB;
			// point.set(clipPoint);
			final Vector2 pcLocalNormal = pc.localNormal;
			final Vector2 pcLocalPoint = pc.localPoint;
			normal.x = xfAq.c * pcLocalNormal.x - xfAq.s * pcLocalNormal.y;
			normal.y = xfAq.s * pcLocalNormal.x + xfAq.c * pcLocalNormal.y;
			final double planePointx = (xfAq.c * pcLocalPoint.x - xfAq.s
					* pcLocalPoint.y)
					+ xfA.p.x;
			final double planePointy = (xfAq.s * pcLocalPoint.x + xfAq.c
					* pcLocalPoint.y)
					+ xfA.p.y;

			final double clipPointx = (xfBq.c * pcLocalPointsI.x - xfBq.s
					* pcLocalPointsI.y)
					+ xfB.p.x;
			final double clipPointy = (xfBq.s * pcLocalPointsI.x + xfBq.c
					* pcLocalPointsI.y)
					+ xfB.p.y;
			final double tempx = clipPointx - planePointx;
			final double tempy = clipPointy - planePointy;
			separation = tempx * normal.x + tempy * normal.y - pc.radiusA
					- pc.radiusB;
			point.x = clipPointx;
			point.y = clipPointy;
			break;
		}

		case FACE_B: {
			// Rot.mulToOutUnsafe(xfBq, pc.localNormal, normal);
			// Transform.mulToOutUnsafe(xfB, pc.localPoint, planePoint);
			//
			// Transform.mulToOutUnsafe(xfA, pcLocalPointsI, clipPoint);
			// temp.set(clipPoint).subLocal(planePoint);
			// separation = Vec2.dot(temp, normal) - pc.radiusA - pc.radiusB;
			// point.set(clipPoint);
			//
			// // Ensure normal points from A to B
			// normal.negateLocal();
			final Vector2 pcLocalNormal = pc.localNormal;
			final Vector2 pcLocalPoint = pc.localPoint;
			normal.x = xfBq.c * pcLocalNormal.x - xfBq.s * pcLocalNormal.y;
			normal.y = xfBq.s * pcLocalNormal.x + xfBq.c * pcLocalNormal.y;
			final double planePointx = (xfBq.c * pcLocalPoint.x - xfBq.s
					* pcLocalPoint.y)
					+ xfB.p.x;
			final double planePointy = (xfBq.s * pcLocalPoint.x + xfBq.c
					* pcLocalPoint.y)
					+ xfB.p.y;

			final double clipPointx = (xfAq.c * pcLocalPointsI.x - xfAq.s
					* pcLocalPointsI.y)
					+ xfA.p.x;
			final double clipPointy = (xfAq.s * pcLocalPointsI.x + xfAq.c
					* pcLocalPointsI.y)
					+ xfA.p.y;
			final double tempx = clipPointx - planePointx;
			final double tempy = clipPointy - planePointy;
			separation = tempx * normal.x + tempy * normal.y - pc.radiusA
					- pc.radiusB;
			point.x = clipPointx;
			point.y = clipPointy;
			normal.x *= -1;
			normal.y *= -1;
		}
			break;
		}
	}
}