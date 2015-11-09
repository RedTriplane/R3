package com.jfixby.r3.fokker.input;

import java.util.Vector;

import com.jfixby.cmns.api.input.Key;
import com.jfixby.cmns.api.input.KeysList;

public class FokkerKeysList implements KeysList {

	final private Vector<Key> list = new Vector<Key>();

	public void add(final Key keyCode) {
		list.add(keyCode);
	}

	@Override
	public int size() {
		return this.list.size();
	}

	@Override
	public Key get(int i) {
		return this.list.get(i);
	}

}
