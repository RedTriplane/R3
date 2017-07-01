
package com.jfixby.r3.ui;

import com.jfixby.r3.uiact.LoadTask;
import com.jfixby.r3.uiact.ProgressListener;
import com.jfixby.r3.uiact.UILoaderListener;
import com.jfixby.scarabei.api.taskman.TaskProgress;
import com.jfixby.scarabei.api.util.JUtils;
import com.jfixby.scarabei.api.util.StateSwitcher;

public class LoadTaskEvent extends UIEvent {

	private LoadTask task;
	private UILoaderListener ui_loader_listener;

	private ProgressListener progress_listener;
	private TaskProgress task_progress;
	private StateSwitcher<TASK_LOADER_STATE> state;
	private RedUIManager tintoUIManager;

	public LoadTaskEvent (LoadTask task, UILoaderListener ui_loader_listener, RedUIManager tintoUIManager) {
		this.task = task;
		this.ui_loader_listener = ui_loader_listener;
		this.tintoUIManager = tintoUIManager;
		state = JUtils.newStateSwitcher(TASK_LOADER_STATE.NEW);
	}

	@Override
	public void go () {
		state.expectState(TASK_LOADER_STATE.NEW);
		progress_listener = tintoUIManager.getLoader().getProgressListener();
		progress_listener.onLoaderBegin();
		task_progress = task.getProgress();
		progress_listener.onUpdateProgress(task_progress);
		state.switchState(TASK_LOADER_STATE.PROCESSING);
		task.launch();
	}

	@Override
	public boolean isOver () {
		state.doesNotExpectState(TASK_LOADER_STATE.NEW);
		if (state.currentState() == TASK_LOADER_STATE.PROCESSING) {
			progress_listener.onUpdateProgress(task_progress);
			if (task_progress.isDone()) {
				progress_listener.onLoaderEnd();
				state.switchState(TASK_LOADER_STATE.CLOSING);
			}
		}

		if (state.currentState() == TASK_LOADER_STATE.CLOSING) {
			if (progress_listener.isDoneListening()) {
				ui_loader_listener.onUILoaderDone();
				return true;
			}
		}

		return false;
	}
}
