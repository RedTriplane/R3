
package com.jfixby.r3.activity.red.layers;

import java.util.ArrayList;

import com.jfixby.scarabei.api.collections.Collection;
import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.log.L;

public final class FastList<T> {

	final ArrayList<T> legacy = new ArrayList<>();

	final public void removeAll (final Collection<?> components) {
		this.legacy.removeAll(components.toJavaList());
	}

	final public boolean contains (final Object component) {
		return this.legacy.contains(component);
	}

	final public void clear () {
		this.legacy.clear();
	}

	final public void remove (final Object component) {
		this.legacy.remove(component);
	}

	final public void add (final T component) {
		this.legacy.add(component);
	}

	final public int size () {
		return this.legacy.size();
	}

	final public T getElementAt (final int k) {
		return this.legacy.get(k);
	}

	final public Collection<T> toCollection () {
		L.e("Low performance call!");
		return Collections.newList(this.legacy);
	}

	public java.util.List<T> toJavaList () {
		final java.util.List<T> result = new ArrayList<>();
		result.addAll(this.legacy);
		return result;
	}

// public void print (final String tag) {
// this.toCollection().print(tag);
// }

}
