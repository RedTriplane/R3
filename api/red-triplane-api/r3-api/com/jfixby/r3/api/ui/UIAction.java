
package com.jfixby.r3.api.ui;

public interface UIAction<T> {
	public void start (T ui);

	public void push (T ui);

	public boolean isDone (T ui);
}
