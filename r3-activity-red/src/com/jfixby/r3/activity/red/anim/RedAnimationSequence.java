
package com.jfixby.r3.activity.red.anim;

import java.util.Comparator;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.animation.AnimationLifecycleListener;
import com.jfixby.r3.activity.api.animation.PositionAnchor;
import com.jfixby.r3.activity.api.animation.PositionsSequence;
import com.jfixby.r3.activity.api.animation.PositionsSequenceSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.update.ActivityClocks;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.collections.CollectionConverter;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.debug.StateSwitcher;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.floatn.Float2;
import com.jfixby.scarabei.api.geometry.CanvasPosition;
import com.jfixby.scarabei.api.geometry.Geometry;
import com.jfixby.scarabei.api.geometry.Spline2D;
import com.jfixby.scarabei.api.geometry.projections.RotateAndOffsetProjection;
import com.jfixby.scarabei.api.math.IntegerMath;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedAnimationSequence implements PositionsSequence, LayerBasedComponent {

	@Override
	public String toString () {
		return "PositionsSequence[" + this.getName() + "] " + isHidden(!this.isVisible()) + " " + this.animations_list;
	}

	private static String isHidden (final boolean isHidden) {
		if (isHidden) {
			return "(hidden)";
		} else {
			return "";
		}

	}

	StateSwitcher<ANIMATION_STATE> state = Debug.newStateSwitcher(ANIMATION_STATE.VOID);

	final List<PositionAnchor> animations_list = Collections.newList();
	PositionAnchor current_anchor_A;
	PositionAnchor current_anchor_B;
	final private RedLayer root;
	private final boolean is_looped;

	private final Layer frame;

	private final TimeStream clock;

	private AnimationLifecycleListener lifecycle_listener;

	private final boolean use_spline;

	private Spline2D spline;

	private final RotateAndOffsetProjection offset;
	static private Comparator<PositionAnchor> anchors_sorter = new Comparator<PositionAnchor>() {

		@Override
		public int compare (final PositionAnchor o1, final PositionAnchor o2) {
// return Long.compare(o1.getTime(), o2.getTime());
			final long l1 = o1.getTime();
			final long l2 = o2.getTime();
			return IntegerMath.compare(l1, l2);

		}
	};

	public RedAnimationSequence (final PositionsSequenceSpecs specs, final RedComponentsFactory master) {

		this.frame = specs.getFramesContainer();
		this.offset = specs.getProjection();

		this.clock = specs.getTimeStream();
		Debug.checkNull("getTimeStream()", this.clock);
// if (this.clock == Sys.SystemTime()) {
// Debug.printCallStack();
// }
// Debug.checkNull("getComponent()", this.frame);

		final List<PositionAnchor> list = specs.listAnchors();
		this.use_spline = specs.useSpline();
		this.animations_list.addAll(list);
		this.animations_list.sort(anchors_sorter);
		if (list.size() == 0) {
			Err.reportError("No animations found, listAnimations() is empty.");
		}

		this.is_looped = specs.isLooped();
		if (!this.is_looped) {
			this.lifecycle_listener = specs.getOnAnimationDoneListener();
		}
		this.root = master.newLayer();
// this.root.setProjection(this.offset);
		this.root.attachComponent(this.updater);
		if (this.frame != null) {
			this.root.setProjection(this.offset);
			this.root.attachComponent(this.frame);
		}

		for (int i = 0; i < this.animations_list.size(); i++) {
			final PositionAnchor element = this.animations_list.getElementAt(i);
			this.loop_time = IntegerMath.max(this.loop_time, element.getTime());
		}
		if (this.loop_time < 0) {
			Err.reportError("Negative loop time: " + this.loop_time);
		}

		if (list.size() == 1 || this.loop_time == 0) {
			// this.state.switchState(ANIMATION_STATES.DONE);

			this.current_anchor_A = this.animations_list.getElementAt(0);
			this.current_anchor_B = this.animations_list.getElementAt(0);
// this.frame.setPosition(this.current_anchor_A);
			this.offset.set(this.current_anchor_A.position());

			Err.reportError("Not supported yet!");

		}

		if (this.use_spline) {

			this.spline = Geometry.newSpline2D();
// this.animations_list.print("animations_list");
			final List<Float2> liastFloat2 = Collections.newList();
			final List<PositionAnchor> listInout = this.animations_list;
			final CollectionConverter<PositionAnchor, Float2> converter = new CollectionConverter<PositionAnchor, Float2>() {
				@Override
				public Float2 convert (final PositionAnchor a) {
					return a.position();
				}
			};
			Collections.convertCollection(listInout, liastFloat2, converter);
			this.spline.computeSpline(liastFloat2, 100);

		}

	}

	long loop_time = 0;

	final OnUpdateListener updater = new OnUpdateListener() {

		@Override
		public void onUpdate (final ActivityClocks unit_clock) {
			RedAnimationSequence.this.update();
		}
	};

	private long start_time;

	private long current_time;

	private final CanvasPosition current_position = Geometry.newCanvasPosition();

	private long anchor_a_time;

	private long anchor_b_time;

	private long current_period;

	private long relative_time;

	private double progress;

	private int anchor_a;

	private int anchor_b;

	public void update () {
		if (this.state.currentState() == ANIMATION_STATE.DONE) {
			return;
		}
		if (this.state.currentState() == ANIMATION_STATE.VOID) {
			// this.startAnimation();
			return;
		}
		if (this.use_spline) {
			this.updateSpline();
		} else {
			this.updateLinear();
		}
		// L.d("current_position", current_position);

		// L.d();
	}

	int loopIndex = 0;

	private void updateSpline () {

		this.current_time();

		if (this.isDone()) {
			this.complete();
			return;
		}

		this.checkLoop();
		final double progress = this.current_time * 1d / this.loop_time;
		Debug.checkTrue("progress <=1 : " + progress, progress <= 1);
		this.spline.readValue(progress, this.current_position);

// this.frame.setPosition(this.current_position);
		this.offset.set(this.current_position);
	}

	private void current_time () {
		this.current_time = this.clock.currentTimeMillis() - this.start_time;
		while (this.current_time < 0) {
			this.current_time = this.current_time + this.loop_time;
		}
	}

	private void checkLoop () {
		while (this.is_looped & this.current_time >= this.loop_time) {
			this.current_time = this.current_time - this.loop_time;
			if (this.lifecycle_listener != null) {
				this.lifecycle_listener.onLoop(this, this.loopIndex);
			}
			this.loopIndex++;
		}
	}

	private void updateLinear () {
		this.current_time();

		if (this.isDone()) {
			this.complete();
			return;
		}

		this.checkLoop();

		this.anchor_a = this.getAnchorA_index(this.current_time);
		this.anchor_b = (this.anchor_a) + 1;
		this.current_anchor_A = this.animations_list.getElementAt(this.anchor_a);
		this.current_anchor_B = this.animations_list.getElementAt(this.anchor_b);

		// L.d("A: " + anchor_a, current_anchor_A);
		// L.d("B: " + anchor_b, current_anchor_B);

		this.anchor_a_time = this.current_anchor_A.getTime();
		this.anchor_b_time = this.current_anchor_B.getTime();

		this.current_period = (this.anchor_b_time - this.anchor_a_time);

		// L.d("current_time", current_time);
		// L.d("current_period", current_period);

		this.relative_time = this.current_time - this.anchor_a_time;
		// L.d("relative_time", relative_time);

		this.progress = this.relative_time * 1d / this.current_period;
		// L.d("progress", progress);

		Geometry.parametrize(this.current_anchor_A.position(), this.progress, this.current_anchor_B.position(),
			this.current_position);

// this.frame.setPosition(this.current_position);
		this.offset.set(this.current_position);
// L.d("offset", this.current_position);
	}

	private void complete () {
		this.state.switchState(ANIMATION_STATE.DONE);
		this.current_anchor_A = this.animations_list.getLast();
		this.current_anchor_B = this.animations_list.getLast();
// this.frame.setPosition(this.current_anchor_A);
		this.offset.set(this.current_anchor_A.position());
		if (this.lifecycle_listener != null) {
			this.lifecycle_listener.onAnimationDone(this, this.loopIndex);
		}
	}

	private int getAnchorA_index (final long current_time) {
		for (int i = this.animations_list.size() - 1; i >= 0; i--) {
			final PositionAnchor candidate = this.animations_list.getElementAt(i);
			if (current_time < candidate.getTime()) {
				continue;
			}
			return i;
		}

		return 0;
	}

	private boolean isDone () {
		if (this.is_looped) {
			return false;
		}
		return this.current_time >= this.loop_time;
	}

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
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public Layer getRoot () {
		return this.root;
	}

	@Override
	public void startAnimation () {
		// this.state.expectStates(ANIMATION_STATES.VOID,
		// ANIMATION_STATES.DONE);
		this.state.switchState(ANIMATION_STATE.PLAYING);
		this.start_time = this.clock.currentTimeMillis();
		if (this.lifecycle_listener != null) {
			this.lifecycle_listener.onAnimationStart(this);
		}
		// current_anchor_index = 0;
		// this.current_anchor_A = this.animations_list
		// .getElementAt(current_anchor_index);
		// this.current_anchor_B = this.animations_list
		// .getElementAt(current_anchor_index + 1);

	}

	@Override
	public float loopsComplete () {
		if (this.state.currentState() == ANIMATION_STATE.DONE) {
			return 1;
		}
		return this.current_time * 1f / this.current_period;
	}

	@Override
	public void stopAnimation () {

		Err.reportError("");
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
