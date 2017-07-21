
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.animation.AnimationProgressFunction;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.SimplePostitionModifyer;
import com.jfixby.r3.activity.api.animation.SimplePostitionModifyerSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.raster.CanvasComponent;
import com.jfixby.r3.activity.api.update.ActivityClocks;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.log.L;
import com.jfixby.scarabei.api.math.FloatMath;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedSimplePostitionModifyer implements SimplePostitionModifyer, LayerBasedComponent {

	final private RedComponentsFactory components_factory;
	final private RedLayer root;
	final private PositionAnchor begin_anchor;
	final private PositionAnchor end_anchor;
	private final boolean is_looped;
	private final TimeStream clock;
	private AnimationProgressFunction func;
	private final OnUpdateListener updater = new OnUpdateListener() {

		@Override
		public void onUpdate (final ActivityClocks unit_clock) {

			RedSimplePostitionModifyer.this.update();
		}

	};

	@Override
	public void stopAnimation () {
		Err.throwNotImplementedYet();
	}

	private final CanvasComponent component;
	private final CanvasPosition current_position;

	public RedSimplePostitionModifyer (final SimplePostitionModifyerSpecs specs, final RedComponentsFactory components_factory) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();

		this.begin_anchor = specs.getBeginAnchor();
		this.end_anchor = specs.getEndAnchor();

		Debug.checkNull("begin_anchor", this.begin_anchor);
		Debug.checkNull("end_anchor", this.end_anchor);
		this.is_looped = specs.isLooped();
		this.clock = specs.getTimeStream();
		Debug.checkNull(".getTimeStream()", this.clock);
		this.func = specs.getProgressFunction();
		if (this.func == null) {
			this.func = SimplePostitionModifyerSpecs.IDENTITY;
		}

		this.component = specs.getCanvasComponent();

		Debug.checkNull("getCanvasComponent()", this.component);

		this.root.attachComponent(this.component);
		this.root.attachComponent(this.updater);

		this.loop_time = this.end_anchor.getTime() - this.begin_anchor.getTime();
		if (this.loop_time < 0) {
			L.d("begin_anchor", this.begin_anchor);
			L.d("end_anchor", this.end_anchor);
			Err.reportError("Begin anchor's timestamp is later than the end anchor's timestamp.");
		}

		this.current_position = Geometry.newCanvasPosition();

	}

	@Override
	public String toString () {
		return "Animation[" + this.component.getName() + "] " + this.begin_anchor + " -> " + this.end_anchor;
	}

	boolean started = false;
	private long start_time;
	private long current_time;
	private final long loop_time;

	private void update () {
		if (!this.root.isVisible()) {
			return;
		}

		if (!this.started) {
			this.startAnimation();
		}

		if (this.isAnimationDone()) {
			this.component.setPosition(this.end_anchor.position());
			return;
		}

		this.current_time = IntegerMath.limit(0, this.current_time, this.loop_time);

		this.current_time = this.current_time % this.loop_time;

		this.progress = FloatMath.limit(0d, this.current_time * 1d / this.loop_time, 1d);
		this.progress = this.func.valueFor(this.progress);

		Geometry.parametrize(this.begin_anchor.position(), this.progress, this.end_anchor.position(), this.current_position);

		// L.d(this.toString() + " : begin_anchor", begin_anchor);
		L.d(this.toString() + " : progress", this.progress);
		// L.d(this.toString() + " : end_anchor", end_anchor);
		// L.d(this.toString() + " : current_position", current_position);

		this.component.setPosition(this.current_position);

	}

	double progress = 0d;

	@Override
	public void hide () {
		this.root.hide();
	}

	@Override
	public void show () {
		this.root.show();
	}

	@Override
	public boolean isVisible () {
		return this.root.isVisible();
	}

	@Override
	public void setName (final String name) {
		this.root.setName(name);
	}

	@Override
	public String getName () {
		return this.root.getName();
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void startAnimation () {
		this.started = true;
		this.start_time = this.clock.currentTimeMillis();
	}

	@Override
	public boolean isAnimationDone () {
		if (this.is_looped) {
			return false;
		}
		this.current_time = this.clock.currentTimeMillis() - this.start_time;
		return this.current_time > this.end_anchor.getTime();
	}

	@Override
	public TimeStream getTimeStream () {
		return this.clock;
	}

	@Override
	public boolean isLooped () {
		return this.is_looped;
	}

	@Override
	public void setDebugRenderFlag (final boolean b) {
	}

	@Override
	public boolean getDebugRenderFlag () {
		return false;
	}

	@Override
	public void setDebugColor (final Color debug_render_color) {
	}

	@Override
	public Color getDebugColor () {
		return null;
	}

}
