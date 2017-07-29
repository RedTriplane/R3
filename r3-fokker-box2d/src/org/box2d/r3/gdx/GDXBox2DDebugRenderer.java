/*******************************************************************************
 * Copyright 2011 See AUTHORS file.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 ******************************************************************************/

package org.box2d.r3.gdx;

import java.util.Iterator;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.EdgeShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.Joint;
import com.badlogic.gdx.physics.box2d.JointDef.JointType;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape.Type;
import com.badlogic.gdx.physics.box2d.Transform;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.physics.box2d.WorldManifold;
import com.badlogic.gdx.physics.box2d.joints.PulleyJoint;
import com.badlogic.gdx.utils.Array;
//import com.jfixby.r3.api.unit.components.physics.body.Body;
//import com.jfixby.r3.api.unit.components.physics.body.CircleShape;

public class GDXBox2DDebugRenderer {

	/** the immediate mode renderer to output our debug drawings **/
	protected ShapeRenderer renderer;

	/** vertices for polygon rendering **/
	private final static Vector2[] vertices = new Vector2[1000];

	private final static Vector2 lower = new Vector2();
	private final static Vector2 upper = new Vector2();

	private final static Array<Body> bodies = new Array<Body>();
	private final static Array<Joint> joints = new Array<Joint>();

	private boolean drawBodies;
	private boolean drawJoints;
	private boolean drawAABBs;
	private boolean drawInactiveBodies;
	private boolean drawVelocities;
	private boolean drawContacts;

	public GDXBox2DDebugRenderer () {
		this(true, true, false, true, false, false);
	}

	public GDXBox2DDebugRenderer (final boolean drawBodies, final boolean drawJoints, final boolean drawAABBs,
		final boolean drawInactiveBodies, final boolean drawVelocities, final boolean drawContacts) {
		// next we setup the immediate mode renderer
		// renderer = new ShapeRenderer();

		// initialize vertices array
		for (int i = 0; i < vertices.length; i++) {
			vertices[i] = new Vector2();
		}

		this.drawBodies = drawBodies;
		this.drawJoints = drawJoints;
		this.drawAABBs = drawAABBs;
		this.drawInactiveBodies = drawInactiveBodies;
		this.drawVelocities = drawVelocities;
		this.drawContacts = drawContacts;
	}

	/** This assumes that the projection matrix has already been set. */
	// public void render (World world, Matrix4 projMatrix) {
	// renderer.setProjectionMatrix(projMatrix);
	// renderBodies(world);
	// }

	public void render (final World world, final ShapeRenderer renderer) {
		this.renderer = renderer;
		this.renderBodies(world);
		this.renderer = null;
	}

	public final Color SHAPE_NOT_ACTIVE = new Color(0.5f, 0.5f, 0.3f, 1);
	public final Color SHAPE_STATIC = new Color(0.5f, 0.9f, 0.5f, 1);
	public final Color SHAPE_KINEMATIC = new Color(0.5f, 0.5f, 0.9f, 1);
	public final Color SHAPE_NOT_AWAKE = new Color(0.6f, 0.6f, 0.6f, 1);
	public final Color SHAPE_AWAKE = new Color(0.9f, 0.7f, 0.7f, 1);
	public final Color JOINT_COLOR = new Color(0.5f, 0.8f, 0.8f, 1);
	public final Color AABB_COLOR = new Color(1.0f, 0, 1.0f, 1f);
	public final Color VELOCITY_COLOR = new Color(1.0f, 0, 0f, 1f);

	private void renderBodies (final World world) {
		this.renderer.begin(ShapeType.Line);

		if (this.drawBodies || this.drawAABBs) {
			world.getBodies(bodies);
			for (final Iterator<Body> iter = bodies.iterator(); iter.hasNext();) {
				final Body body = iter.next();
				if (body.isActive() || this.drawInactiveBodies) {
					this.renderBody(body);
				}
			}
		}

		if (this.drawJoints) {
			world.getJoints(joints);
			for (final Iterator<Joint> iter = joints.iterator(); iter.hasNext();) {
				final Joint joint = iter.next();
				this.drawJoint(joint);
			}
		}
		this.renderer.end();
		if (this.drawContacts) {
			this.renderer.begin(ShapeType.Point);
			for (final Contact contact : world.getContactList()) {
				this.drawContact(contact);
			}
			this.renderer.end();
		}
	}

	protected void renderBody (final Body body) {
		final Transform transform = body.getTransform();
		for (final Fixture fixture : body.getFixtureList()) {
			if (this.drawBodies) {
				this.drawShape(fixture, transform, this.getColorByBody(body));
				if (this.drawVelocities) {
					final Vector2 position = body.getPosition();
					this.drawSegment(position, body.getLinearVelocity().add(position), this.VELOCITY_COLOR);
				}
			}

			if (this.drawAABBs) {
				this.drawAABB(fixture, transform);
			}
		}
	}

