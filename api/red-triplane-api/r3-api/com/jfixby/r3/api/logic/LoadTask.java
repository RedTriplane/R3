package com.jfixby.r3.api.logic;

import com.jfixby.scarabei.api.taskman.TaskProgress;

public interface LoadTask {

	TaskProgress getProgress();

	void launch();

}
