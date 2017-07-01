
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.BodyDef;
import org.box2d.jfixby.api.Box2DComponent;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.CircleShape;
import org.box2d.jfixby.api.FixtureDef;
import org.box2d.jfixby.api.PolygonShape;

//import com.jfixby.r3.api.unit.components.physics.body.CircleShape;
import com.jfixby.scarabei.api.log.L;

public class PhysicalParametersContainer {

	final CircleAspect circleAspect;
	final BoxAspect boxAspect;
	final PolyAspect polyAspect;
	final private Box2DComponent box2d;

	public PhysicalParametersContainer (final Box2DWorldWrap world) {
		this.box2d = world.getBox2DComponent();
		this.circleAspect = new CircleAspect(world.world());
		this.boxAspect = new BoxAspect(world.world());
		this.polyAspect = new PolyAspect(world.world());

	}

	public CircleAspect circleAspect () {
		return this.circleAspect;
	}

	public BoxAspect boxAspect () {
		return this.boxAspect;
	}

	public PolyAspect polyAspect () {
		return this.polyAspect;
	}

	public class CircleAspect {

		public CircleAspect (final Box2DWorld world) {
			this.circleShape = PhysicalParametersContainer.this.box2d.newCircleShape();
			this.fixture = PhysicalParametersContainer.this.box2d.newFixtureDef();
			this.bodyDef = PhysicalParametersContainer.this.box2d.newBodyDef();

		}

		final FixtureDef fixture;
		final BodyDef bodyDef;
		final CircleShape circleShape;

		public FixtureDef getFixture () {
			return this.fixture;
		}

		public BodyDef getBodyDef () {
			return this.bodyDef;
		}

		public CircleShape getCircleShape () {
			return this.circleShape;
		}

	}

	public class BoxAspect {

		public BoxAspect (final Box2DWorld world) {
			this.fixture = PhysicalParametersContainer.this.box2d.newFixtureDef();
			this.bodyDef = PhysicalParametersContainer.this.box2d.newBodyDef();
			this.polyShape = PhysicalParametersContainer.this.box2d.newPolygonShape();
		}

		final FixtureDef fixture;
		final BodyDef bodyDef;
		final PolygonShape polyShape;

		public FixtureDef getFixture () {
			return this.fixture;
		}

		public BodyDef getBodyDef () {
			return this.bodyDef;
		}

		public PolygonShape getPolygonShape () {
			return this.polyShape;
		}

	}

	public class PolyAspect {

		public PolyAspect (final Box2DWorld world) {
			this.circleShape = PhysicalParametersContainer.this.box2d.newCircleShape();
			this.fixture = PhysicalParametersContainer.this.box2d.newFixtureDef();
			this.bodyDef = PhysicalParametersContainer.this.box2d.newBodyDef();
			this.polyShape = PhysicalParametersContainer.this.box2d.newPolygonShape();
		}

		final FixtureDef fixture;
		final BodyDef bodyDef;
		final CircleShape circleShape;
		final PolygonShape polyShape;

		public FixtureDef getFixture () {
			return this.fixture;
		}

		public BodyDef getBodyDef () {
			return this.bodyDef;
		}

		public CircleShape getCircleShape () {
			return this.circleShape;
		}

		public PolygonShape getPolygonShape () {
			return this.polyShape;
		}

	}

	public void dispose () {
		L.d("memory leak:dispose");
	}
}
