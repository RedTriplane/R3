
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.update.ActivityClocks;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.sys.Sys;
import com.jfixby.scarabei.api.time.TimeStream;
import com.jfixby.scarabei.api.util.Utils;
import com.jfixby.scarabei.api.util.StateSwitcher;

public class SimpleAnimator implements OnUpdateListener {

	private static final long DELTA = 15;
	final private List<RedFrameHolder> frames;
	private long loop_time;

	final StateSwitcher<ANIMATION_STATE> state = Utils.newStateSwitcher(ANIMATION_STATE.VOID);

	private final TimeStream clock;
	private long current_time;
	private long progress;
	private final RedLayerAnimation master;
	private boolean no_animation = false;

	public SimpleAnimator (final RedLayerAnimation redLayerAnimation, final long loop_time) {
		this.master = redLayerAnimation;
		this.frames = redLayerAnimation.frames;
		if (this.frames.size() == 0) {
			Err.reportError("No frames found.");
		}

		Debug.checkNull("frames", this.frames);

		this.loop_time = loop_time;
		if (this.frames.size() == 0) {
			this.no_animation = true;
		}

		if (this.loop_time == 0) {
			this.loop_time = Long.MAX_VALUE;
		}

		this.clock = redLayerAnimation.getTimeStream();

		Debug.checkNull("animation_clock", this.clock);

		for (final RedFrameHolder frame : this.frames) {
			frame.hide();
		}
		this.frames.getElementAt(0).show();

	}

	private long long_start_time;

	private long previous_system_time;
	private long system_delta;
	private long current_system_time;

	@Override
	public void onUpdate (final ActivityClocks unit_clock) {
		if (this.no_animation) {
			return;
		}

		if (this.state.currentState() == ANIMATION_STATE.DONE) {
			return;
		}
		if (this.state.currentState() == ANIMATION_STATE.VOID) {
			return;
		}

		this.previous_system_time = this.current_system_time;
		this.current_system_time = Sys.component().SystemTime().currentTimeMillis();
		this.system_delta = this.current_system_time - this.previous_system_time;
		if (this.system_delta < DELTA) {
			return;
		}

		this.current_time = this.clock.currentTimeMillis() - this.long_start_time;

		if (!this.master.isLooped() && this.current_time > this.loop_time) {
			this.frames.getLast().show();
			this.state.switchState(ANIMATION_STATE.DONE);
		} else {
			this.progress = this.current_time % this.loop_time;
			if (this.progress < 0) {
				this.progress = this.loop_time + this.progress;
			}
			for (final RedFrameHolder frame : this.frames) {
				frame.setup(this.progress);
			}
		}

	}

	void start () {
		this.long_start_time = this.clock.currentTimeMillis();
// if (this.long_start_time > 0) {
// Debug.printCallStack();
// Err.reportError("long_start_time=" + this.long_start_time);
// }
		this.state.switchState(ANIMATION_STATE.PLAYING);
	}

	public boolean isDone () {
		return this.state.currentState() == ANIMATION_STATE.DONE;
	}

}
