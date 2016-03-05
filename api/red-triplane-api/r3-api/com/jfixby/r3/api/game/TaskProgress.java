package com.jfixby.r3.api.game;

public interface TaskProgress {

	boolean isDone();

	public int getTotalSteps();

	public int getProcessedSteps();

	public float getProgressValue();

}
