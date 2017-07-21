
package com.jfixby.r3.activity.red.physics;

import org.box2d.jfixby.api.BodyType;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Box2DTransform;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.Box2DWorldsContainer;
import org.box2d.jfixby.api.ChainShape;
import org.box2d.jfixby.api.CircleShape;
import org.box2d.jfixby.api.EdgeShape;
import org.box2d.jfixby.api.Fixture;
import org.box2d.jfixby.api.PolygonShape;
import org.box2d.jfixby.api.ShapeType;

import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.engine.api.render.Drawable;
import com.jfixby.r3.engine.api.render.RenderMachine;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.color.Colors;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.floatn.ReadOnlyFloat2;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Vertex;

public class GDXBox2DDebugRendererComponent implements VisibleComponent, Drawable {

	GDXBox2DDebugRendererComponent (final Box2DWorldsContainer core, final boolean drawBodies, final boolean drawJoints,
		final boolean drawAABBs, final boolean drawInactiveBodies, final boolean drawVelocities, final boolean drawContacts) {
		this.core = core;
		this.drawBodies = drawBodies;
		this.drawJoints = drawJoints;
		this.drawAABBs = drawAABBs;
		this.drawInactiveBodies = drawInactiveBodies;
		this.drawVelocities = drawVelocities;
		this.drawContacts = drawContacts;
	}

	public GDXBox2DDebugRendererComponent (final Box2DWorldsContainer core) {
		this(core, true, true, false, true, false, false);
	}

	private final boolean drawBodies;
	private final boolean drawJoints;
	private final boolean drawAABBs;
	private final boolean drawInactiveBodies;
	private final boolean drawVelocities;
	private final boolean drawContacts;

	final Box2DWorldsContainer core;

	@Override
	public boolean isVisible () {
		return this.is_visible;
	}

	boolean is_visible = true;
	private String name;

	@Override
	public void hide () {
		this.is_visible = false;
	}

	@Override
	public void show () {
		this.is_visible = true;
	}

	@Override
	public void setName (final String name) {
		this.name = name;
	}

	@Override
	public String getName () {
		return this.name;
	}

	@Override
	public void setVisible (final boolean b) {
		this.is_visible = b;
	}

	final float al = 1f;
	public final Color SHAPE_NOT_ACTIVE_DOUBLE = Colors.PURPLE();
	public final Color SHAPE_NOT_ACTIVE_FLOAT = Colors.RED();

	public final Color SHAPE_STATIC = Colors.GRAY().customize().mix(Colors.GREEN(), 0.3f);
	public final Color SHAPE_KINEMATIC = Colors.BROWN();

	public final Color SHAPE_AWAKE_DOUBLE = Colors.ORANGE();
	public final Color SHAPE_AWAKE_FLOAT = Colors.BLUE().customize().mutliply(2f);

	public final Color SHAPE_NOT_AWAKE_DOUBLE = this.SHAPE_AWAKE_DOUBLE.customize().mutliply(0.5f);
	public final Color SHAPE_NOT_AWAKE_FLOAT = this.SHAPE_AWAKE_FLOAT.customize().mutliply(0.5f);

	public final Color JOINT_COLOR = this.newColor(0.5f, 0.8f, 0.8f, this.al);
	public final Color AABB_COLOR = this.newColor(1.0f, 0, 1.0f, this.al);
	public final Color VELOCITY_COLOR = this.newColor(1.0f, 0, 0f, this.al);

	final Float2 offset = Geometry.newFloat2();

	@Override
	public void doDraw () {
		RenderMachine.beginDrawComponent(this);
		RenderMachine.beginShapesMode();

		// RenderMachine.drawShapesRenderable(this);
		this.offset.setXY();
		final Collection<Box2DWorld> worlds = this.core.getWorldsForRender();
		final double d = 0.001;
		for (int i = 0; i < worlds.size(); i++) {

			// offset.set(i * d, i / 2d * d);
			final Box2DWorld world_to_render = worlds.getElementAt(i);

			final Collection<Box2DBody> bodies = world_to_render.listBodies();

			// bodies.print("bodies");

			for (final Box2DBody body : bodies) {
				if (body.isActive() || this.drawInactiveBodies) {
					this.renderBody(body, world_to_render);
				}
			}
		}
		RenderMachine.endShapesMode();
		RenderMachine.endDrawComponent(this);

	}

