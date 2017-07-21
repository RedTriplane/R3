
package com.jfixby.r3.api.activity.act;

public interface UIAction<T> {
	public void start (T ui);

	public void push (T ui);

	public boolean isDone (T ui);
}
