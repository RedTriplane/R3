
package com.jfixby.r3.activity.red.input;

import java.util.Iterator;

import com.jfixby.r3.activity.api.user.KeyboardInputEventListener;
import com.jfixby.r3.activity.api.user.MouseInputEventListener;
import com.jfixby.r3.activity.red.cam.RedCamera;
import com.jfixby.r3.activity.red.layers.FastList;
import com.jfixby.r3.activity.red.layers.RedLayer;
import com.jfixby.r3.api.exe.InputEvent;
import com.jfixby.r3.api.exe.InputQueue;
import com.jfixby.scarabei.api.err.Err;

public class RedLayerInputProcessor {

	private final RedLayer root_layer;

	public RedLayerInputProcessor (final RedLayer root_layer) {
		this.root_layer = root_layer;
	}

	final ActivityInputEventsDeliveryBox box = new ActivityInputEventsDeliveryBox();

	public void deliverInput (final InputQueue input) {
		final Iterator<InputEvent> iterator = input.getIterator();
		while (iterator.hasNext()) {
			final InputEvent input_event = iterator.next();
			this.putInDeliveryBox(input_event);
			if (this.box.containsKeyboardInputEvent()) {
				this.processKeyboardDelivery(this.root_layer);
			} else if (this.box.containsMouseInputEvent()) {
				// L.d("----------------------------------------");
				this.processMouseDelivery(this.root_layer);
				this.box.checkStack();
			}
		}
	}

	boolean processKeyboardDelivery (final RedLayer layer) {
		if (!layer.isVisible()) {
			return false;
		}
		if (!layer.inputValveIsOpen()) {
			return false;
		}
		final FastList<Object> unit_input_listeners = layer.listActivityKeyboardListeners();
		for (int i = 0; i < unit_input_listeners.size(); i++) {
			final int k = unit_input_listeners.size() - 1 - i;
			final Object listener = unit_input_listeners.getElementAt(k);
			final boolean success = this.try_to_deliver_keyboard_event(listener);
			if (success) {
				return true;
			}
		}
		return false;
	}

	boolean processMouseDelivery (final RedLayer layer) {
		// L.d("mouse in", layer);
		if (!layer.isVisible()) {
			return false;
		}
		if (!layer.inputValveIsOpen()) {
			return false;
		}
		final RedCamera camera = layer.getCamera();
		this.box.stackInCamera(camera);
		final FastList<Object> unit_input_listeners = layer.listActivityMouseListeners();
		for (int i = 0; i < unit_input_listeners.size(); i++) {
			final int k = unit_input_listeners.size() - 1 - i;
			final Object listener = unit_input_listeners.getElementAt(k);
			final boolean success = this.try_to_deliver_mouse_event(listener);
			if (success) {
				this.box.stackOutCamera(camera);
				// L.d("mouse out", layer);
				return true;
			}
		}
		this.box.stackOutCamera(camera);
		// L.d("mouse out", layer);
		return false;
	}

	private boolean try_to_deliver_keyboard_event (final Object listener) {
		if (listener instanceof RedLayer) {
			final RedLayer layer = (RedLayer)listener;
			return this.processKeyboardDelivery(layer);
		}

		if (listener instanceof KeyboardInputEventListener) {
			final KeyboardInputEventListener keyboard_listener = (KeyboardInputEventListener)listener;
			return this.box.tryToDeliverTo(keyboard_listener);
		}

		Err.reportError("Unknown listener type: " + listener);
		return false;
	}

	private boolean try_to_deliver_mouse_event (final Object listener) {
		// L.d("try_to_deliver_mouse_event", listener);
		if (listener instanceof RedLayer) {
			final RedLayer layer = (RedLayer)listener;
			return this.processMouseDelivery(layer);
		}

		// if (listener instanceof LayerBasedComponent) {
		// final RedLayer layer = (RedLayer) listener;
		// return processMouseDelivery(layer);
		// }

		if (listener instanceof MouseInputEventListener) {
			final MouseInputEventListener mouse_listener = (MouseInputEventListener)listener;
			return this.box.tryToDeliverTo(mouse_listener);
		}

		Err.reportError("Unknown listener type: " + listener);
		return false;
	}

	private void putInDeliveryBox (final InputEvent input_event) {
		this.box.put(input_event);
	}

	public void markAllAllKeysReleased () {
		this.box.markAllAllKeysReleased();
	}

}
