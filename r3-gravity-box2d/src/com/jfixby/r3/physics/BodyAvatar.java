
package com.jfixby.r3.physics;

import com.jfixby.r3.api.physics.BODY_TYPE;
import com.jfixby.r3.api.physics.Body;
import com.jfixby.r3.physics.body.BodyImpl;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class BodyAvatar {

	enum STATE {
		SPIRIT, MATERIALIZED
	}

	private STATE state;

	final private BodyImpl master;

	public BodyAvatar (final BodyImpl master, final ShapeData to_clone) {
		this.master = master;
		this.state = STATE.SPIRIT;
		this.shape_data = new ShapeData(to_clone);
	}

	private void setState (final STATE next_state) {
		this.state = next_state;
	}

	private final WarpingData warping_data = new WarpingData();
	private final PhysicalPropertiesImpl ph_properties = new PhysicalPropertiesImpl();
	private final ShapeData shape_data;
	private final Box2DBodyWrap body_wrap = new Box2DBodyWrap(this);

	public void onAttached (final Box2DWorldWrap where) {
		if (this.state == STATE.SPIRIT) {
			this.read_all_data_of_the_master();
			this.materialize_body(where);
			this.setState(STATE.MATERIALIZED);
		} else {
			Err.reportError("Wrong state" + this.state);
		}

	}

	private void read_all_data_of_the_master () {
		this.read_warping_data_of_the_master();
		this.read_essential_data_of_the_master();
	}

	private void read_warping_data_of_the_master () {
		this.warping_data.setValues(this.master.warpingData());
	}

	private void read_essential_data_of_the_master () {
		this.ph_properties.setValues(this.master.physics());
		this.shape_data.setValues(this.master.shapeData());
	}

	private void materialize_body (final Box2DWorldWrap where) {
		this.body_wrap.setCurrentWorld(where);
		this.body_wrap.create_body(this.ph_properties, this.shape_data, this.warping_data);
	}

	public void onDetached () {
		if (this.state == STATE.MATERIALIZED) {
			this.body_wrap.destroy_body();
			this.body_wrap.setCurrentWorld(null);
			this.setState(STATE.SPIRIT);
		} else {
			Err.reportError("Wrong state" + this.state);
		}

	}

	boolean warping_rift_is_open = false;

	public void warpIn () {

		if (this.master_was_relocated()) {
			this.read_warping_data_of_the_master();
			this.relocate_body();
		}
	}

	public void redefineIfNeeded () {
		if (this.need_to_rematerialize()) {
			this.read_essential_data_of_the_master();
			this.rematerialize_body();
			// this.relocate_body();
		}
	}

	public void openWarpingRiftIfNeeded () {
		this.warping_rift_is_open = this.need_warping_out();
	}

	private void relocate_body () {
		// this.rematerialize_body();

		this.body_wrap.setTransform(this.warping_data.getX(), this.warping_data.getY(), this.warping_data.getRotation());
		this.body_wrap.setVelocity(this.warping_data.getVelocityX(), this.warping_data.getVelocityY());
		this.body_wrap.setMass(this.warping_data.getMass());
	}

	private boolean master_was_relocated () {
		return !this.warping_data.isEqualTo(this.master.warpingData());
	}

	public void warpOut () {
		if (this.warping_rift_is_open) {
			this.write_warp_data_to_the_master();
			this.warping_rift_is_open = false;// close rift
		}
	}

	private boolean need_warping_out () {
		if (this.master.physics().getType() == BODY_TYPE.STATIC) {
			return false;
		}
		return true;
	}

	private boolean need_to_rematerialize () {
		if (this.master.wantsToBeRematerialized()) {
			// L.d("wantsToBeRematerialized()", this);
			return true;
		}
		if (!this.master.physics().isEqualTo(this.ph_properties)) {
			// L.d("physics().isEqualTo", this);
			return true;
		}
		if (!this.master.shapeData().isEqualTo(this.shape_data)) {
			// L.d("shapeData().isEqualTo", this);
			return true;
		}

		return false;
	}

	private void rematerialize_body () {
		// L.d("rematerialize_body", this.body_wrap);
		// L.d("rematerialize_body", this.body_wrap + " : "
		// + this.body_wrap.getBox2DBody().getFixtureList());

		this.body_wrap.destroy_body();
		this.body_wrap.create_body(this.ph_properties, this.shape_data, this.warping_data);
		// L.d(" ", this.body_wrap);
	}

	private void write_warp_data_to_the_master () {
		this.warping_data.setRotation(this.body_wrap.getAngle());
		this.warping_data.setX(this.body_wrap.getPositionX());
		this.warping_data.setY(this.body_wrap.getPositionY());
		this.warping_data.setVelocity(this.body_wrap.getLinearVelocityX(), this.body_wrap.getLinearVelocityY());
		this.master.warpingData().setValues(this.warping_data);
	}

	public Body getMaster () {
		return this.master;
	}

	public void onCollisionBeginWith (final BodyAvatar red_body_b) {
		if (this.state == STATE.MATERIALIZED) {
			// Log.d("onCollisionBeginWith",red_body_b.getDebugName());
			this.master.listeners().onCollisionBeginWith(red_body_b);
		} else {
			L.e("missed onContactBegin() for " + this.getDebugName() + " vs " + red_body_b.getDebugName());
		}
	}

	public String getDebugName () {
		return this.master.getDebugName();
	}

	public void onCollisionEndWith (final BodyAvatar red_body_b) {
		if (this.state == STATE.MATERIALIZED) {
			// Log.d("onCollisionEndWith",red_body_b.getDebugName());
			this.master.listeners().onCollisionEndWith(red_body_b);
		} else {
			L.e("missed onContactEnd() for " + this.master.getDebugName() + " vs " + red_body_b.getDebugName());
		}
	}

	public void onCollisionLostWith (final BodyAvatar red_body_b) {
		if (this.state == STATE.MATERIALIZED) {
			// Log.d("onCollisionLostWith",red_body_b.getDebugName());
			this.master.listeners().onCollisionLostWith(red_body_b);
		} else {
			L.e("missed onContactLost() for " + this.getDebugName() + " vs " + red_body_b.getDebugName());
		}
	}

	public void applyForce (final double force_x, final double force_y) {
		this.body_wrap.applyForce(force_x, force_y);
	}

}
