
package com.jfixby.r3.physics.body;

import com.jfixby.r3.api.physics.BODY_SHAPE_TYPE;
import com.jfixby.r3.api.physics.Body;
import com.jfixby.r3.api.physics.BodyDebugInfo;
import com.jfixby.r3.api.physics.BodyMassController;
import com.jfixby.r3.api.physics.BodyPositionController;
import com.jfixby.r3.api.physics.PolyBodySpecs;
import com.jfixby.r3.physics.BodyAvatar;
import com.jfixby.r3.physics.BodyDynamicsImpl;
import com.jfixby.r3.physics.BodyListenersImpl;
import com.jfixby.r3.physics.Box2DWorldWrap;
import com.jfixby.r3.physics.DebugProperties;
import com.jfixby.r3.physics.FokkerConfig;
import com.jfixby.r3.physics.PhysicalPropertiesImpl;
import com.jfixby.r3.physics.ShapeData;
import com.jfixby.r3.physics.WarpingData;
import com.jfixby.scarabei.api.err.Err;

public abstract class BodyImpl implements Body {

	private static final boolean FORCE_REMATERIALIZATION = FokkerConfig.FORCE_REMATERIALIZATION;

	public final BodyAvatar body_avatar;

	private final DebugProperties debugProperties = new DebugProperties();
	private final BodyListenersImpl listeners;
	private final PhysicalPropertiesImpl physicalProperties = new PhysicalPropertiesImpl();
	private final WarpingData warpingData = new WarpingData();
	private final BodyDynamicsImpl dynamics = new BodyDynamicsImpl(this);
	private final ShapeData shape_data;

	void setupGeometry (final PolyBodySpecs specs) {
		this.shape_data.setupGeometry(specs);
	}

	public BodyImpl (final ShapeData shape_data) {
		super();
		if (shape_data == null) {
			Err.reportError("ShapeData is null.");
		}
		this.shape_data = shape_data;
		this.body_avatar = new BodyAvatar(this, shape_data);
		this.listeners = new BodyListenersImpl(this.dynamics);
	}

	public void onAttached (final Box2DWorldWrap where) {
		this.body_avatar.onAttached(where);
	}

	public void onDetached () {
		this.body_avatar.onDetached();
	}

	@Override
	public BODY_SHAPE_TYPE getShapeType () {
		return this.shape_data.getShapeType();
	}

	public void warpIn () {
		this.body_avatar.warpIn();

	}

	public void openWarpingRiftIfNeeded () {
		this.body_avatar.openWarpingRiftIfNeeded();
	}

	public void warpOut () {
		this.body_avatar.warpOut();
	}

	@Override
	public String toString () {
		return this.getDebugName();
	}

	// public void onTimeUpdate(final ITimeSource gameTime) {
	// this.listeners.updateTime(gameTime);
	// }

	public void setSize (final double width, final double height) {
		this.shape_data.setSize(width, height);
	}

	public void setWidth (final double width) {
		this.shape_data.setWidth(width);
	}

	public void setHeight (final double height) {
		this.shape_data.setHeight(height);
	}

	public double getWidth () {
		return this.shape_data.getWidth();
	}

	public double getHeight () {
		return this.shape_data.getHeight();
	}

	public void setRadius (final double radius) {
		this.shape_data.setRadius(radius);
	}

	public double getRadius () {
		return this.shape_data.getRadius();
	}

	public String getDebugName () {
		return this.shape_data.getShapeType() + "[" + this.debugProperties.getName() + "]";
	}

	public WarpingData warpingData () {
		return this.warpingData;
	}

	@Override
	public PhysicalPropertiesImpl physics () {
		return this.physicalProperties;
	}

	public ShapeData shapeData () {
		return this.shape_data;
	}

	@Override
	public BodyListenersImpl listeners () {
		return this.listeners;
	}

	@Override
	public BodyPositionController location () {
		return this.warpingData;
	}

	@Override
	public BodyDebugInfo debugInfo () {
		return this.debugProperties;
	}

	public ShapeData shape () {
		return this.shape_data;
	}

	public boolean wantsToBeRematerialized () {
		return FORCE_REMATERIALIZATION;
	}

	public void redefineIfNeeded () {
		this.body_avatar.redefineIfNeeded();
	}

	public void applyForcesIfNeeded () {
		this.dynamics.applyForcesIfNeeded();
	}

	@Override
	public BodyMassController mass () {
		return this.warpingData;
	}

}
