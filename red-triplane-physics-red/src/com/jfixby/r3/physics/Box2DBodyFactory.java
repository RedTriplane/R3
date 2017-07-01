
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.BodyDef;
import org.box2d.jfixby.api.BodyType;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.Box2DWorld;
import org.box2d.jfixby.api.CircleShape;
import org.box2d.jfixby.api.FixtureDef;
import org.box2d.jfixby.api.PolygonShape;

import com.jfixby.r3.api.physics.PolyBodyChain;
import com.jfixby.r3.api.physics.PolyBodyCircle;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.Geometry;

public class Box2DBodyFactory {

	public static Box2DBody createBox (final Box2DWorld box2dWorld, final double angle, final double width, final double height,
		final double density, final double friction, final double restitution, final boolean is_sensor, final BodyType type,
		final double x, final double y, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {

		final FixtureDef fixture = gdx_ph_container.boxAspect().getFixture();
		final BodyDef boxBodyDef = gdx_ph_container.boxAspect().getBodyDef();
		final PolygonShape boxPoly = gdx_ph_container.boxAspect().getPolygonShape();

		fixture.filter().setCategoryBits(categoryBits);
		fixture.filter().setMaskBits(maskBits);

		fixture.setDensity(density);
		fixture.setRestitution(restitution);
		fixture.setFriction(friction);
		fixture.setIsSensor(is_sensor);

		boxBodyDef.setType(type);
		boxBodyDef.position().setX(x);
		boxBodyDef.position().setY(y);

		final Box2DBody newBody = box2dWorld.createBody(boxBodyDef);
		boxPoly.setAsBox(width / 2f, height / 2f);
		fixture.setShape(boxPoly);
		newBody.createFixture(fixture);
		newBody.setTransform(newBody.getWorldCenter(), angle);

		// L.d("created body", newBody + " : " + categoryBits);

		// boxPoly.dispose();
		return newBody;
	}

	public static void disposeBody (final Box2DWorld box2dWorld, final Box2DBody body) {
		// L.d("dispose body", body + " : " + body.getFixtureList());

		box2dWorld.destroyBody(body);
	}

	public static Box2DBody createCircle (final Box2DWorld box2dWorld, final double angle, final double radius,
		final double density, final double friction, final double restitution, final boolean is_sensor, final BodyType type,
		final double x, final double y, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {

		final BodyDef boxBodyDef = gdx_ph_container.circleAspect().getBodyDef();

		boxBodyDef.setType(type);
		boxBodyDef.position().setX(x);
		boxBodyDef.position().setY(y);

		final Box2DBody newBody = box2dWorld.createBody(boxBodyDef);

		final FixtureDef fixture = gdx_ph_container.circleAspect().getFixture();
		// final FixtureDef fixture = newBody.get;
		fixture.filter().setCategoryBits(categoryBits);
		fixture.filter().setMaskBits(maskBits);

		fixture.setDensity(density);
		fixture.setRestitution(restitution);
		fixture.setFriction(friction);
		fixture.setIsSensor(is_sensor);
		// newBody.get
		final CircleShape shape = gdx_ph_container.circleAspect().getCircleShape();
		shape.setRadius(radius);

		fixture.setShape(shape);
		newBody.createFixture(fixture);

		newBody.setTransform(newBody.getWorldCenter(), angle);
		return newBody;
	}

	public static Box2DBody createPoly (final RedPolyBodySpecs shape, final Box2DWorld box2dWorld, final double angle,
		final double density, final double friction, final double restitution, final boolean is_sensor, final BodyType type,
		final double x, final double y, final double scale, final short categoryBits, final short maskBits,
		final PhysicalParametersContainer gdx_ph_container) {

		final BodyDef boxBodyDef = gdx_ph_container.polyAspect().getBodyDef();
		boxBodyDef.setType(type);
		boxBodyDef.position().setX(x);
		boxBodyDef.position().setY(y);
		final Box2DBody newBody = box2dWorld.createBody(boxBodyDef);

		final FixtureDef fixture = gdx_ph_container.polyAspect().getFixture();
		fixture.setDensity(density);
		fixture.setRestitution(restitution);
		fixture.setFriction(friction);
		fixture.setIsSensor(is_sensor);

		fixture.filter().setCategoryBits(categoryBits);
		fixture.filter().setMaskBits(maskBits);

		attachFixture(shape, newBody, fixture, scale, x, y, gdx_ph_container);
		newBody.setTransform(x, y, angle);
		// newBody.setTransform(newBody.getWorldCenter(), angle);
		return newBody;
	}

	private static void attachFixture (final RedPolyBodySpecs shape, final Box2DBody newBody, final FixtureDef fixture_param,
		final double scale, final double x, final double y, final PhysicalParametersContainer gdx_ph_container) {
		// origin_tmp.set(shape.getOriginX() * scale, shape.getOriginY() *
		// scale);
		origin_tmp.setXY(0f, 0f);
		// Log.e("shape", shape.getAssetID());
		// Log.e("shape.getOriginX()", shape.getOriginX());
		// Log.e("shape.getOriginY()", shape.getOriginY());

		final Collection<PolyBodyCircle> circles = shape.listCircles();

		for (final PolyBodyCircle circle : circles) {

			center_tmp.setXY(circle.getX() * scale - origin_tmp.getX(), circle.getY() * scale - origin_tmp.getY());
			final CircleShape circleShape = gdx_ph_container.polyAspect().getCircleShape();
			circleShape.setPosition(center_tmp);

			circleShape.setRadius(circle.getRadius() * scale);
			fixture_param.setShape(circleShape);
			newBody.createFixture(fixture_param);
		}
		final Collection<PolyBodyChain> chains = shape.listPolyBodyChains();
		for (final PolyBodyChain chain : chains) {
			final Collection<Float2> vertices = chain.listVerices();
			final PolygonShape polyShape = gdx_ph_container.polyAspect().getPolygonShape();
			polyShape.setVertices(vertices);
			fixture_param.setShape(polyShape);

			newBody.createFixture(fixture_param);
		}
		// Log.e("#5");

	}

	static final Float2 origin_tmp = Geometry.newFloat2(0f, 0f);
	static final Float2 center_tmp = Geometry.newFloat2(0f, 0f);
}
