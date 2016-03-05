package com.jfixby.r3.api.game;

public interface ProgressListener {

	void onLoaderBegin();

	void onUpdateProgress(TaskProgress task_progress);

	void onLoaderEnd();

	boolean isDoneListening();

}