	private void renderBody (final Box2DBody body, final Box2DWorld world_to_render) {

		final Box2DTransform transform = body.getTransform();
		for (final Fixture fixture : body.getFixtureList()) {
			if (this.drawBodies) {
				final Float2 position = body.getPosition();
				this.drawShape(fixture, transform, this.getColorByBody(body, world_to_render));
				if (this.drawVelocities) {

					this.drawSegment(position, body.getLinearVelocity().add(position), this.VELOCITY_COLOR);
				}
			}

			if (this.drawAABBs) {
				this.drawAABB(fixture, transform);
			}
		}

	}

	private void drawAABB (final Fixture fixture, final Box2DTransform transform) {
		Err.throwNotImplementedYet();
	}

	private void drawSegment (final Float2 A, final Float2 B, final Color color) {
		RenderMachine.drawLine(color, A, B);
	}

	private final Float2 t = Geometry.newFloat2();
	private final Float2 axis = Geometry.newFloat2();
	private int a;
	private int b;

	private void drawShape (final Fixture fixture, final Box2DTransform transform, final Color color) {

		if (fixture.getType() == ShapeType.Circle) {
			final CircleShape circle = (CircleShape)fixture.getShape();
			final ReadOnlyFloat2 position = circle.getPosition();
			this.tmp1.set(position);
			transform.transform(this.tmp1);

			final double radius = circle.getRadius();
			this.tmp2.set(position);
			this.tmp2.setX(this.tmp2.getX() + radius);
			transform.transform(this.tmp2);

			this.tmp2.add(this.offset);
			this.tmp1.add(this.offset);
			// RenderMachine.drawCircle(color, position.getX() + offset.getX(),
			// position.getY() + offset.getY(), radius, transform);
			// tmp1.add(offset);

			RenderMachine.drawCircle(color, this.tmp1.getX(), this.tmp1.getY(), radius);
			RenderMachine.drawLine(color, this.tmp1, this.tmp2);

			return;
		}

		if (fixture.getType() == ShapeType.Edge) {
			final EdgeShape edge = (EdgeShape)fixture.getShape();

			Err.throwNotImplementedYet();
		}

		if (fixture.getType() == ShapeType.Polygon) {
			final PolygonShape shape = (PolygonShape)fixture.getShape();

			final Collection<Vertex> vertices = shape.getClosedPolygonalChain().listVertices();

			for (int i = 0; i < vertices.size(); i++) {
				this.a = i;
				this.b = i + 1;
				if (this.b == vertices.size()) {
					this.b = 0;
				}
				final Vertex vA = vertices.getElementAt(this.a);
				final Vertex vB = vertices.getElementAt(this.b);

				this.tmp1.set(vA.relative());
				this.tmp2.set(vB.relative());

				transform.transform(this.tmp1);
				transform.transform(this.tmp2);

				RenderMachine.drawLine(color, this.tmp1.add(this.offset), this.tmp2.add(this.offset));

			}
			return;
		}

		if (fixture.getType() == ShapeType.Chain) {
			final ChainShape chain = (ChainShape)fixture.getShape();
			Err.throwNotImplementedYet();
		}
	}

	Float2 tmp1 = Geometry.newFloat2();
	Float2 tmp2 = Geometry.newFloat2();
	Float2 tmp3 = Geometry.newFloat2();

	private Color getColorByBody (final Box2DBody body, final Box2DWorld world_to_render) {
		if (body.isActive() == false) {
			if (world_to_render.isFloatPrecision()) {
				return this.SHAPE_NOT_ACTIVE_FLOAT;
			}
			return this.SHAPE_NOT_ACTIVE_DOUBLE;
		} else if (body.getType() == BodyType.StaticBody) {
			return this.SHAPE_STATIC;
		} else if (body.getType() == BodyType.KinematicBody) {

			return this.SHAPE_KINEMATIC;
		} else if (body.isAwake() == false) {
			if (world_to_render.isFloatPrecision()) {
				return this.SHAPE_NOT_AWAKE_FLOAT;
			}
			return this.SHAPE_NOT_AWAKE_DOUBLE;
		} else {
			if (world_to_render.isFloatPrecision()) {
				return this.SHAPE_AWAKE_FLOAT;
			}
			return this.SHAPE_AWAKE_DOUBLE;
		}
	}

	private Color newColor (final float r, final float g, final float b, final float a) {
		return Colors.newColor(a, Colors.limit(r + 0.1f), Colors.limit(g + 0.1f), Colors.limit(b + 0.1f));
	}

	// @Override
	// public void doDraw(FokkerShapesRenderDevice render_device) {
	// core.getWorldForRender().doDraw(render_device);
	// }

}
