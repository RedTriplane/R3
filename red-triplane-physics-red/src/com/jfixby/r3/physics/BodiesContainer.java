
package com.jfixby.r3.physics;

import com.jfixby.r3.physics.body.BodyImpl;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.log.L;

public class BodiesContainer {

	private final Box2DWorldWrap box2d_world;

	public BodiesContainer (final Box2DWorldWrap box2d_world) {
		this.box2d_world = box2d_world;

	}

	final List<BodyImpl> bodies_list = Collections.newList();

	public void attach (final BodyImpl body) {
		if (body == null) {
			Err.reportError("null is not allowed.");
		}
		if (this.bodies_list.contains(body)) {
			Err.reportError(body + " is already attached.");
		}
		this.bodies_list.add(body);
		body.onAttached(this.box2d_world);
	}

	public void detatch (final BodyImpl body) {
		if (body == null) {
			Err.reportError("null is not allowed here.");
		}
		if (!this.bodies_list.contains(body)) {
			L.e(body + " not found.");
			return;
		}
		this.bodies_list.remove(body);
		body.onDetached();
	}

	public void warp_in_all () {
		for (int i = 0; i < this.bodies_list.size(); i++) {
			this.bodies_list.getElementAt(i).warpIn();
		}
	}

	public void prepare_to_warp_out_all () {
		for (int i = 0; i < this.bodies_list.size(); i++) {
			this.bodies_list.getElementAt(i).openWarpingRiftIfNeeded();
		}
	}

	public void warp_out_all () {
		for (int i = 0; i < this.bodies_list.size(); i++) {
			this.bodies_list.getElementAt(i).warpOut();
		}
	}

	public void redefine_all () {
		for (int i = 0; i < this.bodies_list.size(); i++) {
			this.bodies_list.getElementAt(i).redefineIfNeeded();
		}
	}

	public void apply_all_forces () {
		for (int i = 0; i < this.bodies_list.size(); i++) {
			this.bodies_list.getElementAt(i).applyForcesIfNeeded();
		}
	}

}
