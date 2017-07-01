
package com.jfixby.r3.physics;

import org.box2d.jfixby.api.BodyType;
import org.box2d.jfixby.api.Box2DBody;
import org.box2d.jfixby.api.MassData;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.BODY_TYPE;
import com.jfixby.scarabei.api.angles.Angles;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.math.Angle;
import com.jfixby.scarabei.api.math.CustomAngle;

public class Box2DBodyWrap {
	private Box2DWorldWrap world;
	private Box2DBody box2d_body;
	private PhysicalParametersContainer gdx_ph_container;
	private final BodyAvatar owner;

	public Box2DBodyWrap (final BodyAvatar owner) {
		super();
		this.owner = owner;
	}

	@Override
	public String toString () {
		return "" + this.box2d_body;
	}

	final CustomAngle angle = Angles.newAngle();

	public Angle getAngle () {
		return this.angle.setValue(this.box2d_body.getAngle());
	}

	public double getPositionX () {
		return this.box2d_body.getPosition().getX();
	}

	public double getPositionY () {
		return this.box2d_body.getPosition().getY();
	}

	public double getLinearVelocityX () {
		return this.box2d_body.getLinearVelocity().getX();
	}

	public double getLinearVelocityY () {
		return this.box2d_body.getLinearVelocity().getY();
	}

	private void destroy_the_body () {
		this.world.disposeBodyCall(this.box2d_body);
	}

	public void create_body (final PhysicalPropertiesImpl ph_properties, final ShapeData shape_data,
		final WarpingData warping_data) {
		this.box2d_body = this.create_gdx_body(ph_properties, shape_data, warping_data);
		this.world.getContactListener().register(this.box2d_body, this.owner);
	}

	public void destroy_body () {
		this.destroy_the_body();
		this.world.getContactListener().unregister(this.box2d_body, this.owner);
		this.gdx_ph_container.dispose();
		this.gdx_ph_container = null;
		this.box2d_body = null;
	}

	public Box2DBody create_gdx_body (final PhysicalPropertiesImpl gdx_avatarProperties, final ShapeData shape_data,
		final WarpingData warping_data) {
		if (this.gdx_ph_container != null) {
			Err.throwNotImplementedYet();
		}
		this.gdx_ph_container = new PhysicalParametersContainer(this.world);

		if (shape_data.getShapeType() == BODY_SHAPE_TYPE.BOX) {
			return this.world.createBoxCall(warping_data.getRotation(), warping_data.getX(), warping_data.getY(),
				shape_data.getWidth(), shape_data.getHeight(), gdx_avatarProperties.isSensor(),
				resolve_body_type(gdx_avatarProperties.getType()), warping_data.getVelocityX(), warping_data.getVelocityY(),
				gdx_avatarProperties.getDensity(), gdx_avatarProperties.getFriction(), gdx_avatarProperties.getRestitution(),
				gdx_avatarProperties.getCategoryBits(), gdx_avatarProperties.getMaskBits(), this.gdx_ph_container);

		}

		if (shape_data.getShapeType() == BODY_SHAPE_TYPE.CIRCLE) {
			return this.world.createCircleCall(warping_data.getRotation(), warping_data.getX(), warping_data.getY(),
				shape_data.getRadius(), gdx_avatarProperties.isSensor(), resolve_body_type(gdx_avatarProperties.getType()),
				warping_data.getVelocityX(), warping_data.getVelocityY(), gdx_avatarProperties.getDensity(),
				gdx_avatarProperties.getFriction(), gdx_avatarProperties.getRestitution(), gdx_avatarProperties.getCategoryBits(),
				gdx_avatarProperties.getMaskBits(), this.gdx_ph_container);

		}

		if (shape_data.getShapeType() == BODY_SHAPE_TYPE.POLY) {

			return this.world.createPolyBodyCall(shape_data.getPhysicsShape(), warping_data.getRotation(), warping_data.getX(),
				warping_data.getY(), gdx_avatarProperties.isSensor(), resolve_body_type(gdx_avatarProperties.getType()),
				warping_data.getVelocityX(), warping_data.getVelocityY(), gdx_avatarProperties.getDensity(),
				gdx_avatarProperties.getFriction(), gdx_avatarProperties.getRestitution(), shape_data.getScale(),
				gdx_avatarProperties.getCategoryBits(), gdx_avatarProperties.getMaskBits(), this.gdx_ph_container);
		}

		Err.reportError("Unknown shape: " + shape_data.getShapeType());
		return this.box2d_body;

	}

	static final private BodyType resolve_body_type (final BODY_TYPE bodyType) {
		if (bodyType == BODY_TYPE.DYNAMIC) {
			return BodyType.DynamicBody;
		}
		if (bodyType == BODY_TYPE.STATIC) {
			return BodyType.StaticBody;
		}
		if (bodyType == BODY_TYPE.KINEMATIC) {
			return BodyType.KinematicBody;
		}
		Err.throwNotImplementedYet();
		return null;
	}

	public void setCurrentWorld (final Box2DWorldWrap where) {
		this.world = where;
	}

	public void setTransform (final double x, final double y, final Angle rotation) {
		this.box2d_body.setTransform(x, y, rotation.toRadians());
	}

	public void setVelocity (final double velocityX, final double velocityY) {
		this.box2d_body.setLinearVelocity(velocityX, velocityY);
	}

	public void applyForce (final double force_x, final double force_y) {
		this.box2d_body.applyForceToCenter(force_x, force_y, true);
	}

	private MassData mass_data;

	public void setMass (final double mass) {
		this.mass_data = this.box2d_body.getMassData();
		this.mass_data.setMass(mass);
		this.mass_data.setI(1f);
		this.box2d_body.setMassData(this.mass_data);
		this.mass_data = null;
	}

	public Box2DBody getBox2DBody () {
		return this.box2d_body;
	}

}
