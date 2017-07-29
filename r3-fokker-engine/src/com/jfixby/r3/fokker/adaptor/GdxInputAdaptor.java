
package com.jfixby.r3.fokker.adaptor;

import java.util.ArrayList;
import java.util.Iterator;

import com.jfixby.r3.engine.api.exe.InputEvent;
import com.jfixby.r3.engine.api.exe.InputQueue;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Pool;
import com.jfixby.scarabei.api.collections.PoolElementsSpawner;

public class GdxInputAdaptor implements com.badlogic.gdx.InputProcessor, InputQueue {

	private final GdxAdaptor master;
	private ArrayList<InputEvent> public_queue;
	private ArrayList<GdxInputEvent> private_queue;
	private Pool<GdxInputEvent> pool;
	boolean disabled = true;

	public void enable () {
		this.flush();
		this.disabled = false;
	}

	public void disable () {
		this.disabled = true;
		this.flush();
	}

	public void deploy () {
		this.public_queue = new ArrayList<InputEvent>();
		this.private_queue = new ArrayList<GdxInputEvent>();
		this.pool = Collections.newPool(this.spawner);
		this.enable();
	}

	public GdxInputAdaptor (final GdxAdaptor gdxAdaptor) {
		this.master = gdxAdaptor;
	}

	@Override
	public boolean keyDown (final int keycode) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyDown(GdxKeys.resolveGdxKeyCode(keycode));
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean keyUp (final int keycode) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyUp(GdxKeys.resolveGdxKeyCode(keycode));
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean keyTyped (final char character) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.keyTyped(character);
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean touchDown (final int screenX, final int screenY, final int pointer, final int button) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchDown(screenX, screenY, pointer, GdxMouseButtons.resolveGdxMouseButtonCode(button));
		this.enqueue(new_event);
		return true;

	}

	@Override
	public boolean touchUp (final int screenX, final int screenY, final int pointer, final int button) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchUp(screenX, screenY, pointer, GdxMouseButtons.resolveGdxMouseButtonCode(button));
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean touchDragged (final int screenX, final int screenY, final int pointer) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.touchDragged(screenX, screenY, pointer);
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean mouseMoved (final int screenX, final int screenY) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.mouseMoved(screenX, screenY);
		this.enqueue(new_event);
		return true;
	}

	@Override
	public boolean scrolled (final int amount) {
		if (this.disabled) {
			return false;
		}
		final GdxInputEvent new_event = this.pool.obtain();
		new_event.reset();
		new_event.scrolled(amount);
		this.enqueue(new_event);
		return true;
	}

	final PoolElementsSpawner<GdxInputEvent> spawner = new PoolElementsSpawner<GdxInputEvent>() {

		@Override
		public GdxInputEvent spawnNewInstance () {
			return new GdxInputEvent();
		}

	};

	private void enqueue (final GdxInputEvent new_event) {
		this.private_queue.add(new_event);
		this.public_queue.add(new_event);
	}

	int i;

	public void flush () {
		for (this.i = 0; this.i < this.private_queue.size(); this.i++) {
			final GdxInputEvent t = this.private_queue.get(this.i);
			this.pool.free(t);
		}
		// this.pool.freeAll(this.private_queue);
		this.public_queue.clear();
		this.private_queue.clear();
	}

	@Override
	public int size () {
		return this.public_queue.size();
	}

	@Override
	public Iterator<InputEvent> getIterator () {
		return this.public_queue.iterator();
	}

	@Override
	public String toString () {
		return "GdxInputAdaptor [size()=" + this.size() + "]";
	}

}
