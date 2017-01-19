package com.jfixby.r3.api.logic;

import com.jfixby.scarabei.api.taskman.TaskProgress;

public interface ProgressListener {

	void onLoaderBegin();

	void onUpdateProgress(TaskProgress task_progress);

	void onLoaderEnd();

	boolean isDoneListening();

}
