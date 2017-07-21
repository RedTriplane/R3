
package com.jfixby.r3.activity.api.act;

public interface UIAction<T> {
	public void start (T ui);

	public void push (T ui);

	public boolean isDone (T ui);
}
