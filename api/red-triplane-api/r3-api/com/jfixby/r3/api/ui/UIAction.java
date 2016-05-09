
package com.jfixby.r3.api.ui;

public interface UIAction<T> {
	public void start (T ui);

	public void perform (T ui);

	public boolean isDone (T ui);
}
