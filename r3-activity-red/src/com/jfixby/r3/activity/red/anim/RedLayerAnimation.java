
package com.jfixby.r3.activity.red.anim;

import com.jfixby.r3.activity.api.LayerBasedComponent;
import com.jfixby.r3.activity.api.animation.FrameAnimationSpecs;
import com.jfixby.r3.activity.api.animation.LayersAnimation;
import com.jfixby.r3.activity.api.animation.LayersAnimationSpecs;
import com.jfixby.r3.activity.api.layer.Layer;
import com.jfixby.r3.activity.api.layer.VisibleComponent;
import com.jfixby.r3.activity.red.RedComponentsFactory;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.List;
import com.jfixby.scarabei.api.color.Color;
import com.jfixby.scarabei.api.debug.Debug;
import com.jfixby.scarabei.api.err.Err;
import com.jfixby.scarabei.api.time.TimeStream;

public class RedLayerAnimation implements LayersAnimation, LayerBasedComponent {

	private final RedComponentsFactory components_factory;
	private final RedLayer root;
	final List<RedFrameHolder> frames = Collections.newList();
	final List<VisibleComponent> components = Collections.newList();
	private final TimeStream animation_clock;
	private final boolean isLooped;

	@Override
	public void stopAnimation () {
		Err.throwNotImplementedYet();
	}

	// private boolean isSimple;
	// private boolean isPositionModifyer;

	public RedLayerAnimation (final LayersAnimationSpecs specs, final RedComponentsFactory components_factory) {
		this.components_factory = components_factory;
		this.root = components_factory.newLayer();

		final List<FrameAnimationSpecs> frames = specs.getFrames();
		this.animation_clock = specs.getTimeStream();
		Debug.checkNull("getTimeStream()", this.animation_clock);
		long loop_time = 0;
		for (final FrameAnimationSpecs fame : frames) {
			Debug.checkNull("frame", fame);
			final RedFrameHolder holder = new RedFrameHolder();
			holder.setComponent(fame.getComponent());
			final long frameTime = fame.getFrameTime();
			holder.setFrameTime(loop_time, loop_time + frameTime);
			loop_time = loop_time + frameTime;
			this.components.add(holder.getComponent());
			this.frames.add(holder);
			// fame.setOpacity(0.5d);
			this.root.attachComponent(holder.getComponent());

		}

		this.isLooped = specs.isLooped();

		this.animation = new SimpleAnimator(this, loop_time);
		this.root.attachComponent(this.animation);

	}

	SimpleAnimator animation;

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
	public String toString () {
		return "LayersAnimation[" + this.getName() + "] " + isHidden(!this.isVisible());
	}

	private static String isHidden (final boolean isHidden) {
		if (isHidden) {
			return "(hidden)";
		} else {
			return "";
		}

	}

	@Override
	public boolean isLooped () {
		return this.isLooped;
	}

	@Override
	public void setVisible (final boolean b) {
		this.root.setVisible(b);
	}

	@Override
	public void startAnimation () {
		this.animation.start();
	}

	@Override
	public boolean isAnimationDone () {
		return this.animation.isDone();
	}

	@Override
	public TimeStream getTimeStream () {
		return this.animation_clock;
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
		for (int i = 0; i < this.frames.size(); i++) {
			// frames.getElementAt(i).
		}

		return null;
	}

	@Override
	public Collection<VisibleComponent> listFrames () {
		return this.components;
	}

}
