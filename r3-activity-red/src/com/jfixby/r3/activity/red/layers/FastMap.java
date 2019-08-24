
package com.jfixby.r3.activity.red.layers;

import java.util.ArrayList;
import java.util.LinkedList;

import com.jfixby.scarabei.api.collections.Collections;
import com.jfixby.scarabei.api.collections.Pool;
import com.jfixby.scarabei.api.collections.PoolElementsSpawner;

public class FastMap<K, V> {

	private final PoolElementsSpawner<Pair<K, V>> pairSpawner = new PoolElementsSpawner<Pair<K, V>>() {

		@Override
		public Pair<K, V> spawnNewInstance () {
			return new Pair<>();
		}

	};
	private final Pool<Pair<K, V>> pairPool;

	public FastMap () {
		this.pairPool = Collections.newPool(this.pairSpawner);
		this.ensureCapacity(16);
	}

	private void ensureCapacity (final int targetSize) {
		this.hashTable.ensureCapacity(targetSize);
		while (this.hashTable.size() < targetSize) {
			this.hashTable.add(new Bucket<K, V>());
		}
	}

	ArrayList<Bucket<K, V>> hashTable = new ArrayList<>(50);

	static class Bucket<K, V> {
		final LinkedList<Pair<K, V>> array = new LinkedList<>();
	}

	static class Pair<K, V> {
		K key;
		V value;

		@Override
		public int hashCode () {
			final int prime = 31;
			int result = 1;
			result = prime * result + ((this.key == null) ? 0 : this.key.hashCode());
			return result;
		}

		@Override
		public boolean equals (final Object obj) {
			if (this == obj) {
				return true;
			}
			if (obj == null) {
				return false;
			}
			if (this.getClass() != obj.getClass()) {
				return false;
			}
			final Pair other = (Pair)obj;
			if (this.key == null) {
				if (other.key != null) {
					return false;
				}
			} else if (!this.key.equals(other.key)) {
				return false;
			}
			return true;
		}

	}

	int size = 0;

	public void put (final K key, final V value) {
		if (!this.containsKey(key)) {
			this.size++;
		}
		final Bucket<K, V> bucket = put(key, value, this.hashTable, this.pairPool);
		if (this.hashTable.size() * 1f / this.size < 0.75) {
			this.rehash();
		}
	}

	public void get (final K key, final V value) {

	}

	private void rehash () {
		final int targetCapacity = this.hashTable.size() * 2;
		final ArrayList<Bucket<K, V>> oldtable = this.hashTable;
		this.hashTable = new ArrayList<>(targetCapacity);
		this.ensureCapacity(targetCapacity);
		for (final Bucket<K, V> b : oldtable) {
			for (final Pair<K, V> p : b.array) {
				this.put(p.key, p.value);
				this.pairPool.free(p);
			}
		}
	}

	public boolean containsKey (final K key) {
		final Bucket<K, V> bucket = findBucket(key, this.hashTable);
		final Pair<K, V> pair = this.findKey(bucket, key);
		return pair != null;
	}

	private Pair<K, V> findKey (final Bucket<K, V> bucket, final K key) {
		for (final Pair<K, V> e : bucket.array) {
			if (e.key.equals(key)) {
				return e;
			}
		}
		return null;
	}

	static final private <K, V> Bucket<K, V> put (final K key, final V value, final ArrayList<Bucket<K, V>> hashTable,
		final Pool<Pair<K, V>> pairPool) {
		final Bucket<K, V> bucket = findBucket(key, hashTable);
		final Pair<K, V> p = pairPool.obtain();
		p.key = key;
		p.value = value;
		bucket.array.add(p);
		return bucket;
	}

	private static <K, V> Bucket<K, V> findBucket (final K key, final ArrayList<Bucket<K, V>> hashTable) {
		final int index = indexOf(key.hashCode(), hashTable.size());
		final Bucket<K, V> bucket = hashTable.get(index);
		return bucket;
	}

	static final private int indexOf (final int hashCode, final int N) {
		int code = hashCode;
		if (code < 0) {
			code = -code;
		}
		final int index = code % N;
		return index;
	}

}
