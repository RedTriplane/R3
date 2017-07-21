
package com.jfixby.r3.activity.red;

import com.jfixby.r3.activity.api.update.ActivityClocks;
import com.jfixby.r3.activity.api.update.OnUpdateListener;
import com.jfixby.r3.activity.red.layers.FastList;
import com.jfixby.r3.activity.red.layers.RedLayer;

public class RedLayerTaskManager implements ActivityClocks {

	private final RedLayer root_layer;
	// private RedActivityClock unit_clock;
	// private RedActivityClock render_clock;

	public RedLayerTaskManager (final RedLayer root_layer) {
		this.root_layer = root_layer;
		// this.unit_clock = unit_clock;
		// this.render_clock = render_clock;
	}

	public final void updateLayers () {
		this.updateLayer(this.root_layer);
	}

	private final void updateLayer (final RedLayer layer) {
		if (!layer.isVisible()) {
			return;
		}
		if (!layer.isListeningForUpdates()) {
			return;
		}
		final FastList<Object> update_listeners = layer.listUpdateListeners();
		for (int i_updateLayer = 0; i_updateLayer < update_listeners.size(); i_updateLayer++) {
			final Object element = update_listeners.getElementAt(i_updateLayer);
			if (element instanceof RedLayer) {
				this.updateLayer((RedLayer)element);
			} else {
				final OnUpdateListener listener = (OnUpdateListener)element;
				// this.unit_clock.update();
				// this.render_clock.update();
				listener.onUpdate(this);
			}
		}

	}

	// @Override
	// public TimeStream getRenderClock() {
	// return this.render_clock;
	// }
	//
	// @Override
	// public TimeStream getActivityClock() {
	// return this.unit_clock;
	// }

}