	private Color getColorByBody (final Body body) {
		if (body.isActive() == false) {
			return this.SHAPE_NOT_ACTIVE;
		} else if (body.getType() == BodyType.StaticBody) {
			return this.SHAPE_STATIC;
		} else if (body.getType() == BodyType.KinematicBody) {
			return this.SHAPE_KINEMATIC;
		} else if (body.isAwake() == false) {
			return this.SHAPE_NOT_AWAKE;
		} else {
			return this.SHAPE_AWAKE;
		}
	}

	private void drawAABB (final Fixture fixture, final Transform transform) {
		if (fixture.getType() == Type.Circle) {

			final CircleShape shape = (CircleShape)fixture.getShape();
			final float radius = shape.getRadius();
			vertices[0].set(shape.getPosition());
			vertices[0].rotate(transform.getRotation()).add(transform.getPosition());
			lower.set(vertices[0].x - radius, vertices[0].y - radius);
			upper.set(vertices[0].x + radius, vertices[0].y + radius);

			// define vertices in ccw fashion...
			vertices[0].set(lower.x, lower.y);
			vertices[1].set(upper.x, lower.y);
			vertices[2].set(upper.x, upper.y);
			vertices[3].set(lower.x, upper.y);

			this.drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
		} else if (fixture.getType() == Type.Polygon) {
			final PolygonShape shape = (PolygonShape)fixture.getShape();
			final int vertexCount = shape.getVertexCount();

			shape.getVertex(0, vertices[0]);
			lower.set(transform.mul(vertices[0]));
			upper.set(lower);
			for (int i = 1; i < vertexCount; i++) {
				shape.getVertex(i, vertices[i]);
				transform.mul(vertices[i]);
				lower.x = Math.min(lower.x, vertices[i].x);
				lower.y = Math.min(lower.y, vertices[i].y);
				upper.x = Math.max(upper.x, vertices[i].x);
				upper.y = Math.max(upper.y, vertices[i].y);
			}

			// define vertices in ccw fashion...
			vertices[0].set(lower.x, lower.y);
			vertices[1].set(upper.x, lower.y);
			vertices[2].set(upper.x, upper.y);
			vertices[3].set(lower.x, upper.y);

			this.drawSolidPolygon(vertices, 4, this.AABB_COLOR, true);
		}
	}

	private static Vector2 t = new Vector2();
	private static Vector2 axis = new Vector2();

	private void drawShape (final Fixture fixture, final Transform transform, final Color color) {
		if (fixture.getType() == Type.Circle) {
			final CircleShape circle = (CircleShape)fixture.getShape();
			t.set(circle.getPosition());
			transform.mul(t);
			this.drawSolidCircle(t, circle.getRadius(), axis.set(transform.vals[Transform.COS], transform.vals[Transform.SIN]),
				color);
			return;
		}

		if (fixture.getType() == Type.Edge) {
			final EdgeShape edge = (EdgeShape)fixture.getShape();
			edge.getVertex1(vertices[0]);
			edge.getVertex2(vertices[1]);
			transform.mul(vertices[0]);
			transform.mul(vertices[1]);
			this.drawSolidPolygon(vertices, 2, color, true);
			return;
		}

		if (fixture.getType() == Type.Polygon) {
			final PolygonShape chain = (PolygonShape)fixture.getShape();
			final int vertexCount = chain.getVertexCount();
			for (int i = 0; i < vertexCount; i++) {
				chain.getVertex(i, vertices[i]);
				transform.mul(vertices[i]);
			}
			this.drawSolidPolygon(vertices, vertexCount, color, true);
			return;
		}

		if (fixture.getType() == Type.Chain) {
			final ChainShape chain = (ChainShape)fixture.getShape();
			final int vertexCount = chain.getVertexCount();
			for (int i = 0; i < vertexCount; i++) {
				chain.getVertex(i, vertices[i]);
				transform.mul(vertices[i]);
			}
			this.drawSolidPolygon(vertices, vertexCount, color, false);
		}
	}

	private final Vector2 f = new Vector2();
	private final Vector2 v = new Vector2();
	private final Vector2 lv = new Vector2();

