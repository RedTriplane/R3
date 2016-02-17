package com.jfixby.r3.fokker.gwt.collections;

import java.util.ArrayList;
import java.util.Iterator;

import com.jfixby.cmns.api.collections.Collection;
import com.jfixby.cmns.api.collections.Collections;
import com.jfixby.cmns.api.collections.List;
import com.jfixby.cmns.api.collections.Pool;
import com.jfixby.cmns.api.collections.PoolElementsSpawner;

public class GwtPool<T> implements Pool<T> {

	final private ArrayList<T> legacy = new ArrayList<T>();

	private PoolElementsSpawner<T> spawner;

	public GwtPool(PoolElementsSpawner<T> spawner) {
		this.spawner = spawner;
	}

	int in_air = 0;

	@Override
	public T obtain() {
		in_air++;
		if (legacy.size() == 0) {
			T instance = spawner.spawnNewInstance();
			return instance;
		} else {
			T last = legacy.remove(legacy.size() - 1);
			return last;
		}
	}

	@Override
	public void free(T instance) {
		in_air--;
		legacy.add(instance);
	}

	@Override
	public void freeAll(Collection<T> collection) {
		for (int i = 0; i < collection.size(); i++) {
			T t = collection.getElementAt(i);
			this.free(t);
		}
	}


}
