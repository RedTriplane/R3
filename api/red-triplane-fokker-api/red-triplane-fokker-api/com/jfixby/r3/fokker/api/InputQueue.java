package com.jfixby.r3.fokker.api;

import java.util.Iterator;

public interface InputQueue {

	int size();

	Iterator<InputEvent> getIterator();

}