	private void drawSolidCircle (final Vector2 center, final float radius, final Vector2 axis, final Color color) {
		float angle = 0;
		final float angleInc = 2 * (float)Math.PI / 20;
		this.renderer.setColor(color.r, color.g, color.b, color.a);
		for (int i = 0; i < 20; i++, angle += angleInc) {
			this.v.set((float)Math.cos(angle) * radius + center.x, (float)Math.sin(angle) * radius + center.y);
			if (i == 0) {
				this.lv.set(this.v);
				this.f.set(this.v);
				continue;
			}
			this.renderer.line(this.lv.x, this.lv.y, this.v.x, this.v.y);
			this.lv.set(this.v);
		}
		this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
		this.renderer.line(center.x, center.y, 0, center.x + axis.x * radius, center.y + axis.y * radius, 0);
	}

	private void drawSolidPolygon (final Vector2[] vertices, final int vertexCount, final Color color, final boolean closed) {
		this.renderer.setColor(color.r, color.g, color.b, color.a);
		this.lv.set(vertices[0]);
		this.f.set(vertices[0]);
		for (int i = 1; i < vertexCount; i++) {
			final Vector2 v = vertices[i];
			this.renderer.line(this.lv.x, this.lv.y, v.x, v.y);
			this.lv.set(v);
		}
		if (closed) {
			this.renderer.line(this.f.x, this.f.y, this.lv.x, this.lv.y);
		}
	}

	private void drawJoint (final Joint joint) {
		final Body bodyA = joint.getBodyA();
		final Body bodyB = joint.getBodyB();
		final Transform xf1 = bodyA.getTransform();
		final Transform xf2 = bodyB.getTransform();

		final Vector2 x1 = xf1.getPosition();
		final Vector2 x2 = xf2.getPosition();
		final Vector2 p1 = joint.getAnchorA();
		final Vector2 p2 = joint.getAnchorB();

		if (joint.getType() == JointType.DistanceJoint) {
			this.drawSegment(p1, p2, this.JOINT_COLOR);
		} else if (joint.getType() == JointType.PulleyJoint) {
			final PulleyJoint pulley = (PulleyJoint)joint;
			final Vector2 s1 = pulley.getGroundAnchorA();
			final Vector2 s2 = pulley.getGroundAnchorB();
			this.drawSegment(s1, p1, this.JOINT_COLOR);
			this.drawSegment(s2, p2, this.JOINT_COLOR);
			this.drawSegment(s1, s2, this.JOINT_COLOR);
		} else if (joint.getType() == JointType.MouseJoint) {
			this.drawSegment(joint.getAnchorA(), joint.getAnchorB(), this.JOINT_COLOR);
		} else {
			this.drawSegment(x1, p1, this.JOINT_COLOR);
			this.drawSegment(p1, p2, this.JOINT_COLOR);
			this.drawSegment(x2, p2, this.JOINT_COLOR);
		}
	}

	private void drawSegment (final Vector2 x1, final Vector2 x2, final Color color) {
		this.renderer.setColor(color);
		this.renderer.line(x1.x, x1.y, x2.x, x2.y);
	}

	private void drawContact (final Contact contact) {
		final WorldManifold worldManifold = contact.getWorldManifold();
		if (worldManifold.getNumberOfContactPoints() == 0) {
			return;
		}
		final Vector2 point = worldManifold.getPoints()[0];
		this.renderer.setColor(this.getColorByBody(contact.getFixtureA().getBody()));
		this.renderer.point(point.x, point.y, 0);
	}

	public boolean isDrawBodies () {
		return this.drawBodies;
	}

	public void setDrawBodies (final boolean drawBodies) {
		this.drawBodies = drawBodies;
	}

	public boolean isDrawJoints () {
		return this.drawJoints;
	}

	public void setDrawJoints (final boolean drawJoints) {
		this.drawJoints = drawJoints;
	}

	public boolean isDrawAABBs () {
		return this.drawAABBs;
	}

	public void setDrawAABBs (final boolean drawAABBs) {
		this.drawAABBs = drawAABBs;
	}

	public boolean isDrawInactiveBodies () {
		return this.drawInactiveBodies;
	}

	public void setDrawInactiveBodies (final boolean drawInactiveBodies) {
		this.drawInactiveBodies = drawInactiveBodies;
	}

	public boolean isDrawVelocities () {
		return this.drawVelocities;
	}

	public void setDrawVelocities (final boolean drawVelocities) {
		this.drawVelocities = drawVelocities;
	}

	public boolean isDrawContacts () {
		return this.drawContacts;
	}

	public void setDrawContacts (final boolean drawContacts) {
		this.drawContacts = drawContacts;
	}

	public static Vector2 getAxis () {
		return axis;
	}

	public static void setAxis (final Vector2 axis) {
		GDXBox2DDebugRenderer.axis = axis;
	}

	public void dispose () {
		this.renderer.dispose();
	}
}
